/*
 * @Author: EnderKC
 * @Descripttion: 员工管理API
 * @Date: 2023-12-19 14:39:44
 * @LastEditors: Please set LastEditors
 * @LastEditTime: 2023-12-19 14:39:44
 */
import request from '@/utils/request'
import Emp from '@/api/model/empModel'
import ReturnModel from './model/returnModel'

/**
 * 模块基础url
 */

class EmpAPI {
    subBaseUrl = '/tEmp'

    // 查找所有员工
    async findAllEmp(): Promise<ReturnModel> {
        return request({
            url: this.subBaseUrl + '/findAllEmp',
            method: 'post',
        })
    }

    // 删除某个员工
    async deleteEmp(data:{id:string}): Promise<ReturnModel> {
        return request({
            url: this.subBaseUrl + '/deleteEmp',
            method: 'post',
            data: data
        })
    }

    // 添加某个员工
    async addEmp(data: Emp): Promise<ReturnModel> {
        return request({
            url: this.subBaseUrl + '/addEmp',
            method: 'post',
            data: data
        })
    }

    // 修改某个员工
    async updateEmp(data: Emp): Promise<ReturnModel> {
        console.log(data)
        return request({
            url: this.subBaseUrl + '/updateEmp',
            method: 'post',
            data: data
        })
    }

}

export default EmpAPI