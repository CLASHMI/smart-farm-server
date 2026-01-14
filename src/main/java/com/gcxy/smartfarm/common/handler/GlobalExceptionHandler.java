package com.gcxy.smartfarm.common.handler;

import com.gcxy.smartfarm.common.enums.ResultCode;
import com.gcxy.smartfarm.common.exception.BaseException;
import com.gcxy.smartfarm.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * GlobalExceptionHandler 类
 *
 * @author NaNloopia
 * @version 1.0
 * @created 15:05
 * @since 2026/1/13
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 1. 业务异常 (HTTP 200)
     * 对应 ResultCode.BUSINESS_ERROR (100601) 或具体的业务场景码
     */
    @ExceptionHandler(BaseException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<?> handleBaseException(BaseException ex) {
        // 业务层手动抛出的异常，直接返回
        if (ex.getCode() != null) {
            Result<?> result = new Result<>();
            result.setCode(ex.getCode());
            result.setMessage(ex.getMessage());
            return result;
        }
        return Result.error(ResultCode.BUSINESS_ERROR);
    }

    /**
     * 2. 登录失败 (密码错误 / 账号不存在)
     * 归类为：业务逻辑错误 (100601)
     * 注意：这里不用 401，因为 401 是 Token 相关的
     */
    @ExceptionHandler({BadCredentialsException.class, InternalAuthenticationServiceException.class})
    @ResponseStatus(HttpStatus.OK) // 登录接口通常返回 200
    public Result<?> handleBadCredentialsException(Exception ex) {
        // 使用我们在 ResultCode 里定义的专用错误
        return Result.error(ResultCode.USER_LOGIN_ERROR);
    }

    /**
     * 3. 账号被禁用
     * 归类为：业务逻辑错误 (100601)
     */
    @ExceptionHandler(DisabledException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<?> handleDisabledException(DisabledException ex) {
        return Result.error(ResultCode.ACCOUNT_DISABLED);
    }

    /**
     * 4. 参数校验异常 (HTTP 400)
     * 对应 ResultCode.PARAM_ERROR (100400)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> handleValidationException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        Result<?> result = Result.error(ResultCode.PARAM_ERROR);
        result.setMessage(message);
        return result;
    }

    /**
     * 5. 权限不足 (HTTP 403)
     * 对应 ResultCode.FORBIDDEN (100403)
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Result<?> handleAccessDeniedException(AccessDeniedException ex) {
        return Result.error(ResultCode.FORBIDDEN);
    }

    /**
     * 6. 系统兜底异常 (HTTP 500)
     * 对应 ResultCode.SYSTEM_ERROR (100500)
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<?> handleException(Exception ex) {
        log.error("系统未知异常", ex);
        return Result.error(ResultCode.SYSTEM_ERROR);
    }
}
