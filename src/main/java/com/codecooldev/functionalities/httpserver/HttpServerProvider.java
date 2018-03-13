package com.codecooldev.functionalities.httpserver;

import com.codecooldev.functionalities.httprequest.HttpRequest;
import com.codecooldev.functionalities.httprequest.HttpRequestService;
import com.codecooldev.functionalities.response.HttpResponse;
import com.codecooldev.functionalities.response.HttpResponseService;
import com.sun.org.apache.xpath.internal.SourceTree;

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
            try (Socket client = connection) {
                System.out.println("-------------------------------------------------------");
                HttpRequestService service = new HttpRequestService();
                HttpRequest request = service.parse(client.getInputStream());
                System.out.println(request.getMethod() + "\n" + request.getURI());
                HttpResponse response = new  HttpResponse();
                HttpResponseService rs = new HttpResponseService(client.getOutputStream(), response);
                rs.sendResponse();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
