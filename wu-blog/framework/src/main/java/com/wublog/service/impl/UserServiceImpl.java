package com.wublog.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wublog.constants.SystemConstants;
import com.wublog.domain.ResponseResult;
import com.wublog.domain.dto.UserDTO;
import com.wublog.domain.entity.User;
import com.wublog.domain.vo.UserInfoVo;
import com.wublog.enums.AppHttpCodeEnum;
import com.wublog.exception.SystemException;
import com.wublog.service.UserService;
import com.wublog.mapper.UserMapper;
import com.wublog.utils.Assert;
import com.wublog.utils.BeanCopyUtils;
import com.wublog.utils.StpUtils;
import org.springframework.stereotype.Service;

/**
 * @author wdnmd
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2024-08-07 15:34:29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {


    @Override
    public ResponseResult getUserInfo() {
        Long userId = StpUtils.getUserId();
        User user = getById(userId);
        if (user != null)
            return ResponseResult.okResult(BeanCopyUtils.copyBean(user, UserInfoVo.class));

        return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
    }

    @Override
    public ResponseResult getAdminInfo() {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getType, SystemConstants.ADMIN_USER);
        User user = getOne(wrapper, false);
        return ResponseResult.okResult(BeanCopyUtils.copyBean(user, UserInfoVo.class));
    }

    @Override
    public ResponseResult register(User user) {
        // 查询用户是否已经存在
        LambdaQueryWrapper<User> sameNameWrapper = new LambdaQueryWrapper<>();
        sameNameWrapper.eq(User::getUserName, user.getUserName());
        User sameNameUser = getOne(sameNameWrapper);
        Assert.isNull(sameNameUser, AppHttpCodeEnum.USERNAME_EXIST);

        // 查询邮箱是否已经存在
        LambdaQueryWrapper<User> sameEmailWrapper = new LambdaQueryWrapper<>();
        sameEmailWrapper.eq(User::getEmail, user.getEmail());
        User sameEmailUser = getOne(sameEmailWrapper);
        Assert.isNull(sameEmailUser, AppHttpCodeEnum.EMAIL_EXIST);

        String salt = RandomUtil.randomString(32);
        String md5Pwd = SecureUtil.md5(user.getPassword() + salt);
        // 添加用户
        user.setPassword(md5Pwd);
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateUserInfo(UserDTO user) {
        // 查询邮箱是否已经存在
        LambdaQueryWrapper<User> sameEmailWrapper = new LambdaQueryWrapper<>();
        sameEmailWrapper.eq(User::getEmail, user.getEmail());
        User sameEmailUser = getOne(sameEmailWrapper);
        if (sameEmailUser != null && !user.getId().equals(sameEmailUser.getId())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }

        updateById(BeanCopyUtils.copyBean(user, User.class));
        return ResponseResult.okResult();
    }
}




