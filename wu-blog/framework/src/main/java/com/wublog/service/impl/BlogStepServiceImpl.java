package com.wublog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wublog.domain.entity.Article;
import com.wublog.domain.entity.BlogStep;
import com.wublog.domain.entity.BlogThumb;
import com.wublog.domain.service.BlogStepService;
import com.wublog.enums.AppHttpCodeEnum;
import com.wublog.exception.SystemException;
import com.wublog.mapper.BlogStepMapper;
import com.wublog.service.ArticleService;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author wdnmd
 * @description 针对表【blog_step(帖子踩)】的数据库操作Service实现
 * @createDate 2024-08-14 18:35:35
 */
@Service
public class BlogStepServiceImpl extends ServiceImpl<BlogStepMapper, BlogStep>
        implements BlogStepService {


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
    public boolean getIsStep(Long articleId, Long userId) {
        //获取当前用户是否对文章踩
        BlogStep blogStep = new BlogStep();
        blogStep.setUserId(userId);
        blogStep.setArticleId(articleId);
        BlogStep one = this.getOne(new LambdaQueryWrapper<>(blogStep));
        return one != null;
    }

    /**
     * 文章踩或取消点踩
     *
     * @param articleId 文章id
     * @param userId    用户id
     * @return int
     */
    @Override
    public int doPostStep(Long articleId, Long userId) {
        // 判断实体是否存在，根据类别获取实体
        Article article = articleService.getById(articleId);
        if (article == null) {
            throw new SystemException(AppHttpCodeEnum.RESOURCE_NOT_EXIST);
        }
        // 是否已点赞
        // 每个用户串行点赞
        // 锁必须要包裹住事务方法
        BlogStepService blogstepService = (BlogStepService) AopContext.currentProxy();
        synchronized (String.valueOf(userId).intern()) {
            return blogstepService.doPostStepInner(userId, articleId);
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
    public int doPostStepInner(Long userId, Long articleId) {
        BlogStep blogStep = new BlogStep();
        blogStep.setUserId(userId);
        blogStep.setArticleId(articleId);
        QueryWrapper<BlogStep> stepQueryWrapper = new QueryWrapper<>(blogStep);
        BlogStep oldBlogStep = this.getOne(stepQueryWrapper);
        boolean result;
        // 已点赞
        if (oldBlogStep != null) {
            result = this.remove(stepQueryWrapper);
            if (result) {
                // 点赞数 - 1
                result = articleService.update()
                        .eq("id", articleId)
                        .gt("article_step", 0)
                        .setSql("article_step = article_step - 1")
                        .update();
                return result ? 1 : 0;
            } else {
                throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
            }
        } else {
            // 未点赞
            result = this.save(blogStep);
            if (result) {
                // 点赞数 + 1
                result = articleService.update()
                        .eq("id", articleId)
                        .setSql("article_step = article_step + 1")
                        .update();
                return result ? 1 : 0;
            } else {
                throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
            }
        }
    }

}




