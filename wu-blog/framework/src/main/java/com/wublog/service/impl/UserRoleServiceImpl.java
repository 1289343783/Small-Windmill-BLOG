package com.wublog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wublog.domain.entity.UserRole;
import com.wublog.service.UserRoleService;
import com.wublog.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author wdnmd
* @description 针对表【user_role】的数据库操作Service实现
* @createDate 2024-08-07 15:34:29
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{

}




