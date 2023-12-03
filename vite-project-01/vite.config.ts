import { defineConfig } from 'vite'
import { join } from "path";
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  base: './',
  server: {
    port: 5173
  },
  resolve: {
    alias: {
      '@': join(__dirname, "src"),
    }
  }
})
