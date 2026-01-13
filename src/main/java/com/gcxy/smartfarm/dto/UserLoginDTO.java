package com.gcxy.smartfarm.dto;

import com.gcxy.smartfarm.common.constant.MessageConstant;
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
public class UserLoginDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotBlank(message = MessageConstant.USERNAME_NOT_EMPTY)
    @Length(max = 20, message = MessageConstant.USERNAME_LENGTH_ERROR)
    private String username;
    @NotBlank(message = MessageConstant.PASSWORD_NOT_EMPTY)
    @Length(min = 6, max = 20, message = MessageConstant.PASSWORD_LENGTH_ERROR)
    private String password;

}
