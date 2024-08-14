<template>
  <div class="admin-card">
    <el-avatar :size="50" :src="userinfo?.avatar" class="avatar"></el-avatar>
    <div class="admin-description">
      <h3>{{ userinfo?.nickName }}</h3>
      <p>{{ userinfo?.signature }}</p>
    </div>

    <!-- 发表的文章信息 -->
    <div class="article-info-container">
      <div class="article-info">
        <p class="flex justify-center items-center">
          <el-icon :size="24" color="#83eb99" class="ml-2">
            <Icon icon="ic:baseline-article"/>
          </el-icon>
          文章
        </p>
        <p>{{ articleCountInfo?.article }}</p>
      </div>
      <div class="article-info">
        <p class="flex justify-center items-center">
          <el-icon :size="24" color="#e5eb83" class="mr-2">
            <Icon icon="fluent:people-team-32-filled"/>
          </el-icon>
          分类
        </p>
        <p>{{ articleCountInfo?.category }}</p>
      </div>
      <div class="article-info">
        <p class="flex justify-center items-center">
          <el-icon :size="24" color="#1e90ff" class="ml-2">
            <Icon icon="ic:outline-add"/>
          </el-icon>
          标签
        </p>
        <p>{{ articleCountInfo?.tag }}</p>
      </div>
    </div>
    <!-- 关注 -->
    <el-button type="primary" class="follow-button" @click="gotoGithub">
      <div class="justify-center items-center">
        <el-icon :size="20">
          <Icon icon="ic:outline-add"/>
        </el-icon>
      </div>
      关注
    </el-button>
    <!-- 社交软件图标 -->
    <div class="social-icon-container">
      <!-- gitee -->
      <a href="https://gitee.com/wh578/projects">
        <el-icon :size="29" class="social-icon">
          <Icon icon="simple-icons:gitee"/>
        </el-icon>
      </a>
      <!-- GitHub -->
      <a :href="userinfo?.githubUrl">
        <el-icon :size="29" class="social-icon">
          <Icon icon="mdi:github"/>
        </el-icon>
      </a>
      <!--      &lt;!&ndash; QQ &ndash;&gt;-->
      <!--      <el-icon :size="29" class="social-icon">-->
      <!--        <Icon icon="basil:qq-outline"/>-->
      <!--      </el-icon>-->
      <!-- 邮箱 -->
      <a href="mailto:1289343783@qq.com">
        <el-icon :size="29" class="social-icon">
          <Icon icon="ic:outline-email"/>
        </el-icon>
      </a>
    </div>
  </div>
</template>

<script setup lang="ts">
import {Icon} from "@iconify/vue";
import {useAdminStore} from "@/store/modules/user";
import {onMounted} from "vue";

const userStore = useAdminStore();
const userinfo = userStore.$state.adminInfo;
const articleCountInfo = userStore.$state.articleCountInfo;

function gotoGithub() {
  location.href = "";
}

onMounted(async () => {
  await userStore.getAdminApi()
  await userStore.getArticleCountInfo()
});
</script>
<style scoped>
@import url(../assets/css/material-icons.css);

.admin-card {
  background: white;
  border-radius: 8px;
  box-shadow: var(--card-box-shadow);
  text-align: center;
  height: 390px;
  width: 100%;
  padding: 20px;
  box-sizing: border-box;
}

.avatar {
  width: 120px;
  height: 120px;
  transition: transform 0.5s ease-out;
  -webkit-transition: transform 0.6s ease-out;
  -moz-transition: transform 0.5s ease-out;
  -ms-transition: transform 0.5s ease-out;
  -o-transition: transform 0.5s ease-out;
}

.admin-description h3:hover {
  color: #80c8f8;
}

.avatar:hover {
  -webkit-transform: rotate(360deg);
  -moz-transform: rotate(360deg);
  -ms-transform: rotate(360deg);
  -o-transform: rotate(360deg);
  transform: rotate(360deg);
}

.admin-description h3 {
  margin: 0px;
  overflow: hidden;
  display: -webkit-box;
  overflow: hidden;
  text-overflow: ellipsis;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
}

.admin-description p {
  margin: 4px;
  font-size: 14px;
  color: #555;
  overflow: hidden;
  -webkit-line-clamp: 1;
  display: -webkit-box;
  -webkit-box-orient: vertical;
}

.article-info-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0% 8%;
  margin-bottom: 10px;
  margin-top: 0;
}

/* .article-info {
  margin: 0 2px;
} */

.article-info p:nth-child(1) {
  margin-bottom: 0;
}

.article-info p:nth-child(2) {
  margin-top: 5px;
}

.social-icon-container {
  margin-top: 20px;
}

.social-icon {
  margin: 0px 3%;
  font-size: 20px;
  color: #555;
  transition: all 0.3s;
  cursor: pointer;
  transform: translateY(0);
}

.social-icon:hover {
  transform: translateY(-5px);
}

#twitter:hover {
  color: #77ddf6;
}

#github:hover {
  color: black;
}

#qq:hover {
  color: #d43402;
}

#email:hover {
  color: #f7b401;
}

.follow-button {
  display: block;
  margin: 0 auto;
  width: 100%;
}

.github-icon {
  margin-right: 10px;
}

@keyframes floatAni {
}
</style>
