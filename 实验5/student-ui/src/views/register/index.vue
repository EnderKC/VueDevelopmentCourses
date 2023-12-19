<template>
  <a-form
    ref="formRef"
    class="register-form"
    :model="formState"
    :rules="rules"
    :label-col="labelCol"
    :wrapper-col="wrapperCol">
    <a-typography-title :level="3" class="title">用户注册</a-typography-title>
    <context-holder />
    <a-form-item has-feedback ref="username" label="用户名" name="username" class="error-align">
      <a-input v-model:value="formState.username" />
    </a-form-item>
    <a-form-item has-feedback label="密码" name="password" class="error-align">
      <a-input-password v-model:value="formState.password"></a-input-password>
    </a-form-item>
    <a-form-item has-feedback label="二次密码" name="checkPass" class="error-align">
      <a-input-password v-model:value="formState.checkPass"></a-input-password>
    </a-form-item>
    <a-form-item has-feedback ref="stuid" label="学号" name="stuid" class="error-align">
      <a-input v-model:value="formState.stuid" />
    </a-form-item>
    <a-form-item has-feedback ref="stuname" label="姓名" name="stuname" class="error-align">
      <a-input v-model:value="formState.stuname" />
    </a-form-item>
    <a-form-item label="性别" name="gender" class="error-align">
      <a-radio-group v-model:value="formState.gender">
        <a-radio value="1">保密</a-radio>
        <a-radio value="2">男</a-radio>
        <a-radio value="3">女</a-radio>
      </a-radio-group>
    </a-form-item>
    <a-form-item label="出生日期" required name="birthday" class="error-align">
      <a-date-picker
        v-model:value="formState.birthday"
        type="date"
        placeholder="Pick a date"
        style="width: 100%" />
    </a-form-item>
    <a-form-item has-feedback ref="classname" label="班级" name="classname" class="error-align">
      <a-input v-model:value="formState.classname" />
    </a-form-item>
    <a-form-item has-feedback ref="address" label="籍贯" name="address" class="error-align">
      <a-input v-model:value="formState.address" />
    </a-form-item>
    <a-form-item :wrapper-col="{ span: 14, offset: 4 }">
      <a-button type="primary" @click="onSubmit">注册</a-button>
      <a-button style="margin-left: 10px" @click="resetForm">重置</a-button>
      <RouterLink to="/login">已有账号？去登录</RouterLink>
    </a-form-item>
  </a-form>
</template>

<script lang="ts" setup>
import { Dayjs } from 'dayjs'
import type { Rule } from 'ant-design-vue/es/form'
import UserApi from '@/api/user'
import StuInfoApi from '@/api/stuInfo'
import { message } from 'ant-design-vue'
const [messageApi, contextHolder] = message.useMessage()

// 定义表单数据项接口
interface FormState {
  username: string
  password: string
  checkPass: string
  stuid: string
  stuname: string
  gender: string
  birthday: Dayjs | undefined
  classname: string
  address: string
}

// 定义对表单的引用变量
const formRef = ref()
// label列宽度
const labelCol = { span: 5 }
// 文本框列宽度
const wrapperCol = { span: 13 }

// 定义表单数据
const formState: FormState = reactive({
  username: '',
  password: '',
  checkPass: '',
  stuid: '',
  stuname: '',
  gender: '1',
  birthday: undefined,
  classname: '',
  address: ''
})

const userApi = new UserApi()
const stuInfoApi = new StuInfoApi()
// 用户名校验
const validateUsername = async (_rule: Rule, value: string) => {
  if (value === '') {
    return Promise.reject('请输入用户名')
  } else if (value.length < 3 || value.length > 30) {
    return Promise.reject('长度在 3 to 30!')
  } else {
    const ret = await userApi
      .checkUserName(value)
      .then((res) => {
        return res
      })
      .catch((error) => {
        console.log(error)
        return false
      })
    if (ret.data === true) {
      return Promise.resolve()
    } else {
      return Promise.reject('用户名已存在')
    }
  }
}
// 学号校验
const validateStuId = async (_rule: Rule, value: string) => {
  if (value === '') {
    return Promise.reject('请输入学号')
  } else if (value.length < 3 || value.length > 30) {
    return Promise.reject('长度在 3 to 30!')
  } else {
    const ret = await stuInfoApi
      .checkStuId(value)
      .then((res) => {
        return res
      })
      .catch((error) => {
        console.log(error)
        return false
      })
    if (ret.data === true) {
      return Promise.resolve()
    } else {
      return Promise.reject('学号已存在')
    }
  }
}

// 密码校验
const validatePass = async (_rule: Rule, value: string) => {
  if (value === '') {
    return Promise.reject('请再次输入密码')
  } else if (value !== formState.password) {
    return Promise.reject('两次输入的密码不匹配!')
  } else {
    return Promise.resolve()
  }
}

// 定义校验规则
const rules: Record<string, Rule[]> = {
  username: [{ validator: validateUsername, trigger: 'blur' }],
  password: [
    { required: true, message: '请输入密码', trigger: 'change' },
    { min: 3, max: 30, message: '长度在 3 to 30', trigger: 'blur' }
  ],
  checkPass: [{ validator: validatePass, trigger: 'change' }],
  stuid: [{ validator: validateStuId, trigger: 'blur' }],
  stuname: [
    { required: true, message: '请输入学生姓名', trigger: 'change' },
    { min: 3, max: 30, message: '长度在 3 to 30', trigger: 'blur' }
  ],
  gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
  birthday: [{ required: true, message: '请选择出生日期', trigger: 'change', type: 'object' }]
}

// 点击提交时触发
const onSubmit = () => {
  formRef.value
    .validate()
    .then(() => {
      const data = toRaw(formState)
      Reflect.deleteProperty(data, 'checkPass')
      userApi
        .register(data)
        .then(() => {
          messageApi.success('用户注册成功')
        })
        .catch((error: object) => {
          messageApi.error(error.msg)
        })
    })
    .catch((error: object) => {
      console.log('error', error)
    })
}

// 点击重置按钮时触发
const resetForm = () => {
  formRef.value.resetFields()
}
</script>

<style lang="scss" scoped>
/* 设置表单的最小宽度 */
.register-form {
  min-width: 500px;
}

/* 设置标题上下间距 */
.title {
  margin: 30px 0px;
}

/* 检验信息左对齐 */
.error-align {
  text-align: left;
}
</style>
