package com.codecooldev.functionalities.httpserver;

import com.codecooldev.functionalities.httprequest.HttpRequest;
import com.codecooldev.functionalities.response.HttpResponse;

import java.io.IOException;

public class NoResourceHandler implements HttpHandler {

    @Override
    public void handle(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        httpResponse.setStatusCode(404);
        httpResponse.setStatusInfo("Not found");
        httpResponse.setBody("<html><body><h1>404 Not Found</h1></body></html>");
        httpResponse.sendResponse();
    }

}
