package com.gcxy.smartfarm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DictType 类
 *
 * @author NaNloopia
 * @version 1.0
 * @created 9:45
 * @since 2026/1/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("sys_dict_type")
public class DictType implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String dictName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
