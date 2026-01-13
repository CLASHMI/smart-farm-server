package com.gcxy.smartfarm.common.result;

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

    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.code = 1;
        return result;
    }


    public static<T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.code = 1;
        result.data = data;
        return result;
    }


    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<>();
        result.code = 0;
        result.message = message;
        return result;
    }
}
