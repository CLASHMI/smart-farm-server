package com.gcxy.smartfarm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gcxy.smartfarm.entity.DictType;
import com.gcxy.smartfarm.mapper.DictTypeMapper;
import com.gcxy.smartfarm.service.DictTypeService;
import com.gcxy.smartfarm.vo.DictTypeVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * DictTypeServiceImpl 类
 *
 * @author NaNloopia
 * @version 1.0
 * @created 10:45
 * @since 2026/1/14
 */
@Service
public class DictTypeServiceImpl extends ServiceImpl<DictTypeMapper, DictType> implements DictTypeService {
    @Override
    public List<DictTypeVO> listType() {
        return List.of();
    }
}
