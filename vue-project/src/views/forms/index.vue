<template>
  <div>
    <div style="text-align: center; min-width:1200px ">
      <div class="preCharts">
        <app-regular-charts
          :regularCharts="regularCharts"
          @regularChartsOutputEvent="userOutput.regularChartsOutput = $event"
          :allSelects="allSelects"
          :regularChartsOutput="userOutput.regularChartsOutput"
        ></app-regular-charts>
        <app-table
          :tableData="tableData"
          @tableOutputEvent="userOutput.tableOutputs = $event"
          :tableOutputs="userOutput.tableOutputs"
          :allSelects="allSelects"
        ></app-table>
        <app-bottom-chart
          :bottomChartsOutput="userOutput.bottomChartsOutput"
          :bottomChart="bottomChart"
          @bottomChartsOutputEvent="userOutput.bottomChartsOutput = $event"
          :allSelects="allSelects"
        ></app-bottom-chart>
      </div>
      <div class="customCharts">
        <app-custom-charts
          :supportChart="unusedSupportCharts"
          @customChartsOutput="userOutput.customChartsOutput = $event"
          :allSelects="allSelects"
        ></app-custom-charts>
      </div>
    </div>
    <div style="text-align: center">
      <div class="preview panel">
        <div style="text-align: left ">
          <h3 class="section-title" style="width:88px">Preview :</h3>
          <div class="section-content">Reports:</div>
          <select class="select-style">
            <option v-if="previewValidation">Default report</option>
            <option v-else>Please select charts</option>
            <option v-for="(option,index) in previewData" :key="'Preview'+ index">{{ option }}</option>
          </select>
          <button
            class="submit"
            @click="postUserOutput([JSON.stringify(userOutput),JSON.stringify(supportCharts)])"
          >Submit</button>
          <button class="reset">Reset</button>
        </div>
        <img src="../../assets/images/demo.png" class="logo" :scale="4">
      </div>
    </div>
    <p style="margin-top:50px">&nbsp;</p>
  </div>
</template>

<script>
import appBottomChart from './bottomCharts'
import appTable from './table'
import appRegularCharts from './regularCharts'
import appCustomCharts from './customCharts'
import { postMethod } from './postMethod.js'

export default {
  data() {
    return {
      previewValidation: false,
      regularCharts: [
        { id: '001', name: 'Regular Chart A', key: 'RegularChartA' },
        { id: '002', name: 'Regular Chart B', key: 'RegularChartB' },
        { id: '003', name: 'Regular Chart C', key: 'RegularChartC' },
        { id: '004', name: 'Regular Chart D', key: 'RegularChartD' },
        { id: '005', name: 'Regular Chart E', key: 'RegularChartE' }
      ],
      tableData: [
        { id: '009', name: 'Regular Chart I', key: 'RegularChartI' },
        { id: '010', name: 'Regular Chart J', key: 'RegularChartJ' }
      ],
      bottomChart: [
        { id: '006', name: 'Regular Chart F', key: 'RegularChartF' },
        { id: '007', name: 'Regular Chart G', key: 'RegularChartG' },
        { id: '008', name: 'Regular Chart H', key: 'RegularChartH' }
      ],
      supportCharts: [
        { id: '001', name: 'Regular Chart A', key: 'RegularChartA' },
        { id: '002', name: 'Regular Chart B', key: 'RegularChartB' },
        { id: '003', name: 'Regular Chart C', key: 'RegularChartC' },
        { id: '004', name: 'Regular Chart D', key: 'RegularChartD' },
        { id: '005', name: 'Regular Chart E', key: 'RegularChartE' },
        { id: '006', name: 'Regular Chart F', key: 'RegularChartF' },
        { id: '007', name: 'Regular Chart G', key: 'RegularChartG' },
        { id: '008', name: 'Regular Chart H', key: 'RegularChartH' },
        { id: '009', name: 'Regular Chart I', key: 'RegularChartI' },
        { id: '010', name: 'Regular Chart J', key: 'RegularChartJ' }
      ],
      unusedSupportCharts: [
        { id: '001', name: 'Regular Chart A', key: 'RegularChartA' },
        { id: '002', name: 'Regular Chart B', key: 'RegularChartB' },
        { id: '003', name: 'Regular Chart C', key: 'RegularChartC' },
        { id: '004', name: 'Regular Chart D', key: 'RegularChartD' },
        { id: '005', name: 'Regular Chart E', key: 'RegularChartE' },
        { id: '006', name: 'Regular Chart F', key: 'RegularChartF' },
        { id: '007', name: 'Regular Chart G', key: 'RegularChartG' },
        { id: '008', name: 'Regular Chart H', key: 'RegularChartH' },
        { id: '009', name: 'Regular Chart I', key: 'RegularChartI' },
        { id: '010', name: 'Regular Chart J', key: 'RegularChartJ' }
      ],
      userOutput: {
        regularChartsOutput: {
          select1: 'Please select',
          select2: 'Please select',
          select3: 'Please select',
          select4: 'Please select',
          select5: 'Please select',
          select6: 'Please select'
        },
        tableOutputs: {
          select1: 'Please select',
          select2: 'Please select',
          select3: 'Please select'
        },
        bottomChartsOutput: {
          select1: 'Please select',
          select2: 'Please select',
          select3: 'Please select'
        },
        customChartsOutput: []
      }
    }
  },
  methods: {
    postUserOutput(data) {
      postMethod(data)
    }
  },
  computed: {
    previewData() {
      return this.userOutput.customChartsOutput
    },
    allSelects() {
      return Object.values(this.userOutput.regularChartsOutput).concat(
        Object.values(this.userOutput.tableOutputs),
        Object.values(this.userOutput.bottomChartsOutput),
        this.userOutput.customChartsOutput
      )
    }
  },
  watch: {
    allSelects(newValue) {
      for (let i in newValue) {
        if (newValue[i] !== 'Please select') {
          return (this.previewValidation = true)
        }
        this.previewValidation = false
      }
    }
  },
  components:{
    appBottomChart,
    appTable,
    appRegularCharts,
    appCustomCharts
  }
}
</script>

