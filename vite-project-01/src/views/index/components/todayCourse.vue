<template>
    <el-row :gutter="12">
        <el-col :span="23">
            <el-card shadow="always"> 
                <el-table :data="schedule" style="width: 100%">
                    <el-table-column prop="courseStartTime" label="上课时间" width="100" />
                    <el-table-column prop="courseName" label="课程名称" width="180" />
                    <el-table-column prop="courseRoom" label="上课地点" width="180" />
                    <el-table-column prop="courseTeacher" label="教师" />
                </el-table>
            </el-card>
        </el-col>
    </el-row>
</template>

<script lang="ts" setup>
import {getDayLesson,CourseType } from '@/api/getData/getData'
import { ref } from 'vue';

const schedule = ref<CourseType[]>([])

getDayLesson().then((res:CourseType[]) => {
    schedule.value = res
    console.log(res)
})


</script>

<style scoped></style>