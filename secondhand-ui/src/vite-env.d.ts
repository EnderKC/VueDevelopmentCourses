// / <reference types="vite/client" />


interface ImportMetaEnv {
    readonly VITE_PROJECT_NAME: string
    readonly VITE_TITLE: string
    readonly VITE_BASE_URL: string | undefined
    readonly VITE_BASE_API: string
    readonly VITE_LOCAL_STORE: string
}

interface ImportMeta {
    readonly env: ImportMetaEnv
}

// 声明一个模块，用于匹配所有以 ".vue" 结尾的文件
declare module '*.vue' {
    // 从 "vue" 中导入 DefineComponent 类型
    import type { DefineComponent } from 'vue'
    // 定义一个类型为 DefineComponent 的变量 component
    // 它具有三个泛型参数，分别表示组件的 props、组件的 data 和其他的类型。
    // 在这里，我们使用空对象（{}）表示没有 props，使用空对象（{}）表示没有 data，使用 any 表示其他类型可以是任意值。
    // eslint-disable-next-line @typescript-eslint/no-explicit-any, @typescript-eslint/ban-types
    const component: DefineComponent<{}, {}, any>
    // 导出 component 变量，这样其他地方在导入 ".vue" 文件时，TypeScript 编译器会将它识别为一个 Vue 组件
    export default component
}
