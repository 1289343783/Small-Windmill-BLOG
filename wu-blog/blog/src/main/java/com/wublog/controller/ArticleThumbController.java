package com.wublog.controller;

import com.wublog.domain.ResponseResult;
import com.wublog.domain.dto.ArticleThumbOrStepAddRequest;
import com.wublog.enums.AppHttpCodeEnum;
import com.wublog.exception.SystemException;
import com.wublog.service.BlogThumbService;
import com.wublog.utils.StpUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/thumb")
public class ArticleThumbController {

    @Resource
    private BlogThumbService blogThumbService;


    /**
     * 点赞 / 取消点赞
     *
     * @param articleThumbAddRequest 文章id
     * @return resultNum 本次点赞变化数
     */
    @PostMapping()
    public ResponseResult doThumb(@RequestBody ArticleThumbOrStepAddRequest articleThumbAddRequest) {
        if (articleThumbAddRequest == null || articleThumbAddRequest.getArticleId() <= 0) {
            throw new SystemException(AppHttpCodeEnum.PARAM_NOT_VALID);
        }
        // 登录才能点赞
        Long userId = StpUtils.getUserId();
        Long articleId = articleThumbAddRequest.getArticleId();
        int result = blogThumbService.doPostThumb(articleId, userId);
        if (result <= 0) {
            return ResponseResult.errorResult(500, "点赞失败");
        }
        return ResponseResult.okResult();
    }

    /**
     * 获取当前用户是否已经点赞
     *
     * @param id 文章id
     * @return Boolean
     */
    @GetMapping("/{id}")
    public ResponseResult isThumb(@PathVariable("id") Long id) {
        if (id == null || id <= 0) {
            throw new SystemException(AppHttpCodeEnum.PARAM_NOT_VALID);
        }
        //获取当前用户是否已经点赞获取收藏
        boolean isThumb = blogThumbService.getIsThumb(id, StpUtils.getUserId());
        return ResponseResult.okResult(isThumb);
    }


}
