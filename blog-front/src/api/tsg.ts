import { IResponseType, pageDataInfo } from "@/types/shims-axios";
import http from "@/utils/http";

/**
 * 获取所有标签的文章数量
 * @returns promise
 */
export const getTagCountList = () => {
    return http.get<IResponseType<ITag[]>>("/api/tag/tagCountList")
}