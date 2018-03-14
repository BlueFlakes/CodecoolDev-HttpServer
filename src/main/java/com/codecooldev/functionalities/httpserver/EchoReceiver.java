package com.codecooldev.functionalities.httpserver;

import com.codecooldev.functionalities.httprequest.HttpRequest;
import com.codecooldev.functionalities.httprequest.HttpRequestParserService;
import com.codecooldev.functionalities.response.HttpResponse;
import java.net.Socket;
import java.util.Map;

public class EchoReceiver implements Runnable {
    private Socket connection;
    private Map<String, HttpHandler> handlers;

    EchoReceiver(Socket connection, Map<String, HttpHandler> handlers) {
        this.connection = connection;
        this.handlers = handlers;
    }

    @Override
    public void run() {
        try (Socket client = connection) {
            client.setSoTimeout(100);

            System.out.println("-------------------------------------------------------");
            HttpRequestParserService service = new HttpRequestParserService();
            HttpRequest request = service.parse(client.getInputStream());
            HttpResponse response = new  HttpResponse(client.getOutputStream());
            HttpHandler handler = handlers.get(request.getURI());
            if(handler == null) {
                new NoResourceHandler().handle(request,response);
            } else handler.handle(request,response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}