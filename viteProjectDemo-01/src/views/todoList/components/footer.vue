<template>
    <div>
        已完成:{{doneNumThis }}个
        <button @click="deleteAllDoneTask">删除所有已完成的任务</button>
    </div>
</template>

<script setup lang="ts">
import { defineProps, reactive, ref } from "vue"
import Bus from "../bus/bus"
let doneNumThis = ref(0)

// 接收bus传递过来的消息
Bus.on('doneNum', (doneNum:number) => {
    doneNumThis.value = doneNum
})

// 删除所有已完成的任务
const deleteAllDoneTask = () => {
    console.log('删除所有已完成的任务')
    Bus.emit('deleteAllDoneTask')
}

</script>

<style scoped></style>