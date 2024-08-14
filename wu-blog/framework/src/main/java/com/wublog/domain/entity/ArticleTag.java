package com.wublog.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章-标签表
 *
 * @TableName article_tag
 */
@TableName(value = "article_tag")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ArticleTag implements Serializable {
    /**
     * 文章 ID
     */
    private Long articleId;

    /**
     * 标签 ID
     */
    private Long tagId;

    /**
     * 标签的出现次数
     */
    @TableField(exist = false)
    private Integer count = 0;

    public ArticleTag(Long articleId, Long tagId) {
        this.articleId = articleId;
        this.tagId = tagId;
    }

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}