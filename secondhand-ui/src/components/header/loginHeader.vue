<template>
    <a-page-header :style="headerStyle" title="Login" sub-title="welcom to login !" />
</template>

<script lang="ts" setup>
import type { CSSProperties } from 'vue'

const headerStyle: CSSProperties = {
    textAlign: 'center',
    padding: 0,
    marginTop:'30px'
}
</script>

<style scoped></style>