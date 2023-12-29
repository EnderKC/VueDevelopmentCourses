<template>
    <a-layout>
        <a-layout-header :style="headerStyle">
            <loginHeader />
        </a-layout-header>
        <a-layout>
            <a-layout-content :style="contentStyle">
                <context />
            </a-layout-content>
        </a-layout>
        <a-layout-footer :style="footerStyle"><footerContent/></a-layout-footer>
    </a-layout>
</template>

<script lang="ts" setup>
import loginHeader from '@/components/header/loginHeader.vue'
import context from './components/context.vue';
import footerContent from '@/components/footer.vue'
import type { CSSProperties } from 'vue'

const headerStyle: CSSProperties = {
    textAlign: 'center',
    height: '100px',
    backgroundColor: '#ecf0f1'
}

const contentStyle: CSSProperties = {
    width: '100%',
    minHeight: '550px',
    color: '#000',
    padding: '10px',
    backgroundColor: '#fff'
}

const footerStyle: CSSProperties = {
    textAlign: 'center',
    color: '#000',
    backgroundColor: '##ecf0f1'
}

</script>