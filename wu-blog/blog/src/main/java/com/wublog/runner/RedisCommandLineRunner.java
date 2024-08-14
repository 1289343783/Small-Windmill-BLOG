package com.wublog.runner;


import com.wublog.constants.SystemConstants;
import com.wublog.domain.entity.Article;
import com.wublog.service.ArticleService;
import com.wublog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RedisCommandLineRunner implements CommandLineRunner {
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    @Override
    public void run(String... args) {
        List<Article> articles = articleService.listViewCount();
        Map<String, Integer> viewCountMap = articles.stream()
                .collect(Collectors.toMap(
                        article -> article.getId().toString(),
                        article -> article.getViewCount().intValue()
                ));
        redisCache.setCacheMap(SystemConstants.REDIS_ARTICLE_VIEW_COUNT_KEY, viewCountMap);
    }
}
