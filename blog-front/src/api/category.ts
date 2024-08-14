import { IResponseType, pageDataInfo } from "@/types/shims-axios";
import http from "@/utils/http";

/**
 * 获取所有分类的文章数量
 * @returns promise
 */
export const getCategoryCountList = () => {
    return http.get<IResponseType<ICategory[]>>("/api/category/categoryCountList");
}