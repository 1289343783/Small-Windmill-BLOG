package com.wublog.service;

import com.wublog.domain.ResponseResult;
import com.wublog.domain.dto.LoginUserDTO;

public interface BlogLoginService {

    ResponseResult login(LoginUserDTO user);
    ResponseResult logout();
}
