<template>
  <div class="tableContainer">
    <link
      href="https://fonts.googleapis.com/css?family=Alegreya|Crimson+Text&display=swap"
      rel="stylesheet"
    >
    <table>
      <thead>
        <tr class="tableHeader">
          <th v-for="tableItem,index in tableHeader">
            <a @click="sorting(data,tableItem.toLowerCase());sortingPosition = index">
              <p>{{tableItem}}</p>
              <span v-show="sortingPosition==index && sortingDerection == 1">&#9650</span>
              <span v-show="sortingPosition==index && sortingDerection == 0">&#9660</span>
            </a>
          </th>
        </tr>
      </thead>
      <tbody>
        <tr
          class="tableBody"
          v-for="dataItems in data.slice(this.currentPageBegin,this.currentPageEnd)"
        >
          <td v-for="dataItem in tableHeader">
            <p>{{dataItems[dataItem.toLowerCase()]}}</p>
          </td>
        </tr>
      </tbody>
    </table>
    <div class="tableFooter">
      <a @click="previousPage()">
        <icon name="arrow-left" class="navIcon"></icon>
      </a>
      <div>{{currentPageBegin+1}}-{{currentPageEnd}} of {{this.data.length}}</div>
      <a @click="nextPage()">
        <icon name="arrow-right" class="navIcon"></icon>
      </a>
    </div>
  </div>
</template>
<script>
import data from './data'
export default {
  data() {
    return {
      data, //need to store to DB
      displayPerPage: 30, //need to store to DB
      currentPageBegin: 0,
      tableHeader: [
        //need to store to DB
        'ID',
        'Summary',
        'Assignee',
        'Reporter',
        'Priority',
        'Status'
      ],
      sortingDerection: 0,
      sortingPosition: undefined
    }
  },
  computed: {
    currentPageEnd() {
      if (this.currentPageBegin + this.displayPerPage <= data.length) {
        return this.currentPageBegin + this.displayPerPage
      } else {
        return data.length
      }
    }
  },
  methods: {
    nextPage() {
      if (this.currentPageBegin + this.displayPerPage < data.length) {
        this.currentPageBegin += this.displayPerPage
      }
    },
    previousPage() {
      if (this.currentPageBegin >= this.displayPerPage) {
        this.currentPageBegin -= this.displayPerPage
      }
    },
    changeDisplayPerPage(data) {
      this.displayPerPage = data
    },
    ascendingSort(array, filter) {
      array.sort(function(a, b) {
        if (a[filter] < b[filter]) return -1
        if (a[filter] > b[filter]) return 1
        return 0
      })
    },
    descendingSort(array, filter) {
      array.sort(function(a, b) {
        if (a[filter] < b[filter]) return 1
        if (a[filter] > b[filter]) return -1
        return 0
      })
    },
    sorting(array, filter) {
      this.sortingDerection = 1 - this.sortingDerection
      if (this.sortingDerection == 1) {
        this.ascendingSort(array, filter)
      }
      if (this.sortingDerection == 0) {
        this.descendingSort(array, filter)
      }
    }
  }
}
</script>
<style scoped>
thead {
  text-align: left;
  vertical-align: top;
}
table {
  width: 100%;
  border-collapse: collapse;
}
p {
  margin: 8px 20px;
  font-size: 16px;
}
a > p {
  font-size: 22px;
  font-family: 'Alegreya', serif;
  margin: 8px 0;
}
a {
  display: flex;
  align-items: center;
  justify-content: center;
}
span {
  font-size: 14px;
}
.tableBody {
  border-bottom: #e5e5e5 solid 1px;
  line-height: 40px;
}
.tableBody:hover {
  background-color: #e5e5e5;
}
.tableHeader {
  border-bottom: #7a85a1 solid 2px;
}
.tableFooter {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 10px;
}
svg {
  color: black;
}
</style>
