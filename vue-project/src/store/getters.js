const getters = {
  /* app */
  sidebar: state => state.app.sidebar,
  getSliderStateWidth: state => {
    return state.app.sidebar.sliderState === "full" ? "348px" : "100px";
  },
  lockState: state => state.app.lock,
  /* user */
  roles: state => state.user.roles,
  userInfo: state => state.user,
  /*skinid */
  skinId: state => state.skin.skinId
};
export default getters;
