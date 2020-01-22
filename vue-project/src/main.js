import Vue from "vue";
import Element from "element-ui";
import axios from 'axios';
import scroll from "vue-seamless-scroll";
import Icon from "vue-svg-icon/Icon.vue";
import 'element-ui/lib/theme-chalk/index.css';
import '@/assets/styl/index'
import App from "@/App";
import { router } from "@/router";
import { asyncRouterMap } from "@/router/meunsRouterMap";
import store from "@/store";
import i18n from "@/lang";
import Fetch from './utils/fetch';
import isRoleEnable from './utils/role';
import selfLog from './utils/selfLog';
import "./main.css";
Vue.prototype.Fetch = Fetch;
Vue.prototype.$axios = axios;
Vue.prototype.isRoleEnable = isRoleEnable;
Vue.prototype.selfLog = selfLog;
axios.defaults.baseURL = '/api';
axios.defaults.headers.post['Content-Type'] = 'application/json';

Vue.component("icon", Icon);
Vue.use(scroll);
Vue.use(Element, {
    size: "small",
    i18n: (key, value) => i18n.t(key, value)
});
Vue.config.productionTip = false;
asyncRouterMap.then(res => {
    window.$menusRouter = res;
    router.addRoutes(res);
    new Vue({
        el: "#app",
        router,
        store,
        i18n,
        components: { App },
        template: "<App/>"
    });
});



router.beforeEach((to, from, next) => {
    if (to.meta.requireAuth) { // 判断该路由是否需要登录权限
      if (localStorage.getItem('token')) { // 通过vuex state获取当前的token是否存在
        next();
      } else {
        next({
          path: '/',
          query: {
            redirect: router.currentRoute.fullPath
          } // 将跳转的路由path作为参数，登录成功后跳转到该路由
        })
      }
    } else {
      next();
    }  
  
});

//http response 拦截器
axios.interceptors.response.use(
  response => {
    if(response.data.ec == '000003'){ //判断token是否过期，如果过期就返回登录页面
      router.push({
        path:"/",
        querry:{redirect:router.currentRoute.fullPath}//从哪个页面跳转
      })
    }
    return response;
  },
  error => {
    return Promise.reject(error)
  }
)