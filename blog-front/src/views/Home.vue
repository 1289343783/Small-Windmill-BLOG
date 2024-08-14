<template>
  <div id="home">
    <!-- 页头 -->
    <BlogHeader/>
    <BlogCover content="一个追求有道有术的 != 非典型程序员" title="✨ Small-Windmill の 博客 ✨">
    </BlogCover>

    <div class="container">
      <!-- 侧边栏 -->
      <div class="side-content">
        <BlogAdminCard class="mb-8"/>
        <BlogHotArticleCard/>
        <BlogCategoryCard class="mb-8"/>
        <BlogTagCard/>
      </div>
      <!-- 发表的文章 -->
      <div class="post-article-list">
        <BlogPostArticleCard
            v-for="(article, index) in postArticles"
            :key="article.id"
            :article="article"
            :reverse="index % 2 == 1"
        />
        <!-- 分页 -->
        <el-pagination
            v-if="articleCount > 0"
            id="pagination"
            :page-size="pageSize"
            :total="articleCount"
            background
            layout="prev, pager, next"
            @current-change="onCurrentPageChanged"
        >
        </el-pagination>
      </div>
    </div>
    <!-- 页脚 -->
    <BlogFooter adminName="无敌小风车大王"/>
    <!-- 滚动到顶部按钮 -->
    <BlogBackToTop/>
  </div>
</template>

<script lang="ts" setup>
import BlogHeader from "@/components/BlogHeader.vue";
import BlogCover from "@/components/BlogCover.vue";
import BlogAdminCard from "@/components/BlogAdminCard.vue";
import BlogHotArticleCard from "@/components/BlogHotArticleCard.vue";
import BlogPostArticleCard from "@/components/BlogPostArticleCard.vue";
import BlogCategoryCard from "@/components/BlogCategoryCard.vue";
import BlogTagCard from "@/components/BlogTagCard.vue";
import {reactive, ref} from "vue";
import {getPostArticleListApi} from "@/api/article";
import {defaultThumbnail} from "@/utils/thumbnail";
import {onMounted} from "vue";
import BlogFooter from "@/components/BlogFooter.vue";
import BlogBackToTop from "@/components/BlogBackToTop.vue";

const img = "/article-thumbnail.jpg";
let articleCount = ref<number>(0);
let pageSize = ref<number>(10);
const postArticles = reactive<IArticles[]>([]);

const onCurrentPageChanged = async (pageNum: number) => {
  const res = await getPostArticleListApi(pageNum, pageSize.value);
  if (res.code == 200) {
    articleCount.value = parseInt(res.data.total);
    res.data.rows.forEach((article) => {
      article.createTime = article.createTime.split(" ")[0];
      article.thumbnail = article.thumbnail || defaultThumbnail;
    });
    // 重置列表内容
    postArticles.splice(0, postArticles.length, ...res.data.rows);
  }
};

onMounted(() => {
  onCurrentPageChanged(1);
});
</script>

<style lang="less" scoped>
#home {
  height: 100%;
  width: 100%;
}

.container {
  padding: 40px 15px;
  max-width: 1300px;
  margin: 0 auto;
  display: flex;
  animation: fadeInUp 1s;
}

.post-article-list {
  width: 76%;

  .post-article-card {
    margin-top: 20px;
  }

  .post-article-card:nth-child(1) {
    margin-top: 0;
  }
}

.side-content {
  width: 28%;
  margin-right: 20px;
  margin-bottom: 20px;
}

:deep(#pagination) {
  margin-top: 20px;
  justify-content: center;

  & > button {
    box-shadow: var(--card-box-shadow);
    background: white;
    border-radius: 8px;
    height: 35px;
    width: 35px;
  }

  li {
    box-shadow: var(--card-box-shadow);
    border-radius: 8px;
    margin: 0 6px;
    height: 35px;
    width: 35px;
    background-color: rgb(86, 149, 233);
    // 字体颜色
    color: white;
  }

  li.active {
    color: white;
    background: var(--theme-color);
    font-weight: normal;
  }
}

@media screen and (max-width: 900px) {
  .side-content {
    display: none;
  }

  .post-article-list {
    width: 100%;
  }
}

@keyframes fadeInUp {
  from {
    margin-top: 50px;
    opacity: 0;
  }

  to {
    margin-top: 0;
    opacity: 1;
  }
}
</style>
