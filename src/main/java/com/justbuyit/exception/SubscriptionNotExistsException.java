package com.justbuyit.exception;

public class SubscriptionNotExistsException extends JustBuyItException {

    private static final long serialVersionUID = 4385364720383480857L;

    public SubscriptionNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public SubscriptionNotExistsException(String message) {
        super(message);
    }

    public SubscriptionNotExistsException(Throwable cause) {
        super(cause);
    }
    
    @Override
    public String getErrorCode() {
        return "ACCOUNT_NOT_FOUND";
    }

}
