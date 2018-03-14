package com.codecooldev.functionalities.httpserver;

import com.codecooldev.functionalities.httprequest.HttpRequest;
import com.codecooldev.functionalities.response.HttpResponse;
import com.codecooldev.functionalities.response.HttpResponseContainer;
import com.codecooldev.functionalities.response.HttpResponseSenderService;
import com.codecooldev.functionalities.response.ResponseCreatorException;

import java.io.IOException;

public class NoResourceHandler implements HttpHandler {

    @Override
    public void handle(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, ResponseCreatorException {
        HttpResponseSenderService service = new HttpResponseSenderService();
        httpResponse.changeAtribute(HttpResponseContainer.AttrValue.STATUS_CODE, "404");
        httpResponse.changeAtribute(HttpResponseContainer.AttrValue.STATUS_INFO,"Not found");
        httpResponse.changeAtribute(HttpResponseContainer.AttrValue.BODY,"<html><body><h1>404 Not Found</h1></body></html>");
        httpResponse.sendResponse();
    }

}
