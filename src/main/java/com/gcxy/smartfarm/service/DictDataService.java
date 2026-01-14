package com.gcxy.smartfarm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gcxy.smartfarm.dto.DictDataAddDTO;
import com.gcxy.smartfarm.dto.DictDataQueryDTO;
import com.gcxy.smartfarm.entity.DictData;
import com.gcxy.smartfarm.vo.DictDataVO;

import java.util.List;

/**
 * @author NaNloopia
 * @version 1.0
 * @created 10:44
 * @since 2026/1/14
 */
public interface DictDataService extends IService<DictData> {
    /**
     * 删除字典数据
     * @param id
     */
    void deleteData(Long id);

    /**
     * 修改字典数据状态
     * @param status
     * @param id
     */
    void updateStatus(Integer status, Long id);

    /**
     * 新增字典数据
     * @param dictDataAddDTO
     */
    void addData(DictDataAddDTO dictDataAddDTO);

    /**
     * 查询字典数据列表
     * @param dictDataQueryDTO
     * @return
     */
    List<DictDataVO> listData(DictDataQueryDTO dictDataQueryDTO);
}
