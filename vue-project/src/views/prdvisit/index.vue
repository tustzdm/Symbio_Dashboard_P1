<template>
<div class="gridContainer">
    <div style="position:relative;display:flex">
        <top :page="page" style="width:100%"></top>
        <!-- <div v-if="basicHeader" class="bugs-header">
            <div>Project Type >>></div>
            <div style="margin-left:30px">Release >>></div>
            <div style="margin-left:30px">Test set >>></div>
            <a class="bugs-trigger" @click="basicHeader = !basicHeader">Advanced</a>
        </div>
        <div v-else class="bugs-header" style="width:85%">
            <input type="text" style="width:95%">
            <a class="bugs-trigger" style="position:absolute;right:0px" @click="basicHeader = !basicHeader">Basic</a>
        </div>
        <router-link to="/prdvisit/report">
            <button class="bugs-generate-report">Generate Report</button>
        </router-link> -->
    </div>
    <bugs-custom-pie class="bugs-edit" :displayCustomPie="displayCustomPie" :tableHeader="tableHeader" v-if="displayCustomPie" @closeDisplayCustomPie="displayCustomPie = $event.displayCustomPie;customPieSelected = $event.customPieSelected" @cancelDisplayCustomPie="displayCustomPie = $event"></bugs-custom-pie>
    <bugs-custom-bar class="bugs-edit" :displayCustomBar="displayCustomBar" :tableHeader="tableHeader" v-if="displayCustomBar" @closeDisplayCustomBar="displayCustomBar = $event.displayCustomBar;customBarSelected = $event.customBarSelected" @cancelDisplayCustomBar="displayCustomBar = $event"></bugs-custom-bar>
    <div class="bugs-charts">
        <div class="bugs-chartContainer">
            <chart :options="pie" class="panel" id="bugs-chartItem" />
            <button class="bugs-customize" @click="displayCustomPie = true">{{$t('bugsoverview.customize')}}</button>
        </div>
        <div>
            <chart :options="bar" class="panel" />
            <button class="bugs-customize" @click="displayCustomBar = true">{{$t('bugsoverview.customize')}}</button>
        </div>
    </div>
    <bugs-custom-table class="bugs-edit" :displayCustomTable="displayCustomTable" :tableHeader="tableHeader" :displayPerPage="displayPerPage" v-if="displayCustomTable" @closeDisplayCustomTable="displayCustomTable = $event.displayCustomTable;tableHeader = $event.CustomTableSelected;displayPerPage = $event.displayPerPage" @cancelDisplayCustomTable="displayCustomTable = $event"></bugs-custom-table>
    <div class="bugs-table">
        <bugs-table class="tableItem panel" :tableHeaderTitle="tableHeaderTitle" :tableHeader="tableHeader" :displayPerPage="displayPerPage"></bugs-table>
        <button class="bugs-customize" @click="displayCustomTable = true">{{$t('bugsoverview.customize')}}</button>
    </div>
    <div>
        <p style="margin-top:50px">&nbsp;</p>
    </div>
</div>
</template>

<script>
import ECharts from 'vue-echarts'
import 'echarts/lib/chart/pie'
import 'echarts/lib/chart/bar'
import 'echarts/lib/component/legend'
import 'echarts/lib/component/dataset'
import bugsTable from './table'
import bugsCustomPie from './customPie'
import bugsCustomBar from './customBar'
import bugsCustomTable from './customTable'
import top from '../searchvisit/top'

import getPie from './pie'
import getBar from './bar'

export default {
    data() {
        return {
            pie: getPie(),
            bar: getBar(),
            basicHeader: true,
            displayCustomPie: false,
            customPieSelected: undefined,
            displayCustomBar: false,
            customBarSelected: undefined,
            displayCustomTable: false,
            displayPerPage: 30,
            tableHeaderTitle: [
                //need to store to DB
                this.$t('bugsoverview.list.id'),
                this.$t('bugsoverview.list.summary'),
                this.$t('bugsoverview.list.Assignee'),
                this.$t('bugsoverview.list.Reporter'),
                this.$t('bugsoverview.list.Priority'),
                this.$t('bugsoverview.list.Status')
            ],
            tableHeader: [
                //need to store to DB
                'ID',
                'Summary',
                'Assignee',
                'Reporter',
                'Priority',
                'Status'
            ],
            page:'bugReview'
        }
    },
    components: {
        chart: ECharts,
        bugsTable,
        bugsCustomPie,
        bugsCustomBar,
        bugsCustomTable,
        top: top
    },
    computed: {
        chartResize() {
            width = document.querySelector('.bugs-chartContainer').style.width
            consoe.log(width)
        }
    },
    mounted(){
      this.bar.title.text = this.$t('bugsoverview.report.barPriority');
      this.pie.title.text = this.$t('bugsoverview.report.piePriority');
    },
    watch: {
        customPieSelected() {
            this.pie = getPie(this.customPieSelected)
        },
        customBarSelected() {
            this.bar = getBar(this.customBarSelected)
        }
    }
}
</script>

<style scoped>
.panel {
    margin: 0;
    padding: 0;
}

.gridContainer {
    display: grid;
    grid-template-columns: repeat(14, 1fr);
    grid-template-rows: 30px 410px;
    grid-gap: 30px;
}

.gridContainer>div {
    grid-column: 2/14;
}

.bugs-header {
    height: 40px;
    font-family: 'Poppins';
    display: flex;
    align-items: center;
    font-weight: bolder;
    position: relative;
}

.echarts {
    height: 100%;
    width: 100%;
}

.bugs-charts {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    grid-gap: 30px;
}

.bugs-charts>div,
.bugs-table {
    position: relative;
}

.bugs-customize {
    font-family: 'Poppins';
    background-color: white;
    border: solid 1px grey;
    border-radius: 0.4rem;
    font-size: 14px;
}

.bugs-trigger {
    font-family: 'Poppins';
    color: #3b73af;
    text-decoration: underline;
    font-size: 14px;
    margin-left: 15px;
}

.bugs-trigger:hover {
    color: #600;
    text-decoration: underline;
}

input {
    background-color: white;
    border: solid 1px grey;
    border-radius: 0.2rem;
    font-size: 16px;
    padding: 5px;
}

.tableItem {
    padding: 20px 30px;
}

.bugs-generate-report {
    position: absolute;
    right: 0px;
    border-radius: 8px;
    box-shadow: 0 0.46875rem 2.1875rem rgba(90, 97, 105, 0.1),
        0 0.9375rem 1.40625rem rgba(90, 97, 105, 0.1),
        0 0.25rem 0.53125rem rgba(90, 97, 105, 0.12),
        0 0.125rem 0.1875rem rgba(90, 97, 105, 0.1);
    background-color: #7a85a1;
    color: white;
    width: 180px;
    height: 40px;
    border: none;
}

button:hover {
    cursor: pointer;
}

.bugs-customize {
    position: absolute;
    top: 8px;
    right: -20px;
}

.bugs-edit {
    position: absolute;
    margin: auto;
    justify-content: center;
    align-items: center;
}
</style>
