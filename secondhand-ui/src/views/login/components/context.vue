<template>
    <a-row align="middle" class="login-row" justify="center">
        <a-col>
            <a-form class="login-form" :model="formState" name="loginForm" :label-col="{ span: 5 }"
                :wrapper-col="{ span: 18 }" autocomplete="off" @finish="onFinish" @finishFailed="onFinishFailed">
                <a-form-item label="用户名" name="userName"
                    :rules="[{ required: true, message: 'Please input your username!' }]">
                    <a-input v-model:value="formState.userName" />
                </a-form-item>

                <a-form-item label="密码" name="userPassword"
                    :rules="[{ required: true, message: 'Please input your password!' }]">
                    <a-input-password v-model:value="formState.userPassword" />
                </a-form-item>

                <a-form-item :wrapper-col="{ offset: 5, span: 16 }">
                    <a-row justify="space-around">
                        <a-col :span="8"><a-button type="primary" html-type="submit">登录</a-button></a-col>
                        <a-col :span="8"><a-button type="primary" @click="onRegister">注册</a-button></a-col>
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
    userPassword: ''
})

// 表单验证成功
const onFinish = () => {
    notification['info']({
        message: '登录中',
        description:
            '正在为您登录，请稍后....',
    });
    userApi.login(formState).then(res => {
        console.log(res);
        if (res.data.code === 0) {
            notification['success']({
                message: '登录成功！',
                description:
                    '您已经成功登录，正在为您跳转到首页....',
            });
            // 延时
            setTimeout(() => {
                router.push('/index');
            }, 2000);
        }else{
            notification['error']({
                message: '登录失败！',
                description:
                    '请检查您的用户名和密码是否正确！',
            });
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

// 点击注册按钮
const onRegister = () => {
    router.push('/register');
}

</script>

<style scoped>
.login-row {
    height: 70vh;
}

.login-form {
    min-width: 300px;
}
</style>
