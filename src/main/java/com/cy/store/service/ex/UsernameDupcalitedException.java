package com.cy.store.service.ex;
//用户名被占用的异常
public class UsernameDupcalitedException extends ServiceException{
    public UsernameDupcalitedException() {
        super();
    }

    public UsernameDupcalitedException(String message) {
        super(message);
    }

    public UsernameDupcalitedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameDupcalitedException(Throwable cause) {
        super(cause);
    }

    protected UsernameDupcalitedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
