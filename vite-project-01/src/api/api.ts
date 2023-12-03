import { router } from '@/router';
import axios from 'axios';

// 创建axios实例
const service = axios.create({
    baseURL: 'https://qwwq.xjqxz.top/api', // api的base_url
    timeout: 10000 // 请求超时时间
});

// 请求拦截器
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
);

// 响应拦截器
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
);

export default service;