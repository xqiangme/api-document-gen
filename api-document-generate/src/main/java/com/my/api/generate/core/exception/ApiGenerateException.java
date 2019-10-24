package com.my.api.generate.core.exception;


/**
 * 公共异常
 *
 * @author mengqiang
 */
public class ApiGenerateException extends ApiBaseException {

    public ApiGenerateException(String message) {
        super(message);
    }

    public ApiGenerateException(String mess, Object... args) {
        super(mess, args);
    }

}