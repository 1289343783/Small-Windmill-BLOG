interface IImage {
    id: string
    title: string
    url_viewer: string
    url: string
    display_url: string
    width: number
    height: number
    size: number
    time: number
    expiration: number
    image: IImageResponse
    thumb: IThumbResponse
    delete_url: string
}

interface IImageResponse {
    filename: string
    name: string
    mime: string
    extension: string
    url: string
}

interface IThumbResponse {
    filename: string
    name: string
    mime: string
    extension: string
    url: string
}


