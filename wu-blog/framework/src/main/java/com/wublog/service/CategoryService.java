package com.wublog.service;

import com.wublog.domain.ResponseResult;
import com.wublog.domain.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author wdnmd
* @description 针对表【category(分类表)】的数据库操作Service
* @createDate 2024-08-07 15:34:29
*/
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryCountList();
    Category getOrAddCategoryByName(String name);

}
