interface ICatalog {
    id: string
    level: number
    parent: any
    children: ICatalog[]
    rawName: string
    scrollTop: number
    isVisible: boolean
    name: string
}