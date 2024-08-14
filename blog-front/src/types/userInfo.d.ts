interface IUserInfo {
    id: number
    userName: string
    nickName: string
    signature: string
    avatar: string
    sex: string
    isAdmin: boolean
    email: string
    phonenumber: string
    /**
     * 用户类型：0 代表普通用户，1 代表管理员(只能有一个)
     */
    type: string
    githubUrl?: string
}

interface ILoginInfo {
    token: string
    userInfo: IUserInfo
}
