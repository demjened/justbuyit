package com.justbuyit.exception;

/**
 * Exception for a case when an account is not found.
 */
public class AccountNotFoundException extends JustBuyItException {

    private static final long serialVersionUID = 4385364720383480857L;

    public AccountNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountNotFoundException(String message) {
        super(message);
    }

    public AccountNotFoundException(Throwable cause) {
        super(cause);
    }
    
    @Override
    public String getErrorCode() {
        return "ACCOUNT_NOT_FOUND";
    }

}
