// 引入Axios
import service from '../api';

// 设置请求头为 'application/json'
service.defaults.headers.post['Content-Type'] = 'application/json';

// 设置请求的时间，超过这个时间请求中断
service.defaults.timeout = 10000;

service.defaults.withCredentials = true;

// 获取验证码
export const getVerifyCode = async(email:string) => {
    // 创建一个FormData对象来包含表单数据
    const data = {
        to: email,
    }
    return await service.post('/student/sendVerificationCode', data); // 直接传递JSON数据对象
};

// 注册
export const register = async(qqNum:string,StudentNum:string,email:string, password:string,password2:string, verifyCode:string) => {
    // 创建一个FormData对象来包含表单数据
    const data = {
        qqNum: qqNum,
        StudentNum: StudentNum,
        Email: email,
        password: password,
        password2: password2,
        verificationCode: verifyCode,
    }
    return await service.post('/student/register/front', data); // 直接传递JSON数据对象
};
