package com.wublog.controller;

import com.wublog.domain.ResponseResult;
import com.wublog.domain.dto.ArticleThumbOrStepAddRequest;
import com.wublog.domain.service.BlogStepService;
import com.wublog.enums.AppHttpCodeEnum;
import com.wublog.exception.SystemException;
import com.wublog.utils.StpUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/step")
public class ArticleStepController {

    @Resource
    private BlogStepService blogStepService;


    /**
     * 点赞 / 取消点赞
     *
     * @param request 文章id
     * @return resultNum 本次点赞变化数
     */
    @PostMapping()
    public ResponseResult doStep(@RequestBody ArticleThumbOrStepAddRequest request) {
        if (request == null || request.getArticleId() <= 0) {
            throw new SystemException(AppHttpCodeEnum.PARAM_NOT_VALID);
        }
        // 登录才能点赞
        Long userId = StpUtils.getUserId();
        Long articleId = request.getArticleId();
        int result = blogStepService.doPostStep(articleId, userId);
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
    public ResponseResult isStep(@PathVariable("id") Long id) {
        if (id == null || id <= 0) {
            throw new SystemException(AppHttpCodeEnum.PARAM_NOT_VALID);
        }
        //获取当前用户是否已经点赞获取收藏
        boolean isStep = blogStepService.getIsStep(id, StpUtils.getUserId());
        return ResponseResult.okResult(isStep);
    }
}
