<template>
  <div class="buy-root" style="width:85%;margin-left:7.5%">
    <div class="manage-charts">
      <div class="chartContainer">
        <chart :options="pie" class="panel"/>
      </div>
      <div class="chartContainer">
        <chart :options="rect" class="panel"/>
      </div>
    </div>
    <product-list></product-list>
  </div>
</template>
<script>
import ECharts from 'vue-echarts'
import 'echarts/lib/chart/pie'
import 'echarts/lib/chart/bar'
import 'echarts/lib/component/legend'
import 'echarts/lib/component/dataset'
import getPie from './pie'
import getRect from './rect'
import { getTestManager, getProjectInfo } from '@/api/index'
import storage from '@/utils/storage'
import productList from './productList'
export default {
  data() {
    return { pie: getPie(), rect: getRect() }
  },
  components: { productList, chart: ECharts },
  mounted() {},
  methods: {
    initProjet() {
      getProjectInfo().then(res => {
        this.projectInfo = res.data
      })
    }
  }
}
</script>

<style lang="stylus" scoped>
.manage-charts {
  width: 100%;
}

.chartContainer {
  width: 50%;
  float: left;
  text-align: center;
}

.buy-root {
  height: 100%;
  display: flex;
  flex-direction: column;
}
</style>
