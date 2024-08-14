import {IResponseType, pageDataInfo} from "@/types/shims-axios";
import http from "@/utils/http";

/**
 * 点赞 / 取消点赞
 * @param articleId 文章id
 * @returns
 */
export const doThumbApi = (articleId: number) => {
    return http.post<IResponseType>("/api/thumb", {
        articleId: articleId
    }, {
        headers: {
            "needAuthentication": true
        }
    })
}
/**
 * 获取用户是否点赞
 * @param id
 */
export const isThumbApi = (id: number) => {
    return http.get<IResponseType<boolean>>(`/api/thumb/${id}`, {
        headers: {
            "needAuthentication": true
        }
    })
}