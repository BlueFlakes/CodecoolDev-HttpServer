package com.codecooldev.functionalities.httprequest;

public class HttpRequest {
    private String method;
    private String URI;
    private String protocolVersion;

    public HttpRequest(String requestLine) throws RequestLineException {
        splitRequestLine(requestLine);
    }

    // TODO : MOVE Parsing from MODEL to more proper class

    private void splitRequestLine(String requestLine) throws RequestLineException {
        String[] splitedRequestLine = requestLine.split("\\s+");
        if (splitedRequestLine.length != 3) throw new RequestLineException(String.format("Wrong request line \n %s", requestLine));
        this.method = splitedRequestLine[0];
        this.URI = splitedRequestLine[1];
        this.protocolVersion = splitedRequestLine[2];
    }

    public String getMethod() {
        return method;
    }

    public String getURI() {
        return URI;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

}


