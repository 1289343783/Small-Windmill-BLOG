<template>
  <div id="article-details">
    <!-- 页头 -->
    <BlogHeader/>

    <!-- 二次元封面 -->
    <BlogWifeCover>
      <div class="article-info">
        <h1 class="article-title">
          {{ articleDetails.title }}
          <BlogIcon
              v-if="adminStore.getIsAdmin()"
              :icon-size="20"
              icon-value="fa-solid:edit"
              text="编辑"
              @click="editArticle"
          />
        </h1>
        <div class="article-meta-data-wrap">
          <span class="article-meta-data">
            <BlogIcon
                :icon-size="20"
                :text="`发表于 ${articleDetails.createTime}`"
                icon-value="uiw:date"
                stylet-value="ml-2"
            />
          </span>
          <span class="article-meta-data-divider">|</span>
          <span class="article-meta-data">
            <BlogIcon
                :icon-size="20"
                :text="`分类 ${articleDetails.categoryName}`"
                icon-value="ri:archive-fill"
                stylet-value="ml-2"
            />
          </span>
          <span class="article-meta-data-divider">|</span>
          <span class="article-meta-data">
            <BlogIcon
                :icon-size="20"
                :text="`阅读量 ${articleDetails.viewCount}`"
                icon-value="mdi:eye-outline"
                stylet-value="ml-2"
            />
          </span>
        </div>
      </div>
    </BlogWifeCover>

    <div class="container">
      <!-- 侧边栏 -->
      <BlogSideBar>
        <BlogAdminCard/>
        <div class="sticky-layout">
          <BlogCatalogCard v-if="articleLoaded"/>
        </div>
      </BlogSideBar>
      <!-- 文章内容 -->
      <div class="post-body">
        <div class="article-content" v-html="articleDetails.content"></div>

        <!-- 版权声明 -->
        <div class="article-signature">
          <img
              :src="adminStore.$state.adminInfo?.avatar"
              alt="头像"
          />
          <div class="copyright">
            <div class="copyright-item">
              <span class="copyright-title">文章作者：</span>
              <span class="copyright-content">
                <router-link to="/"> 无敌小风车大王</router-link>
              </span>
            </div>
            <div class="copyright-item">
              <span class="copyright-title">文章链接：</span>
              <span class="copyright-content">
                <a :href="articleUrl">{{ articleUrl }}</a>
              </span>
            </div>
            <div class="copyright-item">
              <span class="copyright-title">版权声明：</span>
              <span class="copyright-content">
                本博客所有文章除特别声明外，均采用
                <a href="https://creativecommons.org/licenses/by-nc-nd/4.0/">BY-NC-SA</a>
                许可协议。转载请注明出处！
              </span>
            </div>
          </div>
        </div>

        <!-- 标签 -->
        <div v-if="articleDetails.tags" class="article-tags">
          <span>
            <el-icon>
              <Icon icon="tabler:tags"/>
            </el-icon>
            标签：
          </span>
          <router-link
              v-for="tag in articleDetails.tags"
              :key="tag.id"
              :to="'/tag/' + tag.id"
              class="tag-link"
          >{{ tag.name }}
          </router-link
          >
        </div>
        <!-- 点赞/踩 -->
        <div class="float-right">
          <el-button :disabled="!!isStep" :type="isThumb ? 'primary' : ''" class="mt-3" @click="handleLike">
            <div class="flex items-center justify-center">
              <el-icon :size="20">
                <Icon icon="mdi:like-outline"/>
              </el-icon>
              <span class="mr-2 text-base font-bold">
                {{ articleDetails.articleLike }}
              </span>
            </div>
          </el-button>
          <el-button :disabled="!!isThumb" :type="isStep ? 'primary' : ''" class="mt-3"
                     @click="handleStep">
            <div class="flex items-center justify-center">
              <el-icon :size="20">
                <Icon icon="mdi:dislike-outline"/>
              </el-icon>
              <span class="mr-2 text-base font-bold">
                {{ articleDetails.articleStep }}
              </span>
            </div>
          </el-button>
        </div>

        <!-- 上一篇和下一篇 -->
        <div class="flex justify-center items-center mt-12 h-24 w-full">
          <router-link :to="`/article/${previousArticle.id}`" class="w-full">
            <el-popover :width="400" placement="top-start" trigger="hover">
              <template #default>
                <div v-if="previousArticle.id != 0" class="flex">
                  <div>
                    <img
                        :src="previousArticle.thumbnail"
                        alt="缩略图"
                        class="w-20 h-20 object-cover"
                    />
                  </div>
                  <div class="ml-2">
                    <el-text class="max-w-[90%] text-base font-bold" line-clamp="2">
                      {{ previousArticle.title }}
                    </el-text>
                    <div>
                      {{ previousArticle.createTime }}
                      <span>{{ previousArticle.viewCount }}次围观</span>
                    </div>
                  </div>
                </div>
                <div v-else>
                  <span class=" text-base font-bold">已经见底了，朋友！！！</span>
                </div>
              </template>
              <template #reference>
                <el-button
                    :disabled="!previousArticle.id"
                    class="w-[99%]"
                    style="height: 60px"
                    type="primary"
                >
                  <div>
                    <BlogIcon
                        :icon-size="20"
                        class="mt-3"
                        icon-value="ep:arrow-left-bold"
                        text="上一篇"
                    />
                  </div>
                </el-button>
              </template>
            </el-popover>
          </router-link>
          <router-link :to="`/article/${nextArticle.id}`" class="w-full">
            <el-popover :width="400" placement="top-start" trigger="hover">
              <template #default>
                <div v-if="nextArticle.id != 0" class="flex">
                  <div>
                    <img
                        :src="nextArticle.thumbnail"
                        alt="缩略图"
                        class="w-20 h-20 object-cover"
                    />
                  </div>
                  <div class="ml-2">
                    <el-text class="max-w-[90%] text-base font-bold" line-clamp="2">
                      {{ nextArticle.title }}
                    </el-text>
                    <div>
                      {{ nextArticle.createTime }}
                      <span>{{ nextArticle.viewCount }}次围观</span>
                    </div>
                  </div>
                </div>
                <div v-else>
                  <span class=" text-base font-bold">已经见底了，朋友！！！</span>
                </div>
              </template>
              <template #reference>
                <el-button
                    :disabled="!nextArticle.id"
                    class="w-[99%]"
                    style="height: 60px"
                    type="primary"
                >
                  <div>
                    <BlogIcon
                        :icon-size="20"
                        class="mt-3"
                        icon-value="ep:arrow-right-bold"
                        is-direction="left"
                        text="下一篇"
                    />
                  </div>
                </el-button>
              </template>
            </el-popover>
          </router-link>
        </div>

        <!-- 文章描述 -->
        <div id="article-desc">
          发布: {{ articleDetails.createTime.slice(0, -3) }}
          <span>无敌小风车大王</span>
          <span>阅读({{ articleDetails.viewCount }})</span>
          <span>评论({{ commentCount }})</span>
          <router-link v-if="adminStore.getIsAdmin()" :to="`/article/${articleDetails.id}/edit`"
          >编辑
          </router-link
          >
        </div>
        <!-- 评论列表 -->
        <div v-if="comments.length > 0" id="comment-area">
          <div class="comment-area-title">
            <div class="flex items-center">
              <el-icon :size="25">
                <Icon color="#2d7de6" icon="mage:message-dots-fill"/>
              </el-icon>
              <span class="ml-2 text-base font-bold">评论列表</span>
            </div>
          </div>

          <!-- 评论 -->
          <div id="comment-items">
            <BlogCommentItem
                v-for="(comment, index) in comments"
                :key="comment.id"
                ref="commentItem"
                :comment="comment"
                :floorNumber="(currentCommentPageNum - 1) * commentPageSize + index + 1"
                @delete="onDeleteComment"
                @reply="onReplyComment"
                @update="onUpdateComment"
            />
          </div>

          <!-- 分页 -->
          <el-pagination
              v-if="commentCount > 0"
              id="comment-pagination"
              v-model:page-size="commentPageSize"
              :total="commentCount"
              background
              layout="prev, pager, next"
              @current-change="onCurrentCommentPageChanged"
          >
          </el-pagination>
        </div>

        <!-- 发表评论 -->
        <div id="comment-form">
          <div id="comment-form-title">✏️ 发表评论</div>
          <div id="comment-editor">
            <mavon-editor
                id="mavon"
                ref="mavonRef"
                v-model="commentContent"
                :autofocus="false"
                :boxShadow="false"
                :subfield="false"
                :toolbars="mavonToolbarOption"
                codeStyle="atom-one-dark"
                defaultOpen="edit"
                placeholder="发表一条伟大的评论吧~"
                @imgAdd="onImageAdded"
            />
            <button id="comment-submit-btn" @click="submitComment">
              {{ isInEditMode ? "修改评论" : "提交评论" }}
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- 回到顶部 -->
    <BlogBackToTop/>

    <!-- 图片查看器 -->
    <!-- <BlogLightBox ref="lightBoxRef" v-if="articleLoaded" /> -->

    <!-- 页脚 -->
    <BlogFooter/>
  </div>
