<template>
  <div class="uploader">
    <input
        id="file-input"
        ref="fileInput"
        accept="image/*"
        style="display: none"
        type="file"
        @change="onImageAdded"
    />

    <div v-if="!isThumbnailVisible" class="card upload-card" @click="openFileDialog">
      <svg
          class="icon"
          height="28"
          viewBox="0 0 1024 1024"
          width="28"
          xmlns="http://www.w3.org/2000/svg"
      >
        <path
            d="M480 480V128a32 32 0 0164 0v352h352a32 32 0 110 64H544v352a32 32 0 11-64 0V544H128a32 32 0 010-64h352z"
            fill="#8c939d"
        ></path>
      </svg>
    </div>

    <div v-show="isThumbnailVisible" class="card thumbnail-card">
      <img id="thumbnail" ref="thumbnail" alt="缩略图" src=""/>

      <label v-show="isSuccessLabelVisible" class="success-label"
      ><i class="success-icon"
      >
        <svg
            class="icon"
            height="12"
            viewBox="0 0 1024 1024"
            width="12"
            xmlns="http://www.w3.org/2000/svg"
        >
          <path
              d="M406.656 706.944L195.84 496.256a32 32 0 10-45.248 45.248l256 256 512-512a32 32 0 00-45.248-45.248L406.592 706.944z"
              fill="white"
          ></path>
        </svg
        >
      </i>
      </label>

      <!-- 图标 -->
      <div class="thumbnail-actions">
        <span class="thumbnail-preview" @click="handleThumbnailPreview">
          <svg
              class="icon"
              height="20"
              viewBox="0 0 1024 1024"
              width="20"
              xmlns="http://www.w3.org/2000/svg"
          >
            <path
                d="M795.904 750.72l124.992 124.928a32 32 0 01-45.248 45.248L750.656 795.904a416 416 0 1145.248-45.248zM480 832a352 352 0 100-704 352 352 0 000 704zm-32-384v-96a32 32 0 0164 0v96h96a32 32 0 010 64h-96v96a32 32 0 01-64 0v-96h-96a32 32 0 010-64h96z"
                fill="white"
            ></path>
          </svg>
        </span>
        <span class="thumbnail-delete">
          <svg
              class="icon"
              height="20"
              viewBox="0 0 1024 1024"
              width="20"
              xmlns="http://www.w3.org/2000/svg"
              @click="handleThumbnailRemove"
          >
            <path
                d="M160 256H96a32 32 0 010-64h256V95.936a32 32 0 0132-32h256a32 32 0 0132 32V192h256a32 32 0 110 64h-64v672a32 32 0 01-32 32H192a32 32 0 01-32-32V256zm448-64v-64H416v64h192zM224 896h576V256H224v640zm192-128a32 32 0 01-32-32V416a32 32 0 0164 0v320a32 32 0 01-32 32zm192 0a32 32 0 01-32-32V416a32 32 0 0164 0v320a32 32 0 01-32 32z"
                fill="white"
            ></path>
          </svg>
        </span>
      </div>

      <!-- 进度条 -->
      <el-progress
          v-show="isProgressVisible"
          id="progress"
          :percentage="progress"
          :width="110"
          type="circle"
      />
    </div>

    <VueEasyLightbox
        :imgs="localImageUrl"
        :index="index"
        :visible="isLightBoxVisible"
        moveDisabled
        @hide="handleLightboxHide"
    />
  </div>
</template>

<script lang="ts" setup>
import {ref, computed} from "vue";
import {uploadImageApi} from "@/api/image";
import VueEasyLightbox from "vue-easy-lightbox";
import {ElMessage} from "element-plus";

const emit = defineEmits(["uploaded", "aboutToUpload", "removed"]);
let progress = ref<number>(0);
let isLightBoxVisible = ref<boolean>(false);
let isProgressVisible = ref<boolean>(false);
let isSuccessLabelVisible = ref<boolean>(false);
let imageUrl = ref<string>("");
let localImageUrl = ref<string>("");
let index = ref(0);
const fileInput = ref();

let isThumbnailVisible = computed(() => localImageUrl.value.length > 0);

function openFileDialog() {
  document.getElementById("file-input")?.click();
}

function onImageAdded() {
  if (fileInput.value.files.length == 0) {
    return;
  }
  emit("aboutToUpload");
  let file = fileInput.value.files[0];
  console.log(file)
  setImageUrl(URL.createObjectURL(file));
  upload(file);
}

const thumbnail = ref();

function setImageUrl(url: string) {
  thumbnail.value.src = url;
  localImageUrl.value = url
}

function handleThumbnailRemove(file: any) {
  imageUrl.value = "";
  localImageUrl.value = "";
  emit("removed", file);
}

function handleThumbnailPreview() {
  isLightBoxVisible.value = true;
}

function handleLightboxHide() {
  isLightBoxVisible.value = false;
}

const upload = async (file: any) => {
  progress.value = 0;
  isProgressVisible.value = true;
  isSuccessLabelVisible.value = false;
  const res = await uploadImageApi(file)
  if (res.code == 200) {
    progress.value = 100;
    imageUrl.value = res.data.imgUrl
    thumbnail.value.src = res.data.imgUrl
    emit("uploaded", res.data.imgUrl)
    setTimeout(() => {
      isProgressVisible.value = false;
      isSuccessLabelVisible.value = true;
    }, 200);
  } else {
    isProgressVisible.value = false;
    localImageUrl.value = "";
    emit("uploaded", "");
    ElMessage.error("哎呀，图片上传出错啦~");
  }
}

defineExpose({setImageUrl})

</script>

<style lang="less" scoped>
.uploader {
  display: flex;
}

.card {
  background-color: #fbfdff;
  border: 1px dashed #c0ccda;
  border-radius: 6px;
  width: 148px;
  height: 148px;
  overflow: hidden;
}

.upload-card {
  display: flex;
  justify-content: center;
  align-items: center;
  transition: all 0.3s;
  cursor: pointer;

  &:hover {
    border-color: #409eff;
    color: #409eff;
  }
}

.thumbnail-card {
  border: 1px solid #c0ccda;
  position: relative;

  #thumbnail {
    width: 100%;
    height: 100%;
    object-fit: contain;
    display: inline;
  }

  .success-label {
    position: absolute;
    right: -15px;
    top: -6px;
    width: 40px;
    height: 24px;
    background: #67c23a;
    text-align: center;
    transform: rotate(45deg);
    box-shadow: 0 0 1pc 1px #0003;

    .success-icon {
      position: absolute;
      left: 13px;
      top: 1px;
      transform: rotate(-45deg);
    }
  }

  #progress {
    width: 100%;
    height: 100%;
    position: absolute;
    top: 0;
    left: 0;
    background: rgba(255, 255, 255, 0.7);

    :deep(.el-progress-circle) {
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
    }
  }

  .thumbnail-actions {
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
    opacity: 0;
    transition: all 0.4s ease;
    display: flex;
    justify-content: center;
    align-items: center;
    position: absolute;
    top: 0;
    left: 0;
    border-radius: 6px;

    .thumbnail-preview,
    .thumbnail-delete {
      cursor: pointer;
      margin: 0 8px;
      display: inline-block;
    }

    &:hover {
      opacity: 1;
    }
  }
}

:deep(.vel-img) {
  box-shadow: 0 5px 20px 2px rgba(0, 0, 0, 0.35);
}
</style>
