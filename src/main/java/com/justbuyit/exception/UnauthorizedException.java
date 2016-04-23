package com.justbuyit.exception;

/**
 * Exception for a case when the user is unauthorized, eg. the OAuth credentials are not valid.
 */
public class UnauthorizedException extends JustBuyItException {

    private static final long serialVersionUID = -7343985018344224032L;

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(Throwable cause) {
        super(cause);
    }
    
    @Override
    public String getErrorCode() {
        return "UNAUTHORIZED";
    }
    
}
