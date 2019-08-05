<template>
  <div class="headerContainer">
    <img src="../../assets/images/logo.png" alt class="logo">
    <router-link class="headerQualityOverview headeritems" to="/index/index">
      <icon class="headerIcon" name="qualityOverview"></icon>
      <div class="headerTitle">Quality Overview</div>
    </router-link>
    <router-link class="headerTestManagement headeritems" to="/buy/index">
      <icon class="headerIcon" name="testManagement"></icon>
      <div class="headerTitle">Test Management</div>
    </router-link>
    <router-link class="headerResultReview headeritems" to="/searchvisit/index">
      <icon class="headerIcon" name="resultReview"></icon>
      <div class="headerTitle">Result Review</div>
    </router-link>
    <router-link class="headerBugs headeritems" to="/prdvisit/index">
      <icon class="headerIcon" name="bugs"></icon>
      <div class="headerTitle">Bugs Overview</div>
    </router-link>
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
    infodrop
  }
}
</script>

<style >
.headerContainer {
  height: 80px;
  display: flex;
  align-items: center;
  justify-items: center;
  min-width: 1336px;
}
.logo {
  height: 90px;
}
.headerIcon {
  margin-top: 5px;
}
.headerTitle {
  font-size: 16px;
  margin-bottom: 5px;
  color: white;
  font-family: 'Poppins';
}
.globalSearch {
  margin-left: auto;
  margin-right: 20px;
}
.bell {
  margin-right: 20px;
}
.userInfo {
  margin-right: 1px;
}
.headeritems {
  width: 150px;
  height: 70px;
  margin-left: 40px;
  text-align: center;
  border-top-left-radius: 0.5rem;
  border-top-right-radius: 0.5rem;
  margin-top: 10px;
}
.headerQualityOverview {
  margin-left: 60px;
}
svg {
  color: #ffffff;
  width: 35px;
  height: 35px;
}
.headerTestManagement {
  width: 170px;
}

/* hover event */
.headeritems:hover,
.headeritems:hover svg,
.headeritems:hover div {
  fill: #ff7400 !important;
  color: #ff7400;
}

/* click event */
.router-link-active,
.router-link-active svg,
.router-link-active div {
  background-color: white;
  fill: black !important;
  color: black;
  font-weight: bolder;
}
</style>
