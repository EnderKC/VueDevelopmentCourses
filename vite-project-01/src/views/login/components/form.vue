// 用途: 登录页
<template>
    <el-row class="loginForm">
        <el-col>
            <el-form ref="loginForm" :model="ruleForm" status-icon :rules="rules" label-width="70px">
                <el-row justify="center" class="loginFormItem">
                    <el-col :xs="16" :sm="10" :md="8" :lg="6" :xl="6">
                        <el-form-item label="QQ 号 : " prop="qqNum">
                            <el-input v-model="ruleForm.qqNum" type="text" size="large" autocomplete="off" clearable />
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row justify="center" class="loginFormItem">
                    <el-col :xs="16" :sm="10" :md="8" :lg="6" :xl="6">
                        <el-form-item label="密码 : " prop="password">
                            <el-input v-model="ruleForm.password" type="password" size="large" autocomplete="off"
                                show-password clearable />
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row justify="center"  class="loginFormItem">
                    <el-col :xs="6" :sm="5" :md="4" :lg="2" :xl="2" align="middle">
                        <el-button type="primary" @click="submitForm(loginForm)" size="large">登录</el-button>
                    </el-col>
                    <el-col :xs="6" :sm="5" :md="4" :lg="2" :xl="2" align="middle">
                        <el-button @click="pushToRegister()" size="large" rel="noopener noreferrer"
                            style="text-decoration-line: none;">注册
                        </el-button>
                    </el-col>
                </el-row>
            </el-form>
        </el-col>
    </el-row>
</template>

<script lang="ts" setup>
import { reactive, ref } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import { login } from '@/api/login/login'
import { ElNotification } from 'element-plus'
import { useRouter } from 'vue-router';

const router = useRouter();

const loginForm = ref<FormInstance>()


const validateQqNum = (rule: any, value: any, callback: any) => {
    console.log(rule)
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

const ruleForm = reactive({
    qqNum: '',
    password: '',
})

const rules = reactive<FormRules<typeof ruleForm>>({
    qqNum: [{ validator: validateQqNum, trigger: 'blur' }],
    password: [{ validator: validatePass, trigger: 'blur' }],
})

const submitForm = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.validate((valid) => {
        if (valid) {
            login(ruleForm.qqNum, ruleForm.password).then(res => {
                console.log(res)
                if (res.data.code == 0) {
                    ElNotification({
                        title: '提示',
                        message: '登录成功',
                        type: 'success',
                    })
                    // TODO: 跳转至首页
                    setTimeout(() => {
                        // window.location.href = '/'
                        console.log(ruleForm.qqNum)
                        router.push({
                            name: 'index',
                            params: {
                                username: ruleForm.qqNum
                            }
                        })

                    }, 2000)
                } else {
                    ElNotification({
                        title: '警告',
                        message: '登录失败,请检查账号密码是否正确',
                        type: 'warning',
                    })
                }
            }).catch(err => {
                ElNotification({
                    title: '警告',
                    message: '登录失败,后端服务器错误,请联系管理员'+err,
                    type: 'warning',
                })
            })
        } else {
            ElNotification({
                title: '警告',
                message: '请正确填写账号密码',
                type: 'warning',
            })
            return false
        }
    })
}

const pushToRegister = () => {
    router.push({
        name: 'register'
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
.loginForm {
    justify-content: center;
    align-items: center;
    height: 70vh;
}

.loginFormItem {
    height: 60px;
}
</style>