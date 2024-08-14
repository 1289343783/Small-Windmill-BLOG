interface IComment {
    id: number
    articleId: number
    avatar: string
    content: string
    createBy: number
    createTime: string
    isAdmin: boolean
    userName: string
}

interface IEditedComment {
    id: number

    /**
     * 文章id
     */
    articleId: number

    /**
     * 评论内容
     */
    content: string
}
