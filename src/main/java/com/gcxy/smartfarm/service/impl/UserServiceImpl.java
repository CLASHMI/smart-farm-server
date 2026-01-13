package com.gcxy.smartfarm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gcxy.smartfarm.common.utils.JwtUtils;
import com.gcxy.smartfarm.dto.UserLoginDTO;
import com.gcxy.smartfarm.entity.Role;
import com.gcxy.smartfarm.entity.User;
import com.gcxy.smartfarm.mapper.RoleMapper;
import com.gcxy.smartfarm.mapper.UserMapper;
import com.gcxy.smartfarm.service.UserService;
import com.gcxy.smartfarm.vo.UserLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * UserServiceImpl 类
 *
 * @author NaNloopia
 * @version 1.0
 * @created 16:10
 * @since 2026/1/13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RoleMapper roleMapper;

    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    @Override
    public UserLoginVO login(UserLoginDTO userLoginDTO) {
        // 认证
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword())
        );
        // 查用户
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, userLoginDTO.getUsername()));
        // 创建 Token
        String token = jwtUtils.createToken(user.getId(), user.getUsername());
        // 查角色名称
        String roleName = "";
        if (user.getRoleId() !=  null) {
            Role role = roleMapper.selectById(user.getRoleId());
            roleName = role.getRoleName();
        }
        // 封装成 UserLoginVO返回
        return UserLoginVO.builder()
                .token(token)
                .username(user.getUsername())
                .realName(user.getRealName())
                .roleName(roleName)
                .phone(user.getPhone())
                .build();
    }
}
