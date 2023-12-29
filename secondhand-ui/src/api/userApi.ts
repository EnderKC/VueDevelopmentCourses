/*
 * @Author: EnderKC
 * @Descripttion: 用户登录注册API
 * @Date: 2023-12-19 14:39:44
 * @LastEditors: Please set LastEditors
 * @LastEditTime: 2023-12-19 14:39:44
 */
import request from '@/utils/request'
import {UserLogin,UserRegister,SendVerificationCode} from '@/api/model/userModel'
import ReturnModel from './model/returnModel'
import { UserInfo } from '@/api/model/userModel';

/**
 * 模块基础url
 */

class UserAPI {
    subBaseUrl = ''

    async login(data: UserLogin): Promise<ReturnModel> {
        return request({
            url: this.subBaseUrl + '/login',
            method: 'post',
            data: data
        })
    }

    async register(data: UserRegister): Promise<ReturnModel> {
        return request({
            url: this.subBaseUrl + '/register',
            method: 'post',
            data: data
        })
    }

    async sendVerificationCode(data:SendVerificationCode): Promise<ReturnModel> {
        return request({
            url: this.subBaseUrl + '/sendVerificationCode',
            method: 'post',
            data: data
        })
    }

    // 获取用户个人信息
    async getUserInfo(): Promise<ReturnModel> {
        return request({
            url: this.subBaseUrl + '/userInfo/getInfo',
            method: 'post'
        })
    }

    // 修改用户个人信息
    async updateUserInfo(data: UserInfo): Promise<ReturnModel> {
        return request({
            url: this.subBaseUrl + '/userInfo/update',
            method: 'post',
            data: data
        })
    }
}

export default UserAPI