package com.wublog.domain.service;

import com.wublog.domain.entity.BlogStep;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wdnmd
 * @description 针对表【blog_step(帖子踩)】的数据库操作Service
 * @createDate 2024-08-14 18:35:35
 */
public interface BlogStepService extends IService<BlogStep> {

    /**
     * 查询文章当前用户是否已点赞
     *
     * @param articleId 文章id
     * @param userId    用户id
     * @return boolean
     */
    boolean getIsStep(Long articleId, Long userId);

    /**
     * 文章踩或取消点踩
     *
     * @param articleId 文章id
     * @param userId    用户id
     * @return int
     */
    int doPostStep(Long articleId, Long userId);

    @Transactional(rollbackFor = Exception.class)
    int doPostStepInner(Long userId, Long articleId);
}
