/**
 * 定义返回数据模型
 */
export interface ResponseModel {
  code: number
  msg: string
  data?: any
  timestamp?: string
  path?: string
}
