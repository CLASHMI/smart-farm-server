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
    public static final String LOGIN_SUCCESS = "登录成功";
    public static final String USERNAME_OR_PASSWORD_ERROR = "用户名或密码错误，请重新输入";
    public static final String ACCOUNT_LOCKED = "账号被锁定";
    public static final String ACCOUNT_DISABLED = "账号已禁用，无法登录";
    public static final String ACCOUNT_NOT_FOUND = "账号不存在";
    public static final String TOKEN_INVALID = "身份凭证无效，请重新登录";
    public static final String TOKEN_EXPIRED = "身份凭证已过期，请重新登录";
    public static final String PERMISSION_DENIED = "权限不足";
    public static final String SYSTEM_ERROR = "系统繁忙，请稍后再试";
    public static final String NOT_LOGIN = "未登录，请先登录";
    public static final String USERNAME_NOT_EMPTY = "用户名不能为空";
    public static final String USERNAME_LENGTH_ERROR = "账号长度不能超过20个字符";
    public static final String PASSWORD_NOT_EMPTY = "密码不能为空";
    public static final String PASSWORD_LENGTH_ERROR = "密码长度不能超过20个字符";
    public static final String CHECK_PASSWORD_NOT_EMPTY = "确认密码不能为空";
    public static final String NICKNAME_NOT_EMPTY = "昵称不能为空";
    public static final String PASSWORD_NOT_MATCH = "两次输入的密码不一致";
    public static final String USERNAME_RESERVED = "该用户名属于系统保留字，无法注册";
    public static final String USERNAME_EXISTS = "用户名已存在";
    public static final String REGISTER_SUCCESS = "注册成功";
    public static final String SYSTEM_BUSY = "系统繁忙，请稍后再试";
    public static final String FILE_NOT_EMPTY = "上传文件不能为空";
    public static final String FILE_TYPE_ERROR = "非法的文件类型，仅支持 avatar 或 article";
    public static final String FILE_NAME_ERROR = "文件名解析异常";
    public static final String FILE_UPLOAD_FAILED = "文件写入失败，请联系管理员";
    public static final String USER_STATUS_ERROR = "用户状态异常，请重新登录";
    public static final String PASSWORD_ERROR = "旧密码错误";
    public static final String PASSWORD_SAME_ERROR = "新密码不能与旧密码相同";
    public static final String NICKNAME_EXIST = "该昵称已被占用，请更换";
    public static final String NICKNAME_FORMAT_ERROR = "昵称只能包含中文、字母或数字，长度2-10位";
    public static final String OLD_PASSWORD_NOT_EMPTY = "旧密码不能为空";
    public static final String NEW_PASSWORD_NOT_EMPTY = "新密码不能为空";
    public static final String DELETED_USER_NICKNAME = "该用户已注销";
    public static final String USER_NOT_FOUND = "该用户不存在";
    public static final String ARTICLE_NOT_FOUND = "该文章不存在";
    public static final String COMMENT_NOT_FOUND = "该评论不存在";
    public static final String CATEGORY_EXISTS = "分类已存在";
    public static final String CATEGORY_HAS_ARTICLES = "当前分类下有文章，不能删除";
    public static final String FILE_SIZE_EXCEED = "上传文件大小超出限制，最大允许10MB";

}
