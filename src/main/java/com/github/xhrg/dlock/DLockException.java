package com.github.xhrg.dlock;

public class DLockException extends Exception {

    private static final long serialVersionUID = 1L;

    public DLockException(String message, Throwable cause) {
        super(message, cause);
    }

}
