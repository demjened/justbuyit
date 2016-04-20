package com.justbuyit.model.result;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Result {

    private boolean success;
    private String message;
    private String errorCode;;

    public Result() {
    }
    
    public static Result successResult(String message) {
        Result result = new Result();
        result.setSuccess(true);
        result.setMessage(message);
        return result;
    }    

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

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

}
