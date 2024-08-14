import {IResponseType, pageDataInfo} from "@/types/shims-axios";
import http from "@/utils/http";

/**
 * 踩 / 取消踩
 * @param articleId 文章id
 * @returns
 */
export const doStepApi = (articleId: number) => {
    return http.post<IResponseType>("/api/step", {
        articleId: articleId
    }, {
        headers: {
            "needAuthentication": true
        }
    })
}
/**
 * 获取用户是否踩
 * @param id
 */
export const isStepApi = (id: number) => {
    return http.get<IResponseType<boolean>>(`/api/step/${id}`, {
        headers: {
            "needAuthentication": true
        }
    })
}