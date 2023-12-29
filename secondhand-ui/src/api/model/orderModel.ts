import {Dayjs} from "dayjs";

interface Order{
    goodsID:String
}

interface OrderInfo{
    orderId:String
    sellerUserId:string
    buyerUserId:string
    goodsId:string
    creatDate:Dayjs | string | undefined
    statusId:number
}

interface OrderModify{
    orderId:String
}

export type {Order,OrderInfo,OrderModify}