<template>
  <div id="archive-list">
    <!-- 页头 -->
    <BlogHeader/>

    <!-- 二次元封面 -->
    <BlogWifeCover>
      <h1>归档</h1>
    </BlogWifeCover>

    <div class="container">
      <!-- 侧边栏 -->
      <BlogSideBar/>

      <!-- 归档 -->
      <div class="archive-body">
        <div class="archive-card">
          <el-timeline class="timeline">
            <el-timeline-item
                :timestamp="`历史文章 - ${articleCount}`"
                center
                class="root-item"
                placement="top"
            >
            </el-timeline-item>

            <el-timeline-item
                v-for="archive in archives"
                :key="archive.year"
                :timestamp="archive.year + ''"
                class="year-item"
                placement="top"
            >
              <div
                  v-for="article in archive.articles"
                  :key="article.id"
                  class="article-item"
              >
                <router-link :to="`/article/${article.id}`" class="article-thumbail-link"
                ><img
                    :src="article.thumbnail"
                    alt="缩略图"
                    class="article-thumbnail"
                    @error.once="useDefaultThumbnail"
                />
                </router-link>

                <div class="article-info">
                  <router-link :to="`/article/${article.id}`" class="article-title"
                  >{{ article.title }}
                  </router-link>
                  <div class=" flex items-center  mt-2 text-xs text-gray-500">
                    <el-icon :size="20">
                      <Icon icon="lucide:calendar-days"/>
                    </el-icon>
                    <span class="ml-2">
                    发表于 {{ article.createTime }}
                    </span>
                    <el-icon :size="20" class="ml-2">
                      <Icon icon="ph:eye-duotone"/>
                    </el-icon>
                    <span class="ml-2">
                     {{ article.viewCount }}次围观
                    </span>
                  </div>
                </div>
              </div>
            </el-timeline-item>
          </el-timeline>
        </div>

        <!-- 分页 -->
        <el-pagination
            v-if="articleCount as number > 0"
            id="pagination"
            :page-size="pageSize"
            :total="articleCount"
            background
            layout="prev, pager, next"
            @current-change="onCurrentPageChanged"
        />
      </div>
    </div>

    <!-- 页脚 -->
    <BlogFooter/>

    <!-- 回到顶部 -->
    <BlogBackToTop/>
  </div>
</template>

<script lang="ts" setup>
import {getArchiveListApi} from "@/api/archive";
import {computed, onMounted, reactive} from "vue";
import {defaultThumbnail, useDefaultThumbnail} from "@/utils/thumbnail";
import {useAdminStore} from "@/store/modules/user";
import BlogHeader from "@/components/BlogHeader.vue";
import BlogWifeCover from "@/components/BlogWifeCover.vue";
import BlogSideBar from "@/components/BlogSideBar.vue";
import BlogFooter from "@/components/BlogFooter.vue";
import BlogBackToTop from "@/components/BlogBackToTop.vue";
import {Icon} from "@iconify/vue"

let pageSize = 10;
let archives = reactive<IArchive[]>([]);
const adminStore = useAdminStore();

let articleCount = computed(() => adminStore.articleCountInfo?.article);

const onCurrentPageChanged = async (pageNum: number) => {
  const res = await getArchiveListApi(pageNum, pageSize);
  if (res.code == 200) {
    res.data.rows.forEach((archive: IArchive) => {
      archive.articles?.forEach((article: IArticles) => {
        article.createTime = article.createTime.split(" ")[0];
        article.thumbnail = article.thumbnail || defaultThumbnail;
      });
    });
  }
  archives.splice(0, archives.length, ...res.data.rows);
  // 多插入一个啥也没有的节点
  archives.push({year: 0, articles: []});
};

onMounted(() => {
  window.scrollTo({top: 0});
  onCurrentPageChanged(1);
});
</script>

<style lang="less" scoped>
#archive-list {
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

.archive-body {
  width: 74%;
  height: 100%;

  .archive-card {
    background: white;
    border-radius: 8px;
    box-shadow: var(--card-box-shadow);
    padding: 50px 24px;
  }

  :deep(#pagination) {
    margin-top: 20px;
    justify-content: center;

    & > button {
      box-shadow: var(--card-box-shadow);
      background: white;
      border-radius: 8px;
      height: 35px;
      width: 35px;
    }

    li {
      box-shadow: var(--card-box-shadow);
      background-color: white;
      border-radius: 8px;
      margin: 0 6px;
      height: 35px;
      width: 35px;
    }

    li.active {
      color: white;
      background: var(--theme-color);
      font-weight: normal;
    }
  }
}

:deep(.timeline) {
  margin-top: 20px;

  .root-item {
    .el-timeline-item {
      padding-bottom: 20px;
    }

    .el-timeline-item__node {
      border: 5px solid var(--theme-color);
      background: transparent;
      width: 20px;
      height: 20px;
      left: -5px;
      top: -7px;
    }

    .el-timeline-item__tail {
      top: calc(50% - 15px);
      height: calc(50% + 15px);
      border-left: 2px solid #4ba9fc;
    }

    .el-timeline-item__wrapper {
      transform: translateY(-11px);

      .el-timeline-item__timestamp {
        font-size: 24px;
        color: var(--text-color);
      }
    }
  }

  .year-item {
    .el-timeline-item__node {
      border: 3px solid #ff7242;
      background: white;
      width: 12px;
      height: 12px;
      left: -1px;
      top: 0;
    }

    .el-timeline-item__wrapper {
      transform: translateY(-6px);

      .el-timeline-item__timestamp {
        font-size: 20px;
        color: var(--text-color);
      }
    }

    .el-timeline-item__tail {
      border-left: 2px solid #9eccf5;
    }
  }

  .year-item:nth-last-child(2) {
    padding-bottom: 0;
  }

  .year-item:last-child {
    visibility: hidden;
  }
}

.article-item {
  display: flex;
  justify-content: center;
  align-content: center;
  padding: 10px 0;

  .article-thumbail-link {
    height: 80px;
    width: 80px;
    overflow: hidden;
    border-radius: 6px;

    .article-thumbnail {
      width: 100%;
      height: 100%;
      object-fit: cover;
      transition: all 0.4s ease;
    }
  }

  .article-thumbnail:hover {
    transform: scale(1.1);
  }

  .article-info {
    flex: 1;
    padding-left: 16px;
    word-break: break-all;
    display: inline-block;
    align-self: center;

    .article-title {
      color: var(--text-color);
      font-size: 15px;
      text-decoration: none;
      transition: color 0.4s;
      overflow: hidden;
      display: -webkit-box;
      -webkit-box-orient: vertical;
      line-height: 1.5;
      -webkit-line-clamp: 2;

      &:hover {
        color: var(--theme-color);
      }
    }

    .article-meta-data {
      font-size: 13px;
      color: rgb(133, 133, 133);
      box-sizing: border-box;
      line-height: 30px;
      overflow: hidden;
      -webkit-line-clamp: 1;
      display: -webkit-box;
      -webkit-box-orient: vertical;

      .article-meta-data-icon {
        margin-right: 5px;
      }

      span {
        margin-right: 20px;
      }
    }
  }
}

@media screen and (max-width: 900px) {
  .archive-body {
    width: 100%;
  }
}
</style>
