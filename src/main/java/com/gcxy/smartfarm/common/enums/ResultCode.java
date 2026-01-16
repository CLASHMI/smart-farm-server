package com.gcxy.smartfarm.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ResultCode 类
 *
 * @author NaNloopia
 * @version 1.0
 * @created 11:33
 * @since 2026/1/14
 */
@Getter
@AllArgsConstructor
public enum ResultCode {
    SUCCESS(100200, "操作成功"),
    PARAM_ERROR(100400, "参数校验失败"),
    UNAUTHORIZED(100401, "身份未认证或已过期"),
    FORBIDDEN(100403, "没有操作权限"),
    NOT_FOUND(100404, "请求资源不存在"),
    SYSTEM_ERROR(100500, "系统繁忙，请稍后重试"),
    BUSINESS_ERROR(100601, "业务处理失败");
    private final Integer code;
    private final String defaultMessage;
}