</template>

<script lang="ts" setup>
import {Icon} from "@iconify/vue";
import BlogSideBar from "@/components/BlogSideBar.vue";
import BlogHeader from "@/components/BlogHeader.vue";
import BlogWifeCover from "@/components/BlogWifeCover.vue";
import BlogAdminCard from "@/components/BlogAdminCard.vue";
import BlogIcon from "@/components/BlogIcon.vue";
import BlogCommentItem from "@/components/BlogCommentItem.vue";
import BlogCatalogCard from "@/components/BlogCatalogCard.vue";
import BlogFooter from "@/components/BlogFooter.vue";
import BlogBackToTop from "@/components/BlogBackToTop.vue";
import {reactive, nextTick, ref, onMounted} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";
import markdownIt from "@/utils/markdown-it";
import {useDefaultThumbnail, defaultThumbnail} from "@/utils/thumbnail";
import buildCodeBlock from "@/utils/code-block";
import {renderByMathjax, initMathJax} from "@/utils/mathjax";
import router from "@/router";
import {getBlogApi, previousNextArticleApi, updateViewCountApi} from "@/api/article";
import {addCommentApi, deleteCommentApi, getCommentList, updateCommentApi} from "@/api/comment";
import {useAdminStore} from "@/store/modules/user";
import {uploadImageApi} from "@/api/image";
import {doThumbApi, isThumbApi} from "@/api/thumb";
import {isLogin} from "@/utils/auth";
import {doStepApi, isStepApi} from "@/api/step";

