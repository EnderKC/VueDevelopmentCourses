import axios from 'axios'
import { message } from 'ant-design-vue'
/**
 * 对发给后端的请求进行拦截处理，对返回的结果进行拦截处理
 */
axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
// 允许跨域携带cookie
axios.defaults.withCredentials = true

// 创建axios实例，axios是访问后端接口的异步调用
const service = axios.create({
  // axios中请求配置有baseURL选项，表示请求URL公共部分
  baseURL: import.meta.env.VITE_BASE_API,
  // 超时
  timeout: 10000
})

// request拦截器，对所有的请求进行拦截处理，作用就是对params进行处理http://www.baidu.com?a=1&b=2
service.interceptors.request.use(
  (config) => {
    // get请求映射params参数
    if (config.method === 'get' && config.params) {
      let url = config.url + '?'
      for (const propName of Object.keys(config.params)) {
        const value = config.params[propName]
        const part = encodeURIComponent(propName) + '='
        if (value !== null && typeof value !== 'undefined') {
          if (typeof value === 'object') {
            for (const key of Object.keys(value)) {
              const params = propName + '[' + key + ']'
              const subPart = encodeURIComponent(params) + '='
              url += subPart + encodeURIComponent(value[key]) + '&'
            }
          } else {
            url += part + encodeURIComponent(value) + '&'
          }
        }
      }
      url = url.slice(0, -1)
      config.params = {}
      config.url = url
    }
    return config
  },
  (error) => {
    message.error(error)
    console.log(error)
    Promise.reject(error)
  }
)

// 响应拦截器，对所有的响应进行拦截处理，对所有的响应进行分类处理
service.interceptors.response.use(
  (response) => {
    // 用于处理在url中包含code的情况，例如使用验证码登录
    if (response.config.url != null && response.config.url.includes('code')) {
      // 若请求头url路径是否包含code，返回全部信息
      return response
    } else {
      // 其他情况返回data中信息，response.data是一个对象，包括code、msg、data等属性
      const res = response.data

      // 结果信息包括错误码和错误信息
      if (Object.prototype.hasOwnProperty.call(res, 'code')) {
        // 含有错误码，根据不同错误码进行不同处理
        if (parseInt(res.code) === 200) {
          // 处理成功
          return res
        } else {
          // 处理失败请求
          message.error(res.msg || '未知错误')
          return Promise.reject(res)
        }
      }
      // 不含有错误码，直接返回结果
      return res
    }
  },
  (error) => {
    // 处理网络错误
    let msg = ''
    const status = error?.response?.status
    switch (status) {
      case 401:
        msg = '没有权限，请先登录！'
        break
      case 403:
        msg = '无权访问'
        break
      case 404:
        msg = '404页面不存在'
        break
      case 500:
        msg = '服务器出现问题'
        break
      default:
        msg = '无网络'
    }
    message.error(msg)
    return Promise.reject(error)
  }
)

export default service
