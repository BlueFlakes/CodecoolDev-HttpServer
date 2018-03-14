package com.codecooldev.functionalities.response;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class HttpResponseSenderService {
    public void sendResponse(OutputStream clientOutputStream, ByteArrayOutputStream response) {
        try (PrintWriter out = new PrintWriter(clientOutputStream)) {
            out.print(response);
            out.flush();
        }
    }
}
