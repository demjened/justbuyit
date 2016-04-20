package com.justbuyit.exception;

public class UserAlreadyExistsException extends JustBuyItException {

    private static final long serialVersionUID = -4512533923245959097L;

    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public UserAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getErrorCode() {
        return "USER_ALREADY_EXISTS";
    }
    
}
