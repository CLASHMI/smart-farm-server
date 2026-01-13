package com.gcxy.smartfarm.controller;

import com.gcxy.smartfarm.common.result.Result;
import com.gcxy.smartfarm.dto.UserLoginDTO;
import com.gcxy.smartfarm.service.UserService;
import com.gcxy.smartfarm.vo.UserLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController 类
 *
 * @author NaNloopia
 * @version 1.0
 * @created 9:48
 * @since 2026/1/13
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody @Validated UserLoginDTO userLoginDTO) {
        log.info("用户登录: {}", userLoginDTO);
        UserLoginVO userLoginVO = userService.login(userLoginDTO);
        return Result.success(userLoginVO);
    }

    /**
     * 用户登出
     * @return
     */
    @PostMapping("/logout")
    public Result<?> logout() {
        log.info("用户登出");
        SecurityContextHolder.clearContext();
        return Result.success();
    }


}
