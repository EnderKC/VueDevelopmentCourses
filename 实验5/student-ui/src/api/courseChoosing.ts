/*
 * @Author: EnderKC
 * @Descripttion: 课程管理api
 * @Date: 2023-12-19 14:39:44
 * @LastEditors: Please set LastEditors
 * @LastEditTime: 2023-12-19 14:39:44
 */

import BaseApi from '@/utils/baseApi'
import request from '@/utils/request'
import { ResponseModel } from '@/api/model/resModel'

/**
 * 模块基础url
 */
const subBaseUrl = '/courseChoosing/'

class CourseChoosingApi extends BaseApi {
    // 构造方法
    constructor() {
        super(subBaseUrl)
    }
    /**
   * 删除实体
   * @param {string | number | object} id 实体编号
   * @return {Promise}
   */
    delete(params: string | number | object): Promise<ResponseModel> {
        return request({
            url: this.subBaseUrl + `deleteById`,
            method: 'delete',
            params: params
        })
    }
}

export default CourseChoosingApi