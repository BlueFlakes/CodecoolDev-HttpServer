package com.codecooldev.functionalities.response;

import com.codecooldev.baseclasses.ApplicationException;

public class ResponseCreatorException extends ApplicationException {
    public ResponseCreatorException( ) {
    }

    public ResponseCreatorException(String message) {
        super(message);
    }

    public ResponseCreatorException(Throwable cause) {
        super(cause);
    }

    public ResponseCreatorException(String message, Throwable cause) {
        super(message, cause);
    }
}
