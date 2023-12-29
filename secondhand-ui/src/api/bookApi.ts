/*
 * @Author: EnderKC
 * @Descripttion: 用户查询书籍API
 * @Date: 2023-12-19 14:39:44
 * @LastEditors: Please set LastEditors
 * @LastEditTime: 2023-12-19 14:39:44
 */
import request from '@/utils/request'
import {BookSearch} from '@/api/model/bookModel'
import ReturnModel from './model/returnModel'

/**
 * 模块基础url
 */

class BookAPI {
    subBaseUrl = '/goods'
    // 查询所有的数据
    async listBooks(data:BookSearch): Promise<ReturnModel> {
        return request({
            url: this.subBaseUrl + '/listGoods',
            method: 'post',
            data: data
        })
    }
}

export default BookAPI