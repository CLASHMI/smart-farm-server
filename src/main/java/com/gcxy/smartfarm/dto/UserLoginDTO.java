package com.gcxy.smartfarm.dto;

import com.gcxy.smartfarm.common.constant.MessageConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * UserLoginDTO 类
 *
 * @author NaNloopia
 * @version 1.0
 * @created 15:41
 * @since 2026/1/13
 */

@Data
@Schema(description = "用户登录请求参数")
public class UserLoginDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Schema(description = "用户名", example = "admin")
    @NotBlank(message = MessageConstant.User.USERNAME_NOT_EMPTY)
    @Length(max = 20, message = MessageConstant.User.USERNAME_LEN_ERR)
    private String username;
    @Schema(description = "密码", example = "123456")
    @NotBlank(message = MessageConstant.User.PASSWORD_NOT_EMPTY)
    @Length(max = 20, message = MessageConstant.User.PASSWORD_LEN_ERR)
    private String password;
}
