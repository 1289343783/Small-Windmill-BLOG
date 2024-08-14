package com.wublog.service;

import com.wublog.domain.ResponseResult;
import com.wublog.domain.dto.ArticleDTO;
import com.wublog.domain.dto.ArticleQueryDTO;
import com.wublog.domain.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author wdnmd
 * @description 针对表【article(文章表)】的数据库操作Service
 * @createDate 2024-08-07 15:34:29
 */
public interface ArticleService extends IService<Article> {
    List<Article> listNormalArticle();

    Long getNormalArticleCount();

    ResponseResult getHotArticleList();

    ResponseResult getArticleList(ArticleQueryDTO articleQueryDTO);

    ResponseResult getArticleDetail(Long id);

    ResponseResult getArticleCount();

    List<Article> listViewCount();

    ResponseResult updateViewCount(Long id);

    ResponseResult getPreviousNextArticle(Long id);

    ResponseResult addArticle(ArticleDTO article);

    ResponseResult editArticle(ArticleDTO article);
}
