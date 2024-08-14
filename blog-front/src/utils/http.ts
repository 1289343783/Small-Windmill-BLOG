import axios from "axios";
import {AxiosResponse} from "axios";
import {ElMessage} from "element-plus";
import {clearToken, getToken} from "./auth";
import router from "@/router";

const http = axios.create();
// const { loadingBar, message } = createDiscreteApi(["loadingBar", "message"]);
http.interceptors.request.use(
    (config) => {
        // loadingBar.start();
        let needAuthentication = config.headers.get("needAuthentication")
        // 设置令牌
        if (needAuthentication && getToken()) {
            config.headers.token = getToken();
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

http.interceptors.response.use(
    async (response: AxiosResponse) => {
        const res = response.data;
        // loadingBar.finish();
        if (response.config.responseType === "blob") {
            // 如果是文件流，直接过
            return response;
        } else if (res.code !== 200) {
            if (res.code === 401) {
                clearToken();
                await router.replace('/login')
            }
            if (res.code === 500) {
                clearToken();
                await router.push("/login")
            }
            if (res.msg) {
                ElMessage.error(res.msg);
            }
        }
        return res;
    },
    (error) => {
        return Promise.reject(error);
    }
);
export default http;
