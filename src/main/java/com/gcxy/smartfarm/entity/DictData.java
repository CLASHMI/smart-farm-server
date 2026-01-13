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
 * DictData 类
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
@TableName("sys_dict_data")
public class DictData implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long TypeId;
    private String dictName;
    private String dictSort;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
