import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  // base: '/absproxy/5177',
  server: {
      port: 5177,
  }, 
})
