interface IArticle {
    id: number;
    categoryId: number;
    categoryName: string;
    content: string;
    createTime: string;
    date: string;
    category: string;
    image: string;
    summary: string;
    tags: { id: number, name: string }[];
    thumbnail: string;
    title: string;
    viewCount: number;
    // 点赞数量
    articleLike: number;
    //踩数量
    articleStep: number;
}

interface IArticles {
    createTime: string
    id: number
    thumbnail: string
    title: string
    viewCount: number
}

interface IPreviousAndINext {
    id: number
    createTime: string
    thumbnail: string
    title: string
    viewCount: number
}

interface IArticleCountInfo {
    article: number
    category: number
    tag: number
}

interface IArticleDTO {
    id: number1,
    title: string
    category: string
    content: string
    summary: string
    tags: string[],
    thumbnail: string
    isDraft: boolean
}


