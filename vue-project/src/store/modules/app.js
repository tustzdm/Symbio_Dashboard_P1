import Cookies from "js-cookie";
import storage from "@/utils/storage";
import * as types from "../mutaion-types";

const app = {
  state: {
    firstLogin: storage.get("firstLogin") || "yep",
    version: "V1.0.0",
    language: Cookies.get("language") || "zh",
    sidebar: {
      sliderState: storage.get("sliderState")
    }
  },
  mutations: {
    [types.SET_LANGUAGE]: (state, language) => {
      state.language = language;
      Cookies.set("language", language);
    },
    [types.SET_FIRSTLOGIN]: state => {
      state.firstLogin = "nope";
      storage.set("firstLogin", "nope");
    },
    [types.SET_SLIDERSTATE]: (state, newSliderState) => {
      state.sidebar.sliderState = newSliderState;
      storage.set("sliderState", newSliderState);
    }
  },
  actions: {
    toggleSideBar({ commit }) {
      let state = "";
      storage.get("sliderState") === "full" ? (state = "collapse") : (state = "full");
      commit(types.SET_SLIDERSTATE, state);
    },
    setLanguage({ commit }, language) {
      commit(types.SET_LANGUAGE, language);
    },
    setFirstLogin({ commit }) {
      commit(types.SET_FIRSTLOGIN);
    },
    setSliderState({ commit }, newSliderState) {
      commit(types.SET_SLIDERSTATE, newSliderState);
    }
  }
};

export default app;
