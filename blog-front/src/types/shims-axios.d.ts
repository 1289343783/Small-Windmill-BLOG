import {AxiosRequestConfig} from 'axios'

declare module 'axios' {
    export interface AxiosInstance {
        <T = any>(config: AxiosRequestConfig): Promise<T>

        request<T = any>(config: AxiosRequestConfig): Promise<T>

        get<T = any>(url: string, config?: AxiosRequestConfig): Promise<T>

        delete<T = any>(url: string, config?: AxiosRequestConfig): Promise<T>

        head<T = any>(url: string, config?: AxiosRequestConfig): Promise<T>

        options<T = any>(url: string, config?: AxiosRequestConfig): Promise<T>

        post<T = any>(
            url: string,
            data?: any,
            config?: AxiosRequestConfig
        ): Promise<T>

        put<T = any>(
            url: string,
            data?: any,
            config?: AxiosRequestConfig
        ): Promise<T>

        patch<T = any>(
            url: string,
            data?: any,
            config?: AxiosRequestConfig
        ): Promise<T>
    }
}

// 统一请求返回类型
interface IResponseType<P = object> {
    code: number
    data: P
    message: string
}

interface IImgResponseType<P = object> {
    status: number
    data: P
    success: boolean
}

interface pageDataInfo<P = object> {
    rows: P
    records: P
    total: string
    size: number
    current: number
    optimizeCountSql: boolean
    searchCount: boolean
    maxLimit: number
    countId: number
    pages: number
    code: number
}