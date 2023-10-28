<template>
    <ul>
        <todoItem v-for="item in todoList" :todoitem="item" :key="item.itemName" @delete-task="deleteTask"></todoItem>
    </ul>
</template>

<script setup lang="ts">
import { defineProps, reactive, watch } from "vue"
import todoItem from "./todoItem.vue"
import Bus from "../bus/bus"
import { TodoItemInterface } from "./todoItem.vue"
let todoList: TodoItemInterface[] = reactive([
    {
        itemName: "吃饭",
        isDone: false
    },
    {
        itemName: "睡觉",
        isDone: false
    },
    {
        itemName: "打豆豆",
        isDone: false
    }
])
// 接收子组件传递过来的消息
const deleteTask = (todoitem: TodoItemInterface) => {
    // 数组中找到这个元素
    let index = todoList.findIndex((item) => {
        return item.itemName === todoitem.itemName
    })
    todoList.splice(index, 1)
    console.log(todoList)
}
// 接收bus传递过来的消息
Bus.on('add-task', (todoitem: TodoItemInterface) => {
    todoList.push(todoitem)
})
Bus.on('deleteAllDoneTask', () => {
    for (let i = 0; i < todoList.length; i++) {
        let index = todoList.findIndex((item) => {
            return item.isDone == true
        })
        todoList.splice(index, 1)
    }
})
watch(todoList, (newVal, oldVal) => {
    console.log(newVal)
    console.log(oldVal)
    Bus.emit('doneNum', todoList.filter((item) => {
        return item.isDone
    }).length)
}, { deep: true, immediate: true })
</script>

<style scoped></style>