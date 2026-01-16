package com.gcxy.smartfarm.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {
    ADMIN(1L, "超级管理员"),
    USER(2L, "普通用户");
    private final Long id;
    private final String name;
}
