package com.gcxy.smartfarm.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gcxy.smartfarm.common.result.PageResult;
import com.gcxy.smartfarm.dto.UserAddDTO;
import com.gcxy.smartfarm.dto.UserLoginDTO;
import com.gcxy.smartfarm.dto.UserPageQueryDTO;
import com.gcxy.smartfarm.dto.UserUpdateDTO;
import com.gcxy.smartfarm.entity.User;
import com.gcxy.smartfarm.vo.UserLoginVO;
import com.gcxy.smartfarm.vo.UserVO;

/**
 * @author NaNloopia
 * @version 1.0
 * @created 16:06
 * @since 2026/1/13
 */
public interface UserService extends IService<User> {

    /**
     * 登录
     * @param userLoginDTO
     * @return
     */
    UserLoginVO login(UserLoginDTO userLoginDTO);

    /**
     * 新增员工
     * @param userAddDTO
     */
    void addUser(UserAddDTO userAddDTO);

    /**
     * 分页查询员工
     * @param userPageQueryDTO
     * @return
     */
    PageResult<UserVO> pageQuery(UserPageQueryDTO userPageQueryDTO);

    /**
     * 修改员工
     * @param userUpdateDTO
     */
    void updateUser(UserUpdateDTO userUpdateDTO);

    /**
     * 修改员工状态
     * @param status
     * @param id
     */
    void updateStatus(Integer status, Long id);

    /**
     * 删除员工
     * @param id
     */
    void deleteUser(Long id);
}
