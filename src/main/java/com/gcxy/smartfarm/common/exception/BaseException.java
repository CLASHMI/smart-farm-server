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

    public BaseException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }
    public BaseException(String message) {
        super(message);
        this.code = ResultCode.BUSINESS_ERROR.getCode();
    }
}
