package com.gcxy.smartfarm.vo;

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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String token;
    private String username;
    private String realName;
    private String roleName;
    private String phone;
}
