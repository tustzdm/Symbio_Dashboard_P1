import Vue from "vue";
import Element from "element-ui";
import scroll from "vue-seamless-scroll";
import Icon from "vue-svg-icon/Icon.vue";
import 'element-ui/lib/theme-chalk/index.css';
import '@/assets/styl/index'
import App from "@/App";
import { router } from "@/router";
import { asyncRouterMap } from "@/router/meunsRouterMap";
import store from "@/store";
import i18n from "@/lang";
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
