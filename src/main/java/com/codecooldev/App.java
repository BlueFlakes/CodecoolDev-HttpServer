package com.codecooldev;

import com.codecooldev.functionalities.httpserver.HttpServer;
import com.codecooldev.functionalities.httpserver.MyHandler;

import java.io.IOException;

/**
 * Hello world!
 *
 */

public class App {
    public static void main(String[] args) {
        try {
            HttpServer httpServer = HttpServer.create(9999);
            httpServer.setRoute("/", new MyHandler());
            httpServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
