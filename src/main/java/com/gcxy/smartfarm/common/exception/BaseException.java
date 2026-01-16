package com.gcxy.smartfarm.common.exception;

import com.gcxy.smartfarm.common.enums.ResultCode;
import lombok.Getter;

/**
 * BaseException 类
 *
 * @author NaNloopia
 * @version 1.0
 * @created 15:10
 * @since 2026/1/13
 */
@Getter
public class BaseException extends RuntimeException {

    private final Integer code;
    private final String message;

    /**
     * 使用通用业务错误码 (100601)，自定义错误信息
     */
    public BaseException(String message) {
        super(message);
        this.code = ResultCode.BUSINESS_ERROR.getCode();
        this.message = message;
    }

    /**
     * 指定状态码和错误信息
     */
    public BaseException(ResultCode resultCode) {
        super(resultCode.getDefaultMessage());
        this.code = resultCode.getCode();
        this.message = resultCode.getDefaultMessage();
    }

    /**
     * 最全的构造器
     */
    public BaseException(ResultCode resultCode, String message) {
        super(message);
        this.code = resultCode.getCode();
        this.message = message;
    }
}
