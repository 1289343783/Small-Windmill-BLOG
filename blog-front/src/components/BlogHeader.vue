<template>
  <header >
    <router-link :to="{ name: 'Home' }" class="header-title">
      <div class="flex justify-center items-center">
        <el-icon :size="40">
          <Icon icon="tabler:windmill-filled"/>
        </el-icon>
        Small-Windmill
      </div>
    </router-link>
    <div class="header-menu">
      <div v-for="item in menuItems" :key="item.name">
        <router-link :to="item.href" class="header-menu-item">
          <el-icon :size="20" class="header-icon">
            <Icon :icon="item.icon"/>
          </el-icon>
          {{ item.name }}
        </router-link>
      </div>
    </div>

    <div id="header-menu-button" @click="drawer = !drawer">
      <el-icon :size="20" class="header-icon">
        <Icon icon="ion:home"/>
      </el-icon>
    </div>
    <el-drawer
        v-model="drawer"
        :show-close="false"
        :with-header="false"
        direction="ltr"
        size="40%"
    >
      <BlogAdminMenu/>
    </el-drawer>
  </header>
</template>
<script lang="ts" setup>
import {reactive, ref} from "vue";
import {Icon} from "@iconify/vue";
import {onMounted} from "vue";
import {useAdminStore} from "@/store/modules/user";
import BlogAdminMenu from "./BlogAdminMenu.vue";

const adminStore = useAdminStore();
let drawer = ref(false);
let menuItems = reactive([
  {name: "首页", icon: "ion:home", href: "/"},
  {name: "归档", icon: "solar:document-bold", href: "/archive"},
  {name: "分类", icon: "flat-color-icons:folder", href: "/category"},
  {name: "标签", icon: "mdi:tag", href: "/tag"},
  {name: "个人中心", icon: "clarity:user-solid", href: "/user/settings"},
]);

onMounted(() => {
  if (adminStore.getIsAdmin()) {
    menuItems.splice(4, 0, {
      name: "新随笔",
      icon: "pepicons-print:pen",
      href: "/article/add",
    });
  }

  window.addEventListener("resize", function () {
    let scale = window.devicePixelRatio;
    let width = document.documentElement.clientWidth * scale;
    if (width > 900 * scale) {
      drawer.value = false;
    }
  });
});
</script>
<style scoped>
@import url(https://fonts.googleapis.com/css?family=Kanit:900);

header {
  position: absolute;
  top: 0;
  height: 60px;
  width: 100%;
  z-index: 9999;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 3%;
  box-sizing: border-box;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.3);
  --text-color: #eeeeee;
  --text-hover-color: white;
  animation: fadeUpIn 1s;
}

.header-title {
  color: #eeeeee;
  font-size: 28px;
  font-family: "Kanit";
  text-decoration: none;
  /* 字体粗细 */
  font-weight: 900;
  transition: color 0.4s ease-in-out;
}

.header-title:hover {
  color: var(--text-hover-color);
}

.header-icon {
  margin-right: 10px;
}

.header-menu {
  display: flex;
  position: relative;
}

.header-menu-item {
  color: #ece8e8;
  text-decoration: none;
  font-size: 15.5px;
  margin-left: 32px;
  position: relative;
  padding-bottom: 5px;
  display: flex;
  justify-content: center;
  align-items: center;
  /* 字体粗细 */
  font-weight: 700;
  transition: color 0.4s ease-in-out;
}

.header-menu-item:hover {
  color: var(--text-hover-color);
}

.header-menu-item:after {
  content: "";
  width: 0;
  height: 3px;
  background: #80c8f8;
  position: absolute;
  bottom: 0;
  left: 50%;
  transition: all 0.4s ease-in-out;
  border-radius: 2px;
}

.header-menu-item:hover:after {
  left: 0%;
  width: 100%;
}

#header-menu-button {
  display: none;
  color: var(--text-color);
  cursor: pointer;
  position: relative;
  font-size: 20px;
  margin: 0;
}

#header-menu-button:hover {
  color: var(--text-hover-color);
}

:deep(.el-drawer__body) {
  padding: 0;
}

@media screen and (max-width: 900px) {
  .header-menu {
    display: none;
  }

  #header-menu-button {
    display: inline-block;
  }
}

@keyframes fadeUpIn {
  0% {
    -webkit-transform: translateY(-100%);
    transform: translateY(-100%);
    opacity: 0%;
  }

  100% {
    -webkit-transform: translateY(0);
    transform: translateY(0);
    opacity: 100%;
  }
}
</style>
