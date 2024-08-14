<template>
  <div id="archive-details">
    <!-- 页头 -->
    <BlogHeader />

    <!-- 二次元封面 -->
    <BlogWifeCover>
      <div class="archive-info">
        <h1 class="archive-date">{{ year }} 年 {{ month }} 月</h1>
      </div>
    </BlogWifeCover>

    <div class="container">
      <!-- 侧边栏 -->
      <BlogSideBar />

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
          background
          layout="prev, pager, next"
          :total="articleCount"
          :page-size="pageSize"
          id="pagination"
          @current-change="onCurrentPageChanged"
          v-if="articleCount > 0"
        >
        </el-pagination>
      </div>
    </div>

    <!-- 页脚 -->
    <BlogFooter />

    <!-- 回到顶部 -->
    <BlogBackToTop />
  </div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from "vue";
import { getPostArticleListApi } from "@/api/article";
import { defaultThumbnail } from "@/utils/thumbnail";
import BlogHeader from "@/components/BlogHeader.vue";
import BlogWifeCover from "@/components/BlogWifeCover.vue";
import BlogSideBar from "@/components/BlogSideBar.vue";
import BlogPostArticleCard from "@/components/BlogPostArticleCard.vue";
import BlogFooter from "@/components/BlogFooter.vue";
import BlogBackToTop from "@/components/BlogBackToTop.vue";

const props = defineProps(["year", "month"]);
let pageSize = 10;
let postArticles = reactive<IArticles[]>([]);
let articleCount = ref(0);

const onCurrentPageChanged = async (pageNum: number) => {
  const res = await getPostArticleListApi(
    pageNum,
    pageSize,
    undefined,
    undefined,
    props.year + "/" + props.month
  );
  if (res.code == 200) {
    articleCount.value = parseInt(res.data.total);
    res.data.rows.forEach((article: IArticles) => {
      article.createTime = article.createTime.split(" ")[0];
      article.thumbnail = article.thumbnail || defaultThumbnail;
    });
    postArticles.splice(0, postArticles.length, ...res.data.rows);
  }
};
onMounted(() => {
  window.scrollTo({ top: 0 });
  onCurrentPageChanged(1);
});
</script>

<style lang="less" scoped>
#archive-details {
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

.wife-cover {
  display: flex;
  align-items: center;
  justify-content: center;

  .archive-info {
    text-align: center;
    position: absolute;
    width: 100%;
    text-shadow: 0 3px 6px rgba(0, 0, 0, 0.3);

    .archive-date {
      font-size: 40px;
      color: white;
      line-height: 1.5;
      margin-bottom: 15px;
      padding: 0 30px;
      overflow: hidden;
      display: -webkit-box;
      text-overflow: ellipsis;
      -webkit-line-clamp: 3;
      -webkit-box-orient: vertical;
    }
  }
}

.post-article-list {
  width: 74%;

  .post-article-card {
    margin-top: 20px;
  }

  .post-article-card:nth-child(1) {
    margin-top: 0;
  }
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
    background-color: white;
    border-radius: 8px;
    margin: 0 6px;
    height: 35px;
    width: 35px;
  }

  li.active {
    color: white;
    background: var(--theme-color);
    font-weight: normal;
  }
}

@media screen and (max-width: 900px) {
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
