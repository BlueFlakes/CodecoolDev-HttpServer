package com.codecooldev.functionalities.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServerProvider {

    public final static int PORT = 9999;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            ExecutorService executorService = Executors.newCachedThreadPool();
            while (true) {
                try {
                    Socket connection = server.accept();
                    EchoReceiver task = new EchoReceiver(connection);
                    executorService.execute(task);
                } catch (IOException ex) {}
            }
        } catch (IOException ex) {
            System.err.println("Couldn't start server");
        }

    }

    private static class EchoReceiver implements Runnable {
        private Socket connection;

        EchoReceiver(Socket connection) {
            this.connection = connection;
        }

        @Override
        public void run() {
            try {
                System.out.println("-------------------------------------------------------");
                Socket client = connection;
                String clientAddress = client.getInetAddress().getHostAddress();
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(client.getInputStream()));

                String data;
                while ( (data = in.readLine()) != null ) {
                    System.out.println("\r\nMessage from " + clientAddress + ": " + data);
                }
                System.out.println("-------------------------------------------------------");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
