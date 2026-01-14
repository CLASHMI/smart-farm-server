package com.gcxy.smartfarm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gcxy.smartfarm.entity.DictType;
import com.gcxy.smartfarm.vo.DictTypeVO;

import java.util.List;

/**
 * @author NaNloopia
 * @version 1.0
 * @created 10:44
 * @since 2026/1/14
 */
public interface DictTypeService extends IService<DictType> {

    /**
     * 查询字典类型列表
     * @return
     */
    List<DictTypeVO> listType();
}
