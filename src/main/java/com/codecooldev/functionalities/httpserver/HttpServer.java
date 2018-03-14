package com.codecooldev.functionalities.httpserver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private int port;
    private InetAddress ipAddress;
    private Map<String,HttpHandler> handlers;

    private HttpServer() {
        this.handlers = new HashMap<>();
    }

    public static HttpServer create(Integer port) throws IOException {
        HttpServer httpServer = new HttpServer();
        httpServer.setIpAddress(InetAddress.getLocalHost());
        httpServer.setPort(port);
        return httpServer;
    }

    public void start() {

        try (ServerSocket server = new ServerSocket(this.port)) {
            ExecutorService executorService = Executors.newCachedThreadPool();
            while (true) {
                try {
                    Socket connection = server.accept();
                    EchoReceiver task = new EchoReceiver(connection, handlers);
                    executorService.execute(task);
                } catch (IOException ex) {}
            }
        } catch (IOException ex) {
            System.err.println("Couldn't start server");
        }
    }

    public void createContext(String path, HttpHandler handler) {
        this.handlers.put(path,handler);
    }

    private void setIpAddress(InetAddress ipAddress) {
        this.ipAddress = ipAddress;
    }

    private void setPort(Integer port) {
        if (port < 0 || port > 0xFFFF)
            throw new IllegalArgumentException(
                    "Port value out of range: " + port);
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }
}
