package com.gcxy.smartfarm.dto;

import com.gcxy.smartfarm.common.constant.MessageConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * DictDataAddDTO 类
 *
 * @author NaNloopia
 * @version 1.0
 * @created 10:08
 * @since 2026/1/14
 */
@Data
@Schema(description = "新增字典数据请求参数")
public class DictDataAddDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Schema(description = "字典类型ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = MessageConstant.DICT_ID_NOT_NULL)
    private Long typeId;
    @Schema(description = "字典选项名称", example = "奶牛", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = MessageConstant.DICT_NAME_NOT_NULL)
    @Length(max = 100, message = MessageConstant.DICT_NAME_LENGTH_ERROR)
    private String dictName;
    @Schema(description = "排序号", example = "1", defaultValue = "0")
    private Integer dictSort = 0;
    @Schema(description = "状态 (0:正常, 1:停用)", example = "0", defaultValue = "0")
    private Integer status = 0;
}
