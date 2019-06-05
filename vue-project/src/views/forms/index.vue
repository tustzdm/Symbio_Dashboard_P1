<template>
  <div style="min-height:1000px">
    <link href="https://fonts.googleapis.com/css?family=Poppins" rel="stylesheet">
    <div class="preCharts">
      <div class="preview panel">
        <h3 class="section-title" style="width:88px">Preview :</h3>
        <div class="section-content">Reports:</div>
        <select>
          <option v-for="option in commonChartList" :value="option.key">{{ option.name }}</option>
        </select>
      </div>
      <div class="commonChart panel">
        <h3 class="section-title" style="width:170px">Common Charts :</h3>
        <div v-for="i in 3" :key="'commonChart'+i">
          <div class="section-content">Line {{i}}:</div>
          <select :id="'commonChart'+(2*i-1)">
            <option
              v-for="option in commonChartList"
              :value="option.key"
              @changed="commonChartDisabled=option"
              :class="{disabled:option==commonChartDisabled}"
            >{{ option.name }}</option>
          </select>
          <select :id="'commonChart'+(2*i)">
            <option v-for="option in commonChartList" :value="option.key">{{ option.name }}</option>
          </select>
        </div>
      </div>
      <div class="listChart panel">
        <h3 class="section-title" style="width:110px">List Charts :</h3>
        <div v-for="i in 3" :key="'listChart'+i">
          <div class="section-content">Line {{i}}:</div>
          <select :id="'listChart'+(2*i-1)">
            <option v-for="option in commonChartList" :value="option.key">{{ option.name }}</option>
          </select>
        </div>
      </div>
      <div class="rowCharts panel">
        <h3 class="section-title" style="width:120px">Row Charts :</h3>
        <div v-for="i in 3" :key="'rowChart'+i">
          <div class="section-content">Line {{i}}:</div>
          <select :id="'rowChart'+(2*i-1)">
            <option
              v-for="option in commonChartList"
              :value="option.key"
              :disabled="true"
            >{{ option.name }}</option>
          </select>
        </div>
      </div>
    </div>
    <div class="customCharts">
      <div class="otherCharts panel">
        <h3 class="section-title" style="width:140px">Other Charts :</h3>
        <ul>
          <li
            class="customChart"
            v-for="(listOther,index) in customChart"
            @click="setRemoveID(index),removeSelected = index"
            :class="{customChartActive:index == removeSelected}"
          >{{listOther}}</li>
        </ul>
        <button class="addButton" @click="addCharts(addID)">&#9650</button>
        <button class="removeButton" @click="removeCharts(removeID)">&#9660</button>
        <h3 class="section-title" style="width:180px">Supported Charts :</h3>
        <ul>
          <li
            class="supportChart"
            v-for="(listSuppotted,index) in supportChart"
            @click="setAddID(index),addSelected = index"
            :class="{supportChartActive:index == addSelected}"
          >{{listSuppotted}}</li>
        </ul>
        <p>&nbsp;</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      commonChartList: [
        {
          pos: [0, 0],
          idx: 0,
          key: 'StackedLine',
          name: 'Stacked Line Chart',
          type: 1
        },
        {
          pos: [0, 1],
          idx: 1,
          key: 'BarLabRotation',
          name: 'Bar Label Rotation',
          type: 1
        },
        {
          pos: [1, 1],
          idx: 1,
          key: 'BarLabRotation',
          name: 'Bar Label Rotation2',
          type: 1
        },
        {
          pos: [1, 2],
          idx: 1,
          key: 'BarLabRotation',
          name: 'Bar Label Rotation3',
          type: 1
        },
        {
          pos: [2, 1],
          idx: 1,
          key: 'BarLabRotation',
          name: 'Bar Label Rotation4',
          type: 1
        }
      ],
      commonCharts: [],
      listChart: [],
      rowChart: [],
      customChart: [],
      supportChart: ['Chart1', 'Char2', 'Chart3', 'Chart4', 'Chart5'],
      removeID: 0,
      addID: 0,
      addSelected: undefined,
      removeSelected: undefined,
      commonChartDisabled: undefined
    }
  },
  methods: {
    addCharts: function(addID) {
      if (this.supportChart.length > 0 && addID < this.supportChart.length) {
        let x = this.supportChart.splice(addID, 1)
        this.customChart.push(x[0])
        this.addID = 0
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
  computed: {
    result() {
      return []
    }
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
