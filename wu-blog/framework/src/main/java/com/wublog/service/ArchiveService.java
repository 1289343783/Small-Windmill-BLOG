package com.wublog.service;


import com.wublog.domain.ResponseResult;

public interface ArchiveService {
    ResponseResult getArchiveCountList(Integer pageNum, Integer pageSize);
    ResponseResult getArchiveList(Integer pageNum, Integer pageSize);
}
