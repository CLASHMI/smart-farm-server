package com.gcxy.smartfarm.dto;

import com.gcxy.smartfarm.common.constant.MessageConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * DictDataQueryDTO 类
 *
 * @author NaNloopia
 * @version 1.0
 * @created 11:00
 * @since 2026/1/14
 */
@Data
@Schema(description = "字典数据查询参数")
public class DictDataQueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Schema(description = "字典类型ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = MessageConstant.Dict.TYPE_ID_NULL)
    private Long typeId;
    @Schema(description = "字典选项名称 (模糊查询)", example = "牛")
    private String dictName;
    @Schema(description = "状态筛选 (0:正常, 1:停用)", example = "0")
    private Integer status;
}
