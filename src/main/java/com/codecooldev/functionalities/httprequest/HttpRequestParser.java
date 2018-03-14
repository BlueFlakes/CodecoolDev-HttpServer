package com.codecooldev.functionalities.httprequest;

import java.io.*;
import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpRequestParser {
    private static final ExecutorService executors = Executors.newFixedThreadPool(5);
    private String requestLine;
    private Hashtable<String, String> requestHeaders;
    private StringBuffer messageBody;

    public HttpRequestParser() {
        this.requestHeaders = new Hashtable<>();
        this.messageBody = new StringBuffer();
    }

    /**
     * Parse and HTTP request.
     *
     *
     *            String holding http request.
     * @throws IOException
     *             If an I/O error occurs reading the input stream.
     * @throws HttpFormatException
     *             If HTTP Request is malformed
     */
    public void parseRequest(InputStream clientRequest) throws IOException, HttpFormatException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientRequest));

        setRequestLine(in.readLine()); // Request-Line ; Section 5.1

        String header = in.readLine();
        while (header.length() > 0) {
            appendHeaderParameter(header);
            header = in.readLine();
        }

        this.messageBody = readUntilTimeoutOccur(in);
    }

    private StringBuffer readUntilTimeoutOccur(BufferedReader bf) {
        try {
            InputReader inputReader = new InputReader(bf);
            executors.execute(inputReader);

            Thread.sleep(25);

            return inputReader.getBody();
        } catch (InterruptedException e) {}
        throw new IllegalStateException("This thread shouldn't get ever interrupted.");
    }

    private static class InputReader implements Runnable {
        private StringBuffer body;
        private BufferedReader in;

        private InputReader(BufferedReader bufferedReader) {
            this.in = bufferedReader;
        }

        StringBuffer getBody( ) {
            return body;
        }

        @Override
        public void run( ) {
            this.body = new StringBuffer();

            try {
                String bodyLine;
                while ((bodyLine = in.readLine()) != null) {
                    appendMessageBody(bodyLine);
                }
            } catch (IOException e) {
                System.out.println("Socket Timeout -> Closed by -> Socket Exception");
            }
        }

        private void appendMessageBody(String bodyLine) {
            this.body.append(bodyLine)
                     .append("\r\n");
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

    /**
     * For list of available headers refer to sections: 4.5, 5.3, 7.1 of RFC 2616
     * @param headerName Name of header
     * @return String with the value of the header or null if not found.
     */
    public String getHeaderParam(String headerName){
        return this.requestHeaders.get(headerName);
    }
}