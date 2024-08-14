<template>
  <el-card>
    <template #header>
      <div class="flex items-center">
        <el-icon :size="25">
          <Icon icon="mdi:tag-multiple" color="#2d7de6" />
        </el-icon>
        <span class="ml-2 text-base font-bold">标签云</span>
      </div></template
    >
    <div class="tag-clouds">
      <router-link
        tag="a"
        target="_blank"
        class="tag-item"
        v-for="tag in tagClouds"
        :key="tag.id"
        :to="`/tag/${tag.id}`"
        :style="tag.style"
        >{{ tag.name }}
      </router-link>
    </div>
    <router-link class="tag-item" to="/tag">更多</router-link>
  </el-card>
</template>

<script setup lang="ts">
import { randomColorWordCloud } from "@/utils/word-cloud";
import { ref } from "vue";
import { Icon } from "@iconify/vue";
import { useTagAboutStore } from "@/store/modules/tagAbout";
import { onMounted } from "vue";

const tagAboutStore = useTagAboutStore();
let tagCounts = ref<ITag[]>();
let tagClouds = ref<
  { id: number; name: string; style: { color: string; fontSize: string } }[]
>([]);

const initTagClouds = async () => {
  await tagAboutStore.getTagCountsApi();
  tagCounts.value = tagAboutStore.$state.tagCounts;
  tagClouds.value = randomColorWordCloud(
    tagCounts.value !== undefined ? tagCounts.value : []
  );
};

onMounted(() => {
  initTagClouds();
});
</script>

<style scoped>
.tag-clouds {
  font-size: 14px;
  box-sizing: border-box;
}

.tag-item {
  text-decoration: none;
  display: inline-block;
  transition: all 0.4s;
  padding: 0 4px;
  overflow-wrap: break-word;
  line-height: 2;
}

.tag-item:hover {
  color: var(--theme-color) !important;
}
</style>
