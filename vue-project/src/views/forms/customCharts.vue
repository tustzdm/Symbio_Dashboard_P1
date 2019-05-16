<template>
  <div class="otherCharts panel">
    <h3 class="section-title" style="width:140px">Other Charts :</h3>
    <ul>
      <li
        class="customChart"
        v-for="(listOther,index) in customChart"
        @click="setRemoveID(index),removeSelected = index"
        :class="{customChartActive:index == removeSelected}"
        :key="listOther.name"
      >{{listOther.name}}</li>
    </ul>
    <button class="addButton" @click="addCharts(addID)">&#9650</button>
    <button class="removeButton" @click="removeCharts(removeID)">&#9660</button>
    <h3 class="section-title" style="width:270px">Unused Supported Charts :</h3>
    <ul>
      <li
        class="supportChart"
        v-for="(listSuppotted,index) in supportChart"
        @click="setAddID(index),addSelected = index"
        :class="{supportChartActive:index == addSelected}"
        :key="listSuppotted.name"
        v-show="!allSelects.includes(listSuppotted.name)"
      >{{listSuppotted.name}}</li>
    </ul>
  </div>
</template>
<script>
export default {
  props: { supportChart: Array, allSelects: Array },
  data() {
    return {
      customChart: [],
      removeID: 0,
      addID: undefined,
      addSelected: undefined,
      removeSelected: undefined
    }
  },
  methods: {
    addCharts: function(addID) {
      if (addID == undefined) {
        alert('Please select an item.')
      }
      if (this.supportChart.length > 0 && addID < this.supportChart.length) {
        let x = this.supportChart.splice(addID, 1)
        this.customChart.push(x[0])
        this.addID = undefined
        this.addSelected = undefined
      }
    },
    setRemoveID: function(index) {
      if ((this.supportChart.length > 0) | (this.customChart.length > 0)) {
        return (this.removeID = index)
      }
    },
    setAddID: function(index) {
      if ((this.supportChart.length > 0) | (this.customChart.length > 0)) {
        return (this.addID = index)
      }
    },
    removeCharts: function(removeID) {
      if (this.customChart.length > 0 && removeID < this.customChart.length) {
        let x = this.customChart.splice(removeID, 1)
        this.supportChart.push(x[0])
        this.removeID = 0
        this.removeSelected = undefined
      }
    }
  },
  watch: {
    customChart() {
      let result = []
      for (let i in this.customChart) {
        result.push(this.customChart[i].name)
      }
      this.$emit('customChartsOutput', result)
    }
  }
}
</script>
<style scoped>
.otherCharts {
  height: 740px !important;
  padding-bottom: 0;
}
ul {
  border-radius: 0.625rem;
  margin: 20px 30px 8px 30px;
  border: 1px solid #becad6;
  min-height: 90px;
  max-height: 410px;
  overflow: hidden;
  overflow-y: scroll;
}

li.customChart,
li.supportChart {
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
  box-shadow: 0 0.46875rem 2.1875rem rgba(90, 97, 105, 0.1),
    0 0.9375rem 1.40625rem rgba(90, 97, 105, 0.1),
    0 0.25rem 0.53125rem rgba(90, 97, 105, 0.12),
    0 0.125rem 0.1875rem rgba(90, 97, 105, 0.1);
}

.removeButton {
  background-color: #ff7400;
  border-color: #ff7400;
}
</style>
