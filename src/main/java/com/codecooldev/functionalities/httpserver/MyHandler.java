package com.codecooldev.functionalities.httpserver;

import com.codecooldev.functionalities.httprequest.HttpRequest;
import com.codecooldev.functionalities.response.HttpResponse;
import com.codecooldev.functionalities.response.ResponseCreatorException;

import java.io.IOException;

public class MyHandler implements HttpHandler {

    @Override
    public void handle(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, ResponseCreatorException {
        httpResponse.sendResponse();
    }
}
