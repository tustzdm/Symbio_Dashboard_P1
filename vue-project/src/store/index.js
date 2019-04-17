import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

import app from "./modules/app";
import user from "./modules/user";
import skin from "./modules/skin";
import tagsView from "./modules/tagsView";
import getters from "./getters";

const store = new Vuex.Store({
  modules: {
    app,
    user,
    tagsView,
    skin
  },
  getters,
  strict: false
});

export default store;
