/**
 * 定义登录参数模型
 */
export interface LoginParams {
  username: string
  password: string
}
/**
 * 定义用户信息模型
 */
export interface UserInfoModel {
  stuid: string
  role: string
  stuname?: string
  gender?: string
  classname?: string
}
