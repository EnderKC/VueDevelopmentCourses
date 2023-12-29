<template>
    <a-row align="middle" class="login-row" justify="center">
        <a-col>
            <a-form class="login-form" :model="formState" name="loginForm" :label-col="{ span: 8 }"
                :wrapper-col="{ span: 18 }" autocomplete="off" @finish="onFinish" @finishFailed="onFinishFailed">
                <a-form-item label="用户名" name="userName" :rules="[{ required: true, message: '请输入您的用户名' }]">
                    <a-input v-model:value="formState.userName" />
                </a-form-item>

                <a-form-item label="邮箱" name="userEmail" :rules="[{ required: true, message: '请输入邮箱' }]">
                    <a-row justify="start">
                        <a-col :span="16"><a-input v-model:value="formState.userEmail" /></a-col>
                        <a-col :span="2"><a-button type="dashed" @click="sendCode">验证码</a-button></a-col>
                    </a-row>
                </a-form-item>

                <a-form-item label="验证码" name="verificationCode" :rules="[{ required: true, message: '请输入验证码' }]">
                    <a-input v-model:value="formState.verificationCode" />
                </a-form-item>

                <a-form-item label="密码" name="userPassword" :rules="[{ required: true, message: '请输入密码' }]">
                    <a-input-password v-model:value="formState.userPassword" />
                </a-form-item>


                <a-form-item label="再次输入密码" name="userPassword2" :rules="[{ validator: validatePass2, trigger: 'change' }]">
                    <a-input-password v-model:value="formState.userPassword2" />
                </a-form-item>

                <a-form-item :wrapper-col="{ offset: 5 }">
                    <a-row justify="space-around">
                        <a-col :span="12"><a-button @click="onLogin">返回登录</a-button></a-col>
                        <a-col :span="12"><a-button type="primary" html-type="submit">确认注册</a-button></a-col>
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
    userName: '',
    userEmail: '',
    userPassword: '',
    userPassword2: '',
    verificationCode: ''
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
            if (res.data.msg === '用户名已存在') {
                notification['error']({
                    message: '注册失败！',
                    description:
                        '用户名已存在',
                });
            } else {
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

// 发送验证码
const sendCode = () => {
    notification['info']({
        message: '验证码发送中',
        description:
            '请稍后....',
    });
    userApi.sendVerificationCode({ 'userEmail': formState.userEmail }).then(res => {
        console.log(res);
        if (res.data.code === 0) {
            notification['success']({
                message: '发送成功！',
                description:
                    '验证码已发送，请注意查收',
            });
        } else {
            notification['error']({
                message: '发送失败！',
                description:
                    res.data.msg,
            });
        }
    }).catch(err => {
        console.log(err);
        notification['error']({
            message: '发送失败！',
            description:
                '服务器内部错误问题，请联系管理员',
        });
    })
}

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
    if (value === formState.userPassword) {
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
