package com.wublog.controller;


import com.wublog.domain.ResponseResult;
import com.wublog.domain.entity.Comment;
import com.wublog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    public ResponseResult getCommentList(Long articleId, Integer pageNum, Integer pageSize){
        return commentService.getCommentList(articleId, pageNum, pageSize);
    }

    @PostMapping
    public ResponseResult addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }

    @PutMapping
    public ResponseResult updateComment(@RequestBody Comment comment){
        return commentService.updateComment(comment);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteCommand(@PathVariable("id") Long id){
        return commentService.deleteCommand(id);
    }

}
