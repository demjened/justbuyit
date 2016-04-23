package com.justbuyit.exception;

/**
 * Base application exception that contains an error code as per AppDirect specifications.
 */
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

    /**
     * Gets the error code that this exception represents.
     * 
     * @return the error code
     */
    public abstract String getErrorCode();

}
