package com.wublog.mapper;

import com.wublog.domain.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wublog.domain.vo.ArchiveCountVo;
import com.wublog.domain.vo.ArchiveVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author wdnmd
* @description 针对表【article(文章表)】的数据库操作Mapper
* @createDate 2024-08-07 15:34:29
* @Entity com.wublog.domain.entity.Article
*/
public interface ArticleMapper extends BaseMapper<Article> {

}




