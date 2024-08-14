package com.wublog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVo {
    private Long id;

    private Long articleId;

    private String content;

    private Long createBy;

    private Date createTime;

    private String userName;

    private String avatar;

    private Boolean isAdmin;
}
