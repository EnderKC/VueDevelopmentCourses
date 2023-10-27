import { createRouter, createWebHistory } from "vue-router";
// 导入你的组件
import userLogin from '../views/login/index.vue';
import userRegister from '../views/register/index.vue'

export const router = createRouter({
  history: createWebHistory(),
  routes: [
    // 配置路由的地方
    {
      path: '',
      component: userLogin // 将组件对象分配给 component
    },
    {
      path: '/register',
      component: userRegister // 将组件对象分配给 component
    },
  ],
});