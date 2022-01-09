package br.usp.each.typerace.server;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.Map;

public class Server extends WebSocketServer {

    private final Map<String, WebSocket> connections;

    public Server(int port, Map<String, WebSocket> connections) {
        super(new InetSocketAddress(port));
        this.connections = connections;
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {

        if(idValidator(conn)){
            System.out.println("Valid");
            String username = playerId(conn);
            connections.put(username, conn);
            conn.send("Welcome " + username);
        }
    }

    private String playerId(WebSocket conn) {
        String connInfos = conn.getResourceDescriptor();
        return connInfos.substring(connInfos.indexOf("username=") + 9);
    }

    private boolean idValidator(WebSocket conn) {
        if(connections.containsKey(playerId(conn))) {
            conn.send("Username already taken\n");
            conn.close(1000, "invalidName");
            return false;
        }

        return true;
    }
    

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        // TODO: Implementar
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        // TODO: Implementar
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        // TODO: Implementar
    }

    @Override
    public void onStart() {
        // TODO: Implementar
    }
}
