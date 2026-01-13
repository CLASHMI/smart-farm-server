package com.gcxy.smartfarm.common.handler;

import com.gcxy.smartfarm.common.constant.MessageConstant;
import com.gcxy.smartfarm.common.exception.BaseException;
import com.gcxy.smartfarm.common.result.Result;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
     * 1. 业务异常 (如：UserDetailsServiceImpl 中抛出的 BaseException)
     */
    @ExceptionHandler(BaseException.class)
    public Result<?> handleBaseException(BaseException ex) {
        log.warn("业务异常: {}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /**
     * 2. 登录认证失败 (密码错误 / 账号不存在)
     * Security 默认将 UsernameNotFoundException 转化为 BadCredentialsException
     */
    @ExceptionHandler({BadCredentialsException.class, InternalAuthenticationServiceException.class})
    public Result<?> handleBadCredentialsException(Exception ex) {
        return Result.error(ex.getMessage());
    }

    /**
     * 3. 账号被禁用 (DisabledException)
     */
    @ExceptionHandler(DisabledException.class)
    public Result<?> handleDisabledException(DisabledException ex) {
        return Result.error(ex.getMessage());
    }

    /**
     * 4. 权限不足 (AccessDeniedException)
     * 当普通用户尝试访问管理员接口时触发
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Result<?> handleAccessDeniedException(AccessDeniedException ex, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        return Result.error(MessageConstant.PERMISSION_DENIED);
    }

    /**
     * 5. JWT Token 异常 (过期 / 格式错误 / 签名错误)
     */
    @ExceptionHandler(JwtException.class)
    public Result<?> handleJwtException(JwtException ex, HttpServletResponse response) {
        log.warn("JWT解析异常: {}", ex.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        if (ex instanceof ExpiredJwtException) {
            return Result.error(MessageConstant.TOKEN_EXPIRED);
        }
        return Result.error(MessageConstant.TOKEN_INVALID);
    }

    /**
     * 6. 专门捕获 @Validated 校验失败的异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleValidationException(MethodArgumentNotValidException e) {
        // 从异常里拿到所有的错误信息
        BindingResult bindingResult = e.getBindingResult();
        String message = bindingResult.getFieldError().getDefaultMessage();
        return Result.error(message);
    }

    /**
     * 7. 兜底异常 (系统错误)
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception ex) {
        log.error("系统未知异常", ex);
        return Result.error(MessageConstant.SYSTEM_ERROR);
    }
}
