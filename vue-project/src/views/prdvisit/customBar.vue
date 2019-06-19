<template>
  <div class="edit-bar-background">
    <div class="edit-bar-container panel">
      <div class="edit-bar-title">Edit Source Data</div>
      <div class="edit-bar-items">
        <p
          class="edit-bar-item"
          v-for="item in tableHeader"
          @click="SelectingData(item)"
          :class="selected[0] == item? 'x':selected[1] == item? 'y':''"
        >{{item}}</p>
      </div>
      <p class="edit-bar-text">
        **First selecting or the only selected item will be data for
        <span class="x">X-axis</span>, second selecting will be data for
        <span class="y">Y-axis</span>
      </p>
      <div></div>
      <div class="edit-bar-buttons">
        <button id="cancel" @click="CancelDisplayCustomBar()">Cancel</button>
        <button id="done" @click="CloseDisplayCustomBar()">Done</button>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  props: { displayCustomBar: Boolean, tableHeader: Array },
  methods: {
    CloseDisplayCustomBar() {
      this.displayCustomBar = false
      this.$emit('closeDisplayCustomBar', {
        displayCustomBar: this.displayCustomBar,
        customBarSelected: this.selected
      })
    },
    CancelDisplayCustomBar() {
      this.$emit('cancelDisplayCustomBar', (this.displayCustomBar = false))
    },
    SelectingData(data) {
      if (this.selected.includes(data)) {
        this.selected.splice(this.selected.indexOf(data), 1)
      } else if (this.selected.length < 2) {
        this.selected.push(data)
      }
    }
  },
  data() {
    return {
      selected: []
    }
  }
}
</script>
<style scoped>
.edit-bar-background {
  background-color: rgb(0, 0, 0, 0.4);
  z-index: 999;
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  top: 0;
  left: 0;
}
.edit-bar-container {
  background-color: white;
  width: 500px;
  min-height: 300px;
  border-radius: 10px;
  position: relative;
}
.edit-bar-title {
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
.edit-bar-items {
  margin: 5px 30px 20px 15px;
}
.edit-bar-item {
  font-family: 'Poppins';
  border: solid 1px #7a85a1;
  font-size: 16px;
  padding: 8px;
  text-align: center;
  display: inline-block;
  margin: 15px 0 0 15px;
}
.edit-bar-item:hover {
  background-color: #f3d1cd;
  cursor: pointer;
  border: none;
}
.x {
  background-color: #7a85a1;
  color: white;
  border: none;
}
.y {
  background-color: #c9d4e3;
  color: white;
  border: none;
}
.edit-bar-text {
  margin: 30px 30px;
  font-family: 'Poppins';
  font-size: 14px;
}
</style>
