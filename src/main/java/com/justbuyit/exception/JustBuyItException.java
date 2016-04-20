package com.justbuyit.exception;

public abstract class JustBuyItException extends Exception {

    private static final long serialVersionUID = -362057468366267433L;

    public JustBuyItException(String message, Throwable cause) {
        super(message, cause);
    }

    public JustBuyItException(String message) {
        super(message);
    }

    public JustBuyItException(Throwable cause) {
        super(cause);
    }

    public abstract String getErrorCode();
    
}
