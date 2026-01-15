package com.gcxy.smartfarm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gcxy.smartfarm.common.enums.ResultCode;
import com.gcxy.smartfarm.common.exception.BaseException;
import com.gcxy.smartfarm.common.result.PageResult;
import com.gcxy.smartfarm.common.utils.JwtUtils;
import com.gcxy.smartfarm.dto.UserAddDTO;
import com.gcxy.smartfarm.dto.UserLoginDTO;
import com.gcxy.smartfarm.dto.UserPageQueryDTO;
import com.gcxy.smartfarm.dto.UserUpdateDTO;
import com.gcxy.smartfarm.entity.Role;
import com.gcxy.smartfarm.entity.User;
import com.gcxy.smartfarm.mapper.RoleMapper;
import com.gcxy.smartfarm.mapper.UserMapper;
import com.gcxy.smartfarm.service.UserService;
import com.gcxy.smartfarm.vo.UserLoginVO;
import com.gcxy.smartfarm.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

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
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 登录
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

    /**
     * 新增员工
     * @param userAddDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUser(UserAddDTO userAddDTO) {
        // 手机号唯一性检验
        if (userMapper.selectCount(new LambdaQueryWrapper<User>().eq(User::getPhone, userAddDTO.getPhone())) > 0) {
            throw new BaseException(ResultCode.USER_PHONE_EXIST);
        }
        // 创建员工对象
        User user = new User();
        BeanUtils.copyProperties(userAddDTO, user);
        // 生成用户名
        Long seq = redisTemplate.opsForValue().increment("sys:user:seq");
        user.setUsername(String.format("YZ%04d", seq));
        // 生成密码
        String phone = userAddDTO.getPhone();
        String password = phone.substring(phone.length() - 6);
        user.setPassword(passwordEncoder.encode(password));
        // 增加身份（默认普通用户）
        user.setRoleId(2L);
        // 保存用户
        this.save(user);
    }

    /**
     * 分页查询员工
     * @param userPageQueryDTO
     * @return
     */
    @Override
    public PageResult<UserVO> pageQuery(UserPageQueryDTO userPageQueryDTO) {
        Page<User> pageInfo = new Page<>(userPageQueryDTO.getPage(), userPageQueryDTO.getPageSize());
        // 姓名、手机号、状态查询
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(userPageQueryDTO.getRealName()), User::getRealName, userPageQueryDTO.getRealName())
                .like(StringUtils.hasText(userPageQueryDTO.getPhone()), User::getPhone, userPageQueryDTO.getPhone())
                .eq(userPageQueryDTO.getStatus() != null, User::getStatus, userPageQueryDTO.getStatus())
                .orderByDesc(User::getCreateTime);
        Page<User> userPage = userMapper.selectPage(pageInfo, wrapper);
        List<UserVO> voList = userPage.getRecords().stream().map(user -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            if (user.getRoleId() != null) {
                Role role = roleMapper.selectById(user.getRoleId());
                userVO.setRoleName(role.getRoleName());
            }
            return userVO;
        }).toList();
        return new PageResult<>(userPage.getTotal(), voList);
    }

    /**
     * 修改员工
     * @param userUpdateDTO
     */
    @Override
    public void updateUser(UserUpdateDTO userUpdateDTO) {
        User user = getById(userUpdateDTO.getId());
        if (user == null) {
            throw new BaseException(ResultCode.USER_NOT_EXIST);
        }
        if (user.getRoleId() == 1){
            throw new BaseException(ResultCode.ADMIN_NOT_UPDATE);
        }
        Long count = userMapper.selectCount(new LambdaQueryWrapper<User>()
                .eq(User::getPhone, userUpdateDTO.getPhone())
                .ne(User::getId, userUpdateDTO.getId()));
        if (count > 0) {
            throw new BaseException(ResultCode.USER_PHONE_EXIST);
        }
        user = new User();
        BeanUtils.copyProperties(userUpdateDTO, user);
        this.updateById(user);
    }

    /**
     * 修改员工状态
     * @param status
     * @param id
     */
    @Override
    public void updateStatus(Integer status, Long id) {
        User user = getById(id);
        if (user == null) {
            throw new BaseException(ResultCode.USER_NOT_EXIST);
        }
        if (user.getRoleId() == 1){
            throw new BaseException(ResultCode.ADMIN_NOT_UPDATE);
        }
        user.setStatus(status);
        this.updateById(user);
    }

    /**
     * 删除员工
     * @param id
     */
    @Override
    public void deleteUser(Long id) {
        User user = getById(id);
        if (user == null) {
            throw new BaseException(ResultCode.USER_NOT_EXIST);
        }
        if (user.getRoleId() == 1){
            throw new BaseException(ResultCode.ADMIN_NOT_DELETE);
        }
        this.removeById(id);

    }
}
