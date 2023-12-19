import request from '@/utils/request'
import BaseApi from '@/utils/baseApi'
import { ResponseModel } from './model/resModel'

/**
 * 模块基础url
 */
const subBaseUrl = '/stuinfo/'

class StuInfoApi extends BaseApi {
  constructor() {
    super(subBaseUrl)
  }

  /**
   * 检测学号是否唯一
   * @param {object} data
   * @return {Promise}
   */
  checkStuId(data: object): Promise<ResponseModel> {
    return request({
      url: subBaseUrl + `checkStuId/${data}`,
      method: 'get'
    })
  }
}

export default StuInfoApi
