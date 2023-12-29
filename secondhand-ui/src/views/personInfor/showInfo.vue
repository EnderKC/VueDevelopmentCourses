<template>
    <a-descriptions title="用-户-信-息" bordered>
        <a-descriptions-item label="用户ID" :span="3">{{ userInfo.userId }}</a-descriptions-item>
        <a-descriptions-item label="手机-电话">{{ userInfo.phoneNumber?userInfo.phoneNumber:"暂无"}}</a-descriptions-item>
        <a-descriptions-item label="WeChat-Number">{{ userInfo.wechatNumber?userInfo.wechatNumber:'暂无' }}</a-descriptions-item>
        <a-descriptions-item label="QQ Number" :span="2">{{ userInfo.qqNumber?userInfo.qqNumber:'暂无' }}</a-descriptions-item>
        <a-descriptions-item label="联系地址 ：">
            地址一 :    {{ userInfo.address?userInfo.address:'暂无' }}
            <br />
            <br />
            地址二:    {{ userInfo.address2?userInfo.address2:'暂无' }}
        </a-descriptions-item>
    </a-descriptions>
</template>
    
<script lang="ts" setup>
import { onMounted} from 'vue'
import UserAPI from '@/api/userApi';
import {ref} from 'vue'
import { UserInfo } from '@/api/model/userModel';

const userApi = new UserAPI();

const userInfo =ref<UserInfo>({
    userId:'',
    address:'',
    address2:'',
    phoneNumber:'',
    qqNumber:'',
    wechatNumber:''
})

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