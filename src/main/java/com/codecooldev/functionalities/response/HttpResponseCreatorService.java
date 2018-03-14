package com.codecooldev.functionalities.response;

import java.io.OutputStream;
import java.io.PrintWriter;

public class HttpResponseCreatorService {
    private OutputStream os;
    private HttpResponse response;

    public HttpResponseCreatorService(OutputStream os, HttpResponse response) {
        this.os = os;
        this.response = response;
    }

    // TODO : separate sending and response creation

    public void sendResponse() {
        try (PrintWriter out = new PrintWriter(os)) {
            StringBuilder responseBuilder = ResponseGenerator.create()
                     .addHeader("HTTP/1.0 %s %s", response.getStatusCode(), response.getStatusInfo())
                         .addProperty("Date", "%s", response.getDate())
                         .addProperty("Server", "%s", response.getServer())
                         .addProperty("Content-Type", "%s", response.getContentType())
                         .addProperty("Content-Length", "%s", response.getContentLen())
                         .addProperty("Content-Type", "%s; %s", response.getContentType(), response.getCoding())
                         .finishAddingProperties()
                             .addBody(response.getBody())
                                .getResponse();

            out.print(responseBuilder.toString());
            out.flush();
        }
    }
}
