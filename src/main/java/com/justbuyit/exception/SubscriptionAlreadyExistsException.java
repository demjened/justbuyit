package com.justbuyit.exception;

public class SubscriptionAlreadyExistsException extends JustBuyItException {

    private static final long serialVersionUID = 9066148248213724896L;

    public SubscriptionAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public SubscriptionAlreadyExistsException(String message) {
        super(message);
    }

    public SubscriptionAlreadyExistsException(Throwable cause) {
        super(cause);
    }
    
    @Override
    public String getErrorCode() {
        return "INVALID_RESPONSE";
    }

}
