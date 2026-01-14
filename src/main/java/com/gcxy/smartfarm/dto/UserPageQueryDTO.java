package com.gcxy.smartfarm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * UserPageQueryDTO 类
 *
 * @author NaNloopia
 * @version 1.0
 * @created 9:44
 * @since 2026/1/14
 */
@Data
@Schema(description = "用户分页查询参数")
public class UserPageQueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Schema(description = "页码", example = "1", defaultValue = "1")
    private Integer page = 1;
    @Schema(description = "每页条数", example = "10", defaultValue = "10")
    private Integer pageSize = 10;
    @Schema(description = "姓名 (模糊查询)", example = "张")
    private String realName;
    @Schema(description = "手机号", example = "138")
    private String phone;
    @Schema(description = "状态 (0:正常, 1:停用)", example = "0")
    private Integer status;
}
