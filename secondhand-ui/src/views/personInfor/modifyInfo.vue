<template>
    <context-holder />
    <a-row >
        <a-col :span="8" :offset="6">
            <a-form :model="userInfo" name="basic" :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }" autocomplete="off"
                @finish="onFinish">
                <a-form-item class="from-item" label="userId" name="userId"
                    :rules="[{ required: true, message: 'Please input your username!' }]">
                    <a-input disabled v-model:value="userInfo.userId" />
                </a-form-item>

                <a-form-item class="from-item" label="地址-1" name="address">
                    <a-input v-model:value="userInfo.address" />
                </a-form-item>

                <a-form-item class="from-item" label="地址-2" name="address2">
                    <a-input v-model:value="userInfo.address2" />
                </a-form-item>

                <a-form-item class="from-item" label="手机号" name="phoneNumber">
                    <a-input v-model:value="userInfo.phoneNumber" />
                </a-form-item>

                <a-form-item class="from-item" label="qq号" name="qqNumber">
                    <a-input v-model:value="userInfo.qqNumber" />
                </a-form-item>

                <a-form-item class="from-item" label="微信号" name="wechatNumber">
                    <a-input v-model:value="userInfo.wechatNumber" />
                </a-form-item>

                <a-form-item :wrapper-col="{ offset: 16, span: 16 }">
                    <a-button type="primary" html-type="submit">Submit</a-button>
                </a-form-item>
            </a-form>
        </a-col>
    </a-row>
</template>
    
<script lang="ts" setup>
import { onMounted } from 'vue'
import UserAPI from '@/api/userApi';
import { ref } from 'vue'
import { UserInfo } from '@/api/model/userModel';
import { message } from 'ant-design-vue';
const [messageApi, contextHolder] = message.useMessage();

const userApi = new UserAPI();

const userInfo = ref<UserInfo>({
    userId: '',
    address: '',
    address2: '',
    phoneNumber: '',
    qqNumber: '',
    wechatNumber: ''
})

const onFinish = (values: any) => {
    console.log('Success:', values);
    userApi.updateUserInfo(values).then(res => {
        console.log(res)
        if(res.data.code == 0){
            messageApi.success('修改成功')
        }
    })
};

// 组件挂载后，获取用户数据
onMounted(() => {
    console.log('组件挂载后，获取表格数据')
    userApi.getUserInfo().then(res => {
        console.log(res)
        userInfo.value = res.data.data.userInfo
    })
    console.log(userInfo.value)
})

</script>

<style scoped>

.from-item {
    min-width: 500px;
}

</style>