package com.wublog.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ArticleThumbOrStepAddRequest implements Serializable {

    /**
     * 帖子 id
     */
    private Long articleId;


    private static final long serialVersionUID = 1L;
}
