package com.gcxy.smartfarm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gcxy.smartfarm.common.constant.MessageConstant;
import com.gcxy.smartfarm.entity.Role;
import com.gcxy.smartfarm.entity.User;
import com.gcxy.smartfarm.mapper.RoleMapper;
import com.gcxy.smartfarm.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * UserDetailsServiceImpl 类
 *
 * @author NaNloopia
 * @version 1.0
 * @created 15:00
 * @since 2026/1/13
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. 查用户表
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username));

        // 2. 查无此人 -> 抛异常
        if (user == null) {
            throw new UsernameNotFoundException(MessageConstant.User.NOT_EXIST);
        }

        // 3. 账号被禁用 -> 抛 DisabledException
        if (user.getStatus() != null && user.getStatus() == 1) {
            throw new DisabledException(MessageConstant.Auth.ACCOUNT_DISABLED);
        }

        // 4. 封装权限 (需要用 roleId 查 Role 表)
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (user.getRoleId() != null) {
            Role role = roleMapper.selectById(user.getRoleId());
            if (role != null) {
                // 将角色名作为权限，比如 "超级管理员"
                authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
            }
        }

        // 5. 返回 Security 的 User 对象
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}