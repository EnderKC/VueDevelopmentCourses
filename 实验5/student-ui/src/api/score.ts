/*
 * @Author: EnderKC
 * @Descripttion: 课程管理api
 * @Date: 2023-12-19 14:39:44
 * @LastEditors: Please set LastEditors
 * @LastEditTime: 2023-12-19 14:39:44
 */

import BaseApi from '@/utils/baseApi'

/**
 * 模块基础url
 */
const subBaseUrl = '/score/'

class ScoreApi extends BaseApi {
    // 构造方法
    constructor() {
        super(subBaseUrl)
    }
}

export default ScoreApi