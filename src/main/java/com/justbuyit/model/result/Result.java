package com.justbuyit.model.result;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Result of an AppDirect-initiated service call. Contains details in the format expected by AppDirect (success flag, error code, message, account ID).
 */
@XmlRootElement
public class Result {

    private boolean success;
    private String message;
    private String accountIdentifier;
    private String errorCode;

    public Result() {
    }

    /**
     * Creates a Result of a successful operation with the given message.
     * 
     * @param message
     *            the message
     * @return the result object
     */
    public static Result successResult(String message) {
        Result result = new Result();
        result.setSuccess(true);
        result.setMessage(message);
        return result;
    }

    /**
     * Creates a Result of a successful operation with the given message and account identifier.
     * 
     * @param message
     *            the message
     * @param accountIdentifier
     *            the account identifier
     * @return the result object
     */
    public static Result successResult(String message, String accountIdentifier) {
        Result result = Result.successResult(message);
        result.setAccountIdentifier(accountIdentifier);
        return result;
    }

    /**
     * Creates a Result of a failed operation with the given error code and message.
     * 
     * @param errorCode
     *            the error code
     * @param message
     *            the message
     * @return the result object
     */
    public static Result errorResult(String errorCode, String message) {
        Result result = new Result();
        result.setSuccess(false);
        result.setErrorCode(errorCode);
        result.setMessage(message);
        return result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAccountIdentifier() {
        return accountIdentifier;
    }

    public void setAccountIdentifier(String accountIdentifier) {
        this.accountIdentifier = accountIdentifier;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

}
