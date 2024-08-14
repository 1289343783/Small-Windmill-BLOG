package com.wublog.controller;


import com.wublog.domain.ResponseResult;
import com.wublog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/tagCountList")
    public ResponseResult getTagCountList(){
        return tagService.getTagCountList();
    }
}
