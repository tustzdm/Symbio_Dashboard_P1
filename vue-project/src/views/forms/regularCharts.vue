<template>
  <div class="regularCharts panel">
    <h3 class="section-title" style="width:160px">Regular Charts :</h3>
    <div v-for="i in 3" :key="'regularCharts'+i">
      <div class="section-content">Line {{i}}:</div>
      <select v-model="output['select'+ (2*i-1)]" class="select-style">
        <option selected disable value="Please select">Please select</option>
        <option
          v-for="item in regularCharts"
          :key="item.id"
          :value="item.name"
          v-show="item.name == 'Please select' || !selected.includes(item.name)"
        >{{item.name}}</option>
      </select>
      <select v-model="output['select'+ 2*i]" class="select-style">
        <option selected disable value="Please select">Please select</option>
        <option
          v-for="item in regularCharts"
          :key="item.id"
          :value="item.name"
          v-show="item.name == 'Please select' || !selected.includes(item.name)"
        >{{item.name}}</option>
      </select>
    </div>
  </div>
</template>

<script>
export default {
  props: { regularCharts: Array },
  data: function() {
    return {
      output: {
        select1: 'Please select',
        select2: 'Please select',
        select3: 'Please select',
        select4: 'Please select',
        select5: 'Please select',
        select6: 'Please select'
      }
    }
  },
  computed: {
    selected() {
      this.$emit('regularChartsOutput', [
        this.output.select1.replace(/Please select/gi, ''),
        this.output.select2.replace(/Please select/gi, ''),
        this.output.select3.replace(/Please select/gi, ''),
        this.output.select4.replace(/Please select/gi, ''),
        this.output.select5.replace(/Please select/gi, ''),
        this.output.select6.replace(/Please select/gi, '')
      ])
      return [
        this.output.select1,
        this.output.select2,
        this.output.select3,
        this.output.select4,
        this.output.select5,
        this.output.select6
      ]
    }
  }
}
</script>