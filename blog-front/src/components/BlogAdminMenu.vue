<template>
  <div class="admin-menu">
    <BlogAdminCard/>
    <BlogDivider/>
    <el-menu>
      <!-- 首页 -->
      <el-menu-item index="1" @click="$router.push('/')">
        <el-icon :size="20" class="header-icon">
          <Icon icon="ion:home"/>
        </el-icon>
        <span>首页</span>
      </el-menu-item>

      <!-- 归档 -->
      <el-menu-item index="2" @click="$router.push('/archive')">
        <el-icon :size="20" class="header-icon">
          <Icon icon="solar:document-bold"/>
        </el-icon>
        <span>归档</span>
      </el-menu-item>

      <!-- 分类 -->
      <el-sub-menu index="3">
        <template #title>
          <el-icon :size="20" class="header-icon">
            <Icon icon="flat-color-icons:folder"/>
          </el-icon>
          <span>分类</span>
        </template>
        <el-menu-item
            v-for="category in categoryAboutStore.$state.categoryCounts"
            :key="category.id"
            :index="'3-' + category.id"
            class="link-item"
            @click="$router.push('/category/' + category.id)"
        >🗂️
          <span class="link-name">{{ category.name }}</span>
          <span>{{ category.count }}</span>
        </el-menu-item>
      </el-sub-menu>

      <!-- 标签 -->
      <el-sub-menu index="4">
        <template #title>
          <el-icon :size="20" class="header-icon">
            <Icon icon="mdi:tag"/>
          </el-icon>
          <span>标签</span>
        </template>
        <el-menu-item
            v-for="tag in tagAboutStore.$state.tagCounts"
            :key="tag.id"
            :index="'4-' + tag.id"
            class="link-item"
            @click="$router.push('/tag/' + tag.id)"
        >🔖
          <span class="link-name">{{ tag.name }}</span>
          <span>{{ tag.count }}</span>
        </el-menu-item>
      </el-sub-menu>

      <!-- 新随笔 -->
      <el-menu-item
          index="5"
          v-if="adminStore.getIsAdmin()"
          @click="$router.push('/article/add')"
      >
        <el-icon :size="20" class="header-icon">
          <Icon icon="pepicons-print:pen"/>
        </el-icon>
        <span>新随笔</span>
      </el-menu-item>

      <!-- 个人中心 -->
      <el-menu-item index="6" @click="$router.push('/user/settings')">
        <el-icon :size="20" class="header-icon">
          <Icon icon="clarity:user-solid"/>
        </el-icon>
        <span>个人中心</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script setup lang="ts">
import {onMounted} from "vue";
import BlogAdminCard from "./BlogAdminCard.vue";
import BlogDivider from "./BlogDivider.vue";
import {useCategoryAboutStore} from "@/store/modules/categoryAbout";
import {useTagAboutStore} from "@/store/modules/tagAbout";
import {useAdminStore} from "@/store/modules/user";
import {Icon} from "@iconify/vue";

const adminStore = useAdminStore();
const categoryAboutStore = useCategoryAboutStore();
const tagAboutStore = useTagAboutStore();

onMounted(async () => {
  await categoryAboutStore.getCategoryCountsApi();
  await tagAboutStore.getTagCountsApi();
});
</script>

<style lang="less" scoped>
.admin-menu {
  text-shadow: none !important;
  width: 100%;
}

.admin-card {
  box-shadow: none;
  box-sizing: border-box;
  height: 375px;
}

.el-menu {
  padding: 0 10px;
  margin-top: 10px;
  border-right: none;
}

.menu-icon {
  margin: 0 10px 0 0;
}

:deep(.link-item) {
  display: flex;
  justify-content: space-between;
  text-decoration: none;
  padding-right: 30px;
  color: #4c4948;
  font-size: 14px;
  transition: all 0.4s;
  border-radius: 4px;

  &:hover {
    padding-left: 50px !important;
  }
}

.link-name {
  flex: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  padding-left: 3px;
}
</style>
