package com.codecooldev.functionalities.response;

import com.sun.org.apache.regexp.internal.RE;

import java.io.OutputStream;

public class HttpResponse {
    private HttpResponseContainer container;
    private HttpResponseSenderService service;
    private OutputStream os;

    public HttpResponse(OutputStream os) {
        this.os = os;
        this.container = new HttpResponseContainer();
        this.service = new HttpResponseSenderService();
    }

    public void sendResponse() throws ResponseCreatorException {
        service.sendResponse(os,container.createResponse());
    }

    public void changeAtribute(HttpResponseContainer.AttrValue atrr, String value ) {
        container.addKnownAttr(atrr, value);
    }

    public void addAttribute(HttpResponseContainer.AttrValue atrr, String value) {
        container.addKnownAttr(atrr, value);
    }
}

