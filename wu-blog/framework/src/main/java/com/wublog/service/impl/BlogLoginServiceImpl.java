package com.wublog.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wublog.constants.SystemConstants;
import com.wublog.domain.ResponseResult;
import com.wublog.domain.dto.LoginUserDTO;
import com.wublog.domain.entity.User;
import com.wublog.domain.vo.BlogUserLoginVo;
import com.wublog.domain.vo.UserInfoVo;
import com.wublog.enums.AppHttpCodeEnum;
import com.wublog.exception.SystemException;
import com.wublog.mapper.UserMapper;
import com.wublog.service.BlogLoginService;
import com.wublog.utils.Assert;
import com.wublog.utils.BeanCopyUtils;
import com.wublog.utils.RedisCache;
import com.wublog.utils.StpUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisCache redisCache;

    @Override
    public ResponseResult login(LoginUserDTO loginUserDTO) {
        // 验证用户名
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUserName, loginUserDTO.getUserName()));
        Assert.notNull(user, AppHttpCodeEnum.LOGIN_ERROR);

        // 验证密码
        if (LoginRecord(user, loginUserDTO.getPassword())) {
            throw new SystemException(AppHttpCodeEnum.LOGIN_ERROR);
        }
        StpUtil.login(user.getId());
        // 将用户信息存入 redis
        String userId = user.getId().toString();
        UserInfoVo userInfo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        redisCache.setCacheObject(SystemConstants.REDIS_USER_ID_PREFIX + userId, userInfo);
        // 将 token 和用户信息返回给用户
        String token = StpUtil.getTokenValue();
        userInfo.setIsAdmin(SystemConstants.ADMIN_USER.equals(user.getType()));
        BlogUserLoginVo blogUserLoginVo = new BlogUserLoginVo(token, userInfo);
        return ResponseResult.okResult(blogUserLoginVo);
    }

    @Override
    public ResponseResult logout() {
        // 从 redis 中删除用户信息
        redisCache.deleteObject(SystemConstants.REDIS_USER_ID_PREFIX + StpUtils.getUserId().toString());
        StpUtil.logout();
        return ResponseResult.okResult();
    }

    /**
     * 校验密码
     *
     * @param user     用户信息
     * @param password 用户登录密码
     * @return boolean
     */
    private boolean LoginRecord(User user, String password) {
        String md5Pwd = SecureUtil.md5(password);
        if (!md5Pwd.equals(user.getPassword())) {
            return false;
        }
        // 登录成功
        return true;
    }
}
