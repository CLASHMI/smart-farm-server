package com.gcxy.smartfarm.controller;

import com.gcxy.smartfarm.common.result.PageResult;
import com.gcxy.smartfarm.common.result.Result;
import com.gcxy.smartfarm.dto.UserAddDTO;
import com.gcxy.smartfarm.dto.UserLoginDTO;
import com.gcxy.smartfarm.dto.UserPageQueryDTO;
import com.gcxy.smartfarm.dto.UserUpdateDTO;
import com.gcxy.smartfarm.service.UserService;
import com.gcxy.smartfarm.vo.UserLoginVO;
import com.gcxy.smartfarm.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.TimeUnit;

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
@Tag(name = "人员管理模块")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 登录
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    @Operation(summary = "登录")
    public Result<UserLoginVO> login(@RequestBody @Validated UserLoginDTO userLoginDTO) {
        log.info("登录: {}", userLoginDTO);
        UserLoginVO userLoginVO = userService.login(userLoginDTO);
        return Result.success(userLoginVO, "登录成功");
    }

    /**
     * 登出
     * @return
     */
    @PostMapping("/logout")
    @Operation(summary = "登出")
    public Result<?> logout() {
        log.info("登出");
        Object credentials = SecurityContextHolder.getContext().getAuthentication().getCredentials();
        if (credentials instanceof String token) {
            // 将 Token 存入黑名单，设置 24 小时过期
            redisTemplate.opsForValue().set("sys:token:blacklist:" + token, "1", 24, TimeUnit.HOURS);
        }
        SecurityContextHolder.clearContext();
        return Result.success("登出成功");
    }

    /**
     * 新增员工
     * @param userAddDTO
     * @return
     */
    @PreAuthorize("hasAuthority('超级管理员')")
    @PostMapping("/save")
    @Operation(summary = "新增员工")
    public Result<?> save(@RequestBody @Validated UserAddDTO userAddDTO) {
        log.info("新增员工: {}", userAddDTO);
        userService.addUser(userAddDTO);
        return Result.success("新增成功");
    }

    /**
     * 分页查询员工
     * @param userPageQueryDTO
     * @return
     */
    @PostMapping("/page")
    @Operation(summary = "分页查询员工")
    public Result<PageResult<UserVO>> page(@RequestBody UserPageQueryDTO userPageQueryDTO) {
        log.info("分页查询: {}", userPageQueryDTO);
        PageResult<UserVO> pageResult = userService.pageQuery(userPageQueryDTO);
        return Result.success(pageResult, "查询成功");
    }

    /**
     * 修改员工
     * @param userUpdateDTO
     * @return
     */
    @PreAuthorize("hasAuthority('超级管理员')")
    @PutMapping("/update")
    @Operation(summary = "修改员工")
    public Result<?> update(@RequestBody @Validated UserUpdateDTO userUpdateDTO) {
        log.info("更新用户: {}", userUpdateDTO);
        userService.updateUser(userUpdateDTO);
        return Result.success("修改成功");
    }

    /**
     * 修改员工状态
     * @param status
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('超级管理员')")
    @PutMapping("/status/{status}")
    @Operation(summary = "修改员工状态")
    public Result<?> updateStatus(@PathVariable("status") Integer status, @RequestParam Long id) {
        log.info("修改员工状态: {}, {}", status, id);
        userService.updateStatus(status, id);
        return Result.success("修改成功");
    }

    /**
     * 删除员工
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('超级管理员')")
    @DeleteMapping
    @Operation(summary = "删除员工")
    public Result<?> deleteUser(@RequestParam Long id) {
        log.info("删除员工: {}", id);
        userService.deleteUser(id);
        return Result.success("删除成功");
    }
}
