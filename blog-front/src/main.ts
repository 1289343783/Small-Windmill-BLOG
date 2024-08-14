import {createApp} from 'vue'
import './style.css'
import router from '@/router'
import App from './App.vue'
import '@/styles/tailwind.css'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import VueMavonEditor from "mavon-editor";
import "mavon-editor/dist/css/index.css";
import store from './store'
import '@/styles/el-loading.css'

const app = createApp(App)
app.use(router)
app.use(store)
app.use(VueMavonEditor)
app.use(ElementPlus)
app.mount('#app')
