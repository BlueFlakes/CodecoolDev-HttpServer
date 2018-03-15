package com.codecooldev.functionalities.response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HttpResponse {
    private HttpResponseContainer container;
    private ResponseBody responseBody;
    private HttpResponseSenderService service;
    private OutputStream os;

    public HttpResponse(OutputStream os) {
        this.os = os;
        this.container = new HttpResponseContainer();
        this.service = new HttpResponseSenderService();
        this.responseBody = new ResponseBody();
    }

    public void write(byte[] stream) throws IOException {
        this.responseBody.writeToBody(stream);
    }

    public void sendResponse() throws ResponseCreatorException {
        int bodySize = this.responseBody.size();
        changeAttribute(HttpResponseContainer.AttrValue.CONTENT_LENGTH, String.valueOf(bodySize));
        ByteArrayOutputStream bodyStream = this.responseBody.getBodyStream();

        service.sendResponse(os, this.container.createResponse(bodyStream));
    }

    public void changeAttribute(HttpResponseContainer.AttrValue attr, String value ) {
        container.addKnownAttr(attr, value);
    }

    public void addAttribute(HttpResponseContainer.AttrValue attr, String value) {
        container.addKnownAttr(attr, value);
    }
}

