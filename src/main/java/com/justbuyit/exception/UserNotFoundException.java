package com.justbuyit.exception;

public class UserNotFoundException extends JustBuyItException {

    private static final long serialVersionUID = 2903949705182499181L;

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getErrorCode() {
        return "USER_NOT_FOUND";
    }
    
}
