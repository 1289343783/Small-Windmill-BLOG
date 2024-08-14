package com.wublog.service;

import com.wublog.domain.ResponseResult;
import com.wublog.domain.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author wdnmd
* @description 针对表【comment(评论表)】的数据库操作Service
* @createDate 2024-08-07 15:34:29
*/
public interface CommentService extends IService<Comment> {

    ResponseResult getCommentList(Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);

    ResponseResult updateComment(Comment comment);

    ResponseResult deleteCommand(Long id);

}
