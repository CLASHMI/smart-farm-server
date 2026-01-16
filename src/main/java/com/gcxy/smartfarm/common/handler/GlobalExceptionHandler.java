package com.gcxy.smartfarm.common.handler;

import com.gcxy.smartfarm.common.constant.MessageConstant;
import com.gcxy.smartfarm.common.enums.ResultCode;
import com.gcxy.smartfarm.common.exception.BaseException;
import com.gcxy.smartfarm.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

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
        return Result.error(ex.getCode(), ex.getMessage());
    }

    /**
     * 2. 登录失败 (密码错误 / 账号不存在)
     * 归类为：业务逻辑错误 (100601)
     */
    @ExceptionHandler({BadCredentialsException.class, InternalAuthenticationServiceException.class})
    @ResponseStatus(HttpStatus.OK)
    public Result<?> handleAuthenticationException(Exception ex) {
        // 账号被禁用
        if (ex.getCause() instanceof DisabledException) {
            return Result.error(ResultCode.BUSINESS_ERROR.getCode(), MessageConstant.Auth.ACCOUNT_DISABLED);
        }
        // 其他都视为用户名或密码错误，防止暴力破解猜测
        return Result.error(ResultCode.BUSINESS_ERROR.getCode(), MessageConstant.Auth.LOGIN_ERROR);
    }

    /**
     * 3. 参数校验异常 (HTTP 400)
     * 对应 ResultCode.PARAM_ERROR (100400)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        // 获取第一条错误信息
        String msg = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
        log.warn("参数校验失败: {}", msg);
        return Result.error(ResultCode.PARAM_ERROR.getCode(), msg);
    }

    /**
     * 4. 权限不足 (HTTP 403)
     * 对应 ResultCode.FORBIDDEN (100403)
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Result<?> handleAccessDeniedException(AccessDeniedException ex) {
        return Result.error(ResultCode.FORBIDDEN);
    }

    /**
     * 5. 系统兜底异常 (HTTP 500)
     * 对应 ResultCode.SYSTEM_ERROR (100500)
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<?> handleException(Exception ex) {
        log.error("系统未知异常", ex);
        return Result.error(ResultCode.SYSTEM_ERROR);
    }
}
