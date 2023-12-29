/*
 * @Author: EnderKC
 * @Descripttion: 用户查询书籍API
 * @Date: 2023-12-19 14:39:44
 * @LastEditors: Please set LastEditors
 * @LastEditTime: 2023-12-19 14:39:44
 */
import request from '@/utils/request'
import {Order, OrderModify} from '@/api/model/orderModel'
import ReturnModel from './model/returnModel'

/**
 * 模块基础url
 */

class OrderAPI {
    subBaseUrl = '/order'
    // 下单
    async addOrder(data:Order): Promise<ReturnModel> {
        return request({
            url: this.subBaseUrl + '/placeOrder',
            method: 'post',
            data: data
        })
    }

    // 获取订单列表
    async getMyOrder(): Promise<ReturnModel> {
        return request({
            url: this.subBaseUrl + '/getMyOrder',
            method: 'post'
        })
    }

    // 标记为已发货
    async sendGoods(data:OrderModify): Promise<ReturnModel> {
        return request({
            url: this.subBaseUrl + '/sendGoods',
            method: 'post',
            data: data
        })
    }

    // 标记为已完成
    async finishOrder(data:OrderModify): Promise<ReturnModel> {
        return request({
            url: this.subBaseUrl + '/finishOrder',
            method: 'post',
            data: data
        })
    }
}

export default OrderAPI