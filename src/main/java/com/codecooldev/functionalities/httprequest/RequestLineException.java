package com.codecooldev.functionalities.httprequest;

import com.codecooldev.baseclasses.ApplicationException;

public class RequestLineException extends ApplicationException {

    public RequestLineException() {}

    public RequestLineException(String message) {
        super(message);
    }
    
    public RequestLineException(Throwable cause) {
        super(cause);
    }

    public RequestLineException(String message, Throwable cause) {
        super(message, cause);
    }
}
