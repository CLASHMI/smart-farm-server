package com.gcxy.smartfarm.common.result;

import com.gcxy.smartfarm.common.enums.ResultCode;
import lombok.Data;

import java.io.Serializable;

/**
 * Result 类
 *
 * @author NaNloopia
 * @version 1.0
 * @created 15:09
 * @since 2026/1/13
 */
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer code;
    private String message;
    private T data;

    /**
     * 无数据返回的成功
     */
    public static <T> Result<T> success() {
        return success(null);
    }

    /**
     * 带数据的成功，使用默认文案 ("操作成功")
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(ResultCode.SUCCESS.getDefaultMessage());
        result.setData(data);
        return result;
    }

    /**
     * 自定义成功文案 (适配 MessageConstant)
     */
    public static <T> Result<T> success(String message) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(message);
        return result;
    }

    /**
     * 既有数据，又有自定义文案
     */
    public static <T> Result<T> success(T data, String message) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    /**
     * 场景A: 标准状态码错误
     * 传入 ResultCode (如: 401, 403, 500)
     * 使用该状态码的默认文案
     */
    public static <T> Result<T> error(ResultCode resultCode) {
        Result<T> result = new Result<>();
        result.setCode(resultCode.getCode());
        result.setMessage(resultCode.getDefaultMessage());
        return result;
    }

    /**
     * 场景B: 业务自定义错误
     * 默认使用 BUSINESS_ERROR (100601) 状态码
     * 传入 MessageConstant 里的具体文案
     */
    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.BUSINESS_ERROR.getCode());
        result.setMessage(message);
        return result;
    }

    /**
     * 场景C: 全自定义错误
     * 专门给 GlobalExceptionHandler 使用，用于透传 Exception 里的 code 和 message
     */
    public static <T> Result<T> error(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}