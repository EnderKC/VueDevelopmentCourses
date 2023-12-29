interface Book{
    id:string
    goodsName:string
    goodsDescription:string
    goodsStock:string
    userId:string
}

interface BookSearch{
    goodsName:string
    goodsDescription:string
}

export type {Book,BookSearch}