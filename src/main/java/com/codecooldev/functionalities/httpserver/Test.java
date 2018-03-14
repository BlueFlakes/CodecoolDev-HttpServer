package com.codecooldev.functionalities.httpserver;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException{
        HttpServer server = HttpServer.create(9999);
        server.setRoute("/", new MyHandler());
        server.start();
    }
}
