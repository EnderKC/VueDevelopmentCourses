// 引入Axios
import service from '../api';

// 设置请求头为 'application/json'
service.defaults.headers.post['Content-Type'] = 'application/json';

// 设置请求的时间，超过这个时间请求中断
service.defaults.timeout = 10000;

service.defaults.withCredentials = true;

// 用户登录
export const login = async (qqNum: string, password: string) => {
    // 创建一个FormData对象来包含表单数据
    const data = {
        qqNum: qqNum,
        password: password,
    }
    return await service.post('/login', data); // 直接传递JSON数据对象
};