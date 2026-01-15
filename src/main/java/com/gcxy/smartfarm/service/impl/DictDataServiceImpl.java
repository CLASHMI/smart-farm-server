package com.gcxy.smartfarm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.gcxy.smartfarm.common.enums.ResultCode;
import com.gcxy.smartfarm.common.exception.BaseException;
import com.gcxy.smartfarm.dto.DictDataAddDTO;
import com.gcxy.smartfarm.dto.DictDataQueryDTO;
import com.gcxy.smartfarm.entity.DictData;
import com.gcxy.smartfarm.mapper.DictDataMapper;
import com.gcxy.smartfarm.service.DictDataService;
import com.gcxy.smartfarm.vo.DictDataVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * DictDataServiceImpl 类
 *
 * @author NaNloopia
 * @version 1.0
 * @created 10:46
 * @since 2026/1/14
 */
@Service
@Slf4j
public class DictDataServiceImpl extends ServiceImpl<DictDataMapper, DictData> implements DictDataService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    private static final String DICT_DATA_KEY = "sys:dict:data:";

    /**
     * 删除字典数据
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(Long id) {
        DictData data = this.getById(id);
        if (data == null) {
            throw new BaseException(ResultCode.DICT_NOT_EXIST);
        }
        if (data.getIsReferenced() == 1) {
            throw new BaseException(ResultCode.DICT_IN_USE);
        }
        this.removeById(id);
        redisTemplate.delete("sys:dict:data:" + data.getTypeId());
    }

    /**
     * 修改字典数据状态
     * @param status
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Integer status, Long id) {
        DictData data = this.getById(id);
        if (data == null) {
            throw new BaseException(ResultCode.DICT_NOT_EXIST);
        }
        if (status == 1 && data.getIsReferenced() == 1) {
            throw new BaseException(ResultCode.DICT_IN_USE);
        }
        data.setStatus(status);
        this.updateById(data);
        redisTemplate.delete("sys:dict:data:" + data.getTypeId());
    }

    /**
     * 新增字典数据
     * @param dictDataAddDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addData(DictDataAddDTO dictDataAddDTO) {
        LambdaQueryWrapper<DictData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DictData::getTypeId, dictDataAddDTO.getTypeId())
                .eq(DictData::getDictName, dictDataAddDTO.getDictName());
        if (this.count(wrapper) > 0) {
            throw new BaseException(ResultCode.DICT_NAME_EXIST);
        }
        DictData data = new DictData();
        BeanUtils.copyProperties(dictDataAddDTO, data);
        this.save(data);
        redisTemplate.delete("sys:dict:data:" + dictDataAddDTO.getTypeId());
    }

    /**
     * 查询字典数据
     * @param dictDataQueryDTO
     * @return
     */
    @Override
    public List<DictDataVO> listData(DictDataQueryDTO dictDataQueryDTO) {
        String cacheKey = DICT_DATA_KEY + dictDataQueryDTO.getTypeId();
        String json = redisTemplate.opsForValue().get(cacheKey);
        List<DictDataVO> dataList = null;
        if (StringUtils.hasText(json)) {
            try {
                log.info("从 Redis 获取字典数据全量列表");
                dataList = objectMapper.readValue(json, new TypeReference<List<DictDataVO>>() {});
            } catch (Exception e) {
                log.error("字典数据缓存解析失败", e);
            }
        }
        if (dataList == null) {
            LambdaQueryWrapper<DictData> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(DictData::getTypeId, dictDataQueryDTO.getTypeId())
                    .orderByAsc(DictData::getDictSort);
            dataList = this.list(wrapper).stream().map(data -> {
                DictDataVO vo = new DictDataVO();
                BeanUtils.copyProperties(data, vo);
                return vo;
            }).toList();
            try {
                long timeout = dataList.isEmpty() ? 5 : 24 * 60;
                redisTemplate.opsForValue().set(
                        cacheKey,
                        objectMapper.writeValueAsString(dataList),
                        timeout,
                        TimeUnit.MINUTES
                );
            } catch (Exception e) {
                log.error("字典数据缓存写入失败", e);
            }
        }
        return dataList.stream()
                .filter(item -> !StringUtils.hasText(dictDataQueryDTO.getDictName()) || item.getDictName().contains(dictDataQueryDTO.getDictName()))
                .filter(item -> dictDataQueryDTO.getStatus() == null || item.getStatus().equals(dictDataQueryDTO.getStatus()))
                .collect(Collectors.toList());
    }
}
