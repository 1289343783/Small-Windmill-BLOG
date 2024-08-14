import { IResponseType, pageDataInfo } from "@/types/shims-axios";
import http from "@/utils/http";

/**
 * 获取归档及其文章数量统计
 * @param {number} pageNum 当前页码
 * @param {number} pageSize 每页条目数
 * @returns promise
 */
export const getArchiveCountListApi = (pageNum: number, pageSize: number) => {
    return http.get<IResponseType<pageDataInfo<IArchiveCount[]>>>("/api/archive/archiveCountList",
        {
            params: {
                pageNum,
                pageSize,
            },
        })
}

/**
 * 获取归档列表
 * @param {number} pageNum 当前页码
 * @param {number} pageSize 每页条目数
 * @returns promise
 */
export const getArchiveListApi = (pageNum: number, pageSize: number) => {
    return http.get<IResponseType<pageDataInfo<IArchive[]>>>("/api/archive/archiveList",
        {
            params: {
                pageNum,
                pageSize,
            },
        }
    )
}

