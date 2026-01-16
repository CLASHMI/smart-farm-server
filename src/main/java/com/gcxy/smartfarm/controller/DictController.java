package com.gcxy.smartfarm.controller;

import com.gcxy.smartfarm.common.constant.MessageConstant;
import com.gcxy.smartfarm.common.result.Result;
import com.gcxy.smartfarm.dto.DictDataAddDTO;
import com.gcxy.smartfarm.dto.DictDataQueryDTO;
import com.gcxy.smartfarm.service.DictDataService;
import com.gcxy.smartfarm.service.DictTypeService;
import com.gcxy.smartfarm.vo.DictDataVO;
import com.gcxy.smartfarm.vo.DictTypeVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * DictController 类
 *
 * @author NaNloopia
 * @version 1.0
 * @created 9:49
 * @since 2026/1/13
 */
@RestController
@Slf4j
@RequestMapping("/dict")
@Tag(name = "字典管理模块")
public class DictController {

    @Autowired
    private DictTypeService dictTypeService;
    @Autowired
    private DictDataService dictDataService;

    /**
     * 查询字典类型列表
     * @return
     */
    @GetMapping("/type/list")
    @Operation(summary = "查询字典类型列表")
    public Result<List<DictTypeVO>> listType() {
        log.info("查询字典类型列表");
        List<DictTypeVO> list = dictTypeService.listType();
        return Result.success(list, MessageConstant.Common.QUERY_SUCCESS);
    }

    /**
     * 查询字典数据列表
     * @return
     */
    @PostMapping("/data/list")
    @Operation(summary = "查询字典数据列表")
    public Result<List<DictDataVO>> listData(@RequestBody @Validated DictDataQueryDTO dictDataQueryDTO) {
        log.info("查询字典数据列表");
        List<DictDataVO> list = dictDataService.listData(dictDataQueryDTO);
        return Result.success(list, MessageConstant.Common.QUERY_SUCCESS);
    }

    /**
     * 新增字典数据
     * @param dictDataAddDTO
     * @return
     */
    @PreAuthorize("hasAuthority('超级管理员')")
    @PostMapping("/data")
    @Operation(summary = "新增字典数据")
    public Result<?> save(@RequestBody @Validated DictDataAddDTO dictDataAddDTO) {
        log.info("新增字典数据: {}", dictDataAddDTO);
        dictDataService.addData(dictDataAddDTO);
        return Result.success(MessageConstant.Common.ADD_SUCCESS);
    }

    /**
     * 修改字典数据状态
     * @param status
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('超级管理员')")
    @PutMapping("/data/status/{status}")
    @Operation(summary = "修改字典数据状态")
    public Result<?> updateStatus(@PathVariable("status") Integer status, @RequestParam Long id) {
        log.info("修改字典数据状态: {}, {}", status, id);
        dictDataService.updateStatus(status, id);
        return Result.success(MessageConstant.Common.UPDATE_SUCCESS);
    }

    /**
     * 删除字典数据
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('超级管理员')")
    @DeleteMapping("/data")
    @Operation(summary = "删除字典数据")
    public Result<?> deleteData(@RequestParam Long id) {
        log.info("删除字典数据: {}", id);
        dictDataService.deleteData(id);
        return Result.success(MessageConstant.Common.DELETE_SUCCESS);
    }
}
