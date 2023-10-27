// main.ts
import { createApp } from 'vue'
import App from './App.vue'

// elemnt plus 需要导入的库
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import 'element-plus/theme-chalk/dark/css-vars.css'
import 'element-plus/theme-chalk/display.css'

// vur-router 需要导入的库
import { router } from './router/index.ts'

const app = createApp(App)
app.use(router)
app.use(ElementPlus)
app.mount('#app')
