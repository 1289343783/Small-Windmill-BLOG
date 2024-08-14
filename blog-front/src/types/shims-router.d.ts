import VueRouter, { Route } from 'vue-router'
declare module 'vue-router' {
  export interface RouteMeta {
    title?: string
    isAuth?: boolean
    isAdmin?: boolean
    isKeepAlive?: boolean
    isMenuShow?: boolean
    roles?: string[]
    menuIcon?: string
    /**
     * @description 是否显示在tab上
     */
    isTab?: boolean
    // 权重
    weight?: number
  }
}
