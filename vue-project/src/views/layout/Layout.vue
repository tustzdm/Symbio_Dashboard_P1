<template>
  <el-container>
    <el-header :style="{'background-color':skinList[skinId].headerBg,'height': '64px'}">
      <nav-bar />
    </el-header>
    <el-container>
      <el-aside :width="asideWidth">
        <side-bar class="sidebar-container" />
      </el-aside>
      <el-main>
        <!-- <tags-view /> -->
        <app-main />
        <footer class="app-footer">北京信必优信息技术有限公司</footer>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import Navbar from "./Navbar";
import Sidebar from "./Sidebar";
import AppMain from "./AppMain";
import TagsView from "./TagsView";
import skinList from "@/config/skin.js"
export default {
  name: "Layout",
  data() {
      return {
          skinList: skinList
      }
  },
  components: {
    "side-bar": Sidebar,
    "nav-bar": Navbar,
    "app-main": AppMain,
    "tags-view": TagsView
  },
  computed: {
    asideWidth() {
        return this.$store.getters.getSliderStateWidth;
    },
    skinId() {
        return this.$store.state.skin.skinId;
    }
  }
};
</script>

<style lang="stylus" scoped>
.el-container
  width 100%
  height 100%

.el-header
  padding 0
  font-size 0
  height 50px
  line-height 50px
  box-sizing border-box
  overflow hidden

.el-aside
  transition width .3s
  color el-aside-color
  overflow hidden
  border-right 1px solid #cac4c4

.el-main
  padding 0
  overflow auto
.app-footer
    height 50px
    line-height 50px
    text-align center
    font-size 14px
    background #e1e1e1
    color #7d7d7d
    border-top 1px solid #cac4c4
    box-sizing border-box
</style>
