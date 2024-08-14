package com.wublog.mapper;

import com.wublog.domain.entity.Access;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author wdnmd
* @description 针对表【access(权限表)】的数据库操作Mapper
* @createDate 2024-08-07 15:34:29
* @Entity com.wublog.domain.entity.Access
*/
public interface AccessMapper extends BaseMapper<Access> {

    List<String> selectPermissionsByUserId(Long id);

}




