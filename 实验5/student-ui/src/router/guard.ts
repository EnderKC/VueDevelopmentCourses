import type { Router } from 'vue-router'
import { useUserStore } from '@/store/modules/user'

/**
 * 全局前置守卫（beforeEach）
 * @param router 路由
 */
export function setupRouterGuard(router: Router) {
  const userStore = useUserStore()

  router.beforeEach(async (to, from, next) => {
    const isLogin = userStore.isLogin
    // 已登录，允许路由继续进行
    if (isLogin) {
      next()
    } else {
      // 未登录，判断是否是登录页或注册页，如果是登录页或注册页，允许路由继续进行
      if (to.path === '/login' || to.path === '/register') {
        next()
      } else {
        // 如果不是登录页，跳转到登录页
        next('/login')
      }
    }
  })
}
