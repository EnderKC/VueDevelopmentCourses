/*
 * @Author: EnderKC
 * @Descripttion: 用户登录注册API
 * @Date: 2023-12-19 14:39:44
 * @LastEditors: Please set LastEditors
 * @LastEditTime: 2023-12-19 14:39:44
 */
import request from '@/utils/request'
import User from '@/api/model/userModel'
import ReturnModel from './model/returnModel'

/**
 * 模块基础url
 */

class UserAPI {
    subBaseUrl = ''

    async login(data: User): Promise<ReturnModel> {
        return request({
            url: this.subBaseUrl + '/login',
            method: 'post',
            data: data
        })
    }

    async register(data: User): Promise<ReturnModel> {
        return request({
            url: this.subBaseUrl + '/register',
            method: 'post',
            data: data
        })
    }

}

export default UserAPI