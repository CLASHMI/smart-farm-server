package com.gcxy.smartfarm.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * UserVO 类
 *
 * @author NaNloopia
 * @version 1.0
 * @created 9:41
 * @since 2026/1/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "用户列表展示对象")
public class UserVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Schema(description = "用户ID", example = "2")
    private Long id;
    @Schema(description = "用户名", example = "lisi")
    private String username;
    @Schema(description = "真实姓名", example = "李四")
    private String realName;
    @Schema(description = "角色名称", example = "普通用户")
    private String roleName;
    @Schema(description = "手机号", example = "13800000000")
    private String phone;
    @Schema(description = "用户状态", example = "0")
    private Integer status;
    @Schema(description = "创建时间", example = "2026-01-14 09:41:00")
    private LocalDateTime createTime;
}