const adminStore = useAdminStore()
const props = defineProps(["id"])
let articleLoaded = ref(false);
let articleUrl = ref(window.location.href);
let previousArticle = reactive<IPreviousAndINext>({
  id: 0,
  createTime: "",
  thumbnail: "",
  title: "",
  viewCount: 0,
});
let nextArticle = reactive<IPreviousAndINext>({
  id: 0,
  createTime: "",
  thumbnail: "",
  title: "",
  viewCount: 0,
});
// let lightBoxRef = ref();

// 获取文章内容
let articleDetails = reactive<IArticle>({
  articleLike: 0,
  articleStep: 0,
  id: 0,
  categoryId: 0,
  categoryName: "",
  content: "",
  createTime: "",
  date: "",
  category: "",
  image: "",
  summary: "",
  tags: [],
  thumbnail: "",
  title: "",
  viewCount: 0
});
const getArticleDetails = async () => {
  const res = await getBlogApi(props.id);
  if (res.code === 200) {
    Object.assign(articleDetails, res.data);
    articleDetails.content = markdownIt.render(res.data.content);
    nextTick(() => {
      initMathJax({}, () => {
        renderByMathjax(".article-content");
        renderByMathjax(".comment-item-content");
      });
      buildCodeBlock(".article-content");
      articleLoaded.value = true;
    }).then(() => {
      // lightBoxRef.value.addImageClickedListener();
    });
  }
};

