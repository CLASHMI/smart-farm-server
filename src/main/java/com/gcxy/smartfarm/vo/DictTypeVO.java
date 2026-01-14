package com.gcxy.smartfarm.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DictTypeVO 类
 *
 * @author NaNloopia
 * @version 1.0
 * @created 10:12
 * @since 2026/1/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "字典类型展示对象")
public class DictTypeVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Schema(description = "字典类型ID", example = "1")
    private Long id;
    @Schema(description = "字典名称", example = "动物品种")
    private String dictName;
    @Schema(description = "创建时间", example = "2026-01-01 10:00:00")
    private LocalDateTime createTime;
}
