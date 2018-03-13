package com.codecooldev.functionalities.httprequest;

import com.codecooldev.baseclasses.ApplicationException;

public class HttpFormatException extends ApplicationException {

    public HttpFormatException() {
    }

    public HttpFormatException(String message) {
        super(message);
    }

    public HttpFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