<style lang="stylus" scoped>
.panel {
  background-color: #fff;
  border: none;
  border-radius: 0.625rem;
  box-shadow: 0 0.46875rem 2.1875rem rgba(90, 97, 105, 0.1), 0 0.9375rem 1.40625rem rgba(90, 97, 105, 0.1), 0 0.25rem 0.53125rem rgba(90, 97, 105, 0.12), 0 0.125rem 0.1875rem rgba(90, 97, 105, 0.1);
  margin: 20px 15px 30px 15px;
  width: 550px;
}

.section-title {
  padding: 15px 0 5px 10px;
  font-family: 'Poppins';
  border-bottom: 2px solid #17c671;
  margin: 10px;
}

.section-content {
  font-family: 'Poppins';
  padding: 10px;
  display: inline-block;
  margin: 8px;
  width: 48px;
}

select {
  padding: 5px;
  font-size: 0.85rem;
  line-height: 1.125rem;
  color: #495057;
  border: 1px solid #becad6;
  font-weight: 300;
  border-radius: 0.375rem;
  display: inline-block;
  margin: 10px;
  font-family: 'Poppins';
}

.preCharts, .customCharts {
  display: inline-block;
  vertical-align: top;
  text-align: left;
}

button {
  background-color: #17c671;
  border-color: #17c671;
  box-shadow: none;
  color: #fff;
  border-radius: 50px;
  font-family: 'Poppins';
  width: 100px;
  height: 40px;
  text-align: center;
  box-shadow: 0 0.46875rem 2.1875rem rgba(90, 97, 105, 0.1), 0 0.9375rem 1.40625rem rgba(90, 97, 105, 0.1), 0 0.25rem 0.53125rem rgba(90, 97, 105, 0.12), 0 0.125rem 0.1875rem rgba(90, 97, 105, 0.1);
}

.submit {
  position: absolute;
  right: 150px;
}

.reset {
  position: absolute;
  right: 20px;
  background-color: #ff7400;
  border-color: #ff7400;
}

.preview {
  margin: 0 30px;
  display: inline-block;
  text-align: center;
  position: relative;
  width: 1140px;
}

img {
  margin-top: 10px;
  width: 80%;
  height: 80%;
  max-width: 100%;
  max-height: 100%;
}

ul {
  border-radius: 0.625rem;
  margin: 20px 30px 8px 30px;
  border: 1px solid #becad6;
  min-height: 90px;
}

li.customChart, li.supportChart {
  padding: 15px 15px 15px 30px;
}

.supportChartActive {
  background-color: #adedc5;
  border-radius: 0.625rem;
}

.supportChart:hover {
  border: 2px solid #adedc5;
  border-radius: 0.625rem;
}

.customChartActive {
  background-color: #ffc7ab;
  border-radius: 0.625rem;
}

.customChart:hover {
  border: 2px solid #ffc7ab;
  border-radius: 0.625rem;
}

button {
  background-color: #17c671;
  border-color: #17c671;
  box-shadow: none;
  color: #fff;
  border-radius: 50px;
  margin: 25px 100px 10px;
  width: 64px;
  height: 31px;
  text-align: center;
  box-shadow: 0 0.46875rem 2.1875rem rgba(90, 97, 105, 0.1), 0 0.9375rem 1.40625rem rgba(90, 97, 105, 0.1), 0 0.25rem 0.53125rem rgba(90, 97, 105, 0.12), 0 0.125rem 0.1875rem rgba(90, 97, 105, 0.1);
}

.removeButton {
  background-color: #ff7400;
  border-color: #ff7400;
}

.disabled {
  cursor: not-allowed;
  background-color: #ff7400;
}
</style>
