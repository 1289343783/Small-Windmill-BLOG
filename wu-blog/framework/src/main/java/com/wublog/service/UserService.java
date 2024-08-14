package com.wublog.service;

import com.wublog.domain.ResponseResult;
import com.wublog.domain.dto.UserDTO;
import com.wublog.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author wdnmd
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2024-08-07 15:34:29
*/
public interface UserService extends IService<User> {

    ResponseResult getUserInfo();
    ResponseResult getAdminInfo();
    ResponseResult register(User user);
    ResponseResult updateUserInfo(UserDTO user);

}
