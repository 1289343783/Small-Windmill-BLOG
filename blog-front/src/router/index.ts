import {createRouter, createWebHistory} from "vue-router";
import {getUserInfo} from "@/utils/auth";
import {useAdminStore} from "@/store/modules/user";
import {ElLoading} from 'element-plus'

const routes = [
    {
        path: "/",
        name: "Home",
        component: () => import("@/views/Home.vue"),
        meta: {
            title: "首页"
        }
    },
    {
        path: "/login",
        name: "Login",
        component: () => import("@/views/Login.vue"),
        meta: {
            title: "登录"
        }
    },
    {
        path: "/register",
        name: "Register",
        component: () => import("@/views/Register.vue"),
        meta: {
            title: "注册用户"
        }
    },
    {
        path: "/article/add",
        name: "ArticleAdd",
        component: () => import("@/views/article/ArticleEdit.vue"),
        meta: {
            needAuthentication: true,
            title: "添加文章"
        }
    },
    {
        path: "/article/:id",
        name: "ArticleDetails",
        component: () => import('@/views/article/ArticleDetails.vue'),
        props: true,
        meta: {
            title: "文章详情"
        }
    },
    {
        path: "/article/:id/edit",
        name: "ArticleEdit",
        component: () => import("@/views/article/ArticleEdit.vue"),
        props: true,
        meta: {
            needAuthentication: true,
            title: "编辑文章"
        }
    },
    {
        path: "/archive",
        name: "ArchiveList",
        component: () => import("@/views/archive/ArchiveList.vue"),
        meta: {
            title: "文章归档"
        }
    },
    {
        path: "/archive/:year/:month",
        name: "ArchiveDetails",
        component: () => import("@/views/archive/ArchiveDetails.vue"),
        props: true,
        meta: {
            title: "归档详情"
        }
    },
    {
        path: "/category",
        name: "CategoryList",
        component: () => import("@/views/category/CategoryList.vue"),
        meta: {
            title: "分类列表"
        }
    },
    {
        path: "/category/:id",
        name: "CategoryDetails",
        component: () => import("@/views/category/CategoryDetails.vue"),
        props: true,
        meta: {
            title: "分类文章"
        }
    },
    {
        path: "/tag",
        name: "TagList",
        component: () => import("@/views/tag/TagList.vue"),
        meta: {
            title: "标签列表"
        }
    },
    {
        path: "/tag/:id",
        name: "TagDetails",
        component: () => import("@/views/tag/TagDetails.vue"),
        props: true,
        meta: {
            title: "标签文章"
        }
    },
    {
        path: "/user/settings",
        name: "UserSettings",
        component: () => import("@/views/Settings.vue"),
        meta: {
            needLogin: true,
            title: "用户设置"
        }
    },
    {
        path: '/:pathMatch(.*)*',
        component: () => import("@/views/PageNotFound.vue"),
        meta: {
            title: "404"
        }
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

/**
 * 路由守卫
 */
let loadingInstance: any = null;
router.beforeEach(async (to, from, next) => {
    const options = {
        fullscreen: true,
        spinner: 'el-icon-loading',
        // 添加自定义类名，避免影响页面内部及button的默认loading样式
        customClass: 'iam-loading',
        text: '正在加载中...'
    };
    loadingInstance = ElLoading.service(options)
    const {title, needAuthentication} = to.meta
    // 修改标题
    if (title) {
        document.title = (title as string) + ' - BLOG-WINDMILL'
    } else {
        document.title = 'BLOG-WINDMILL'
    }
    if (needAuthentication) {
        let isAdmin = getUserInfo() ? getUserInfo().isAdmin : false;
        if (isAdmin) {
            await useAdminStore().getAdminApi()
            next()
        } else {
            next({name: "Login"})
        }
    } else if (to.meta.needLogin) {
        let userInfo = getUserInfo()
        if (userInfo) {
            await useAdminStore().getAdminApi()
            next()
        } else {
            next({name: "Login"})
        }
    } else {
        next()
    }
})

router.afterEach(() => {
    setTimeout(() => {
        loadingInstance.close()
    }, 500)
});

export {routes};
export default router;
