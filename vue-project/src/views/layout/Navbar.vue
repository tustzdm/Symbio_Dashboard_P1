<template>
  <div class="sidebar-wrap">
    <img src="../../assets/images/logo.png" alt="" class="logo">
    <global-search class="globalSearch"></global-search>
    <notice class="bell"/>
    <infodrop class="userInfo"/>
  </div>
</template>

<script>

import notice from '@/components/notice'
import infodrop from './NavbarInfoDrop'
import globalSearch from '@/components/globalSearch'


export default {
  name: 'Sidebar',
  components: {
    globalSearch,
    notice,
    infodrop,
  },
  mounted() {
    if (!this.$store.getters.sidebar.sliderState) {
      this.$store.dispatch('setSliderState', 'full')
    }
  },
  data() {
    return {
      system_version: this.$store.state.app.version
    }
  },
  computed: {},
  methods: {
    handleSwitchNavbar() {
      this.$store.dispatch('toggleSideBar')
    }
  }
}
</script>

<style >
.logo{
    padding:13px;
    height:30px;    
}
.bell{
    display: inline-block;
    position: absolute;
    right: 80px;
}
.userInfo{
    display: inline-block;
    position: absolute;
    right: 15px;
}
.globalSearch{
    display: inline-block;
    position: absolute;
    right: 150px;
    top: 16px;
}
</style>
