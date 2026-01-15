package com.gcxy.smartfarm.dto;

import com.gcxy.smartfarm.common.constant.MessageConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * UserUpdateDTO 类
 *
 * @author NaNloopia
 * @version 1.0
 * @created 10:04
 * @since 2026/1/14
 */
@Data
@Schema(description = "修改用户请求参数")
public class UserUpdateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = MessageConstant.ID_NOT_NULL)
    @Schema(description = "用户ID", example = "1001", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "姓名", example = "李四", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = MessageConstant.REALNAME_NOT_EMPTY)
    @Length(max = 10, message = MessageConstant.REALNAME_LENGTH_ERROR)
    private String realName;

    @Schema(description = "手机号", example = "13900139000", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = MessageConstant.PHONE_NOT_EMPTY)
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = MessageConstant.PHONE_FORMAT_ERROR)
    private String phone;

    @Schema(description = "状态 (0:正常, 1:停用)", example = "0")
    private Integer status;
}
