package com.wublog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wublog.domain.ResponseResult;
import com.wublog.domain.entity.Article;
import com.wublog.domain.entity.ArticleTag;
import com.wublog.domain.entity.Tag;
import com.wublog.domain.vo.TagCountVo;
import com.wublog.mapper.ArticleTagMapper;
import com.wublog.service.ArticleService;
import com.wublog.service.TagService;
import com.wublog.mapper.TagMapper;
import com.wublog.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wdnmd
 * @description 针对表【tag(标签表)】的数据库操作Service实现
 * @createDate 2024-08-07 15:34:29
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
        implements TagService {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Override
    public ResponseResult getTagCountList() {
        // 查询出所有非草稿文章携带的标签 id
        List<Article> articles = articleService.listNormalArticle();
        List<Long> articleIds = articles.stream().map(Article::getId).collect(Collectors.toList());
        QueryWrapper<ArticleTag> articleTagWrapper = new QueryWrapper<>();
        articleTagWrapper.in("article_id", articleIds);
        articleTagWrapper.select("tag_id", "count(*) as count").groupBy("tag_id");
        List<ArticleTag> articleTags = articleTagMapper.selectList(articleTagWrapper);

        // 获取标签名和标签出现次数
        Map<Long, Integer> tagCountMap = articleTags.stream()
                .collect(Collectors.toMap(ArticleTag::getTagId, ArticleTag::getCount));
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Tag::getId, tagCountMap.keySet());
        List<TagCountVo> tagVos = BeanCopyUtils.copyBeanList(list(wrapper), TagCountVo.class);
        tagVos.forEach(tagVo -> tagVo.setCount(tagCountMap.get(tagVo.getId())));

        return ResponseResult.okResult(tagVos);
    }

    @Override
    public Tag getOrAddTagByName(String name) {
        LambdaQueryWrapper<Tag> tagWrapper = new LambdaQueryWrapper<>();
        tagWrapper.eq(Tag::getName, name);
        Tag tag = getOne(tagWrapper);
        if (tag == null) {
            tag = new Tag();
            tag.setName(name);
            save(tag);
        }

        return tag;
    }
}




