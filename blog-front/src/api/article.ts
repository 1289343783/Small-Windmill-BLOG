import {IResponseType, pageDataInfo} from "@/types/shims-axios";
import http from "@/utils/http";

/**
 * 获取发表的文章
 * @param {number} pageNum 当前页码
 * @param {number} pageSize 每一页的文章数
 * @param {number} categoryId 文章所属分类，可以不传
 * @param {number} tagId 文章的标签，可以不传
 * @param {string} date 文章的发表日期，格式为 `yyyy/MM`，比如 `2021/1` 或 `2021/02`，可以不传
 * @returns promise
 */
export const getPostArticleListApi = (pageNum: number, pageSize: number, categoryId?: number, tagId?: number, date?: string) => {
    let params = {pageNum, pageSize, categoryId, tagId, date}
    return http.get<IResponseType<pageDataInfo<IArticles[]>>>("/api/article/articleList", {
        params: params
    });
}
/*
* 获取热门文章
* @returns promise
*/
export const getHotArticleListApi = () => {
    return http.get<IResponseType<IArticles[]>>("/api/article/hotArticleList");
}

/**
 * 根据id获取文章接口
 * @param data
 * @returns
 */
export const getBlogApi = (id: number) => {
    return http.get<IResponseType<IArticle>>(`/api/article/${id}`);
}
/**
 * 更新文章阅读数
 * @param {number} id 文章 id
 * @returns promise
 */
export const updateViewCountApi = (id: number) => {
    return http.put<IResponseType>("/api/article/updateViewCount/" + id);
}

/**
 * 获取文章上一篇文章和下一篇文章接口
 * @param id  id number
 * @returns
 */
export const previousNextArticleApi = (id: number) => {
    return http.get<IResponseType<{
        previous: IPreviousAndINext,
        next: IPreviousAndINext
    }>>(`/api/article/previousNextArticle/${id}`);
}

/**
 * 获取文章数量接口
 */
export const getArticleCountApi = () => {
    return http.get<IResponseType<IArticleCountInfo>>("/api/article/count");
}

/**
 * 添加文章
 * @param {object} article 文章
 * @returns promise
 */
export const addArticleApi = (article: IArticleDTO) => {
    return http.post<IResponseType<number>>("/api/article", article, {
        headers: {
            "needAuthentication": true
        }
    });
}

/**
 * 编辑文章
 * @param {object} article 文章
 * @returns promise
 */
export const editArticleApi = (article: IArticleDTO) => {
    return http.put<IResponseType<number>>("/api/article", article, {
        headers: {
            "needAuthentication": true
        }
    });
}





