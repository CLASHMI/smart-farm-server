package com.gcxy.smartfarm.common.exception;

/**
 * BaseException 类
 *
 * @author NaNloopia
 * @version 1.0
 * @created 15:10
 * @since 2026/1/13
 */
public class BaseException extends RuntimeException{
    public BaseException(String msg) {
        super(msg);
    }
    public BaseException() {
    }
}
