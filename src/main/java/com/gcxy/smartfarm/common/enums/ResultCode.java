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

    // --- 1. 成功 ---
    SUCCESS(100200, "操作成功"),

    // --- 2. 客户端错误 (1004xx) ---
    PARAM_ERROR(100400, "参数错误"),
    NOT_LOGIN(100401, "未登录，请先登录"),
    TOKEN_EXPIRED(100401, "身份已过期，请重新登录"),
    TOKEN_INVALID(100401, "身份无效，请重新登录"),
    FORBIDDEN(100403, "权限不足"),
    NOT_FOUND(100404, "资源不存在"),

    // --- 3. 服务器错误 (1005xx) ---
    SYSTEM_ERROR(100500, "服务器内部错误"),

    // --- 4. 业务逻辑错误 (Code 统一为 100601) ---
    // 通用业务错误
    BUSINESS_ERROR(100601, "业务逻辑错误"),
    USER_PHONE_EXIST(100601, "手机号已存在"),
    DICT_NAME_EXIST(100601, "字典名称已存在"),
    DICT_IN_USE(100601, "该字典已被使用，无法停用"),
    USER_LOGIN_ERROR(100601, "用户名或密码错误"),
    ACCOUNT_DISABLED(100601, "账号已禁用，无法登录"),
    USER_NOT_EXIST(100601, "用户不存在"),
    DICT_NOT_EXIST(100601, "字典不存在");
    private final Integer code;
    private final String message;
}
