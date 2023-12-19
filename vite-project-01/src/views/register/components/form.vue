// 用途: 登录页
<template>
    <el-row class="registerForm" justify="center">
        <el-col align="middle">
            <el-form ref="registerForm" :model="ruleForm" status-icon :rules="rules" label-width="120px"
                :label-position="labelPosition">
                <el-col :xs="inputItim.xs" :sm="inputItim.sm" :md="inputItim.md" :lg="inputItim.lg" :xl="inputItim.xl">
                    <el-form-item label="QQ 号 : " prop="qqNum">
                        <el-input v-model="ruleForm.qqNum" type="text" size="large" autocomplete="off" clearable />
                    </el-form-item>
                </el-col>

                <el-col :xs="inputItim.xs" :sm="inputItim.sm" :md="inputItim.md" :lg="inputItim.lg" :xl="inputItim.xl">
                    <el-form-item label="学 号 : " prop="studentNum">
                        <el-input v-model="ruleForm.studentNum" type="text" size="large" autocomplete="off" clearable />
                    </el-form-item>
                </el-col>

                <el-col :xs="inputItim.xs" :sm="inputItim.sm" :md="inputItim.md" :lg="inputItim.lg" :xl="inputItim.xl">
                    <el-form-item label="邮箱 : " prop="email">
                        <el-input v-model="ruleForm.email" type="text" size="large" autocomplete="off" clearable />
                    </el-form-item>
                </el-col>

                <el-col :xs="inputItim.xs" :sm="inputItim.sm" :md="inputItim.md" :lg="inputItim.lg" :xl="inputItim.xl">
                    <el-form-item label="验证码 : " prop="code">
                        <el-input placeholder="请输入验证码" v-model="ruleForm.code" type="text" size="large" autocomplete="off"
                            clearable :style="verificationCodeClass?.input" />
                        <el-button @click="sendCode()" :style="verificationCodeClass?.button" size="large">获取</el-button>
                    </el-form-item>
                </el-col>

                <el-col :xs="inputItim.xs" :sm="inputItim.sm" :md="inputItim.md" :lg="inputItim.lg" :xl="inputItim.xl">
                    <el-form-item label="密码 : " prop="password">
                        <el-input v-model="ruleForm.password" type="password" size="large" autocomplete="off" show-password
                            clearable />
                    </el-form-item>
                </el-col>

                <el-col :xs="inputItim.xs" :sm="inputItim.sm" :md="inputItim.md" :lg="inputItim.lg" :xl="inputItim.xl">
                    <el-form-item label="再次输入密码 : " prop="password2">
                        <el-input v-model="ruleForm.password2" type="password" size="large" autocomplete="off" show-password
                            clearable />
                    </el-form-item>
                </el-col>

                <el-row justify="center" class="registerFormItem" align="middle">
                    <el-col :xs="7" :sm="5" :md="4" :lg="3" :xl="3" align="middle">
                        <el-button type="primary" size="large" tag="a" @click="toLogin()" rel="noopener noreferrer"
                            style="text-decoration-line: none;">返回登录</el-button>
                    </el-col>
                    <el-col :xs="7" :sm="5" :md="4" :lg="3" :xl="3" align="middle">
                        <el-button @click="submitForm(registerForm)" size="large">确认注册</el-button>
                    </el-col>
                </el-row>
            </el-form>
        </el-col>
    </el-row>
</template>

<script lang="ts" setup>
import { reactive, ref, computed } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import { ElNotification } from 'element-plus'
import { getVerifyCode,register } from "@/api/register/register"
import { useRouter } from 'vue-router';

const router = useRouter();

const registerForm = ref<FormInstance>()

const labelPosition = ref("left")

const inputItim = reactive({
    xs: 18,
    sm: 12,
    md: 9,
    lg: 7,
    xl: 7,
})
// 获取当前屏幕宽度
const width = ref(window.innerWidth)

// 根据屏幕宽度，调整验证码输入框的宽度
const verificationCodeClass = computed(() => {
    if (width.value >= 1500) {
        return {
            input: { width: "14vw" },
            button: "5vw"
        }
    } else if (width.value >= 1100) {
        return {
            input: { width: "10vw" },
            button: "5vw"
        }
    } else if (width.value > 992 && width.value < 1100) {
        return {
            input: { width: "16vw" },
            button: "5vw"
        }
    } else if (width.value > 768 && width.value <= 992) {
        return {
            input: { width: "19vw" },
            button: "5vw"
        }
    } else if (width.value > 576 && width.value <= 768) {
        return {
            input: { width: "17vw" },
            button: "5vw"
        }
    } else if (width.value > 0 && width.value <= 576) {
        return {
            input: { width: "27vw" },
            button: "5vw"
        }
    }
})

const ruleForm = reactive({
    qqNum: '',
    studentNum:'',
    email: '',
    password: '',
    password2: '',
    code: ''
})

