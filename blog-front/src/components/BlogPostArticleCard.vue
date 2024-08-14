<template>
  <div :class="cardClass">
    <!-- 缩略图 -->
    <router-link
        :class="thumbailLinkClass"
        :to="`/article/${articles.id}`"
        tag="a"
        target="_blank"
    >

      <img :src="articles.thumbnail"
           alt="缩略图"
           class="post-article-thumbnail"
           @error.once="useDefaultThumbnail"
      />
    </router-link>
    <!-- 文章信息 -->
    <div class="post-article-info">
      <!-- 文章标题 -->
      <router-link
          :to="`/article/${articles.id}`"
          class="post-article-title"
          tag="a"
          target="_blank"
      >{{ articles.title }}
      </router-link>
      <!-- 其他元数据 -->
      <div class="post-article-meta-data-wrap">
        <span class="post-article-meta-data">
          <div class="flex items-center">
            <el-icon :size="17">
              <Icon icon="fontisto:date"/>
            </el-icon>
            <span class="ml-2 font-bold"> 发表于 {{ articles.createTime }}</span>
          </div>
        </span>
        <span class="post-article-meta-data-divider">|</span>
        <span class="post-article-meta-data">
          <div class="flex items-center">
            <el-icon :size="17">
              <Icon icon="flat-color-icons:folder"/>
            </el-icon>
            <span class="ml-2 font-bold"> 分类 {{ articles.categoryName }}</span>
          </div>
        </span>
        <span class="post-article-meta-data-divider">|</span>
        <span class="post-article-meta-data">
          <div class="flex items-center">
            <el-icon :size="17">
              <Icon icon="mdi:eye"/>
            </el-icon>
            <span class="ml-2 font-bold"> 阅读量 {{ articles.viewCount }}</span>
          </div>
        </span>
      </div>
      <!-- 文章摘要 -->
      <div class="post-article-summary">摘要： {{ articles.summary }}</div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import {onMounted, reactive, ref} from "vue";
import {useDefaultThumbnail} from "@/utils/thumbnail";
import {Icon} from "@iconify/vue";

const props = defineProps({
  article: {
    type: Object,
    require: true,
  },
  reverse: {
    type: Boolean,
    require: false,
  },
});
const articles = reactive<any>(props.article);
let cardClass = reactive(["post-article-card"]);
let thumbailLinkClass = reactive(["post-article-thumbail-link"]);
onMounted(() => {
  if (props.reverse) {
    cardClass.push("post-article-card-reversed");
    thumbailLinkClass.push("post-article-thumbail-link-reversed");
  }
});
</script>

<style scoped>
.post-article-card {
  height: 252px;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: row;
  background: white;
  border-radius: 8px;
  box-shadow: var(--card-box-shadow);
}

.post-article-card-reversed {
  flex-direction: row-reverse;
}

.post-article-thumbail-link {
  width: 44%;
  height: 100%;
  overflow: hidden;
  box-sizing: border-box;
  border-radius: 8px 0 0 8px;
}

.post-article-thumbail-link-reversed {
  border-radius: 0 8px 8px 0;
}

.post-article-thumbnail {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: all 0.4s ease;
  box-sizing: border-box;
  overflow: hidden;
}

.post-article-thumbnail:hover {
  transform: scale(1.1);
}

.post-article-info {
  width: 57%;
  padding: 0 40px;
}

.post-article-title {
  color: #1f2d3d;
  font-size: 24px;
  text-decoration: none;
  transition: color 0.4s;
  overflow: hidden;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  line-height: 1.5;
  -webkit-line-clamp: 2;
}

.post-article-title:hover {
  color: var(--theme-color);
}

.post-article-meta-data-wrap {
  display: flex;
  margin: 9px 0;
}

.post-article-meta-data {
  font-size: 15px;
  color: rgb(133, 133, 133);
  box-sizing: border-box;
  line-height: 24px;
  overflow: hidden;
  -webkit-line-clamp: 1;
  display: -webkit-box;
  -webkit-box-orient: vertical;
}

.post-article-meta-data-divider {
  color: rgb(133, 133, 133);
  margin: 3px 8px;
  font-size: 12px;
}

.post-article-summary {
  color: var(--text-color);
  font-size: 17px;
  overflow: hidden;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  line-height: 2;
  -webkit-line-clamp: 2;
}
</style>
