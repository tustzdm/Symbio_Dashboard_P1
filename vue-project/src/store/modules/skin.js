import storage from "@/utils/storage";
import * as types from "../mutaion-types";

const skin = {
  state: {
    skinId: storage.get("skinId") || 0,
  },
  mutations: {
    [types.SKIN_ID]: (state, skinId) => {
        state.skinId = skinId;
        storage.set("skinId", skinId);
    }
  },
  actions: {
    setSkinId({ commit }, skinId) {
        commit(types.SKIN_ID, skinId);
    }
  }
};

export default skin;
