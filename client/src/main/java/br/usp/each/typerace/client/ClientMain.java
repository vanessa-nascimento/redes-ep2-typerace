package br.usp.each.typerace.client;

import java.util.Scanner;

import org.java_websocket.client.WebSocketClient;

import java.net.URI;
import java.net.URISyntaxException;

public class ClientMain {

    private static final Scanner input = new Scanner(System.in);

    private WebSocketClient client;

    public ClientMain(WebSocketClient client) {
        this.client = client;
    }

    public void init(String username) {
        System.out.println("Conectando usuário " + username + " ao jogo...");
        this.client.connect();
    }

    public static void main(String[] args) throws URISyntaxException {
        String urlServer = "ws://localhost:8080";
        String username;

        System.out.println("\nDigite o endereço do servidor (Aperte Enter caso deseje o servidor padrão ws://localhost:8080): ");
        String customServer = input.nextLine();

        if (!customServer.isEmpty()) {
            urlServer = customServer;
        }
       
        WebSocketClient client;

        try {
            
            System.out.println("Qual é o seu nome de usuário?");
            username = input.nextLine();

            if (username.isEmpty()) {
                System.out.println("Nome de suário vazio. Informe um nome válido.");
            }

            client = new Client(new URI(urlServer + "/username=" + username));

            ClientMain main = new ClientMain(client);
            main.init(username);
 
            while(true) {
                String text = input.nextLine();
                if(text.length() > 0) client.send(text);

                try {
                    Thread.sleep(500);
                }
                catch(Exception e) {
                    e.printStackTrace();
                } 

                if (client.isOpen()) {
                    break;
                }
            }

            input.nextLine();
            client.send("start");      

        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.out.print(e.getMessage());
        }
    }
}
