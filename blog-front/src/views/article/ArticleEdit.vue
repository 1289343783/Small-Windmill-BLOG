<template>
  <div id="arctile-edit">
    <!-- 页头 -->
    <BlogHeader/>

    <!-- 二次元封面 -->
    <BlogWifeCover>
      <h1>{{ title }}</h1>
    </BlogWifeCover>

    <!-- 编辑表单 -->
    <div class="edit-card">
      <h1 class="kila-kila-blog-title">✨ 无敌小风车 Blog ✨</h1>
      <el-form
          ref="ruleFormRef"
          :model="ruleForm"
          :rules="rules"
          label-width="60px"
          class="edit-ruleForm"
      >
        <!-- 标题 -->
        <el-form-item prop="title" label="标题">
          <el-input
              v-model="ruleForm.title"
              class="w-50 m-3"
              size="large"
              placeholder="给博客起一个好标题很重要哦"
          ></el-input>
        </el-form-item>

        <!-- 摘要 -->
        <el-form-item prop="summary" label="摘要">
          <el-input
              v-model="ruleForm.summary"
              class="w-50 m-3"
              size="large"
              type="textarea"
              placeholder="简要介绍一下这篇博客吧"
          ></el-input>
        </el-form-item>

        <div class="inline-form-row">
          <!-- 分类 -->
          <el-form-item label="分类" prop="category" inline style="width: 35%">
            <el-select
                v-model="ruleForm.category"
                placeholder="给博客分个类吧"
                style="width: 100%; vertical-align: middle"
                allow-create
                filterable
                clearable
            >
              <el-option
                  v-for="item in categories"
                  :key="item.id"
                  :label="item.name"
                  :value="item.name"
              />
            </el-select>
          </el-form-item>

          <!-- 标签 -->
          <el-form-item label="标签" prop="tag" inline style="width: 60%">
            <el-select
                v-model="ruleForm.tags"
                placeholder="来贴几个标签吧"
                style="width: 100%; vertical-align: middle"
                multiple
                allow-create
                filterable
                clearable
            >
              <el-option
                  v-for="item in tags"
                  :key="item.id"
                  :label="item.name"
                  :value="item.name"
              />
            </el-select>
          </el-form-item>
        </div>

        <!-- 编辑器 -->
        <el-form-item prop="content" label="内容">
          <mavon-editor
              v-model="ruleForm.content"
              id="mavon"
              codeStyle="atom-one-dark"
              :autofocus="false"
              :boxShadow="false"
              @imgAdd="onImageAdded"
              ref="mavonRef"
          />
        </el-form-item>

        <!-- 缩略图 -->
        <el-form-item prop="thumbnail" label="缩略图">
          <BlogUploader
              @uploaded="handleThumbnailUploaded"
              @aboutToUpload="handleAboutToUploadThumbnail"
              @removed="handleRemoveThumbnail"
              ref="uploaderRef"
          />
        </el-form-item>

        <!-- 按钮 -->
        <el-form-item>
          <el-button
              type="primary"
              @click="submitForm(ruleFormRef, false)"
              color="#1892ff"
              class="el-button"
              :disabled="submitButton"
              id="submit-button"
          >立刻发布
          </el-button
          >
          <el-button
              class="el-button"
              id="draft-button"
              :disabled="draftButton"
              @click="submitForm(ruleFormRef, true)"
          >存为草稿
          </el-button
          >
          <el-button @click="cancelSubmit">取消</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 页脚 -->
    <BlogFooter/>
  </div>
</template>

<script setup lang="ts">
import {ref, reactive, computed, nextTick} from "vue";
import {useCategoryAboutStore} from "@/store/modules/categoryAbout";
import {ElMessage, ElMessageBox} from "element-plus";
import {addArticleApi, editArticleApi, getBlogApi} from "@/api/article";
import {uploadImageApi} from "@/api/image";
import router from "@/router";
import bus from "@/utils/bus";
import markdownIt from "@/utils/markdown-it";
import BlogHeader from "@/components/BlogHeader.vue";
import BlogWifeCover from "@/components/BlogWifeCover.vue";
import BlogFooter from "@/components/BlogFooter.vue";
import BlogUploader from "@/components/BlogUploader.vue";
import {useTagAboutStore} from "@/store/modules/tagAbout";
import {onMounted} from "vue";

const categoryAboutStore = useCategoryAboutStore();
const TagAboutStore = useTagAboutStore();
let props = defineProps(["id"]);

let isInEditMode: boolean = !!props.id;
let content = ref("");
let mavonRef = ref();
let ruleFormRef = ref();
let uploaderRef = ref();
let title = computed(() => (isInEditMode ? "编辑博客" : "新随笔"));

let categories = ref<ICategory[]>([]);
let tags = ref<ITag[]>([]);
computed(() => {
  if (categoryAboutStore.$state.categoryCounts) {
    categories.value = categoryAboutStore.$state.categoryCounts;
  }
});
computed(() => {
  if (TagAboutStore.$state.tagCounts) {
    tags.value = TagAboutStore.$state.tagCounts;
  }
});
let ruleForm = reactive<IArticleDTO>({
  id: undefined,
  title: "",
  summary: "",
  content: "",
  category: "",
  tags: [],
  thumbnail: "",
  isDraft: false,
});
let rules = reactive({
  title: [
    {
      required: true,
      message: "文章标题不能为空",
      trigger: "change",
    },
  ],
  content: [
    {
      required: true,
      message: "文章内容不能为空",
      trigger: "change",
    },
  ],
  category: [
    {
      required: true,
      message: "分类不能为空",
      trigger: "change",
    },
  ],
});

