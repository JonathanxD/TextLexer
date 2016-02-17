package com.github.jonathanxd.textlexer.ext.parser.structure.exceptions;

/**
 * Created by jonathan on 17/02/16.
 */
public class EmptyCheckException extends RuntimeException {

    public EmptyCheckException() {
    }

    public EmptyCheckException(String message) {
        super(message);
    }

    public EmptyCheckException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyCheckException(Throwable cause) {
        super(cause);
    }

    public EmptyCheckException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
