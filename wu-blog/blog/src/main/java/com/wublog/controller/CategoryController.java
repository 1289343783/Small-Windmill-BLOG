package com.wublog.controller;


import com.wublog.domain.ResponseResult;
import com.wublog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categoryCountList")
    public ResponseResult getCategoryCountList() {
        return categoryService.getCategoryCountList();
    }
}
