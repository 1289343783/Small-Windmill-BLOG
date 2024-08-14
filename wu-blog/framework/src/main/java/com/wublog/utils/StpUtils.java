package com.wublog.utils;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.log.Log;
import com.wublog.constants.SystemConstants;
import com.wublog.domain.vo.UserInfoVo;
import com.wublog.enums.AppHttpCodeEnum;
import com.wublog.exception.SystemException;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class StpUtils {


    @Resource
    private RedisCache redisCache;


    /**
     * 获取当前登录用户id
     *
     * @return Integer
     */
    public static Long getUserId() {
        long userId;
        try {
            userId = StpUtil.getLoginIdAsLong();
        } catch (Exception e) {
            System.out.println(e);
            throw new SystemException(AppHttpCodeEnum.NEED_LOGIN);
        }
        return userId;
    }

    public Boolean isAdmin() {
        Long id = getLoginUser().getId();
        return id != null && 1L == id;
    }

    /**
     * 获取当前登录用户新
     *
     * @return User
     */
    public UserInfoVo getLoginUser() {
        int loginId = StpUtil.getLoginIdAsInt();
        if (loginId == -1) {
            throw new SystemException(AppHttpCodeEnum.NEED_LOGIN);
        }
        UserInfoVo UserInfoVo = redisCache.getCacheObject(SystemConstants.REDIS_USER_ID_PREFIX + loginId);
        if (UserInfoVo == null) {
            throw new SystemException(AppHttpCodeEnum.RESOURCE_NOT_EXIST);
        }
        return UserInfoVo;
    }
}
