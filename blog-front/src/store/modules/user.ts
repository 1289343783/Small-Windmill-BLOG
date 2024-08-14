import {getArticleCountApi} from "@/api/article";
import {getAdminInfoApi} from "@/api/user";
import {clearToken, getUserInfo} from "@/utils/auth";
import {defineStore} from "pinia";
import {ElMessage} from "element-plus";

export interface AdminState {
    adminInfo?: IUserInfo,
    articleCountInfo?: IArticleCountInfo,
    isAdmin?: boolean
}

export const useAdminStore = defineStore("admin", {
    state: (): AdminState => {
        return {
            adminInfo: {
                id: 0,
                userName: "",
                nickName: "",
                signature: "",
                avatar: "/image/avatar.jpg",
                sex: "",
                isAdmin: false,
                email: "",
                phonenumber: "",
                type: "",
                githubUrl: "https://github.com/1289343783",
            },
            articleCountInfo: {
                article: 0,
                category: 0,
                tag: 0,
            },
            isAdmin: getUserInfo() ? getUserInfo().isAdmin : false
        }
    },
    getters: {
        /**
         * 获取用户信息
         * @returns 用户信息
         */
        getAdminInfo(): IUserInfo | undefined {
            return this.adminInfo
        }
    },
    persist: {
        key: "admin",
        storage: localStorage,
    },
    actions: {
        /**
         * 设置用户信息
         * @param adminInfo
         */
        setAdminInfo(adminInfo: IUserInfo | undefined): void {
            this.adminInfo = adminInfo
        },
        /**
         * 设置文章数量
         * @param articleCountInfo 文章数量信息
         */
        setArticleCountInfo(articleCountInfo: IArticleCountInfo | undefined): void {
            this.articleCountInfo = articleCountInfo
        },
        /**
         * 获取用户是否为管理员
         */
        getIsAdmin() {
            return this.isAdmin = getUserInfo() ? getUserInfo().isAdmin : false
        },
        async getAdminApi() {
            await getAdminInfoApi()
                .then((res) => {
                    if (res.data) {
                        this.setAdminInfo(res.data)
                    }
                })
                .catch(() => {
                    this.clearAdminInfo()
                    ElMessage.error('服务器连接失败！')
                })
        },
        async getArticleCountInfo() {
            await getArticleCountApi().then((res) => {
                this.setArticleCountInfo(res.data)
            })
        },
        /**
         * 清除所有与用户数据相关信息
         */
        clearAdminInfo() {
            clearToken()
            this.setAdminInfo(undefined)
        },
        /**
         * 注销登录
         */
        // async logout(): Promise<void> {
        //     await logoutApi()
        //     this.clearUserInfo()
        // }
    }
})
