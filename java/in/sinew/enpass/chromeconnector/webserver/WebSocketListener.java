package in.sinew.enpass.chromeconnector.webserver;

import in.sinew.enpass.chromeconnector.webserver.drafts.Draft;
import in.sinew.enpass.chromeconnector.webserver.exceptions.InvalidDataException;
import in.sinew.enpass.chromeconnector.webserver.framing.Framedata;
import in.sinew.enpass.chromeconnector.webserver.handshake.ClientHandshake;
import in.sinew.enpass.chromeconnector.webserver.handshake.Handshakedata;
import in.sinew.enpass.chromeconnector.webserver.handshake.ServerHandshake;
import in.sinew.enpass.chromeconnector.webserver.handshake.ServerHandshakeBuilder;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

public interface WebSocketListener {
    String getFlashPolicy(WebSocket webSocket) throws InvalidDataException;

    InetSocketAddress getLocalSocketAddress(WebSocket webSocket);

    InetSocketAddress getRemoteSocketAddress(WebSocket webSocket);

    void onWebsocketClose(WebSocket webSocket, int i, String str, boolean z);

    void onWebsocketCloseInitiated(WebSocket webSocket, int i, String str);

    void onWebsocketClosing(WebSocket webSocket, int i, String str, boolean z);

    void onWebsocketError(WebSocket webSocket, Exception exception);

    void onWebsocketHandshakeReceivedAsClient(WebSocket webSocket, ClientHandshake clientHandshake, ServerHandshake serverHandshake) throws InvalidDataException;

    ServerHandshakeBuilder onWebsocketHandshakeReceivedAsServer(WebSocket webSocket, Draft draft, ClientHandshake clientHandshake) throws InvalidDataException;

    void onWebsocketHandshakeSentAsClient(WebSocket webSocket, ClientHandshake clientHandshake) throws InvalidDataException;

    void onWebsocketMessage(WebSocket webSocket, String str);

    void onWebsocketMessage(WebSocket webSocket, ByteBuffer byteBuffer);

    void onWebsocketMessageFragment(WebSocket webSocket, Framedata framedata);

    void onWebsocketOpen(WebSocket webSocket, Handshakedata handshakedata);

    void onWebsocketPing(WebSocket webSocket, Framedata framedata);

    void onWebsocketPong(WebSocket webSocket, Framedata framedata);

    void onWriteDemand(WebSocket webSocket);
}