const updateViewCount = async () => {
  const res = await updateViewCountApi(props.id);
  if (res.code !== 200) {
    ElMessage.error("出了一点小差错！！！")
  }
}

// 获取上一篇和下一篇文章
const getPreviousNextArticle = async () => {
  const res = await previousNextArticleApi(props.id);
  if (res.code == 200) {
    Object.assign(previousArticle, res.data.previous);
    if (!previousArticle.thumbnail) {
      previousArticle.thumbnail = defaultThumbnail;
    }
  }
  if (res.data.next) {
    Object.assign(nextArticle, res.data.next);
    if (!nextArticle.thumbnail) {
      nextArticle.thumbnail = defaultThumbnail;
    }
  }
};

// 获取评论列表
let comments = ref<IComment[]>([]);
let commentCount = ref(0);
let commentPageSize = 5;
let currentCommentPageNum = ref(1);
const commentItem = ref()
const onCurrentCommentPageChanged = async (pageNum: number) => {
  const res = await getCommentList(props.id, pageNum, commentPageSize);
  if (res.code === 200) {
    currentCommentPageNum.value = pageNum;
    commentCount.value = parseInt(res.data.total);
    comments.value.splice(0, comments.value.length, ...res.data.rows);
  }
  nextTick(() => {
    renderByMathjax(".comment-item-content");
    buildCodeBlock(".comment-item-content");
  });
};

/**
 * 修改文章
 */
function editArticle() {
  router.push(`/article/${props.id}/edit`);
}

// 添加评论
let mavonToolbarOption = {
  bold: true, // 粗体
  italic: true, // 斜体
  header: true, // 标题
  underline: true, // 下划线
  strikethrough: true, // 中划线
  mark: true, // 标记
  superscript: true, // 上角标
  subscript: true, // 下角标
  quote: true, // 引用
  ol: true, // 有序列表
  ul: true, // 无序列表
  link: true, // 链接
  imagelink: true, // 图片链接
  code: true, // code
  table: true, // 表格
  fullscreen: true, // 全屏编辑
  help: true, // 帮助
  navigation: true, // 导航目录
  alignleft: true, // 左对齐
  aligncenter: true, // 居中
  alignright: true, // 右对齐
  subfield: true, // 单双栏模式
  preview: true, // 预览
};
let commentContent = ref("");
let isInEditMode = ref(false);
let editedComment: IEditedComment = {articleId: 0, content: "", id: 0};
let mavonRef = ref();

const onImageAdded = async (pos: any, file: any) => {
  const res = await uploadImageApi(file)
  if (res.code == 200) {
    mavonRef.value.$img2Url(pos, res.data.imgUrl);
  }
}

const submitComment = async () => {
  let promise;
  if (commentContent.value.trim().length == 0) {
    ElMessage.warning("评论内容不能为空哦~");
    return;
  }
  if (!isInEditMode.value) {
    promise = await addCommentApi(props.id, commentContent.value);
  } else {
    promise = await updateCommentApi(editedComment.id, commentContent.value);
  }
  if (promise.code == 200) {
    ElMessage.success("吐槽成功啦");
    commentContent.value = "";
    // await onCurrentCommentPageChanged(currentCommentPageNum.value);
  }
  isInEditMode.value = false;
}

function onReplyComment(comment: IComment) {
  commentContent.value = `@${comment.userName}\n>${comment.content.replace(
      /\n/g,
      "\n>"
  )}\n\n`;
}

const onDeleteComment = (comment: IComment) => {
  ElMessageBox.confirm("前辈确定删除这条评论吗？", "一条友善的提示", {
    confirmButtonText: "你在教我做事？",
    cancelButtonText: "我再想想",
    type: "warning",
  }).then(async () => {
    const res = await deleteCommentApi(comment.id)
    if (res.code == 200) {
      ElMessage.success("删除评论成功~");
      await onCurrentCommentPageChanged(currentCommentPageNum.value);
    }
  })
}