// 如果有传入文章 id 需要先获取文章信息
const getBlogInfo = async () => {
  const res = await getBlogApi(props.id);
  if (res.code == 200) {
    Object.assign(ruleForm, res.data);
    ruleForm.category = res.data.categoryName;
    ruleForm.tags = res.data.tags.map((t) => t.name);
    console.log(res.data.tags);
    if (res.data.thumbnail) {
      console.log(res.data.thumbnail)
      uploaderRef.value.setImageUrl(res.data.thumbnail);
      uploaderRef.value.isSuccessLabelVisible = true;
    }
  }
};

const onImageAdded = async (pos: any, file: any) => {
  const res = await uploadImageApi(file)
  if (res.status == 200) {
    mavonRef.value.$img2Url(pos, res.data.thumb.url);
  }
}

const submitButton = ref<boolean>(false);
const draftButton = ref<boolean>(false);

function handleThumbnailUploaded(url: string) {
  ruleForm.thumbnail = url;
  submitButton.value = false;
  draftButton.value = false;
}

function handleAboutToUploadThumbnail() {
  submitButton.value = true;
  draftButton.value = true;
}

function handleRemoveThumbnail(file: any) {
  ruleForm.thumbnail = "";
}

const submitForm = async (form: any, isDraft: boolean) => {
  if (!validateForm(form)) return;

  ruleForm.isDraft = isDraft;
  generateSummary();

  let name = isDraft ? "草稿" : "博客";
  if (!isInEditMode) {
    const res = await addArticleApi(ruleForm);
    if (res.code == 200) {
      ElMessage.success(name + "保存成功啦");
      setTimeout(() => {
        router.push("/article/" + res.data);
      }, 1500);
    }
  } else {
    const res = await editArticleApi(ruleForm);
    if (res.code == 200) {
      ElMessage.success(name + "编辑成功啦");
      bus.emit("articlePosted");
      setTimeout(() => {
        router.push("/article/" + res.data);
      }, 1500);
    }
  }
};

function cancelSubmit() {
  ElMessageBox.confirm("前辈已经想好要取消这篇博客的发表了吗？", "一条友善的提示", {
    confirmButtonText: "你在教我做事？",
    cancelButtonText: "我再想想",
    type: "warning",
  }).then(() => {
    router.push("/");
  });
}

function validateForm(form: any) {
  if (!form) return false;

  form.validate((valid: any) => {
    if (!valid) {
      ElMessage.error("必填字段不能为空哦");
      return false;
    }
  });

  return true;
}

function generateSummary() {
  if (ruleForm.summary) {
    return;
  }

  let html = markdownIt.render(ruleForm.content);
  ruleForm.summary = html.replace(/<[^>]+>/g, "").slice(0, 150);
}

nextTick(() => {
  window.scrollTo({top: 0});
});

const initTagsAbout = async () => {
  await TagAboutStore.getTagCountsApi();
  if (TagAboutStore.$state.tagCounts) {
    tags.value = TagAboutStore.$state.tagCounts;
  }
};

const initCategoryAbout = async () => {
  await categoryAboutStore.getCategoryCountsApi();
  if (categoryAboutStore.$state.categoryCounts) {
    categories.value = categoryAboutStore.$state.categoryCounts;
  }
};

onMounted(() => {
  if (isInEditMode) {
    getBlogInfo();
  }
  initCategoryAbout();
  initTagsAbout();
});
</script>

<style lang="less" scoped>
.wife-cover {
  display: flex;
  align-items: center;
  justify-content: center;

  h1 {
    font-size: 35px;
    font-weight: normal;
    color: white;
    line-height: 1.5;
    margin-bottom: 15px;
    position: absolute;
    text-shadow: 0 3px 6px rgba(0, 0, 0, 0.3);
  }
}

.edit-card {
  background: white;
  border-radius: 8px;
  box-shadow: var(--card-box-shadow);
  padding: 10px 30px;
  width: 70%;
  margin: 50px auto 0;
  box-sizing: border-box;
  animation: fadeInUp 1s;

  .kila-kila-blog-title {
    text-align: center;
    font-size: 26px;
    font-family: "Kanit";
    color: #4679fa;
  }

  .inline-form-row {
    display: flex;
    justify-content: space-between;
  }

  #mavon {
    width: 100%;
    max-height: 600px;
    border-color: #d9d9d9;

    &:focus {
      border-color: var(--el-color-primary);
    }

    .v-note-op {
      border-bottom-color: #d9d9d9;
    }
  }

  .el-button {
    transition: all 0.4s;
  }
}

#submit-button:disabled,
#draft-button:disabled {
  cursor: not-allowed;
}

@keyframes fadeInUp {
  from {
    transform: translateY(50px);
    opacity: 0;
  }

  to {
    transform: translateX(0);
    opacity: 1;
  }
}
</style>
