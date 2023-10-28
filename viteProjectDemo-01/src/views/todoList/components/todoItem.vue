<template>
    <!-- 单选框 事件名称 删除按钮 -->
    <li>
        <input type="checkbox" v-model="todoitem.isDone" :style="taskDone">
        <span :style="taskDone">{{ props.todoitem.itemName }}</span>
        <input @click="deleteTask" type="button" value="删除">
    </li>
</template>

<script setup lang="ts">
import { defineProps,computed,reactive,defineEmits } from "vue"
// 从父组件接收数据
interface  TodoItem {
    itemName: string,
    isDone: boolean
}
const props = defineProps({
    todoitem: {
        type: Object as () => TodoItem,
        required: true
    }
})
// 自动变更字体样式
const todoitem = reactive(props.todoitem)
const taskDone = computed(()=>{
    if(todoitem.isDone){
        return {"text-decoration": "line-through"}
    }else{
        return {"text-decoration": "none"}
    }
})
// 向父组件传递消息
const emit = defineEmits(["deleteTask"])
const deleteTask = ()=>{
    emit("deleteTask",todoitem)
}
export interface TodoItemInterface {
    itemName: string,
    isDone: boolean
}
</script>

<style scoped>
</style>