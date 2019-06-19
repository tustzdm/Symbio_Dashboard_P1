<template>
  <div class="edit-table-background">
    <div class="edit-table-container panel">
      <div class="edit-table-title">Select your header</div>
      <div class="edit-table-items">
        <div>
          <p
            class="edit-table-item"
            v-for="item in DatabaseTable"
            @click="selected.includes(item)?'':selected.push(item)"
            :class="selected.includes(item)? 'edit-table-select':''"
          >{{item}}</p>
        </div>
        <hr style="margin-top:25px">
        <div style="min-height:100px">
          <p
            class="edit-table-item"
            v-for="item in selected"
            @click="selected.splice(selected.indexOf(item), 1)"
            :class="item == selected? 'edit-table-select':''"
          >{{item}}</p>
        </div>
      </div>
      <div style="display:flex">
        <div class="edit-table-title">Display per page</div>
        <input id="display" type="number" value="30" class="edit-table-title">
      </div>
      <div class="edit-table-buttons">
        <button id="cancel" @click="CancelDisplayCustomTable()">Cancel</button>
        <button id="done" @click="CloseDisplayCustomTable()">Done</button>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    displayCustomTable: Boolean,
    tableHeader: Array,
    displayPerPage: Number
  },
  methods: {
    CloseDisplayCustomTable() {
      this.displayCustomTable = false
      this.displayPerPage = Number(document.querySelector('#display').value)
      this.$emit('closeDisplayCustomTable', {
        displayCustomTable: this.displayCustomTable,
        CustomTableSelected: this.selected,
        displayPerPage: this.displayPerPage
      })
    },
    CancelDisplayCustomTable() {
      this.$emit('cancelDisplayCustomTable', (this.displayCustomTable = false))
    }
  },
  data() {
    return {
      selected: [
        //need to store to DB
        'ID',
        'Summary',
        'Assignee',
        'Reporter',
        'Priority',
        'Status'
      ],
      DatabaseTable: [
        'ID',
        'Summary',
        'Assignee',
        'Reporter',
        'Priority',
        'Status',
        'Project',
        'Last Update',
        'Last Modified',
        'Date'
      ]
    }
  }
}
</script>
<style scoped>
.edit-table-background {
  background-color: rgb(0, 0, 0, 0.4);
  z-index: 1000;
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  top: 0;
  left: 0;
}
.edit-table-container {
  background-color: white;
  width: 500px;
  min-height: 600px;
  border-radius: 10px;
  position: relative;
}
.edit-table-title {
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
.edit-table-items {
  margin: 5px 30px 20px 15px;
}
.edit-table-item {
  font-family: 'Poppins';
  border: solid 1px #7a85a1;
  font-size: 16px;
  padding: 8px;
  text-align: center;
  display: inline-block;
  margin: 15px 0 0 15px;
}
.edit-table-item:hover {
  background-color: #f3d1cd;
  cursor: pointer;
  border: none;
}
.edit-table-select {
  background-color: #7a85a1;
  cursor: pointer;
  color: white;
}
input {
  height: 30px;
  width: 70px;
  text-align: center;
}
</style>
