package com.codecooldev.functionalities.httpserver;

import com.codecooldev.functionalities.httprequest.HttpRequest;
import com.codecooldev.functionalities.response.HttpResponse;
import com.codecooldev.functionalities.response.HttpResponseCreatorService;
import com.codecooldev.functionalities.response.HttpResponseService;


import java.io.IOException;

public interface HttpHandler {
    void handle(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;
}
