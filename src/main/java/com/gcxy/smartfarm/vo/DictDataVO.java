package com.gcxy.smartfarm.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DictDataVO 类
 *
 * @author NaNloopia
 * @version 1.0
 * @created 10:14
 * @since 2026/1/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "字典数据展示对象")
public class DictDataVO implements Serializable {
    private static final long serialVersionUID = 1L;
    // 数据ID
    @Schema(description = "数据ID", example = "101")
    private Long id;
    // 关联的字典类型ID
    @Schema(description = "关联的字典类型ID", example = "1")
    private Long typeId;
    // 字典选项名称
    @Schema(description = "字典选项名称", example = "奶牛")
    private String dictName;
    // 排序号
    @Schema(description = "排序号", example = "1")
    private Integer dictSort;
    // 状态 (0:正常, 1:停用)
    @Schema(description = "状态 (0:正常, 1:停用)", example = "0")
    private Integer status;
    // 是否被引用 (0:否, 1:是)，如果为1，前端应禁止用户点击停用
    @Schema(description = "是否被引用 (0:否, 1:是)，如果为1，前端应禁止用户点击停用", example = "0")
    private Integer isReferenced;
    // 创建时间
    @Schema(description = "创建时间", example = "2026-01-01 12:00:00")
    private LocalDateTime createTime;
}
