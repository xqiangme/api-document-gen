package com.tool.api.generate.core.exception;

import java.text.MessageFormat;

/**
 * 基础异常
 *
 * @author mengqiang
 */
public class ApiBaseException extends RuntimeException {
    protected String msg;

    protected ApiBaseException(String message) {
        super(message);
    }

    protected ApiBaseException(String msgFormat, Object... args) {
        super(MessageFormat.format(msgFormat, args));
        this.msg = MessageFormat.format(msgFormat, args);
    }

    public String getMsg() {
        return this.msg;
    }

}