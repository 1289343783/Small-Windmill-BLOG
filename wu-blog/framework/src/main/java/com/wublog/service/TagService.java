package com.wublog.service;

import com.wublog.domain.ResponseResult;
import com.wublog.domain.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author wdnmd
* @description 针对表【tag(标签表)】的数据库操作Service
* @createDate 2024-08-07 15:34:29
*/
public interface TagService extends IService<Tag> {

    ResponseResult getTagCountList();
    Tag getOrAddTagByName(String name);

}
