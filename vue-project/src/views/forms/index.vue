<template>
  <div style="min-height:1000px">
    <div class="preCharts">
      <div class="preview panel">
        <h3 class="section-title" style="width:88px">Preview :</h3>
        <div class="section-content">Reports:</div>
        <select class="select-style">
          <option>Default report</option>
          <option v-for="option in previewData">{{ option }}</option>
        </select>
      </div>
      <app-regular-charts
        :regularCharts="regularCharts"
        @regularChartsOutput="userOutput['regularChartsOutput'] = $event"
      ></app-regular-charts>
      <app-table :tableData="table" @tableOutput="userOutput['tableOutput'] = $event"></app-table>
      <app-bottom-chart
        :bottomChart="bottomChart"
        @bottomChartsOutput="userOutput['bottomChartsOutput'] = $event"
      ></app-bottom-chart>
    </div>
    <div class="customCharts">
      <app-custom-charts
        :supportChart="supportCharts"
        @customChartsOutput="userOutput['customChartsOutput'] = $event"
      ></app-custom-charts>
    </div>
    <div>
      User Output:
      <br>
      RegularChartsOutPut(listChartCommon): {{userOutput.regularChartsOutput}}
      <br>
      TableOutput(listList): {{userOutput.tableOutput}}
      <br>
      BottomChartsOutput(listRowChart): {{userOutput.bottomChartsOutput}}
      <br>
      customChartsOutput(listChartOther):{{userOutput.customChartsOutput}}
      <p>&nbsp;</p>
    </div>
  </div>
</template>

<script>
import appBottomChart from './bottomCharts'
import appTable from './table'
import appRegularCharts from './regularCharts'
import appCustomCharts from './customCharts'

export default {
  components: {
    appBottomChart,
    appTable,
    appRegularCharts,
    appCustomCharts
  },
  data() {
    return {
      regularCharts: [
        { id: '001', name: 'Regular Chart A' },
        { id: '002', name: 'Regular Chart B' },
        { id: '003', name: 'Regular Chart C' },
        { id: '004', name: 'Regular Chart D' },
        { id: '005', name: 'Regular Chart E' }
      ],
      table: [
        { id: '009', name: 'Regular Chart I' },
        { id: '010', name: 'Regular Chart J' }
      ],
      bottomChart: [
        { id: '006', name: 'Regular Chart F' },
        { id: '007', name: 'Regular Chart G' },
        { id: '008', name: 'Regular Chart H' }
      ],
      supportCharts: ['Chart1', 'Char2', 'Chart3', 'Chart4', 'Chart5'],
      userOutput: {
        regularChartsOutput: '',
        tableOutput: '',
        bottomChartsOutput: '',
        customChartsOutput: []
      }
    }
  },
  computed: {
    previewData() {
      return this.userOutput.customChartsOutput
    }
  }
}
</script>

<style scoped>
.panel {
  width: 550px;
}

.preCharts,
.customCharts {
  display: inline-block;
  vertical-align: top;
}
</style>
