package com.gcxy.smartfarm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gcxy.smartfarm.entity.DictType;
import com.gcxy.smartfarm.mapper.DictTypeMapper;
import com.gcxy.smartfarm.service.DictTypeService;
import com.gcxy.smartfarm.vo.DictTypeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * DictTypeServiceImpl 类
 *
 * @author NaNloopia
 * @version 1.0
 * @created 10:45
 * @since 2026/1/14
 */
@Service
@Slf4j
public class DictTypeServiceImpl extends ServiceImpl<DictTypeMapper, DictType> implements DictTypeService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    private static final String DICT_TYPES_KEY = "sys:dict:types";

    /**
     * 查询字典类型列表
     * @return
     */
    @Override
    public List<DictTypeVO> listType() {
        String json = redisTemplate.opsForValue().get(DICT_TYPES_KEY);
        List<DictTypeVO> typeList = null;
        if (StringUtils.hasText(json)) {
            try {
                log.info("从 Redis 获取字典类型全量列表");
                typeList = objectMapper.readValue(json, new TypeReference<List<DictTypeVO>>() {});
            } catch (Exception e) {
                log.error("字典类型缓存解析失败", e);
            }
        }
        if (typeList == null) {
            typeList = this.list().stream().map(type -> {
                DictTypeVO vo = new DictTypeVO();
                BeanUtils.copyProperties(type, vo);
                return vo;
            }).toList();
            if (!typeList.isEmpty()) {
                try {
                    redisTemplate.opsForValue().set(DICT_TYPES_KEY, objectMapper.writeValueAsString(typeList), 7, TimeUnit.DAYS);
                } catch (Exception e) {
                    log.error("字典类型缓存写入失败", e);
                }
            }
            return typeList;
        } else {
            return typeList;
        }
    }
}
