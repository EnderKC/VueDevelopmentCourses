<template>
    代办事件:
    <input type="text" v-model="itemName">
    <button @click="addTask">add</button>

</template>


<script setup lang="ts">
import {ref } from "vue"
import  {TodoItemInterface}  from "./todoItem.vue"
import Bus from "../bus/bus"
const itemName = ref('')
const addTask = ()=>{
    let todoitem:TodoItemInterface = {
        itemName: itemName.value,
        isDone: false
    }
    Bus.emit('add-task',todoitem)
    itemName.value = ''
}
</script>

<style scoped>
</style>