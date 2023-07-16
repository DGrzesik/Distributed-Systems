package org.tcpchat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ClientService implements Runnable {
    private final List<ClientService> clients;
    private final PrintWriter out;
    private final BufferedReader in;
    private String id;

    public ClientService(Socket clientSocket, List<ClientService> clients) throws IOException {
        this.clients = clients;
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            id = in.readLine().trim();
            System.out.println("[NEW CLIENT CONNECTED] id: " + id);
            clients.add(this);
            while (true) {
                String message = in.readLine().trim();
                System.out.printf("\n[%s] Sending new message to: ", id);
                for (ClientService client : clients) {
                    if (!client.id.equals(id)) {
                        System.out.printf("[%s] ", client.id);
                        client.out.println(String.format("%s:%s", id, message));
                    }
                }
                System.out.println("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}