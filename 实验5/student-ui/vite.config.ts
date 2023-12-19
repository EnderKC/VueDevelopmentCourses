import { defineConfig, loadEnv } from 'vite'
import vue from '@vitejs/plugin-vue'
import eslintPlugin from 'vite-plugin-eslint'
//
import { resolve } from 'path'
// 自动导入ui-组件 比如说ant-design-vue  element-plus等
import Components from 'unplugin-vue-components/vite'
// ant-design-vue
import { AntDesignVueResolver } from 'unplugin-vue-components/resolvers'
// 这个插件是为了解决在开发中的导入问题
import AutoImport from 'unplugin-auto-import/vite'
// 引入压缩
import viteCompression from 'vite-plugin-compression'
// mock测试
import mockDevServerPlugin from 'vite-plugin-mock-dev-server'
// https://vitejs.dev/config/
export default defineConfig(({ command, mode }) => {
  // 根据当前工作目录中的 `mode` 加载 .env 文件
  // 第二个参数：process.cwd()表示返回运行当前脚本的工作目录的路径（current work directory）
  // 设置第三个参数为 '' 来加载所有环境变量，而不管是否有 `VITE_` 前缀。
  const env = loadEnv(mode, process.cwd(), 'VITE_')

  console.log('env:', env, ',mode:', mode, ',command:', command)
  // 定义要被去除的路径正则表达式
  const apiBaseRegex = new RegExp(`^\/${env.VITE_BASE_API}`)

  return {
    //  开发或生产环境服务的公共基础路径：默认'/'   1、绝对 URL 路径名： /foo/；  2、完整的 URL： https://foo.com/； 3、空字符串或 ./（用于开发环境）
    base: '/',
    // 静态资源处理
    assetsInclude: resolve(__dirname, './src/assets'),

    // ******插件配置******
    plugins: [
      vue(),
      // mockDevServerPlugin(),
      eslintPlugin({
        include: ['src/**/*.ts', 'src/**/*.vue', 'src/*.ts', 'src/*.vue']
      }),
      AutoImport({
        dts: 'types/auto-imports.d.ts', // 生成配置文件，如果是ts项目，通常我们会把声明文件放在根目录/types中
        // 预设负责告诉插件应该自动引入哪些内容
        imports: [
          'vue',
          'vue-router',
          'pinia',
          {
            axios: [
              ['default', 'axios'] // import { default as axios } from 'axios',
            ]
          }
        ],
        eslintrc: {
          enabled: false, // 默认false, true启用。生成一次就可以，避免每次工程启动都生成，一旦生成配置文件之后，最好把enable关掉，即改成false
          // 否则这个文件每次会在重新加载的时候重新生成，这会导致eslint有时会找不到这个文件。当需要更新配置文件的时候，再重新打开
          filepath: './.eslintrc-auto-import.json', // 生成json文件,可以不配置该项，默认就是将生成在根目录
          globalsPropValue: true
        }
      }),
      Components({
        dirs: ['src/components'], // 配置需要默认导入的自定义组件文件夹，该文件夹下的所有组件都会自动 import
        resolvers: [
          AntDesignVueResolver({
            //  importStyle?: boolean | 'css' | 'less'
            importStyle: false, // css in js
            resolveIcons: true
          })
        ]
      }),
      viteCompression({
        verbose: true,
        disable: false,
        // 超过20k的文件压缩
        threshold: 20480,
        algorithm: 'gzip',
        ext: '.gz'
      })
    ],
    // 引入全局scss文件
    css: {
      preprocessorOptions: {
        scss: {
          additionalData: '@import "@/assets/css/main.scss";',
          charset: false,
          javascriptEnabled: true
        }
      }
    },
    // ******resolver配置******
    resolve: {
      // 配置路径别名
      alias: {
        // 如果报错__dirname找不到，需要安装node,执行npm install @types/node --save-dev
        '@': resolve(__dirname, 'src')
      }
    },
    // ******开发服务器配置******
    server: {
      host: 'localhost',
      // 设置服务启动端口号
      port: 8000,
      // 设置服务启动时是否自动打开浏览器
      open: true,
      // 允许跨域
      cors: true,
      // 是否开启 https
      https: false,
      /**
       * 在生产中服务时的基本公共路径。
       * @default '/'
       */
      base: './',
      // 反向代理
      proxy: {
        // 本地开发环境通过代理实现跨域
        // 正则表达式写法/api
        ['/' + env.VITE_BASE_API]: {
          target: env.VITE_BASE_URL, // 后端服务实际地址http://localhost:8025/user/login
          changeOrigin: true, // 开启代理
          ws: false,
          timeout: 60 * 1000,
          rewrite: (path) => path.replace(apiBaseRegex, '')
        }
      }

      // mock测试配置
      // proxy: {
      //   '^/api': 'http://example.com/'
      // }
    },
    // ******项目构建配置******
    build: {
      // 设置最终构建的浏览器兼容目标  //es2015(编译成es5) | modules
      target: 'modules',
      // 构建得包名  默认：dist
      outDir: 'dist',
      // 静态资源得存放路径文件名  assets
      assetsDir: 'assets',
      // 构建后是否生成 source map 文件
      sourcemap: false,
      // 启用/禁用 brotli 压缩大小报告。 禁用该功能可能会提高大型项目的构建性能
      brotliSize: false,
      // chunk 大小警告的限制（以 kbs 为单位）默认：500
      chunkSizeWarningLimit: 500,
      // 防止 vite 将 rgba() 颜色转化为 #RGBA 十六进制符号的形式  (要兼容的场景是安卓微信中的 webview 时,它不支持 CSS 中的 #RGBA 十六进制颜色符号)
      cssTarget: 'chrome61',
      // css代码拆分,禁用则所有样式保存在一个css里面
      cssCodeSplit: true,
      // 项目压缩 :boolean | 'terser' | 'esbuild',生产环境取消 console
      minify: 'terser',
      terserOptions: {
        compress: {
          drop_console: true,
          drop_debugger: true
        }
      },

      // 会打包出 css js 等文件夹 目录显得清晰
      rollupOptions: {
        output: {
          chunkFileNames: 'js/[name]-[hash].js',
          entryFileNames: 'js/[name]-[hash].js',
          assetFileNames: '[ext]/[name]-[hash].[ext]',
          manualChunks(id) {
            // 静态资源分拆打包
            // if (id.includes('style.css')) {
            //   // 需要单独分割那些资源 就写判断逻辑就行
            //   return 'src/style.css'
            // }
            // if (id.includes('HelloWorld.vue')) {
            //   // 单独分割hello world.vue文件
            //   return 'src/components/HelloWorld.vue'
            // }
            // 最小化拆分包
            if (id.includes('node_modules')) {
              return id.toString().split('node_modules/')[1].split('/')[0].toString()
            }
          }
        }
      }
    }
  }
})
