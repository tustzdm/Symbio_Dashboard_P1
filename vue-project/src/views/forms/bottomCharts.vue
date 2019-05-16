<template>
  <div class="bottomCharts panel">
    <h3 class="section-title" style="width:155px">Bottom Charts :</h3>
    <div v-for="i in 3" :key="'bottomChart'+i">
      <div class="section-content">Line {{i}}:</div>
      <select v-model="bottomChartsOutput['select'+i]" class="select-style">
        <option selected disable value="Please select">Please select</option>
        <option
          v-for="item in bottomChart"
          :key="item.name"
          :value="item.name"
          v-show="item.name == 'Please select' || !allSelects.includes(item.name)"
        >{{item.name}}</option>
      </select>
    </div>
  </div>
</template>

<script>
export default {
  props: { bottomChartsOutput: Object, bottomChart: Array, allSelects: Array },
  computed: {
    selected() {
      this.$emit('bottomChartsOutput', {
        select1: this.bottomChartsOutput.select1,
        select2: this.bottomChartsOutput.select2,
        select3: this.bottomChartsOutput.select3
      })
    }
  }
}
</script>