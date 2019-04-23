<template>
  <div class="table panel">
    <h3 class="section-title" style="width:70px">Table :</h3>
    <div v-for="i in 3" :key="'table'+i">
      <div class="section-content">Line {{i}}:</div>
      <select v-model="output['select'+i]" class="select-style">
        <option selected disable value="Please select">Please select</option>
        <option
          v-for="item in tableData"
          :key="item.name"
          :value="item.name"
          v-show="item.name == 'Please select' || !selected.includes(item.name)"
        >{{item.name}}</option>
      </select>
    </div>
  </div>
</template>

<script>
export default {
  props: { tableData: Array },
  data: function() {
    return {
      output: {
        select1: 'Please select',
        select2: 'Please select',
        select3: 'Please select'
      }
    }
  },
  computed: {
    selected() {
      this.$emit('tableOutput', [
        this.output.select1.replace(/Please select/gi, ''),
        this.output.select2.replace(/Please select/gi, ''),
        this.output.select3.replace(/Please select/gi, '')
      ])
      return [this.output.select1, this.output.select2, this.output.select3]
    }
  }
}
</script>