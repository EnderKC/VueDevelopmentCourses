<template>
    <a-page-header :style="headerStyle" title="Welcome!" sub-title="欢迎来到员工管理界面！" />
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