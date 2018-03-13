package com.codecooldev.functionalities.httprequest;

import java.io.IOException;
import java.io.InputStream;

public class HttpRequestService {

    public HttpRequest parse(InputStream request) throws IOException, HttpFormatException, RequestLineException {
        HttpRequestParser parser = new HttpRequestParser();
        parser.parseRequest(request);
        return new HttpRequest(parser.getRequestLine());
    }
}
