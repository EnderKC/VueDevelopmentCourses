<script setup lang="ts">
import firstDemo from "./components/01-OptionsAPI.vue"
import second from "./components/02-CompositionAPI.vue"
import thired from "./components/03-SetUpDemo.vue"
</script>

<template>
  <firstDemo></firstDemo>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <second></second>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <thired></thired>
  
</template>

<style scoped>
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}
.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}
.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
</style>
