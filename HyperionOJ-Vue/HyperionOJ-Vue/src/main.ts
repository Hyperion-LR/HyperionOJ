import { createApp } from 'vue';
import { createPinia } from "pinia";
import './style.css';
import 'element-plus/dist/index.css';
import router from "@/router";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue';
import * as ElementPlusIconsVue from '@element-plus/icons-vue'



const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);
const app = createApp(App)

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}
app.use(pinia)
app.use(router)
app.use(ElementPlus)
app.mount('#app')
