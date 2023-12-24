import { createRouter, createWebHashHistory } from "vue-router";
// 导入你的组件
import userLogin from '@/views/login/index.vue';
import userRegister from '@/views/register/index.vue'
import index from '@/views/index/index.vue'
import request from '@/utils/request'

// 引入Axios


export const router = createRouter({
    history: createWebHashHistory(),
    routes: [
        // 配置路由的地方
        {
            path: '',
            component: index // 将组件对象分配给 component
        },
        {
            path: '/login',
            name: 'login',
            component: userLogin // 将组件对象分配给 component
        },
        {
            path: '/register',
            name: 'register',
            component: userRegister // 将组件对象分配给 component
        },
        {
            path: '/index',
            name: 'index',
            component: index, // 将组件对象分配给 component
            props: true
        },
    ],
});

// 检查token是否有效
const checkToken = (token: string) => {
    const data = {
        token: token
    }
    return request({
        url: '/checkToken',
        method: 'post',
        data: data
    })
}

router.beforeEach((to, from, next) => {
    console.log("from:" + from.path)
    console.log("to:" + to.path)
    // 如果访问的是登录/注册界面则直接放行
    if (to.path === '/login' || to.path === '/register') return next();
    // 获取用户页面token信息
    let token = localStorage.getItem('token');
    // 如果token数据为null则跳转到指定路径
    if (!token) return next("/login");

    // 如果token数据不为null则进行token验证
    checkToken(token)
        .then(res => {
            if (res.data.code === 0) {
                console.log(res.data.msg);
                next();
            } else {
                next("/login");
            }
        })
        .catch(err => {
            console.log(err);
            next("/login"); // 处理请求失败的情况，可能是网络错误等
        });
});