package com.wublog.controller;


import com.wublog.domain.ResponseResult;
import com.wublog.domain.dto.UserDTO;
import com.wublog.domain.entity.User;
import com.wublog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    public ResponseResult getUserInfo() {
        return userService.getUserInfo();
    }

    @PutMapping("/userInfo")
    public ResponseResult updateUserInfo(@Valid @RequestBody UserDTO user) {
        return userService.updateUserInfo(user);
    }

    @GetMapping("/adminInfo")
    public ResponseResult getAdminInfo() {
        return userService.getAdminInfo();
    }

    @PostMapping("/register")
    public ResponseResult register(@Valid @RequestBody User user) {
        return userService.register(user);
    }
}
