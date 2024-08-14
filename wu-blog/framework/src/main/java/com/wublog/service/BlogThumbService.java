package com.wublog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wublog.domain.entity.BlogThumb;

/**
 * @author wdnmd
 * @description 针对表【blog_thumb(帖子点赞)】的数据库操作Service
 * @createDate 2024-08-14 16:30:37
 */
public interface BlogThumbService extends IService<BlogThumb> {

    /**
     * 查询文章当前用户是否已点赞
     *
     * @param articleId 文章id
     * @param userId 用户id
     * @return boolean
     */
    boolean getIsThumb(Long articleId, Long userId);

    /**
     * 文章电赞或取消点赞
     *
     * @param articleId 文章id
     * @param userId 用户id
     * @return int
     */
    int doPostThumb(Long articleId, Long userId);

    int doPostThumbInner(Long userId, Long articleId);


}
