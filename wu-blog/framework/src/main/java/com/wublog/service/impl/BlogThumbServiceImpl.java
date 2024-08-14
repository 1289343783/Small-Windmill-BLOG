package com.wublog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wublog.domain.entity.Article;
import com.wublog.domain.entity.BlogThumb;

import com.wublog.enums.AppHttpCodeEnum;
import com.wublog.exception.SystemException;
import com.wublog.mapper.BlogThumbMapper;
import com.wublog.service.ArticleService;
import com.wublog.service.BlogThumbService;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author wdnmd
 * @description 针对表【blog_thumb(帖子点赞)】的数据库操作Service实现
 * @createDate 2024-08-14 16:30:37
 */
@Service
public class BlogThumbServiceImpl extends ServiceImpl<BlogThumbMapper, BlogThumb>
        implements BlogThumbService {


    @Resource
    private ArticleService articleService;

    /**
     * 查询文章当前用户是否已点赞
     *
     * @param articleId 文章id
     * @param userId    用户id
     * @return boolean
     */
    @Override
    public boolean getIsThumb(Long articleId, Long userId) {
        //获取当前用户是否对文章点赞
        BlogThumb blogThumb = new BlogThumb();
        blogThumb.setUserId(userId);
        blogThumb.setArticleId(articleId);
        BlogThumb noe = this.getOne(new LambdaQueryWrapper<>(blogThumb));
        return noe != null;
    }

    /**
     * 文章点赞或取消点赞
     *
     * @param articleId 文章id
     * @param userId    用户id
     * @return int
     */
    @Override
    public int doPostThumb(Long articleId, Long userId) {
        // 判断实体是否存在，根据类别获取实体
        Article article = articleService.getById(articleId);
        if (article == null) {
            throw new SystemException(AppHttpCodeEnum.RESOURCE_NOT_EXIST);
        }
        // 是否已点赞
        // 每个用户串行点赞
        // 锁必须要包裹住事务方法
        BlogThumbService blogThumbService = (BlogThumbService) AopContext.currentProxy();
        synchronized (String.valueOf(userId).intern()) {
            return blogThumbService.doPostThumbInner(userId, articleId);
        }
    }

    /**
     * 封装了事务的方法
     *
     * @param userId    用户id
     * @param articleId 文章id
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int doPostThumbInner(Long userId, Long articleId) {
        BlogThumb blogThumb = new BlogThumb();
        blogThumb.setUserId(userId);
        blogThumb.setArticleId(articleId);
        QueryWrapper<BlogThumb> thumbQueryWrapper = new QueryWrapper<>(blogThumb);
        BlogThumb oldBlogThumb = this.getOne(thumbQueryWrapper);
        boolean result;
        // 已点赞
        if (oldBlogThumb != null) {
            result = this.remove(thumbQueryWrapper);
            if (result) {
                // 点赞数 - 1
                result = articleService.update()
                        .eq("id", articleId)
                        .gt("article_like", 0)
                        .setSql("article_like = article_like - 1")
                        .update();
                return result ? 1 : 0;
            } else {
                throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
            }
        } else {
            // 未点赞
            result = this.save(blogThumb);
            if (result) {
                // 点赞数 + 1
                result = articleService.update()
                        .eq("id", articleId)
                        .setSql("article_like = article_like + 1")
                        .update();
                return result ? 1 : 0;
            } else {
                throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
            }
        }
    }

}




