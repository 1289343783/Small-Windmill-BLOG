<template>
  <div class="cover" style="background-image: url('../../public/naruto.jpg'); background-size: cover;/* 背景图不平铺 */
    background-repeat: no-repeat;  background-attachment: fixed; background-position: center center;">
    <div class="cover-word-container">
      <h1 class="cover-title particletext bubbles">{{ title }}</h1>
      <p class="cover-content">{{ content }}</p>
      <el-icon class="arrow-down" size="70px" @click="scrollToContent">
        <Icon icon="iconamoon:arrow-down-2-bold"/>
      </el-icon>
    </div>
  </div>
</template>

<script lang="ts" setup>
import {onMounted, reactive} from "vue";
import createParticles from "@/utils/text-effect";
import {Icon} from "@iconify/vue";

function scrollToContent() {
  let h = document.getElementsByClassName("cover")[0].scrollHeight;
  window.scrollTo({top: h, behavior: "smooth"});
}

onMounted(() => {
  createParticles();
})
const props = defineProps({
  imgUrl: {
    type: String,
    require: true
  },
  title: {
    type: String,
    require: true,
  },
  content: {
    type: String,
    require: true,
  },
});
</script>

<style scoped>
.cover {
  height: 100vh;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  animation: fadeUpInCover 1s;
}

.cover-word-container {
  margin: 0 auto;
  text-align: center;
}

.cover-title {
  font-family: "Kanit";
  color: white;
  font-size: 65px;
  margin: 0px;
  text-shadow: 0 3px 6px rgba(0, 0, 0, 0.3);
  animation: fadeUpIn 1.5s;
}

.cover-content {
  color: white;
  font-size: 24px;
  font-family: "Long Cang", sans-serif;
  text-shadow: 0 3px 6px rgba(0, 0, 0, 0.3);
  animation: fadeUpInSlow 1.5s;
}

.arrow-down {
  position: absolute;
  bottom: 6%;
  cursor: pointer;
  color: rgba(255, 255, 255, 0.6);
  transition: color 0.2s;
  animation: bounce 4s infinite;
}

.arrow-down:hover {
  color: rgba(255, 255, 255, 0.8);
  transition: color 0.2s;
}

@-webkit-keyframes bounce {
  0%,
  10%,
  25%,
  40%,
  50% {
    -webkit-transform: translateY(0);
    transform: translateY(0);
  }

  20% {
    -webkit-transform: translateY(-10px);
    transform: translateY(-10px);
  }

  30% {
    -webkit-transform: translateY(-5px);
    transform: translateY(-5px);
  }
}

@keyframes bounce {
  0%,
  10%,
  25%,
  40%,
  50% {
    -webkit-transform: translateY(0);
    transform: translateY(0);
  }

  20% {
    -webkit-transform: translateY(-10px);
    transform: translateY(-10px);
  }

  30% {
    -webkit-transform: translateY(-5px);
    transform: translateY(-5px);
  }
}

@keyframes fadeUpInCover {
  0% {
    -webkit-transform: translateY(-70px);
    transform: translateY(-70px);
    opacity: 0%;
  }
  100% {
    -webkit-transform: translateY(0);
    transform: translateY(0);
    opacity: 100%;
  }
}

@keyframes fadeUpIn {
  0% {
    -webkit-transform: translateY(-50px);
    transform: translateY(-50px);
    opacity: 0%;
  }
  100% {
    -webkit-transform: translateY(0);
    transform: translateY(0);
    opacity: 100%;
  }
}

@keyframes fadeUpInSlow {
  0% {
    -webkit-transform: translateY(-100px);
    transform: translateY(-100px);
    opacity: 0%;
  }
  100% {
    -webkit-transform: translateY(0);
    transform: translateY(0);
    opacity: 100%;
  }
}
</style>