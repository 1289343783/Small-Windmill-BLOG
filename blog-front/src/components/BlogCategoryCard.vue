<template>
  <div>
    <el-card class="mt-8">
      <template #header>
        <div class="flex items-center">
          <el-icon :size="25">
            <Icon icon="flat-color-icons:folder" />
          </el-icon>
          <span class="ml-2 text-base font-bold">分类</span>
        </div>
      </template>
      <div class="category-list">
        <router-link
          tag="a"
          target="_blank"
          v-for="category in categoryCounts"
          :key="category.id"
          class="category-item"
          :to="`/category/${category.id}`"
        >
          <span class="category-name">{{ category.name }}</span>
          <span class="category-count">{{ category.count }}</span>
        </router-link>
      </div>
    </el-card>
  </div>
</template>
<script setup lang="ts">
import { Icon } from "@iconify/vue";
import { ref } from "vue";
import { useCategoryAboutStore } from "@/store/modules/categoryAbout";
import { onMounted } from "vue";

const categoryCounts = ref<ICategory[]>([]);
const categoryAbout = useCategoryAboutStore();

const initCategoryClouds = async () => {
  await categoryAbout.getCategoryCountsApi();
  categoryCounts.value =
    categoryAbout.$state.categoryCounts !== undefined
      ? categoryAbout.$state.categoryCounts
      : [];
};

onMounted(() => {
  initCategoryClouds();
});
</script>
<style scoped>
.category-item {
  display: flex;
  justify-content: space-between;
  text-decoration: none;
  padding: 13px 10px;
  color: var(--text-color);
  font-size: 16px;
  transition: all 0.4s;
  border-radius: 4px;
}

.category-item:hover {
  background-color: var(--theme-color);
  color: white;
  padding: 13px 17px;
}

.category-name {
  flex: 1;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>
