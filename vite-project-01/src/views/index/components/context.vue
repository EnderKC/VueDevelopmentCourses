<template>
    <div class="content">
        <div class="avatar"/>
        <el-row>
            <el-col class="welcome">{{welcome}} , {{ props.username ? props.username : '同学'}}</el-col>
        </el-row>
        <el-row>
            <el-col :span="8">
                <el-row
                    style="margin-top: 60px;
                    text-align: center;">
                    <el-col :span="24"> <el-button :type="optionButton.type" class="optionButton" >课 表</el-button> </el-col>
                </el-row>
                <el-row
                    style="margin-top: 40px;
                    text-align: center;">
                    <el-col :span="24"> <el-button :type="optionButton.type" class="optionButton">查询成绩</el-button> </el-col>
                </el-row>
                <el-row
                    style="margin-top: 40px;
                    text-align: center;">
                    <el-col :span="24"> <el-button :type="optionButton.type" class="optionButton">晚签到</el-button> </el-col>
                </el-row>
                <el-row
                    style="margin-top: 40px;
                    text-align: center;">
                    <el-col :span="24"> <el-button :type="optionButton.type" class="optionButton">keep 代跑</el-button> </el-col>
                </el-row>
            </el-col>
            <el-col :span="16" style="margin-top: 60px">
                <todayCourse />
            </el-col>
        </el-row>
    </div>
</template>

<script lang="ts" setup>
import { defineProps } from 'vue'
import todayCourse from './todayCourse.vue';

// 接收参数
const props = defineProps({
    username: {
        type: String,
        required: true
    }
})
console.log('props.username:'+props.username)
// 判断系统时间
const now = new Date()
const welcome = now.getHours() < 12 ? '早上好' : now.getHours() < 18 ? '下午好' : '晚上好'

// 选项按钮
const optionButton = {
    type: 'info',
    style: 'width: 150px; height: 50px; font-size: 20px;'
}


</script>

<style scoped>
:root {
    --light-background: #f2f2f2;
    --dark-background: #282C34;
}

/* 头像元素 固定在左上角*/
.avatar {
    width: 15vw;
    height: 15vw;
    background-color: #fff;
    border-radius: 50%;
    position: absolute;
    top: -7vw;
    left: -5vw;
    /* 添加阴影 */
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
    background-image: url('../../../static/images/LOGO.jfif');
    background-size: cover;
    background-position: center;
}
.dark .avatar {
    filter: brightness(60%);
}

/* 当屏幕宽度小于600px时，改变头像元素的大小 */
@media (max-width: 600px) {
    .avatar {
        width: 20vw;
        height: 20vw;
        top: -8vw;
        left: -7vw;
    }
}

/* 当屏幕宽度大于900px时，改变头像元素的大小 */
@media (min-width: 900px) {
    .avatar {
        width: 10vw;
        height: 10vw;
        top: -4vw;
        left: -3vw;
    }
}

/* 内容元素 */
.content {
    width: 80vw;
    min-height: 70vh;
    border-radius: 20px; 
    position: relative;
    /* 水平居中 */
    margin: 10vh auto auto auto;
    /* 添加阴影 */
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
    background-color: var(--light-background);
}

/* 开启黑暗模式的内容底色 */
.dark .content {
    /* 添加阴影 */
    box-shadow: 0 0 10px rgba(253, 253, 253, 0.5);
    background-color: var(--dark-background);
}

.context {
    justify-content: center;
    align-items: center;
    height: 70vh;
    border: brown solid 1px;
}

/* 欢迎语 */
.welcome {
    /* 字体水平居中 */
    text-align: center;
    font-size: xx-large;
    color: #66b1ff;
    margin-top: 15px;
}

/* 选择按钮 */
.optionButton {
    width: 100px;
    height: 50px;
    font-size: 18px;
}

@media (max-width: 600px) {
    .optionButton {
        width: 90px;
        height: 40px;
        font-size: 15px;
    }
    
}

@media (min-width: 900px) {
    .optionButton {
        width: 150px;
        height: 50px;
        font-size: 20px;
    }
}

</style>