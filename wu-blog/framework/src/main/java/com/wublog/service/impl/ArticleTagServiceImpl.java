package com.wublog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wublog.domain.entity.ArticleTag;
import com.wublog.service.ArticleTagService;
import com.wublog.mapper.ArticleTagMapper;
import org.springframework.stereotype.Service;

/**
* @author wdnmd
* @description 针对表【article_tag(文章-标签表)】的数据库操作Service实现
* @createDate 2024-08-07 15:34:29
*/
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag>
    implements ArticleTagService{

}




