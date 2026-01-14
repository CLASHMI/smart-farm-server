package com.gcxy.smartfarm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gcxy.smartfarm.dto.DictDataAddDTO;
import com.gcxy.smartfarm.dto.DictDataQueryDTO;
import com.gcxy.smartfarm.entity.DictData;
import com.gcxy.smartfarm.mapper.DictDataMapper;
import com.gcxy.smartfarm.service.DictDataService;
import com.gcxy.smartfarm.vo.DictDataVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * DictDataServiceImpl 类
 *
 * @author NaNloopia
 * @version 1.0
 * @created 10:46
 * @since 2026/1/14
 */
@Service
public class DictDataServiceImpl extends ServiceImpl<DictDataMapper, DictData> implements DictDataService {
    @Override
    public void deleteData(Long id) {

    }

    @Override
    public void updateStatus(Integer status, Long id) {

    }

    @Override
    public void addData(DictDataAddDTO dictDataAddDTO) {

    }

    @Override
    public List<DictDataVO> listData(DictDataQueryDTO dictDataQueryDTO) {
        return List.of();
    }
}
