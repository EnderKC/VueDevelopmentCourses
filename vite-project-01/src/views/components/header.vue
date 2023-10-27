<template>
    <el-row class="hidden-xs-only">
        <el-col>
            <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" :ellipsis="false"
                @select="handleSelect">
                <el-menu-item index="0">
                    <h1 class="titleText">{{ titleText }}</h1>
                </el-menu-item>
                <div class="flex-grow" />
                <el-menu-item index="1">{{ aboutUs }}</el-menu-item>
                <el-sub-menu index="2">
                    <template #title>{{ moreApplication }}</template>
                    <el-menu-item index="2-1">
                        <el-link href="https://element-plus.org" target="_blank">qq 机器人</el-link>
                    </el-menu-item>
                    <el-menu-item index="2-2">
                        <el-link href="https://element-plus.org" target="_blank">博客</el-link>
                    </el-menu-item>
                    <el-menu-item index="2-3">
                        <el-link href="https://element-plus.org" target="_blank">开发文档</el-link>
                    </el-menu-item>
                </el-sub-menu>
                <el-menu-item index="3">
                    <el-switch v-model="isDark" :active-icon="Moon" :inactive-icon="Sunny" @change="toggleDark" />
                </el-menu-item>
            </el-menu>
        </el-col>
    </el-row>
    <!-- 上面是大屏幕显示的内容 === 下面是小屏幕显示额内容 -->
    <el-row class="hidden-sm-and-up">
        <el-col>
            <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" :ellipsis="false"
                @select="handleSelect">
                <el-menu-item index="0" @click="drawer = true">
                    <el-icon>
                        <Menu />
                    </el-icon>
                    <el-text type="primary">Menu</el-text>
                </el-menu-item>
                <div class="flex-grow" />
                <el-menu-item index="3">
                    <el-switch v-model="isDark" :active-icon="Moon" :inactive-icon="Sunny" @change="toggleDark" />
                </el-menu-item>
            </el-menu>
        </el-col>
    </el-row>
    <el-drawer v-model="drawer" title="I am the title" :with-header="false" :direction="LTR">
        <h1 class="titleText">Menu</h1>
        <el-collapse>
            <el-collapse-item title="更多应用" name="1">
                <el-row>
                    <el-col align="middle">
                        <el-link href="https://element-plus.org" target="_blank">qq 机器人</el-link>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col align="middle">
                        <el-link href="https://element-plus.org" target="_blank">博客</el-link>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col align="middle">
                        <el-link href="https://element-plus.org" target="_blank">开发文档</el-link>
                    </el-col>
                </el-row>
            </el-collapse-item>
        </el-collapse>
        <br>
        <el-row>
            <el-col align="middle">
                <el-link href="https://element-plus.org" target="_blank">关于我们</el-link>
            </el-col>
        </el-row>
    </el-drawer>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
// 黑暗模式开关
import { useDark, useToggle } from '@vueuse/core'
// element ui 图标
import { Sunny, Moon, Menu } from '@element-plus/icons-vue'
import { LTR } from 'element-plus/es/components/virtual-list/src/defaults.mjs';

const activeIndex = ref('1')
const handleSelect = (key: string, keyPath: string[]) => {
    console.log(key, keyPath)
}
const titleText = ref('网络情缘一线牵-HGU')
const aboutUs = ref('关于我们')
const moreApplication = ref('更多应用')
// 黑暗模式
const isDark = ref(useDark())
const toggleDark = useToggle(useDark()); // 切换黑暗模式状态

const drawer = ref(false)

</script>

<style>
.flex-grow {
    flex-grow: 1;
}

.titleText {
    font-size: 25px;
    font-weight: bold;
    color: #66b1ff;
}
</style>
