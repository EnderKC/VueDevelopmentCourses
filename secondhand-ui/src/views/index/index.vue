<template>
    <a-layout>
        <a-layout-header :style="headerStyle">
            <headerWelcome />
        </a-layout-header>
        <a-layout-content style="padding: 0 50px">
            <a-breadcrumb style="margin: 16px 0">
                <a-breadcrumb-item>Login</a-breadcrumb-item>
                <a-breadcrumb-item>Home</a-breadcrumb-item>
            </a-breadcrumb>
            <a-layout style="padding: 24px 0; background: #fff">
                <a-layout-sider width="200" style="background: #fff">
                    <a-menu v-model:selectedKeys="selectedKeys" 
                        v-model:openKeys="openKeys" mode="inline"
                        style="height: 100%"
                        @click="clickMenuItem">
                        <a-sub-menu key="sub1">
                            <template #title>
                                <span>
                                    <user-outlined />
                                    个人信息
                                </span>
                            </template>
                            <a-menu-item key="showInfo">查看个人信息</a-menu-item>
                            <a-menu-item key="modifyInfo">修改个人信息</a-menu-item>
                        </a-sub-menu>
                        <a-sub-menu key="sub2">
                            <template #title>
                                <span>
                                    <laptop-outlined />
                                    订单管理
                                </span>
                            </template>
                            <a-menu-item key="allOrder">所有订单</a-menu-item>
                            <a-menu-item key="notShippedOrder">未发货</a-menu-item>
                            <a-menu-item key="completedOrder">已完成</a-menu-item>
                        </a-sub-menu>
                        <a-sub-menu key="sub3">
                            <template #title>
                                <span>
                                    <notification-outlined />
                                    书籍市场
                                </span>
                            </template>
                            <a-menu-item key="myBooks">我发布的</a-menu-item>
                            <a-menu-item key="zty">周天宇专场</a-menu-item>
                            <a-menu-item key="informationEngineering">信息工程学院</a-menu-item>
                            <a-menu-item key="otherBooks">所有</a-menu-item>
                        </a-sub-menu>
                    </a-menu>
                </a-layout-sider>
                <a-layout-content :style="{ padding: '0 24px', minHeight: '500px' }">
                    <RouterView></RouterView>
                </a-layout-content>
            </a-layout>
        </a-layout-content>
        <a-layout-footer style="text-align: center">
            Ant Design ©2018 Created by Ant UED
        </a-layout-footer>
    </a-layout>
</template>
<script lang="ts" setup>
import { ref } from 'vue';
import { UserOutlined, LaptopOutlined, NotificationOutlined } from '@ant-design/icons-vue';
import headerWelcome from '@/components/header/header.vue'
import type { CSSProperties } from 'vue'
import {router} from '@/router'

const selectedKeys = ref<string[]>(['otherBooks']);
const openKeys = ref<string[]>(['sub3']);

const headerStyle: CSSProperties = {
    textAlign: 'center',
    height: '100px',
    backgroundColor: '#ecf0f1'
}

/**
 * 处理菜单点击事件
 * @param param0
 */
function clickMenuItem({ key }:{key:string}) {
    console.log(key)
    switch (key) {
        case 'showInfo':
            router.push('/index/showInfo')
            break
        case 'modifyInfo':
            router.push('/index/modifyInfoVue')
            break
        case 'informationEngineering':
            router.push('/index/informationEngineering')
            break
        case 'zty':
            router.push('/index/ztyBook')
            break
        case 'otherBooks':
            router.push('/index/otherBooks')
            break
        default:
            break
    }
}

</script>
<style scoped>
#components-layout-demo-top-side .logo {
    float: left;
    width: 120px;
    height: 31px;
    margin: 16px 24px 16px 0;
    background: rgba(255, 255, 255, 0.3);
}

.ant-row-rtl #components-layout-demo-top-side .logo {
    float: right;
    margin: 16px 0 16px 24px;
}

.site-layout-background {
    background: #fff;
}
</style>