function onUpdateComment(comment: IEditedComment) {
  commentContent.value = comment.content;
  isInEditMode.value = true;
  editedComment = comment;
}

const isThumb = ref<boolean>(false)
const isStep = ref<boolean>(false)

// 点赞
const handleLike = async () => {
  const res = await doThumbApi(props.id)
  if (res.code === 200) {
    await getArticleDetails()
    await getUserIsThumb()
  }
}
//获取用户是否点赞
const getUserIsThumb = async () => {
  const res = await isThumbApi(props.id)
  if (res.code === 200) {
    isThumb.value = res.data
  }
}

// 踩
const handleStep = async () => {
  const res = await doStepApi(props.id)
  if (res.code === 200) {
    await getArticleDetails()
    await getUserIsStep()
  }
}
//获取用户是否点赞
const getUserIsStep = async () => {
  const res = await isStepApi(props.id)
  if (res.code === 200) {
    isStep.value = res.data
  }
}

onMounted(() => {
  if (isLogin()) {
    getUserIsThumb()
    getUserIsStep()
  }
  window.scrollTo({top: 0});
  getArticleDetails();
  updateViewCount()
  getPreviousNextArticle();
  onCurrentCommentPageChanged(1);
});
</script>

<style lang="less" scoped>
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
}

.article-info {
  text-align: center;
  position: absolute;
  width: 100%;
  text-shadow: 0 3px 6px rgba(0, 0, 0, 0.3);
}

.article-title {
  font-size: 35px;
  font-weight: normal;
  color: white;
  line-height: 1.5;
  margin-bottom: 15px;
  padding: 0 30px;
  overflow: hidden;
  display: -webkit-box;
  text-overflow: ellipsis;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
}

.edit-icon {
  display: inline-block;
  cursor: pointer;
  transition: all 0.4s;

  &:hover {
    color: #ff7242;
  }
}

.article-meta-data-wrap {
  display: flex;
  justify-content: center;
}

.article-meta-data {
  font-size: 14px;
  color: white;
  box-sizing: border-box;
  line-height: 24px;
  overflow: hidden;
  -webkit-line-clamp: 1;
  display: -webkit-box;
  -webkit-box-orient: vertical;
}

.article-meta-data-divider {
  color: white;
  margin: 3px 8px;
  font-size: 14px;
}

