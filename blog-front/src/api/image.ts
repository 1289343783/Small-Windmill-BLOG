import {IImgResponseType} from "@/types/shims-axios";
import axios from "axios";
import {AxiosResponse} from "axios";
import {ElMessage} from "element-plus";

const keyValue = "a1ed47d638b9c1a110fc76844cf8eb3a";

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
    (error) => {
        return Promise.reject(error);
    }
);


/**
 * 上传图片
 * @param {File} file 图片文件
 * @param config
 * @returns promise
 */
export const uploadImageApi = (file: File, config?: any) => {
    let formData = new FormData();
    formData.append("image", file)
    return service.post<IImgResponseType<IImage>>(`https://api.imgbb.com/1/upload?key=${keyValue}`, formData, config);
}

