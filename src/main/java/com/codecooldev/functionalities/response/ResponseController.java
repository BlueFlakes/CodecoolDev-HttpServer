package com.codecooldev.functionalities.response;

import java.io.OutputStream;

public class ResponseController {
    private HttpResponseSenderService httpResponseSenderService;

    public ResponseController(HttpResponseSenderService httpResponseSenderService) {
        this.httpResponseSenderService = httpResponseSenderService;
    }

    public void send(HttpResponse httpResponse, OutputStream clientOutputStream)
            throws ResponseCreatorException {

        this.httpResponseSenderService.sendResponse(clientOutputStream, httpResponse.createResponse());
    }
}
