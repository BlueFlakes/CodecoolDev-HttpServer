package com.codecooldev.functionalities.response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

class ResponseBody {
    private ByteArrayOutputStream bodyStream = new ByteArrayOutputStream();

    void writeToBody(byte[] bytes) throws IOException {
        this.bodyStream.write(bytes);
    }

    ByteArrayOutputStream getBodyStream( ) {
        return bodyStream;
    }

    int size( ) {
        return this.bodyStream.size();
    }
}
