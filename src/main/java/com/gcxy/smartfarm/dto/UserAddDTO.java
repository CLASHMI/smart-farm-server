package com.gcxy.smartfarm.dto;

import com.gcxy.smartfarm.common.constant.MessageConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * UserAddDTO 类
 *
 * @author NaNloopia
 * @version 1.0
 * @created 17:57
 * @since 2026/1/13
 */
@Data
@Schema(description = "新增用户请求参数")
public class UserAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 姓名
     * 必填，最长 10 字符
     */
    @Schema(description = "姓名 (最长10字符)", example = "张三", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = MessageConstant.REALNAME_NOT_EMPTY)
    @Length(max = 10, message = MessageConstant.REALNAME_LENGTH_ERROR)
    private String realName;
    /**
     * 手机号
     * 必填，11位数字
     */
    @Schema(description = "手机号 (11位数字)", example = "13800138000", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = MessageConstant.PHONE_NOT_EMPTY)
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = MessageConstant.PHONE_FORMAT_ERROR)
    private String phone;
    /**
     * 初始状态
     * 0:正常, 1:停用
     */
    @Schema(description = "初始状态 (0:正常, 1:停用)", example = "0", defaultValue = "0")
    private Integer status = 0;
}
