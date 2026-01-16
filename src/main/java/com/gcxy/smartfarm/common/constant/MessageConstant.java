package com.gcxy.smartfarm.common.constant;

/**
 * MessageConstant 类
 *
 * @author NaNloopia
 * @version 1.0
 * @created 15:07
 * @since 2026/1/13
 */
public class MessageConstant {
    // 私有构造，防止实例化
    private MessageConstant() {}

    /**
     * 通用模块
     */
    public static class Common {
        public static final String ID_NOT_NULL = "主键ID不能为空";
        public static final String ADD_SUCCESS = "添加成功";
        public static final String UPDATE_SUCCESS = "修改成功";
        public static final String DELETE_SUCCESS = "删除成功";
        public static final String QUERY_SUCCESS = "查询成功";
        public static final String LOGIN_SUCCESS = "登录成功";
        public static final String LOGOUT_SUCCESS = "登出成功";
    }

    /**
     * 认证授权模块
     */
    public static class Auth {
        public static final String NOT_LOGIN = "未登录，请先登录";
        public static final String TOKEN_EXPIRED = "身份已过期，请重新登录";
        public static final String TOKEN_INVALID = "身份无效，请重新登录";
        public static final String LOGIN_ERROR = "用户名或密码错误";
        public static final String ACCOUNT_DISABLED = "账号已禁用，无法登录";
    }

    /**
     * 用户管理模块
     */
    public static class User {
        public static final String USERNAME_NOT_EMPTY = "用户名不能为空";
        public static final String USERNAME_LEN_ERR = "账号长度不能超过20个字符";
        public static final String PASSWORD_NOT_EMPTY = "密码不能为空";
        public static final String PASSWORD_LEN_ERR = "密码长度不能超过20个字符";
        public static final String REALNAME_NOT_EMPTY = "姓名不能为空";
        public static final String REALNAME_LEN_ERR = "姓名最长10个字符";
        public static final String PHONE_NOT_EMPTY = "手机号不能为空";
        public static final String PHONE_FMT_ERR = "手机号格式不正确";
        public static final String PHONE_EXIST = "手机号已存在";
        public static final String NOT_EXIST = "用户不存在";
        public static final String ADMIN_IMMUTABLE = "超级管理员不允许修改或删除";
    }

    /**
     * 字典/系统配置模块
     */
    public static class Dict {
        public static final String TYPE_ID_NULL = "字典类型ID不能为空";
        public static final String NAME_NOT_NULL = "字典名称不能为空";
        public static final String NAME_LEN_ERR = "字典名称过长";
        public static final String NAME_EXIST = "字典名称已存在";
        public static final String NOT_EXIST = "字典数据不存在";
        public static final String IN_USE = "该字典已被使用，无法停用/删除";
    }
}
