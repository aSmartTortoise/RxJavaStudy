package com.wyj.rxjava.network;

import java.io.IOException;

public class NoNetWorkException extends IOException {
    private int errorCode;
    private String errorMsg;

    public NoNetWorkException(HttpError error, Throwable throwable) {
        super(throwable);
        this.errorCode = error.getCode();
        this.errorMsg = error.getErrorMsg();
    }
}
