import {IResponseType} from "@/types/shims-axios";
import axios from "axios";
import {AxiosResponse} from "axios";
import {ElMessage} from "element-plus";

const keyValue = "06c00e7dc34f4c3f95308459bc87db14";

interface IImageParam {
    imgWidth: number
    imgHeight: number
    imgFormat: string
    imgName: string
    imgDesc: string
}

interface IGallery {
    /**
     * 主键id
     */
    id: number

    /**
     * 用户id
     */
    userid: number

    /**
     * 图片类型
     */
    imgType: string


    /**
     * 图片大小
     */
    imgSize: number

    /**
     * 图片宽度
     */
    imgWidth: number

    /**
     * 图片高度
     */
    imgHeight: number

    /**
     * 图片格式
     */
    imgFormat: string

    /**
     * 图片hash
     */
    imgHash: string

    /**
     * 图片名称
     */
    imgName: string

    /**
     * 图片地址
     */
    imgUrl: string

    /**
     * 图片描述
     */
    imgDesc: string


    /**
     * 创建时间
     */
    createTime: string

    /**
     * 更新时间
     */
    updateTime: string
}


const service = axios.create();
service.interceptors.response.use(
    async (response: AxiosResponse) => {
        const code = response.data.code || 200
        if (code === 200) {
            console.log(response.data)
            return response.data
        }
        let msg = response.data.code + " " + response.data.msg
        ElMessage.error(msg)

        return Promise.reject('上传图片失败：' + msg)
    },
    (error: any) => {
        return Promise.reject(error);
    }
);


/**
 * 上传图片
 * @param {File} file 图片文件
 * @param config
 * @returns promise
 */
// export const uploadImageApi = (file: File, config?: any) => {
//     let formData = new FormData();
//     formData.append("image", file)
//     return service.post<IImgResponseType<IImage>>(`https://api.imgbb.com/1/upload?key=${keyValue}`, formData, config);
// }

/**
 * 上传图片
 * @param {File} file 图片文件
 * @param config
 * @param param
 * @returns promise
 */
export const uploadImageApi = (file: File, config?: any, param?: IImageParam) => {
    let formData = new FormData();
    let imgWidth = param?.imgWidth ? String(param.imgWidth) : "100"
    let imgHeight = param?.imgHeight ? String(param.imgHeight) : "100"
    formData.append("file", file)
    formData.append("imgKey", keyValue)
    formData.append("imgWidth", imgWidth)
    formData.append("imgHeight", imgHeight)
    formData.append("imgFormat", param?.imgFormat == undefined ? '' : param.imgFormat)
    formData.append("imgName", param?.imgName == undefined ? '' : param.imgName)
    formData.append("imgDesc", param?.imgDesc == undefined ? '' : param.imgDesc)
    return service.post<IResponseType<IGallery>>("http://localhost:8081/file/upload", formData, config);
}


