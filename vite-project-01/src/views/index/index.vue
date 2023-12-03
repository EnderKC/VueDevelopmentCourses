<template>
    <el-container>
        <el-header>
            <headerLogin />
        </el-header>
        <el-main>
            <!-- 等待放入组件 -->
            <context :username="username" />
        </el-main>
    </el-container>
</template>

<script lang="ts" setup>
import headerLogin from '../components/header.vue';
import context from './components/context.vue';
import {useRoute } from 'vue-router';

const route = useRoute();
const { params } = route;

// Access the params property correctly
console.log(params);

const username = Array.isArray(route.params.username) ? route.params.username[0] : route.params.username;


</script>


<style scoped>
</style>