package com.github.xhrg.dlock;

public class DlockException extends Exception {

    private static final long serialVersionUID = 1L;

    public DlockException(String message, Throwable cause) {
        super(message, cause);
    }

}
