import {IResponseType, pageDataInfo} from "@/types/shims-axios";
import http from "@/utils/http";
import {getRandomAvatar} from "@/utils/avatar"

/**
 * 用户登录
 * @param {string} userName 用户名
 * @param {string} password 密码
 * @returns promise
 */
export const login = (userName: string, password: string) => {
    let param = {userName: userName, password: password};
    return http.post<IResponseType<ILoginInfo>>("/api/login", param);
}
/**
 * 用户注销
 * @returns promise
 */
export const logout = () => {
    return http.post<IResponseType>("/api/logout", null, {
        headers: {
            "needAuthentication": true
        }
    });
}

/**
 * 用户注册
 * @returns promise
 * @param param 参数
 */
export const register = (param: IRegisterDTO) => {
    param.avatar = getRandomAvatar(); // 随机生成头像
    return http.post<IResponseType>("/api/user/register", param);
}

/**
 *  获取管理员信息
 */
export const getAdminInfoApi = () => {
    return http.get<IResponseType<IUserInfo>>("/api/user/adminInfo", {
        headers: {
            "needAuthentication": true
        }
    });
}

/**
 * 获取用户信息
 */
export const getUserInfoApi = () => {
    return http.get<IResponseType<IUserInfo>>("/api/user/userInfo", {
        headers: {
            "needAuthentication": true
        }
    });
}
/**
 * 更新用户信息
 * @returns promise
 * @param param
 */
export const updateUserInfoApi = (param: IUserInfo) => {
    return http.put<IResponseType>("/api/user/userInfo", param, {
        headers: {
            "needAuthentication": true
        }
    });
}






