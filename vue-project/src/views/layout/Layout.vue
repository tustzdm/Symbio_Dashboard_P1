<template>
  <el-container>
    <el-header style="height:80px">
      <nav-bar/>
    </el-header>
    <el-container>
      <el-main>
        <app-main/>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import Navbar from './Navbar'
import Sidebar from './Sidebar'
import AppMain from './AppMain'
import TagsView from './TagsView'
import skinList from '@/config/skin.js'
export default {
  name: 'Layout',
  components: {
    'side-bar': Sidebar,
    'nav-bar': Navbar,
    'app-main': AppMain,
    'tags-view': TagsView
  }
}
</script>

<style lang="stylus" scoped>
.el-container {
  width: 100%;
  height: 100%;
}

.el-header {
  background-color: #454545;
}

.el-aside {
  transition: width 0.3s;
  color: el-aside-color;
  overflow: hidden;
  border-right: 1px solid #cac4c4;
}

.el-main {
  padding: 0;
  overflow: auto;
  background-color: white;
}
</style>
