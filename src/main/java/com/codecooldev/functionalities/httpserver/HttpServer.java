package com.codecooldev.functionalities.httpserver;

import java.io.IOException;
import java.net.InetAddress;

public class HttpServer {
    private InetAddress inetAddress;
    private Integer port;

    private HttpServer() {}

    public static HttpServer create(String ipAddress, Integer port)
            throws IOException {

        HttpServer httpServer = new HttpServer();
        httpServer.setIpAddress(InetAddress.getByName(ipAddress));
        httpServer.setPort(port);
        return httpServer;
    }

    // TODO : add port validation (is empty)

    public static HttpServer create(Integer port) throws IOException {
        HttpServer httpServer = new HttpServer();
        httpServer.setIpAddress(InetAddress.getLocalHost());
        httpServer.setPort(port);
        return httpServer;
    }

    private void setIpAddress(InetAddress ipAddress) {
        this.inetAddress = ipAddress;
    }

    private void setPort(Integer port) {
        this.port = port;
    }
}
