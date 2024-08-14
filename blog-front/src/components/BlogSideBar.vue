<template>
  <!-- 侧边栏 -->
  <div id="side-bar">
    <slot>
      <BlogAdminCard class="mb-6"/>
      <BlogHotArticleCard />
      <BlogCategoryCard class="mb-6"/>
      <BlogTagCard />
    </slot>
  </div>
</template>

<script setup lang="ts">
import BlogAdminCard from "./BlogAdminCard.vue";
import BlogHotArticleCard from "./BlogHotArticleCard.vue";
import BlogCategoryCard from "./BlogCategoryCard.vue";
import BlogTagCard from "./BlogTagCard.vue";
</script>

<style lang="less" scoped>
#side-bar {
  width: 28%;
  margin-right: 20px;
}

@media screen and (max-width: 900px) {
  #side-bar {
    display: none;
  }
}
</style>
