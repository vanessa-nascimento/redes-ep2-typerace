package br.usp.each.typerace.client;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
public class Client extends WebSocketClient {

    public Client(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("\nVocê entrou. Conectado ao servidor.");
    }

    @Override
    public void onMessage(String message) {
        if(message.length() > 0){
            System.out.println(message);
        } else {
            System.out.println("Digite a palavra: " + message);
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        if (code == 4000) {
            System.out.println("Sessão finalizada. Motivo: " + reason);
            System.exit(0);
        }
        else {
            System.out.println("\nAté logo!");
            System.exit(0);
        }
    }

    @Override
    public void onError(Exception ex) {
        System.out.println(ex);
    }
}