.post-body {
  width: 74%;
  background: white;
  border-radius: 8px;
  box-shadow: var(--card-box-shadow);
  padding: 30px 40px;
  box-sizing: border-box;

  :deep(.article-content) {
    img {
      display: block;
      margin: 15px auto 15px;
      border-radius: 6px;
      width: 100%;
      cursor: pointer;
      cursor: zoom-in;
      box-shadow: 0 1px 15px rgba(27, 31, 35, 0.15), 0 0 1px rgba(106, 115, 125, 0.35);
    }

    h1 code,
    h2 code,
    h3 code,
    h4 code,
    h5 code,
    h6 code,
    p > code,
    li > code,
    table code {
      color: #c7254e;
      line-height: 1.2;
      font-family: consolas !important;
      vertical-align: middle;
      margin: 0 3px;
      background-color: #f9f2f4 !important;
      font-size: 14px !important;
      padding: 0.2em 0.3em !important;
      border-radius: 3px !important;
      border: 1px solid #f9f2f4 !important;
    }

    p {
      color: var(--text-color);
      font-size: 15px;
      line-height: 28px;
    }

    h1,
    h2,
    h3,
    h4,
    h5,
    h6 {
      overflow: hidden;
      -webkit-line-clamp: 4;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-box-orient: vertical;
      color: #1f2d3d;
      transition: all 0.2s ease-out;
    }

    h4,
    h5,
    h6 {
      font-size: 16px;
    }

    h1 {
      font-size: 24px;
      margin: 10px 0;
    }

    h2 {
      font-size: 20px;
    }

    h3 {
      font-size: 17px;
    }

    /* 代码样式 */

    pre {
      white-space: pre;
      position: relative;
      border-radius: 7px;
      color: #bababa;
      background-color: #282c34;
      font-size: 14px;
      padding: 0;

      code {
        border: none;
        border-radius: 7px;
        font-family: Consolas, Monaco, "Andale Mono", "Ubuntu Mono", monospace !important;
        line-height: 21px;
      }
    }

    kbd {
      background-color: #f7f7f7;
      color: #222325;
      border-radius: 0.25rem;
      border: 1px solid #cbcccd;
      box-shadow: 0 2px 0 1px #cbcccd;
      cursor: default;
      font-family: Arial, sans-serif;
      font-size: 0.75em;
      line-height: 1;
      min-width: 0.75rem;
      padding: 2px 5px;
      position: relative;
      top: -1px;

      &:hover {
        box-shadow: 0 1px 0 0.5px #cbcccd;
        top: 1px;
      }
    }

    a {
      color: #2d8cf0;
      text-decoration: none;
      transition: all 0.3s ease;
      position: relative;

      &::after {
        content: "";
        display: block;
        width: 0;
        height: 1px;
        position: absolute;
        left: 0;
        bottom: -2px;
        background: #2d8cf0;
        transition: all 0.3s ease-in-out;
      }

      &:hover::after {
        width: 100%;
      }
    }

    hr {
      position: relative;
      margin: 20px 0;
      border: 2px dashed #bfe4fb;
      width: 100%;
      box-sizing: content-box;
      height: 0;
      overflow: visible;
      box-sizing: border-box;
    }

    hr::before {
      position: absolute;
      top: -11px;
      left: 2%;
      z-index: 1;
      color: #bfe4fb;
      content: "✂";
      font-size: 21px;
      line-height: 1;
      -webkit-transition: all 1s ease-in-out;
      -moz-transition: all 1s ease-in-out;
      -o-transition: all 1s ease-in-out;
      -ms-transition: all 1s ease-in-out;
      transition: all 1s ease-in-out;
    }

    hr:hover::before {
      left: calc(98% - 20px);
    }

    table {
      font-size: 15px;
      width: 100%;
      margin: 15px 0px;
      display: block;
      overflow-x: auto;
      border: none;
      border-collapse: collapse;
      border-spacing: 0;

      &::-webkit-scrollbar {
        height: 4px !important;
      }

      th {
        background: #bfe4fb;
        border: 1px solid #a6d6f5;
        white-space: nowrap;
        font-weight: 400;
        padding: 6px 15px;
        min-width: 100px;
      }

      td {
        border: 1px solid #a6d6f5;
        padding: 6px 15px;
        min-width: 100px;
      }
    }

    ul,
    ol {
      li {
        margin: 4px 0px;
      }
    }

    ul li {
      list-style: circle;

      &::marker {
        transition: all 0.4s;
        /* color: #49b1f5; */
        color: var(--theme-color);
        font-weight: 600;
        font-size: 1.05em;
      }

      &:hover::marker {
        color: #ff7242;
      }
    }

    blockquote {
      border: none;
      margin: 15px 0px;
      color: inherit;
      border-radius: 4px;
      padding: 1px 15px;
      border-left: 4px solid var(--theme-color);
      background-color: #f8f8f8;
    }
  }

  .article-signature {
    border: 1px solid #ddd;
    position: relative;
    overflow: hidden;
    margin: 30px 5px 10px 5px;
    border-radius: 6px;
    display: flex;
    align-items: center;
    padding: 12px;
    transition: all 0.4s;

    &:hover {
      box-shadow: 0 3px 15px rgba(0, 0, 0, 0.1);
      transform: translateY(-1px);
    }

    img {
      margin-left: 10px;
      width: 90px;
      height: 90px;
      border-radius: 50%;
      transition: all 0.6s;

      &:hover {
        transform: rotate(-360deg);
      }
    }

    .copyright {
      padding-left: 20px;

      .copyright-item {
        display: -webkit-box;
        overflow: hidden;
        text-overflow: ellipsis;
        -webkit-line-clamp: 1;
        -webkit-box-orient: vertical;
        line-height: 28px;
        font-size: 15px;
        color: var(--text-color);

        a {
          color: #99a9bf;
          transition: all 0.4s;
          cursor: pointer;

          &:hover {
            color: #19b1f5;
          }
        }

        .copyright-title {
          font-weight: bold;
          color: #19b1f5;
        }
      }
    }
  }

  .article-tags {
    padding-left: 3px;
    margin-top: 20px;
    color: var(--text-color);
    font-size: 15px;

    a {
      border-radius: 4px;
      font-size: 13px;
      padding: 3px 12px;
      text-decoration: none;
      transition: all 0.4s;
      background: #49b1f5;
      margin-right: 8px;
      color: white;
      display: inline-block;
    }
  }

  .previous-next-article {
    width: 100%;
    margin-top: 50px;
    border-radius: 9px;

    .previous-article,
    .next-article {
      width: 50%;

      a {
        height: 70px;
        overflow: hidden;
        display: block;
        position: relative;

        img {
          height: 100%;
          width: 100%;
          position: absolute;
          object-fit: cover;
          opacity: 0.5;
          transition: all 0.6s ease-in-out;

          &:hover {
            transform: scale(1.1);
            opacity: 0.8;
          }
        }

        .previous-article-info,
        .next-article-info {
          pointer-events: none;
          position: absolute;
          top: 50%;
          width: 100%;
          padding: 20px 40px;
          transform: translate(0, -50%);
          color: white;
          line-height: 28px;
          box-sizing: border-box;

          .label {
            font-size: 13px;
          }

          .title {
            font-weight: 500;
            font-size: 14px;
            display: -webkit-box;
            overflow: hidden;
            text-overflow: ellipsis;
            -webkit-line-clamp: 1;
            -webkit-box-orient: vertical;
          }
        }

        .next-article-info {
          text-align: right;
        }
      }
    }
  }

  #article-desc {
    font-size: 14px;
    margin-top: 8px;
    text-align: right;
    color: var(--text-color);

    span,
    a {
      padding: 0 4px;
    }

    a {
      text-decoration: none;
      color: var(--text-color);
      transition: all 0.6s ease-out;

      &:hover {
        color: var(--theme-color);
      }
    }
  }

  #comment-area {
    margin-top: 50px;

    .comment-area-title {
      font-size: 20px;
      margin: 20px 0;
      padding-bottom: 5px;
      color: var(--text-color);

      .comment-icon {
        margin-right: 7px;
        color: #e173b3;
      }
    }

    :deep(#comment-pagination) {
      margin: 20px 0;
      justify-content: center;

      .number,
      .btn-prev,
      .btn-next {
        border-radius: 6px;
      }
    }
  }

  #comment-form {
    #comment-form-title {
      font-size: 20px;
      margin: 40px 0 20px;
      color: var(--text-color);
    }

    #comment-editor {
      #mavon {
        border-color: #eef2f8;
      }

      #comment-submit-btn {
        color: white;
        background-color: var(--theme-color);
        border: 1px solid var(--theme-color);
        border-radius: 5px;
        cursor: pointer;
        padding: 7px 17px;
        font-size: 13px;
        margin: 10px 0;
        transition: all 0.3s ease-out;

        &:hover {
          opacity: 0.7;
        }
      }
    }
  }
}

.sticky-layout {
  position: sticky;
  top: 20px;
}

@media screen and (max-width: 900px) {
  .post-body {
    width: 100%;
  }

  #article-desc {
    display: none;
  }
}

@keyframes fadeInUp {
  from {
    margin-top: 50px;
    opacity: 0;
  }

  to {
    margin-top: 0;
    opacity: 1;
  }
}
</style>
