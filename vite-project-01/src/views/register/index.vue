<script setup lang="ts">
import formLogin from './components/form.vue'
import headerLogin from '../components/header.vue';

</script>

<template>
    <el-container>
        <el-header>
            <headerLogin />
        </el-header>
        <el-main>
            <form-login />
        </el-main>
    </el-container>
</template>

<style scoped>

</style>