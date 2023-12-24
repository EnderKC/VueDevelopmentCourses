<template>
    <a-row align="middle" class="login-row" justify="center">
        <a-col>
            <a-form class="login-form" :model="formState" name="loginForm" :label-col="{ span: 8 }"
                :wrapper-col="{ span: 18 }" autocomplete="off" @finish="onFinish" @finishFailed="onFinishFailed">
                <a-form-item label="用户名" name="username"
                    :rules="[{ required: true, message: '请输入您的用户名' }]">
                    <a-input v-model:value="formState.username" />
                </a-form-item>

                <a-form-item label="真实姓名" name="realname"
                    :rules="[{ required: true, message: '请输入您的真实姓名' }]">
                    <a-input v-model:value="formState.realname" />
                </a-form-item>

                <a-form-item label="密码" name="password"
                    :rules="[{ required: true, message: '请输入密码' }]">
                    <a-input-password v-model:value="formState.password" />
                </a-form-item>

                <a-form-item label="性别" name="gender">
                    <a-radio-group v-model:value="formState.sex">
                        <a-radio value="保密">保密</a-radio>
                        <a-radio value="男">男</a-radio>
                        <a-radio value="女">女</a-radio>
                    </a-radio-group>
                </a-form-item>

                <a-form-item label="再次输入密码" name="password2"
                    :rules="[{ validator: validatePass2, trigger: 'change' }]">
                    <a-input-password v-model:value="formState.password2" />
                </a-form-item>

                <a-form-item :wrapper-col="{ offset: 1, span: 24 }">
                    <a-row justify="space-around">
                        <a-col :span="8"><a-button type="primary" @click="onLogin">返回登录</a-button></a-col>
                        <a-col :span="8"><a-button type="primary" html-type="submit">确认注册</a-button></a-col>
                    </a-row>
                </a-form-item>
            </a-form>
        </a-col>
    </a-row>
</template>

<script lang="ts" setup>
import { reactive } from 'vue'
import { notification } from 'ant-design-vue';
import { useRouter } from 'vue-router';
import UserApi from '@/api/userApi';


const router = useRouter();
const userApi = new UserApi()

const formState = reactive({
    username: '',
    realname:'',
    sex: '保密',
    password: '',
    password2:''
})

// 表单验证成功
const onFinish = () => {
    notification['info']({
        message: '注册中',
        description:
            '正在为您注册，请稍后....',
    });
    userApi.register(formState).then(res => {
        console.log(res);
        if (res.data.code === 0) {
            notification['success']({
                message: '注册成功！',
                description:
                    '您已经成功注册，正在为您跳转到登录页面....',
            });
            // 延时
            setTimeout(() => {
                router.push('/login');
            }, 2000);
        } else {
            if(res.data.msg === '用户名已存在'){
                notification['error']({
                    message: '注册失败！',
                    description:
                        '用户名已存在',
                });
            }else{
                notification['error']({
                    message: '注册失败！',
                    description:
                        '请联系管理员',
                });
            }
        }
    }).catch(err => {
        console.log(err);
        notification['error']({
            message: '登录失败！',
            description:
                '服务器内部错误问题，请联系管理员',
        });
    })
};

// 表单验证失败
const onFinishFailed = (errorInfo: any) => {
    console.log('Failed:', errorInfo);
    notification['error']({
        message: '格式错误',
        description:
            '请您正确输入账号密码！',
    });
};

// 点击返回登录按钮
const onLogin = () => {
    router.push('/login');
}

// 密码验证
const validatePass2 = (rule: any, value: any, callback: any) => {
    if (!value) {
        return callback(new Error('请再次输入密码'));
    }
    if (value === formState.password) {
        callback();
    } else {
        callback(new Error('两次密码输入不一致'));
    }
    console.log(rule)
};

</script>

<style scoped>
.login-row {
    height: 70vh;
}

.login-form {
    min-width: 300px;
}
</style>
