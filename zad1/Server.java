package org.tcpchat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
    private static final List<ClientService> clients = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {
        // zbiór wątków, w których później wywołuję klasę obsługującą komunikację między klientami
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

        System.out.println("[SERVER LAUNCHED]\n");
        int portNumber = 12345;

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientService clientService = new ClientService(clientSocket, clients);
                executor.execute(clientService);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
