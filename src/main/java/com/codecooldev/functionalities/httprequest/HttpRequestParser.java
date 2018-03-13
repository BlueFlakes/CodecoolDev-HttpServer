package com.codecooldev.functionalities.httprequest;

import java.io.*;
import java.util.Hashtable;

/**
     * Class for HTTP request parsing as defined by RFC 2612:
     *
     * Request = Request-Line ; Section 5.1 (( general-header ; Section 4.5 |
     * request-header ; Section 5.3 | entity-header ) CRLF) ; Section 7.1 CRLF [
     * message-body ] ; Section 4.3
     *
     * @author izelaya
     *
     */
public class HttpRequestParser {

    private String requestLine;
    private Hashtable<String, String> requestHeaders;
    private StringBuffer messageBody;

    public HttpRequestParser() {
        this.requestHeaders = new Hashtable<String, String>();
        this.messageBody = new StringBuffer();
    }

    /**
     * Parse and HTTP request.
     *
     * @param request
     *            String holding http request.
     * @throws IOException
     *             If an I/O error occurs reading the input stream.
     * @throws HttpFormatException
     *             If HTTP Request is malformed
     */
    public void parseRequest(InputStream clientRequest) throws IOException, HttpFormatException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(clientRequest));

        setRequestLine(reader.readLine()); // Request-Line ; Section 5.1

        String header = reader.readLine();
        while (header.length() > 0) {
            appendHeaderParameter(header);
            header = reader.readLine();
        }

        String bodyLine = reader.readLine();
        while (bodyLine != null) {
            appendMessageBody(bodyLine);
            bodyLine = reader.readLine();
        }
    }

    /**
     *
     * 5.1 Request-Line The Request-Line begins with a method token, followed by
     * the Request-URI and the protocol version, and ending with CRLF. The
     * elements are separated by SP characters. No CR or LF is allowed except in
     * the final CRLF sequence.
     *
     * @return String with Request-Line
     */
    public String getRequestLine() {
        return this.requestLine;
    }

    private void setRequestLine(String requestLine) throws HttpFormatException {
        if (requestLine == null || requestLine.length() == 0) {
            throw new HttpFormatException("Invalid Request-Line: " + requestLine);
        }
        this.requestLine = requestLine;
    }

    private void appendHeaderParameter(String header) throws HttpFormatException {
        int idx = header.indexOf(":");
        if (idx == -1) {
            throw new HttpFormatException("Invalid Header Parameter: " + header);
        }
        this.requestHeaders.put(header.substring(0, idx), header.substring(idx + 1, header.length()));
    }

    /**
     * The message-body (if any) of an HTTP message is used to carry the
     * entity-body associated with the request or response. The message-body
     * differs from the entity-body only when a transfer-coding has been
     * applied, as indicated by the Transfer-Encoding header field (section
     * 14.41).
     * @return String with message-body
     */
    public String getMessageBody() {
        return this.messageBody.toString();
    }

    private void appendMessageBody(String bodyLine) {
        this.messageBody.append(bodyLine).append("\r\n");
    }

    /**
     * For list of available headers refer to sections: 4.5, 5.3, 7.1 of RFC 2616
     * @param headerName Name of header
     * @return String with the value of the header or null if not found.
     */
    public String getHeaderParam(String headerName){
        return this.requestHeaders.get(headerName);
    }
}

