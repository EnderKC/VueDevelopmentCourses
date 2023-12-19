/*
 * @Author: 李焕哲
 * @Descripttion: 基础api模块
 * @Date: 2021-08-20 09:29:44
 * @LastEditors: Please set LastEditors
 * @LastEditTime: 2023-10-22 17:34:37
 */

import request from '@/utils/request'
import { ResponseModel } from '@/api/model/resModel'

class BaseApi {
  /**
   * 基础url
   */
  subBaseUrl: string

  /**
   * 构造函数
   * @param baseUrl 基础url参数
   */
  constructor(baseUrl: string) {
    this.subBaseUrl = baseUrl
  }

  /**
   * 根据id获取实体
   * @param {string | number} id 实体编号
   * @return {Promise}
   */
  getById(id: string | number): Promise<ResponseModel> {
    return request({
      url: this.subBaseUrl + `getById/${id}`,
      method: 'get'
    })
  }

  /**
   * 根据条件查询实体列表
   * @param {object | null} data 查询条件
   * @param {string} pageNum 查询起始页
   * @param {string} pageSize 查询条数
   * @return {Promise}
   */
  list(data: object | null, pageNum: number, pageSize: number): Promise<ResponseModel> {
    const params = new URLSearchParams()
    params.append('pageNum', pageNum.toString())
    params.append('pageSize', pageSize.toString())
    return request({
      url: this.subBaseUrl + `list`,
      method: 'post',
      data,
      params: params
    })
  }

  /**
   * 新增实体
   * @param {object} data  实体信息
   * @return {Promise}
   */
  add(data: object): Promise<ResponseModel> {
    return request({
      url: this.subBaseUrl + `add`,
      method: 'post',
      data
    })
  }

  /**
   * 修改实体信息
   * @param {object} data 实体信息
   * @return {Promise}
   */
  update(data: object): Promise<ResponseModel> {
    return request({
      url: this.subBaseUrl + `update`,
      method: 'put',
      data
    })
  }

  /**
   * 删除实体
   * @param {string | number} id 实体编号
   * @return {Promise}
   */
  delete(id: string | number): Promise<ResponseModel> {
    return request({
      url: this.subBaseUrl + `deleteById/${id}`,
      method: 'delete'
    })
  }

  /**
   * 批量删除实体
   * @param {string} ids 要删除的实体编号列表，以逗号分隔
   * @return {Promise}
   */
  batchDelete(ids: string): Promise<ResponseModel> {
    return request({
      url: this.subBaseUrl + `batchDelete`,
      method: 'delete',
      params: {
        ids: ids
      }
    })
  }

  /**
   * 根据id逻辑删除实体
   * @param {number | string} id 要逻辑删除的实体编号
   * @return {Promise}
   */
  logicDeleteById(id: number | string): Promise<ResponseModel> {
    return request({
      url: this.subBaseUrl + `logicDeleteById/${id}`,
      method: 'get'
    })
  }

  /**
   * 根据id取消删除实体
   * @param {number | string} id 要取消删除的实体编号
   * @return {Promise}
   */
  recallDeleteById(id: number | string): Promise<ResponseModel> {
    return request({
      url: this.subBaseUrl + `recallDeleteById/${id}`,
      method: 'get'
    })
  }

  /**
   * 根据id列表逻辑删除
   * @param {string} ids 要逻辑删除的实体编号列表，以逗号分隔
   * @return {Promise}
   */
  logicDeleteByIds(ids: string): Promise<ResponseModel> {
    return request({
      url: this.subBaseUrl + `logicDeleteByIds`,
      method: 'put',
      params: { ids }
    })
  }

  /**
   * 根据id列表取消删除
   * @param {string} ids 要取消删除的实体编号列表，以逗号分隔
   * @return {Promise}
   */
  recallDeleteByIds(ids: string): Promise<ResponseModel> {
    return request({
      url: this.subBaseUrl + `recallDeleteByIds`,
      method: 'put',
      params: { ids }
    })
  }

  /**
   * 把已做删除标记的数据清空
   * @return {Promise}
   */
  emptyRecycleBin(): Promise<ResponseModel> {
    return request({
      url: this.subBaseUrl + `emptyRecycleBin`,
      method: 'delete'
    })
  }
}
export default BaseApi
