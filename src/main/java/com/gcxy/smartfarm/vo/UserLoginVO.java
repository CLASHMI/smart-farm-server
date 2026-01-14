package com.gcxy.smartfarm.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * UserLoginVO 类
 *
 * @author NaNloopia
 * @version 1.0
 * @created 15:40
 * @since 2026/1/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "用户登录响应数据")
public class UserLoginVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Schema(description = "JWT 令牌", example = "eyJhbGciOiJIUzI1NiJ9...")
    private String token;
    @Schema(description = "用户名", example = "admin")
    private String username;
    @Schema(description = "真实姓名", example = "张三")
    private String realName;
    @Schema(description = "角色名称", example = "超级管理员")
    private String roleName;
    @Schema(description = "手机号", example = "13800138000")
    private String phone;
}
