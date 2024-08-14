package com.wublog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wublog.domain.entity.Access;
import com.wublog.service.AccessService;
import com.wublog.mapper.AccessMapper;
import org.springframework.stereotype.Service;

/**
* @author wdnmd
* @description 针对表【access(权限表)】的数据库操作Service实现
* @createDate 2024-08-07 15:34:29
*/
@Service
public class AccessServiceImpl extends ServiceImpl<AccessMapper, Access>
    implements AccessService{

}




