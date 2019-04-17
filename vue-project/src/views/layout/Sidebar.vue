<template>
  <el-scrollbar wrapClass="scrollbar-wrapper">
    <el-menu
      class="sidebar-menu"
      mode="vertical"
      :show-timeout="200"
      :default-active="$route.path"
      :collapse="isCollapse"
      :background-color="skinList[skinId].sliderBg"
      :text-color="skinList[skinId].sliderTc"
      :active-text-color="skinList[skinId].sliderAtc">
      <sidebar-item :routes="asyncRouterMap"></sidebar-item>
    </el-menu>
  </el-scrollbar>
</template>

<script>
import { mapGetters } from "vuex";
import sidebarItem from "./SidebarItem";
import skinList from "@/config/skin.js"
export default {
  name: "Sidebar",
  data() {
    return {
      skinList: skinList,
      asyncRouterMap: window.$menusRouter
    };
  },
  components: {
    sidebarItem
  },
  computed: {
    ...mapGetters(["sidebar", "skinId"]),
    isCollapse() {
      if (this.sidebar.sliderState === "full") {
        return false;
      }
      return true;
    }
  }
};
</script>
