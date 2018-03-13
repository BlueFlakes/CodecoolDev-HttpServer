package com.codecooldev.functionalities.response;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class HttpResponseService {
    private OutputStream os;
    private HttpResponse response;
    private static final String CRLF = "\r\n";

    public HttpResponseService(OutputStream os, HttpResponse response) {
        this.os = os;
        this.response = response;
    }

    public void sendResponse() {

        try (PrintWriter out = new PrintWriter(os)) {
            out.print(String.format("HTTP/1.0 %d %s%s",response.getStatusCode(), response.getStatusInfo(), CRLF));
            out.print(String.format("Date: %s%s", response.getDate(), CRLF));
            out.print(String.format("Server: %s%s",response.getServer(), CRLF));
            out.print(String.format("Content-Type: %s%s", response.getContentType(), CRLF));
            out.print(String.format("Content-Length: %d%s", response.getContentLen(), CRLF));
            out.println(String.format("Content-Type: %s; %s%s",response.getContentType(),response.getCoding(),CRLF));
            out.println(response.getBody());
        }
    }
}
