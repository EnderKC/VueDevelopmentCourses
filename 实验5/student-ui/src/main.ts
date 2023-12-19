import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import { setupRouterGuard } from './router/guard'

// 导入路由文件
// 导入router文件夹中的index.js中的router实例化对象
// 一个文件夹里面只有一个index.js文件在脚手架中可以把./router/index.js简写为./router
import router from './router'

// 引入pinia
import { createPinia } from 'pinia'
const pinia = createPinia()

// 创建应用实例
const app = createApp(App)

// 挂载pinia
app.use(pinia)
// 挂载路由模块
app.use(router)
// 设置全局路由守卫
setupRouterGuard(router)
app.mount('#app')
