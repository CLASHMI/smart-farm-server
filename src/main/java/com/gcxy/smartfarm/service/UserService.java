package com.gcxy.smartfarm.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gcxy.smartfarm.dto.UserLoginDTO;
import com.gcxy.smartfarm.entity.User;
import com.gcxy.smartfarm.vo.UserLoginVO;

/**
 * @author NaNloopia
 * @version 1.0
 * @created 16:06
 * @since 2026/1/13
 */
public interface UserService extends IService<User> {

    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    UserLoginVO login(UserLoginDTO userLoginDTO);
}
