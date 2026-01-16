CREATE TABLE sys_dict_data
(
    id            bigint AUTO_INCREMENT COMMENT '主键'
        PRIMARY KEY,
    type_id       bigint                               NOT NULL COMMENT '关联的字典类型ID (关联 sys_dict_type.id)',
    dict_name     varchar(100)                         NOT NULL COMMENT '字典选项名称',
    dict_sort     int        DEFAULT 0                 NULL COMMENT '排序号',
    status        tinyint(1) DEFAULT 0                 NOT NULL COMMENT '状态 (0:正常, 1:停用)',
    is_referenced tinyint(1) DEFAULT 0                 NOT NULL COMMENT '是否被引用 (0:否, 1:是)',
    create_time   datetime   DEFAULT CURRENT_TIMESTAMP NULL COMMENT '创建时间',
    update_time   datetime                             NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
)
    COMMENT '数据字典数据表';

CREATE INDEX idx_type_id
    ON sys_dict_data (type_id);

CREATE TABLE sys_dict_type
(
    id          bigint AUTO_INCREMENT COMMENT '主键'
        PRIMARY KEY,
    dict_name   varchar(100)                       NOT NULL COMMENT '字典名称 (如：牲畜类型)',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NULL COMMENT '创建时间',
    update_time datetime                           NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
)
    COMMENT '数据字典类型表';

CREATE TABLE sys_role
(
    id          bigint AUTO_INCREMENT COMMENT '主键'
        PRIMARY KEY,
    role_name   varchar(50)                        NOT NULL COMMENT '角色名称',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NULL COMMENT '创建时间',
    update_time datetime                           NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    CONSTRAINT role_name
        UNIQUE (role_name)
)
    COMMENT '角色表';

CREATE TABLE sys_user
(
    id          bigint AUTO_INCREMENT COMMENT '主键，自增'
        PRIMARY KEY,
    username    varchar(20)                          NOT NULL COMMENT '登录账号 (YZ+序号)，唯一',
    password    varchar(100)                         NOT NULL COMMENT '登录密码 (加密存储)',
    real_name   varchar(10)                          NOT NULL COMMENT '人员姓名',
    role_id     bigint                               NOT NULL COMMENT '角色ID (关联sys_role.id)',
    phone       varchar(11)                          NOT NULL COMMENT '手机号 (唯一)',
    status      tinyint(1) DEFAULT 0                 NOT NULL COMMENT '状态 (0:正常, 1:停用)',
    create_time datetime   DEFAULT CURRENT_TIMESTAMP NULL COMMENT '创建时间',
    update_time datetime                             NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    CONSTRAINT phone
        UNIQUE (phone),
    CONSTRAINT username
        UNIQUE (username)
)
    COMMENT '人员/用户表';


