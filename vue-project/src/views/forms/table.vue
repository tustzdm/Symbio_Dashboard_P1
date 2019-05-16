<template>
  <div class="table panel">
    <h3 class="section-title" style="width:70px">Table :</h3>
    <div v-for="i in 3" :key="'table'+i">
      <div class="section-content">Line {{i}}:</div>
      <select v-model="tableOutputs['select'+i]" class="select-style">
        <option selected value="Please select">Please select</option>
        <option
          v-for="item in tableData"
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
  props: { tableData: Array, tableOutputs: Object, allSelects: Array },
  computed: {
    selected() {
      this.$emit('tableOutputEvent', {
        select1: this.tableOutputs.select1,
        select2: this.tableOutputs.select2,
        select3: this.tableOutputs.select3
      })
    }
  }
}
</script>