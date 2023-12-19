<template>
  <a-form
    :model="formState"
    name="normal_login"
    class="login-form"
    @finish="onFinish"
    @finishFailed="onFinishFailed">
    <a-form-item>
      <a-typography-title :level="3">学生成绩管理系统</a-typography-title>
    </a-form-item>
    <!--消息提示的占位符-->
    <context-holder />
    <a-form-item
      label="用户"
      name="username"
      :rules="[
        { required: true, message: '请输入用户名!' },
        { min: 3, max: 30, message: '长度在 3 to 30', trigger: 'blur' }
      ]"
      class="error-align">
      <a-input v-model:value="formState.username">
        <template #prefix>
          <UserOutlined class="site-form-item-icon" />
        </template>
      </a-input>
    </a-form-item>

    <a-form-item
      label="密码"
      name="password"
      :rules="[
        { required: true, message: '请输入密码!' },
        { min: 3, max: 30, message: '长度在 3 to 30', trigger: 'blur' }
      ]"
      class="error-align">
      <a-input-password v-model:value="formState.password">
        <template #prefix>
          <LockOutlined class="site-form-item-icon" />
        </template>
      </a-input-password>
    </a-form-item>

    <a-form-item>
      <a-form-item name="remember" no-style>
        <a-checkbox v-model:checked="formState.remember">记住我</a-checkbox>
      </a-form-item>
      <a class="login-form-forgot" href="">忘记密码</a>
    </a-form-item>

    <a-form-item>
      <a-button :disabled="disabled" type="primary" html-type="submit" class="login-form-button">
        登入
      </a-button>
      Or
      <router-link to="/register">注册!</router-link>
    </a-form-item>
  </a-form>
</template>
<script lang="ts" setup>
// @代表src目录
import router from '@/router'
import { UserOutlined, LockOutlined } from '@ant-design/icons-vue'
import { useUserStore } from '@/store/modules/user'
import { LoginParams } from '@/api/model/userModel'
import { message } from 'ant-design-vue'
const [messageApi, contextHolder] = message.useMessage()

interface FormState {
  username: string
  password: string
  remember: boolean
}
const formState = reactive<FormState>({
  username: 'admin',
  password: '',
  remember: true
})

// 获取userStore，以便访问userStore中的方法
const userStore = useUserStore()
/**
 * 提交表单处理
 * @param values 要提交的表单数据
 */
const onFinish = (values: object) => {
  // 异步调用userStore中的login方法
  userStore
    .login(values as LoginParams)
    .then((res) => {
      const info = `${res!.data}登录成功`
      messageApi.success(info)
      router.push('/main')
    })
    .catch((err) => {
      messageApi.error(err.response.data || '登录失败')
    })
}

const onFinishFailed = (errorInfo: object) => {
  console.log('Failed:', errorInfo)
}
const disabled = computed(() => {
  return !(formState.username && formState.password)
})
</script>

<style lang="scss" scoped>
/* 设置表单的最小宽度 */
.login-form {
  min-width: 300px;
}
/* 忘记密码右对齐 */
.login-form-forgot {
  float: right;
}

/* 登录表单按钮占满整列 */
.login-form-button {
  width: 100%;
}

/* 检验信息左对齐 */
.error-align {
  text-align: left;
}
</style>
