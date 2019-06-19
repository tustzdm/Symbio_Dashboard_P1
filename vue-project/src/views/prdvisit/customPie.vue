<template>
  <div class="edit-pie-background">
    <div class="edit-pie-container panel">
      <div class="edit-pie-title">Edit Source Data</div>
      <div class="edit-pie-items">
        <p
          class="edit-pie-item"
          v-for="item in tableHeader"
          @click="selected=item"
          :class="item == selected? 'edit-pie-select':''"
        >{{item}}</p>
      </div>
      <div class="edit-pie-buttons">
        <button id="cancel" @click="CancelDisplayCustomPie()">Cancel</button>
        <button id="done" @click="CloseDisplayCustomPie()">Done</button>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  props: { displayCustomPie: Boolean, tableHeader: Array },
  methods: {
    CloseDisplayCustomPie() {
      this.displayCustomPie = false
      this.$emit('closeDisplayCustomPie', {
        displayCustomPie: this.displayCustomPie,
        customPieSelected: this.selected
      })
    },
    CancelDisplayCustomPie() {
      this.$emit('cancelDisplayCustomPie', (this.displayCustomPie = false))
    }
  },
  data() {
    return {
      selected: undefined
    }
  }
}
</script>
<style scoped>
.edit-pie-background {
  background-color: rgb(0, 0, 0, 0.4);
  z-index: 1000;
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  top: 0;
  left: 0;
}
.edit-pie-container {
  background-color: white;
  width: 500px;
  min-height: 300px;
  border-radius: 10px;
  position: relative;
}
.edit-pie-title {
  font-family: 'Poppins';
  font-size: 25px;
  margin-left: 30px;
  margin-top: 25px;
}
button {
  font-family: 'Poppins';
  background-color: #7a85a1;
  border: none;
  border-radius: 4px;
  color: white;
  font-size: 16px;
  width: 90px;
  height: 35px;
  position: absolute;
  bottom: 30px;
  right: 30px;
}
button:hover {
  cursor: pointer;
}
#cancel {
  color: #7a85a1;
  background-color: white;
  border: solid 1px #7a85a1;
  right: 140px;
}
.edit-pie-items {
  margin: 5px 30px 20px 15px;
}
.edit-pie-item {
  font-family: 'Poppins';
  border: solid 1px #7a85a1;
  font-size: 16px;
  padding: 8px;
  text-align: center;
  display: inline-block;
  margin: 15px 0 0 15px;
}
.edit-pie-item:hover {
  background-color: #f3d1cd;
  cursor: pointer;
  border: none;
}
.edit-pie-select {
  background-color: #7a85a1;
  cursor: pointer;
  color: white;
}
</style>
