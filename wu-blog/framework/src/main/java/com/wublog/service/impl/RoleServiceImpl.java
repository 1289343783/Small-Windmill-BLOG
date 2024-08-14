package com.wublog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wublog.domain.entity.Role;
import com.wublog.service.RoleService;
import com.wublog.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
* @author wdnmd
* @description 针对表【role(角色表)】的数据库操作Service实现
* @createDate 2024-08-07 15:34:29
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

}




