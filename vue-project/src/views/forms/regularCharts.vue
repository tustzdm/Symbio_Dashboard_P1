<template>
  <div class="regularCharts panel">
    <h3 class="section-title" style="width:160px">Regular Charts :</h3>
    <div v-for="i in 3" :key="'regularCharts'+i">
      <div class="section-content">Line {{i}}:</div>
      <select v-model="regularChartsOutput['select'+ (2*i-1)]" class="select-style">
        <option selected disable value="Please select">Please select</option>
        <option
          v-for="item in regularCharts"
          :key="item.id"
          :value="item.name"
          v-show="item.name == 'Please select' || !allSelects.includes(item.name)"
        >{{item.name}}</option>
      </select>
      <select v-model="regularChartsOutput['select'+ 2*i]" class="select-style">
        <option selected disable value="Please select">Please select</option>
        <option
          v-for="item in regularCharts"
          :key="item.id"
          :value="item.name"
          v-show="item.name == 'Please select' || !allSelects.includes(item.name)"
        >{{item.name}}</option>
      </select>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    regularCharts: Array,
    allSelects: Array,
    regularChartsOutput: Object
  },
  computed: {
    selected() {
      this.$emit('regularChartsOutput', {
        select1: this.regularChartsOutput.select1,
        select2: this.regularChartsOutput.select2,
        select3: this.regularChartsOutput.select3,
        select4: this.regularChartsOutput.select4,
        select5: this.regularChartsOutput.select5,
        select6: this.regularChartsOutput.select6
      })
    }
  }
}
</script>