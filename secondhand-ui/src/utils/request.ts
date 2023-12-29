import axios from 'axios'
import { router } from '@/router';

/**
 * 对发给后端的请求进行拦截处理，对返回的结果进行拦截处理
 */
axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
// 允许跨域携带cookie
axios.defaults.withCredentials = true

// 创建axios实例，axios是访问后端接口的异步调用
const service = axios.create({
  // axios中请求配置有baseURL选项，表示请求URL公共部分
  baseURL: 'http://localhost:8082',
  // 超时
  timeout: 10000
})

// request拦截器，对所有的请求进行拦截处理，作用就是对params进行处理http://www.baidu.com?a=1&b=2
service.interceptors.request.use(
  config => {
    // 如果本地存在token，则将token添加到请求头
    const token = localStorage.getItem('token');
    if (token) {
      config.headers['Authorization'] = token;
    }
    return config;
  },
  error => {
    // 请求错误处理
    console.log(error); // for debug
    Promise.reject(error);
  }
)

// 响应拦截器，对所有的响应进行拦截处理，对所有的响应进行分类处理
service.interceptors.response.use(
  response => {
    // 如果响应中包含token，则保存token
    const token = response.data?.data?.token;
    if (token) {
      localStorage.setItem('token', token);
    }
    return response;
  },
  error => {
    // 响应错误处理
    if (error.response && error.response.data.msg === 'Token失效请重新登录!') {
      // 如果返回的消息表示token失效，你可以在这里处理，例如重定向到登录页面
      console.log('Token失效,请重新登录');
      // 例如，如果你在Vue中使用这个axios实例，你可以使用router进行重定向
      router.push('/login');
    }
    console.log(error); // for debug
    return Promise.reject(error);
  }
)

export default service