const username = (rule: any, value: any, callback: any) => {
    console.log(rule)
    console.log(value)
    console.log(callback)
    if (value === '') {
        callback(new Error('账号为空,请认真填写~'))
    }
    callback()
}

const validatePass = (rule: any, value: any, callback: any) => {
    console.log(rule)
    if (value === '') {
        callback(new Error('密码为空,请认真填写~'))
    }
    callback()
}

const validatePass2 = (rule: any, value: any, callback: any) => {
    console.log(rule)
    // 验证密码是否一致
    if (value !== ruleForm.password) {
        callback(new Error('两次输入密码不一致!'))
    } else if (value === '') {
        callback(new Error('请再次输入密码!'))
    }
    callback()
}

const validateEmail = (rule: any, value: any, callback: any) => {
    console.log(rule)
    if (value === '') {
        callback(new Error('邮箱为空,请认真填写~'))
    }
    // 验证邮箱格式
    const reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/
    if (!reg.test(value)) {
        callback(new Error('邮箱格式不正确!'))
    }
    callback()
}

const validateCode = (rule: any, value: any, callback: any) => {
    console.log(rule)
    if (value === '') {
        callback(new Error('验证码为空,请认真填写~'))
    }
    callback()
}

const validateStudentNum = (rule: any, value: any, callback: any) => {
    console.log(rule)
    if (value === '') {
        callback(new Error('学号为空,请认真填写~'))
    }
    callback()
}

const rules = reactive<FormRules<typeof ruleForm>>({
    qqNum: [{ validator: username, trigger: 'blur' }],
    password: [{ validator: validatePass, trigger: 'blur' }],
    password2: [{ validator: validatePass2, trigger: 'blur' }],
    email: [{ validator: validateEmail, trigger: 'blur' }],
    code: [{ validator: validateCode, trigger: 'blur' }],
    studentNum: [{ validator: validateStudentNum, trigger: 'blur' }],
})

const sendCode = () => {
    // 验证邮箱输入框规则
    registerForm.value?.validateField('email', (valid) => {
        if (valid) {
            // 发送验证码
            const result = getVerifyCode(ruleForm.email)
            result.then((res) => {
                console.log(res)
                if(res.status == 200){
                    const data = res.data
                    if(data.code == -1){
                        ElNotification({
                            title: '警告',
                            message: data.msg,
                            type: 'warning',
                        })
                    }
                    else{
                        ElNotification({
                            title: '提示',
                            message: '验证码发送成功，请注意查收',
                            type: 'success',
                        })
                    }}
                else{
                        ElNotification({
                            title: '警告',
                            message: '验证码发送失败，请稍后再试',
                            type: 'warning',
                        })
                    }
            }).catch((err) => {
                console.log(err)
                ElNotification({
                    title: '警告',
                    message: '验证码发送失败，请稍后再试'+err,
                    type: 'warning',
                })
            })
        } else {
            ElNotification({
                title: '警告',
                message: '您输入的邮箱有问题，请检查后再试',
                type: 'warning',
            })
        }
    })
}

const submitForm = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.validate((valid) => {
        if (valid) {
            ElNotification({
                title: '提示',
                message: '正在注册，请稍后',
                type: 'info',
            })
            // 注册
            register(ruleForm.qqNum,ruleForm.studentNum,ruleForm.email,ruleForm.password,ruleForm.password2,ruleForm.code).then((res) => {
                console.log(res)
                if(res.status == 200){
                    const data = res.data
                    console.log(data)
                    if(data.code == -1){
                        ElNotification({
                            title: '警告',
                            message: data.msg,
                            type: 'warning',
                        })
                    }
                    else{
                        ElNotification({
                            title: '提示',
                            message: '注册成功',
                            type: 'success',
                        })
                        // 延迟2s跳转到登录页面
                        setTimeout(() => {
                            window.location.href = '/'
                        }, 2000)
                    }
                }
                else{
                    ElNotification({
                        title: '警告',
                        message: '注册失败，请稍后再试'+res.status,
                        type: 'warning',
                    })
                }
            }).catch((err) => {
                console.log(err)
                ElNotification({
                    title: '警告',
                    message: '注册失败，服务器出现问题'+err,
                    type: 'warning',
                })
            })
        } else {
            ElNotification({
                title: '警告',
                message: '请按照要求输入正确的内容',
                type: 'warning',
            })
            console.log('error submit!')
            return false
        }
    })
}

// 跳转到登录页
const toLogin = () => {
    router.push({
        name: 'login'
    })
}

</script>

<style scoped>
/* 
    loginForm
    表单在屏幕中央，
    垂直居中，
    随着屏幕的宽度而变化
 */
.registerForm {
    justify-content: center;
    align-items: center;
    height: 75vh;
}

.registerFormItem {
    height: 60px;
}
</style>