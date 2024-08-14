package com.wublog.controller;


import com.wublog.domain.ResponseResult;
import com.wublog.domain.dto.ArticleDTO;
import com.wublog.domain.dto.ArticleQueryDTO;
import com.wublog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/hotArticleList")
    public ResponseResult getHotArticleList() {
        return articleService.getHotArticleList();
    }

    @GetMapping("/articleList")
    public ResponseResult getArticleList(@Valid ArticleQueryDTO articleQueryDTO){
        return articleService.getArticleList(articleQueryDTO);
    }

    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id) {
        return articleService.getArticleDetail(id);
    }

    @PostMapping
//    @PreAuthorize("hasAuthority('article:add')")
    public ResponseResult addArticle(@Valid @RequestBody ArticleDTO article){
        return articleService.addArticle(article);
    }

    @PutMapping
//    @PreAuthorize("hasAuthority('article:edit')")
    public ResponseResult editArticle(@Valid @RequestBody ArticleDTO article){
        return articleService.editArticle(article);
    }

    @GetMapping("/count")
    public ResponseResult getArticleCount(){
        return articleService.getArticleCount();
    }

    @PutMapping("/updateViewCount/{id}")
    public ResponseResult updateViewCount(@PathVariable Long id){
        return articleService.updateViewCount(id);
    }

    @GetMapping("/previousNextArticle/{id}")
    public ResponseResult getPreviousNextArticle(@PathVariable Long id){
        return articleService.getPreviousNextArticle(id);
    }

}
