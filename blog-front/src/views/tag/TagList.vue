<template>
  <div id="tag-list">
    <!-- 页头 -->
    <BlogHeader/>

    <!-- 二次元封面 -->
    <BlogWifeCover>
      <h1>标签</h1>
    </BlogWifeCover>

    <div class="container">
      <!-- 侧边栏 -->
      <BlogSideBar/>
      <BlogWordCloudCard :words="tagCounts" baseUrl="/tag"/>
    </div>
    <!-- 页脚 -->
    <BlogFooter/>

    <!-- 回到顶部 -->
    <BlogBackToTop/>
  </div>
</template>

<script setup lang="ts">
import BlogBackToTop from "@/components/BlogBackToTop.vue";
import BlogFooter from "@/components/BlogFooter.vue";
import BlogHeader from "@/components/BlogHeader.vue";
import BlogSideBar from "@/components/BlogSideBar.vue";
import BlogWifeCover from "@/components/BlogWifeCover.vue";
import BlogWordCloudCard from "@/components/BlogWordCloudCard.vue";
import {useTagAboutStore} from "@/store/modules/tagAbout";
import {onMounted, ref} from "vue";

const tagAboutStore = useTagAboutStore();
const tagCounts = ref<ITag[]>([]);
onMounted(async () => {
  await tagAboutStore.getTagCountsApi();
  tagCounts.value = tagAboutStore.$state.tagCounts == undefined ? [] : tagAboutStore.$state.tagCounts;
  window.scrollTo({top: 0});
});
</script>

<style lang="less" scoped>
#tag-list {
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

  h1 {
    width: 100%;
    text-align: center;
    position: absolute;
    text-shadow: 0 3px 6px rgba(0, 0, 0, 0.3);
    font-size: 40px;
    color: white;
    line-height: 1.5;
    margin-bottom: 15px;
    padding: 0 30px;
    box-sizing: border-box;
  }
}

.cloud-card {
  width: 74%;
  margin: 0;
}

@media screen and (max-width: 900px) {
  .cloud-card {
    width: 100%;
  }
}
</style>
