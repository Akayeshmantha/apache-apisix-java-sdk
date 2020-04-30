package com.apiseven.apisix.common.exception;

public class ApisixSDKExcetion extends Exception {
    private static final long serialVersionUID = 1L;

    private String errorCode;

    public ApisixSDKExcetion(String message) {
        this(message, "");
    }


    public ApisixSDKExcetion(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String toString() {
        return "[ApisixSDKExcetion]"
                + "message:"
                + this.getMessage()
                + " errorCode:"
                + this.getErrorCode();
    }
}