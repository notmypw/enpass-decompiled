package in.sinew.enpass.chromeconnector.webserver;

import in.sinew.enpass.chromeconnector.webserver.drafts.Draft;
import in.sinew.enpass.chromeconnector.webserver.exceptions.InvalidDataException;
import in.sinew.enpass.chromeconnector.webserver.exceptions.InvalidHandshakeException;
import in.sinew.enpass.chromeconnector.webserver.framing.Framedata;
import in.sinew.enpass.chromeconnector.webserver.framing.Framedata.Opcode;
import in.sinew.enpass.chromeconnector.webserver.framing.FramedataImpl1;
import in.sinew.enpass.chromeconnector.webserver.handshake.ClientHandshake;
import in.sinew.enpass.chromeconnector.webserver.handshake.HandshakeImpl1Server;
import in.sinew.enpass.chromeconnector.webserver.handshake.ServerHandshake;
import in.sinew.enpass.chromeconnector.webserver.handshake.ServerHandshakeBuilder;
import java.net.InetSocketAddress;

public abstract class WebSocketAdapter implements WebSocketListener {
    public ServerHandshakeBuilder onWebsocketHandshakeReceivedAsServer(WebSocket conn, Draft draft, ClientHandshake request) throws InvalidDataException {
        return new HandshakeImpl1Server();
    }

    public void onWebsocketHandshakeReceivedAsClient(WebSocket conn, ClientHandshake request, ServerHandshake response) throws InvalidDataException {
    }

    public void onWebsocketHandshakeSentAsClient(WebSocket conn, ClientHandshake request) throws InvalidDataException {
    }

    public void onWebsocketMessageFragment(WebSocket conn, Framedata frame) {
    }

    public void onWebsocketPing(WebSocket conn, Framedata f) {
        FramedataImpl1 resp = new FramedataImpl1(f);
        resp.setOptcode(Opcode.PONG);
        conn.sendFrame(resp);
    }

    public void onWebsocketPong(WebSocket conn, Framedata f) {
    }

    public String getFlashPolicy(WebSocket conn) throws InvalidDataException {
        InetSocketAddress adr = conn.getLocalSocketAddress();
        if (adr == null) {
            throw new InvalidHandshakeException("socket not bound");
        }
        StringBuffer sb = new StringBuffer(90);
        sb.append("<cross-domain-policy><allow-access-from domain=\"*\" to-ports=\"");
        sb.append(adr.getPort());
        sb.append("\" /></cross-domain-policy>\u0000");
        return sb.toString();
    }
}
