import {IResponseType, pageDataInfo} from "@/types/shims-axios";
import http from "@/utils/http";
import qs from "query-string";

/**
 * 获取文章评论列表
 * @param {number} articleId 文章 id
 * @param {number} pageNum 页码
 * @param {number} pageSize 每页评论数
 */
export const getCommentList = (articleId: number, pageNum: number, pageSize: number) => {
    return http.get<IResponseType<pageDataInfo<IComment[]>>>("/api/comment/commentList",
        {
            params: {articleId, pageNum, pageSize}
        }
    )
}

/**
 * 添加评论
 * @param {number} articleId 文章 id
 * @param {string} content 评论内容
 */
export const addCommentApi = (articleId: number, content: string) => {
    let param = {articleId: articleId, content: content}
    return http.post<IResponseType>("/api/comment", param, {
        headers: {
            "needAuthentication": true
        }
    })
}

/**
 * 更新评论
 * @param {number} id 评论 id
 * @param {string} content 评论内容
 */
export const updateCommentApi = (id: number, content: string) => {
    let param = {id: id, content: content}
    return http.put<IResponseType>("/api/comment", param, {
        headers: {
            "needAuthentication": true
        }
    })
}

/**
 *
 * @param {number} id 删除评论
 */
export const deleteCommentApi = (id: number) => {
    return http.delete<IResponseType>(`/api/comment/${id}`, {
        headers: {
            "needAuthentication": true
        }
    })
}




