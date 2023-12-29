import { createApp } from 'vue';
import Antd from 'ant-design-vue';
import App from './App.vue';
import 'ant-design-vue/dist/reset.css';
// vur-router 需要导入的库
import { router } from './router/index.ts'

const app = createApp(App);
app.use(router)
app.use(Antd).mount('#app');
