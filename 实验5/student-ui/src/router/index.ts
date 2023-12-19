// 1 从vue-router按需导入两个方法
// createRouter方法用于创建路由实例对象
// createWebHashHistory方法用于指定路由的工作模式（hash模式）
import { createRouter, createWebHashHistory } from 'vue-router'

// 创建路由规则：定义一个路由数组对象
const routes = [
  /**
   *一个对象就对应了一个路由
   *path 就是路由的地址
   *name 给路由起的名字
   *component 具体跳转的是哪个组件页面
   */
  {
    // path: '/' 根页面，表示已进入就显示的页面
    path: '/',
    // 路由重定向：redirect意味着重定向，当浏览器访问'/'根路径时,将会自动重定向到'/login'
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/login/index.vue')
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('@/views/register/index.vue')
  },
  {
    path: '/main',
    name: 'main',
    component: () => import('@/views/main/index.vue'),
    redirect: '/main/user',
    children: [
      {
        path: 'user',
        name: 'user',
        component: () => import('@/views/user/userManage.vue')
      },
      {
        path: 'stuInfo',
        name: 'stuInfo',
        component: () => import('@/views/main/stuInfoManage.vue')
      },
      {
        path: 'course',
        name: 'course',
        component: () => import('@/views/main/courseManage.vue')
      },
      {
        path: 'courseChoosing',
        name: 'courseChoosing',
        component: () => import('@/views/main/courseChoosingManage.vue')
      },
      {
        path: 'score',
        name: 'score',
        component: () => import('@/views/main/scoreManage.vue')
      }
    ]
  },
  {
    // path:'/:catchAll(.*)' 必须要放最后,表示上面的路由没有匹配到，则进入下面的页面
    path: '/:catchAll(.*)',
    name: 'NotFound',
    component: () => import('@/components/NotFound.vue') // 定义找不到已有组件时显示404
  }
]

// 创建路由器对象
const router = createRouter({
  history: createWebHashHistory(),
  routes: routes
})

// 导出路由器对象
// 把实例化路由对象 router 默认导出
export default router
