package in.sinew.enpass.chromeconnector;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import in.sinew.enpass.chromeconnector.webserver.WebSocket;
import in.sinew.enpass.chromeconnector.webserver.handshake.ClientHandshake;
import in.sinew.enpass.chromeconnector.webserver.server.WebSocketServer;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EnpassWebSocketServer extends WebSocketServer {
    String TAG = "EnpassWebSocketServer";
    Map<WebSocket, ChromeConnectionHandler> mConnectionMap = new HashMap();
    Context mContext;

    public EnpassWebSocketServer(InetSocketAddress address, Context context) {
        super(address);
        this.mContext = context;
    }

    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        ChromeConnectionHandler handler = new ChromeConnectionHandler(this.mContext, this);
        this.mConnectionMap.put(conn, handler);
        handler.startAuthProcess();
    }

    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        conn.close();
        ((ChromeConnectionHandler) this.mConnectionMap.get(conn)).deleteCredentials();
        this.mConnectionMap.remove(conn);
        this.mContext.stopService(new Intent(this.mContext, ChromeConnectorForegroundService.class));
    }

    public void onDisconnect() {
        for (Entry<WebSocket, ChromeConnectionHandler> entry : this.mConnectionMap.entrySet()) {
            ((WebSocket) entry.getKey()).close();
            ((ChromeConnectionHandler) entry.getValue()).deleteCredentials();
        }
        this.mConnectionMap = null;
    }

    public void onMessage(WebSocket conn, String message) {
        if (!TextUtils.isEmpty(message)) {
            if (isJSONValid(message)) {
                try {
                    JSONObject obj = new JSONObject(message);
                    if (obj.has("header") && obj.getString("header").equals("c_step2")) {
                        ((ChromeConnectionHandler) this.mConnectionMap.get(conn)).onMessage(conn, message);
                        return;
                    }
                    return;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }
            }
            ((ChromeConnectionHandler) this.mConnectionMap.get(conn)).messageFromClient(message);
        }
    }

    public void onMessage(WebSocket conn, ByteBuffer message) {
        super.onMessage(conn, message);
        ((ChromeConnectionHandler) this.mConnectionMap.get(conn)).onMessage(conn, message);
    }

    public void onError(WebSocket conn, Exception ex) {
        ChromeConnectionHandler handler = (ChromeConnectionHandler) this.mConnectionMap.get(conn);
        if (handler != null) {
            handler.onError(conn, ex);
        }
        if (ex.getMessage().contains("EADDRINUSE")) {
            LocalBroadcastManager.getInstance(this.mContext).sendBroadcast(new Intent("PORT_IN_USE_TRY_ANOTHER_PORT"));
        }
        ex.printStackTrace();
    }

    public void sendToAll(String text) {
        Collection<WebSocket> con = connections();
        synchronized (con) {
            for (WebSocket c : con) {
                c.send(text);
            }
        }
    }

    public boolean isJSONValid(String test) {
        try {
            JSONObject jSONObject = new JSONObject(test);
        } catch (JSONException e) {
            try {
                JSONArray jSONArray = new JSONArray(test);
            } catch (JSONException e2) {
                return false;
            }
        }
        return true;
    }

    public void sendEncryptMessageToConnectionHandler(String msg) {
        if (this.mConnectionMap != null) {
            for (Entry<WebSocket, ChromeConnectionHandler> entry : this.mConnectionMap.entrySet()) {
                ((ChromeConnectionHandler) entry.getValue()).sendEncryptMessage(msg);
            }
        }
    }
}
