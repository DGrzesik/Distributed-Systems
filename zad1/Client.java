package org.tcpchat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {

    private static Runnable handleReceivedMessages(BufferedReader in) {
        return () -> {
            try {
                while (true) {
                    String[] message = in.readLine().trim().split(":",2);
                    if (message.length==2){
                        System.out.printf("Received message from [%s]: \"%s\"\n", message[0],message[1]);
                    }
                    else{
                        System.out.printf("Received message from [%s]: \"\"\n", message[0]);
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }


    public static void main(String[] args) {
//         wątek, w którym klient będzie oczekiwał na nadchodzące wiadomości
        ExecutorService executor = Executors.newSingleThreadExecutor();
        System.out.println("[CLIENT LAUNCHED]");
        String hostName = "localhost";
        int portNumber = 12345;

        try (Socket socket = new Socket(hostName, portNumber)) {

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Scanner stdin = new Scanner(System.in);
            System.out.print("Enter your name: ");
//            id clientów są ich nazwy
            String id = stdin.nextLine();
            System.out.println("[CONNECTING CLIENT]");
            out.println(id);
            executor.execute(handleReceivedMessages(in));

            while (true) {
                String message = stdin.nextLine();
                out.println(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}