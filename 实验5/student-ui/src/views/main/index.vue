<template>
  <a-layout style="width: 1200px">
    <a-layout-header :style="headerStyle">
      <a-row>
        <a-col :span="6"></a-col>
        <a-col :span="12">
          <a-typography-title :level="3">学生成绩管理系统</a-typography-title>
        </a-col>
        <a-col :span="6">
          <a-typography-text type="success">{{ username }}</a-typography-text>
          &nbsp;
          <a-button v-if="isLogin" type="text" @click="logout">登出</a-button>
        </a-col>
      </a-row>
    </a-layout-header>
    <a-layout>
      <a-layout-sider>
        <a-menu
          v-model:openKeys="state.openKeys"
          v-model:selectedKeys="state.selectedKeys"
          mode="inline"
          theme="dark"
          :inline-collapsed="state.collapsed"
          :items="items"
          @click="clickMenuItem"></a-menu>
      </a-layout-sider>
      <!--router-view作用是就是在当前位置根据路由显示其关联的组件-->
      <a-layout-content :style="contentStyle"><router-view></router-view></a-layout-content>
    </a-layout>
    <a-layout-footer :style="footerStyle">Footer</a-layout-footer>
  </a-layout>
</template>
<script lang="ts" setup>
/* stylelint-disable-next-line CssSyntaxError */
import type { CSSProperties } from 'vue'
import { reactive, watch } from 'vue'
import { storeToRefs } from 'pinia'
import { useUserStore } from '@/store/modules/user'
import router from '@/router'
import { filterMenusByRole } from '@/router/menu'

// 使用useUserStore()获取userStore，在userStore中定义了username和isLogin
const userStore = useUserStore()
// 将解构出来的参数变成响应式
const { username, isLogin } = storeToRefs(userStore)

/**
 * 用户登出
 */
function logout() {
  userStore.logout(true)
}

/**
 * 定义当前菜单
 */
const state = reactive({
  // 菜单是否收缩
  collapsed: false,
  // 默认选中user菜单
  selectedKeys: ['user']
})

// 过滤用户能使用的菜单项
const items = filterMenusByRole(userStore.userInfo?.role)

/**
 * 监视当前展开菜单状态的变化
 */
watch(
  () => state.openKeys,
  (_val, oldVal) => {
    state.preOpenKeys = oldVal
  }
)

/**
 * 处理菜单点击事件
 * @param param0
 */
function clickMenuItem({ key }) {
  switch (key) {
    case 'user':
      router.push('/main/user')
      break
    case 'student':
      router.push('/main/stuInfo')
      break
    case 'course':
      router.push('/main/course')
      break
    case 'course-choosing':
      router.push('/main/courseChoosing')
      break
    case 'score':
      router.push('/main/score')
      break
    default:
      break
  }
}

const headerStyle: CSSProperties = {
  textAlign: 'center',
  height: '100px',
  lineHeight: '100px',
  backgroundColor: '#ecf0f1'
}

const contentStyle: CSSProperties = {
  position: 'relative',
  textAlign: 'left',
  width: '100%',
  minHeight: '750px',
  color: '#000',
  padding: '10px',
  backgroundColor: '#fff'
}

// const siderStyle: CSSProperties = {
//   backgroundColor: '#ff6b81'
// }

const footerStyle: CSSProperties = {
  textAlign: 'center',
  color: '#000',
  backgroundColor: '##ecf0f1'
}
</script>
@/router/menu
