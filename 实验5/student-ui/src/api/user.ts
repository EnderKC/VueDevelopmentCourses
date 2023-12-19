/*
 * @Author: 李焕哲
 * @Descripttion: 用户管理api
 * @Date: 2021-08-20 09:29:44
 * @LastEditors: Please set LastEditors
 * @LastEditTime: 2023-10-22 17:47:17
 */
import request from '@/utils/request'
import BaseApi from '@/utils/baseApi'
import { LoginParams } from './model/userModel'
import { ResponseModel } from './model/resModel'

/**
 * 模块基础url
 */
const subBaseUrl = '/user/'

class UserApi extends BaseApi {
  constructor() {
    super(subBaseUrl)
  }

  /**
   * 用户登录
   * @param data 用户登录信息
   * @returns
   */
  login(params: LoginParams): Promise<ResponseModel> {
    const headers = {
      'Content-Type': 'multipart/form-data'
    }
    const { username, password } = params
    return request({
      url: `/login`,
      method: 'post',
      headers,
      data: {
        username,
        password
      }
    })
  }
  /**
   * 用户登出
   * @returns
   */
  logout(): Promise<ResponseModel> {
    return request({
      url: `/logout`,
      method: 'get'
    })
  }

  /**
   * 获取当前用户的信息
   * @return {Promise}
   */
  getUserInfo(): Promise<ResponseModel> {
    return request({
      url: subBaseUrl + `getUserInfo`,
      method: 'get'
    })
  }

  /**
   * 注册新月户
   * @param {object} data  实体信息
   * @return {Promise}
   */
  register(data: object): Promise<ResponseModel> {
    return request({
      url: this.subBaseUrl + `register`,
      method: 'post',
      data
    })
  }

  /**
   * 管理员重置密码
   * @param {object} data
   * @return {Promise}
   */
  resetPwd(data: object): Promise<ResponseModel> {
    return request({
      url: subBaseUrl + `resetPwd`,
      method: 'put',
      data
    })
  }

  /**
   * 检测用户名是否唯一
   * @param {object} data
   * @return {Promise}
   */
  checkUserName(data: object): Promise<ResponseModel> {
    return request({
      url: subBaseUrl + `checkUserName/${data}`,
      method: 'get'
    })
  }

  /**
   * 检测手机号是否唯一
   * @param {object} data
   * @return {Promise}
   */
  checkMobile(data: object): Promise<ResponseModel> {
    return request({
      url: subBaseUrl + `checkMobile`,
      method: 'post',
      data
    })
  }

  /**
   * 修改用户信息,普通更新
   * @param {object} data
   * @return {Promise}
   */
  updateUserInfo(data: object): Promise<ResponseModel> {
    return request({
      url: subBaseUrl + `updateUserInfo`,
      method: 'put',
      data
    })
  }

  /**
   * 校验旧密码是否正确
   * @param {string} oldPassword
   * @return {Promise}
   */
  checkOldPassWord(oldPassword: string): Promise<ResponseModel> {
    return request({
      url: subBaseUrl + `checkOldPassWord`,
      method: 'put',
      params: { oldPassword }
    })
  }

  /**
   * 根据用户的手机号查询用户信息
   * @param {string} mobile
   * @return {Promise}
   */
  getUserByMobile(mobile: string): Promise<ResponseModel> {
    return request({
      url: subBaseUrl + `getUserByMobile/${mobile}`,
      method: 'get'
    })
  }
}
export default UserApi
