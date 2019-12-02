<template>
  <div class="gridContainer">
    <div class="dropdown">
      <div>Project Type >>></div>
      <div style="margin-left:30px">Release >>></div>
      <div style="margin-left:30px">Test set >>></div>
    </div>
    <div class="cards">
      <div class="card panel" style="background-color: #8396a2">
        <div class="cardTitle">Product Overview</div>
        <div class="cardNumber">32</div>
        <div class="cardPercentage">49%</div>
        <icon class="cardIcon" name="qualityOverview"></icon>
      </div>
      <div class="card panel" style="background-color: #BECDDF">
        <div class="cardTitle">Release Overview</div>
        <div class="cardNumber">44</div>
        <div class="cardPercentage">23%</div>
        <icon class="cardIcon" name="testManagement"></icon>
      </div>
      <div class="card panel" style="background-color: #E9CECE">
        <div class="cardTitle">Test Cases Overview</div>
        <div class="cardNumber">140</div>
        <div class="cardPercentage">77%</div>
        <icon class="cardIcon" name="resultReview"></icon>
      </div>
      <div class="card panel" style="background-color: #F6B8B8">
        <div class="cardTitle">Bug Fix Rate</div>
        <div class="cardNumber">13</div>
        <div class="cardPercentage">8%</div>
        <icon class="cardIcon" name="bugs"></icon>
      </div>
    </div>
    <div class="regularCharts">
      <chart :options="pie" class="panel"/>
      <chart :options="bar" class="panel"/>
      <chart :options="stackedArea" class="panel"/>
      <chart :options="radar" class="panel"/>
      
    </div>
    <div class="table panel">
      <chart :options="polar" class="panel"/>
      <!-- <chart :options="map" class="panel"/> -->
    </div>
    <!-- <div class="bottomCharts panel"></div> -->
    <div></div>
  </div>
</template>

<script>
import ECharts from 'vue-echarts'
import 'echarts/lib/chart/pie'
import 'echarts/lib/chart/bar'
import 'echarts/lib/chart/line'
import 'echarts/lib/component/polar'
import 'echarts/lib/component/tooltip'
import 'echarts/lib/component/title'
import 'echarts/lib/component/legend'
import 'echarts/lib/component/dataset'
import 'echarts/lib/chart/map'
import 'echarts/lib/component/visualMap'
import 'echarts/map/js/world'

import map from './map'
import getPie from './pie'
import getstackedArea from './stackedArea'
import getBar from './bar'
import polar from './polar'
import radar from './radar'
// Map of China
import chinaMap from './china.json'
// registering map data
ECharts.registerMap('china', chinaMap)
export default {
  data() {
    return {
      pie: getPie(),
      stackedArea: getstackedArea(),
      bar: getBar(),
      polar,
      map,
      radar
    }
  },
  components: {
    chart: ECharts
  },
  mounted() {
    console.log(this.bar.title.text)
    this.bar.title.text='ssssss'},
}
</script>

<style scoped>
.panel {
  margin: 0;
  padding: 0;
}
.gridContainer {
  display: grid;
  grid-template-columns: repeat(14, 1fr);
  grid-template-rows: 25px 140px 830px;
  grid-auto-rows: 530px;
  grid-gap: 30px;
}
.gridContainer > div {
  grid-column: 2/14;
}
.dropdown {
  height: 40px;
  font-family: 'Poppins';
  display: flex;
  align-items: center;
  font-weight: bolder;
}
.cards {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  grid-template-rows: 140px;
  grid-gap: 30px;
}
.card {
  position: relative;
  font-family: 'Poppins';
  font-size: 25px;
  color: white;
}
.cardTitle {
  position: absolute;
  top: 15px;
  left: 15px;
}
.cardNumber {
  position: absolute;
  bottom: 15px;
  left: 30px;
  font-size: 65px;
}
.cardPercentage {
  position: absolute;
  bottom: 16px;
  left: 80px;
  font-size: 18px;
  border-radius: 0.5rem;
  width: 50px;
  background-color: #adedc5;
  text-align: center;
}
.card svg {
  position: absolute;
  bottom: 5px;
  right: 3px;
  width: 80px;
  height: 80px;
}
.echarts {
  height: 100%;
  width: 100%;
}
.regularCharts {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  grid-auto-rows: 400px;
  grid-gap: 30px;
}
</style>
