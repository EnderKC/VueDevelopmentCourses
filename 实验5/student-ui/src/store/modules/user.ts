import { defineStore } from 'pinia'
import router from '@/router'
import { UserInfoModel, LoginParams } from '@/api/model/userModel'
import { ResponseModel } from '@/api/model/resModel'
import UserApi from '@/api/user'

/**
 * 定义用户状态的接口
 */
interface UserState {
  /**
   * 用户名
   */
  username: string | null

  /**
   * 用户信息
   */
  userInfo: UserInfoModel | null

  /**
   * 是否已登录
   */
  isLogin: boolean

  /**
   * 上次登录时间
   */
  lastUpdateTime: number
}

// 实例化UserApi类，生成一个userApi对象
const userApi = new UserApi()

/**
 * 定义用户模块的store
 */
export const useUserStore = defineStore({
  // 定义存储的唯一ID
  id: 'app-user',
  // 初始状态
  state: (): UserState => ({
    username: null,
    userInfo: null,
    isLogin: false,
    lastUpdateTime: 0
  }),
  /**
   * 定义获取状态的方法,返回的状态是state中保存的状态
   */
  getters: {
    getUsername(state): string | null {
      return state.username
    },
    getUserInfo(state): UserInfoModel | null {
      return state.userInfo
    },
    getIsLogin(state): boolean {
      return !!state.isLogin
    },
    getLastUpdateTime(state): number {
      return state.lastUpdateTime
    }
  },
  /**
   * 定义修改状态的方法
   */
  actions: {
    setUsername(username: string | null) {
      this.username = username
    },

    setUserInfo(userInfo: UserInfoModel | null) {
      this.userInfo = userInfo
    },

    setIsLogin(flag: boolean) {
      this.isLogin = flag
      if (flag) {
        this.lastUpdateTime = Date.now()
      }
    },
    /**
     * 获取用户信息
     */
    async getUserInfoAction(): Promise<UserInfoModel | null> {
      if (this.isLogin) {
        try {
          const userInfo: ResponseModel = await userApi.getUserInfo()

          this.setUserInfo(userInfo.data)
          return userInfo.data
        } catch (error) {
          return Promise.reject(error)
        }
      } else {
        return Promise.resolve(null)
      }
    },
    /**
     * 登录后的操作
     * @param goHome 是否跳转到首页
     */
    async afterLoginAction(goHome?: boolean): Promise<UserInfoModel | null> {
      // get user info
      try {
        const userInfo = await this.getUserInfoAction()
        if (userInfo) {
          try {
            // await router.replace('/main')相当于把当前路由替换为'/main'路由
            goHome && (await router.replace('/main')) // 加入 try..catch 以处理路由跳转失败的情况
          } catch (routeError) {
            console.error('Route navigation failed', routeError)
          }
        }
        return userInfo
      } catch (error) {
        return Promise.reject(error)
      }
    },
    /**
     * 用户登录
     */
    async login(
      // 把LoginParams 和 {goHome?: boolean}的所有属性进行合并
      params: LoginParams & { goHome?: boolean }
    ): Promise<UserInfoModel | null> {
      try {
        const { goHome = true, ...loginParams } = params
        // await要和async搭配使用，有await的地方必须有async，await的作用是等待userApi.login的返回结果
        // 如果这里不加await,即使没有返回结果，也会继续执行下面的代码
        const data: ResponseModel = await userApi.login(loginParams)
        this.setUsername(data.data)
        this.setIsLogin(true)
        return this.afterLoginAction(goHome)
      } catch (error) {
        return Promise.reject(error)
      }
    },
    /**
     * 用户登出
     */
    async logout(goLogin = false) {
      if (this.isLogin) {
        try {
          await userApi.logout()
        } catch {
          console.log('登出失败')
        }
      }
      this.setUsername(null)
      this.setIsLogin(false)
      this.setUserInfo(null)
      // 跳转到登录页面
      goLogin && router.push('/login')
    }
  }
})
