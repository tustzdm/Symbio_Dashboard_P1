<template>
    <div class="car-root">
        <el-card shadow="hover" style="margin-bottom:10px" :body-style="{padding: '10px'}">
            <el-dropdown @command="handleCommand" class="el-dropdown-custom" trigger="click">
                <span class="el-dropdown-link">{{proTitle}}<i class="el-icon-arrow-down el-icon--right"></i></span>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item v-for="project in projectInfo" :command="['p', project]" :key="project.key">{{project.value}}</el-dropdown-item>
                    <el-dropdown-item :command="['add', '/addproject']" v-if="projectInfo.length>0">Add Project</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
            <el-dropdown @command="handleCommand" class="el-dropdown-custom" trigger="click">
                <span class="el-dropdown-link">{{verTitle}}<i class="el-icon-arrow-down el-icon--right"></i></span>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item v-for="version in versionInfo" :command="['v', version]" :key="version.key">{{version.value}}</el-dropdown-item>
                    <el-dropdown-item :command="['add', '/addversion']" v-if="versionInfo.length>0">Add Version</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
            <el-dropdown @command="handleCommand" class="el-dropdown-custom" trigger="click">
                <span class="el-dropdown-link">{{caseTitle}}<i class="el-icon-arrow-down el-icon--right"></i></span>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item v-for="caseItem in caseInfo" :command="['c', caseItem]" :key="caseItem.key">{{caseItem.value}}</el-dropdown-item>
                    <el-dropdown-item :command="['add', '/addcase']" v-if="caseInfo.length>0">Add Case</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
             <el-select v-model="chartGroup" placeholder="请选择" @change="changeHandle">
                <el-option v-for="item in listCombox" :key="item.key" :label="item.name" :value="item.key"></el-option>
            </el-select>
        </el-card>
        <template v-if="chartGroup=='common'">
            <h5>图形统计</h5>
            <el-card class="echart-wapper" shadow="hover" :body-style="{'display': 'flex'}">
                <v-chart :options="item" auto-resize class="echarts-bar" v-for="item in listCharts" :key="item.index"></v-chart>
            </el-card>

            <h5>列表统计</h5>
            <el-card shadow="hover" v-for="item in listList" :key="item.key">
                <el-table :data="item.data.data" max-height="350" border style="width: 100%;background-color:rgb(240,240,240)" :stripe="true">
                    <el-table-column  v-for="columns in item.data.columns" :key="columns.key" :label="columns.label" :prop="columns.field">
                       
                    </el-table-column>
                    
                </el-table>
                <el-pagination background layout="total, sizes, prev, pager, next, jumper" 
                    :total="item.data.totalRecord" 
                    :page-sizes="[10, 20, 30, 40, 50]"
                    :page-size="item.data.pageSize"
                    style="text-align:center;margin: 10px 0"
                    @current-change="currentChange"
                    @size-change="sizeChange">
                </el-pagination> 
            </el-card>

            <h5>图形统计</h5>
            <el-card class="echart-wapper" shadow="hover" :body-style="{'display': 'flex'}">
                <v-chart :options="item" auto-resize class="echarts-bar" v-for="item in listRowCharts" :key="item.index"></v-chart>
            </el-card>
        </template>
       
       
    </div>
</template>

<script>
import areaData from 'china-area-data'
import ECharts from 'vue-echarts/components/ECharts'
import 'echarts/lib/chart/bar'
import 'echarts/map/js/world'
import stackedLine from './stackedLine'
import barLabRotation from './barLabRotation'
import mixLineBar from './mixLineBar'
import echartBarOpt from './echartBarOpt'
import echartPieOpt from './echartPieOpt'
import echartLineOpt from './echartLineOpt'
import echartRadarOpt from './echartRadarOpt'
import echartGaugeOpt from './echartGaugeOpt'
import { getCarCount, getProjectInfo } from '@/api/index'
import storage from "@/utils/storage"
export default {
  name: 'index',
  data() {
    return {
      provList: areaData[86],
      cityList: [],
      pageSize: 10,
      currentPage: 0,
      xAxisData: [],
      echartTitle: "",
      echartBarOpt: {},
      echartPieOpt: {},
      echartLineOpt: {},
      echartRadarOpt: {},
      echartGaugeOpt: {},
      dataList: [],
      chartGroup: 'common',
      proTitle: "Project",
      verTitle: "Release",
      caseTitle: "Testset",
      projectInfo: [],
      versionInfo: [],
      caseInfo: [],
      listCharts:[],
      listCombox:[],
      listList:[],
      listRowCharts:[]
    }
  },
  mounted() {
    this.searchForm();
    this.initProjet();
  },
  methods: {
      changeHandle(level) {
          this.searchByFilter();
      },
    searchByFilter() {
        let params = {
            pageSize: this.pageSize,
            currentPage: this.currentPage
        }
        getCarCount(params).then(res => {
            let data = [];
            Object.values(this.xAxisData).map((item,i) => {
                let el = {
                        name: item,
                        value: Object.values(res.data)[i]
                    }
                data.push(el)
            });
            this.dataList = data;
            console.log(this.chartGroup);
            for(let i=0;i<this.listCombox.length;i++){
                console.log(this.listCombox[i].condition);
                if(this.chartGroup == this.listCombox[i].key){
                  if(typeof(this.listCombox[i].condition)!="undefined"){
                        this.projectInfo = this.listCombox[i].condition[0].data;
                        this.proTitle = this.listCombox[i].condition[0].name;
                        this.versionInfo = this.listCombox[i].condition[1].data;
                        this.verTitle = this.listCombox[i].condition[1].name;
                        this.caseInfo = this.listCombox[i].condition[2].data;
                        this.caseTitle =this.listCombox[i].condition[2].name;
                  }else{
                       this.projectInfo = [];
                        this.versionInfo = [];
                        this.caseInfo = [];
                  }
                }
            }
        });
      },
      currentChange(currentPage) {
          this.currentPage = currentPage;
          this.searchByFilter();
      },
      sizeChange(size) {
          this.pageSize = size;
          this.searchByFilter();
      },
      searchForm() {
          this.xAxisData = Object.values(this.provList);
          this.searchByFilter();
      },
      initProjet() {
          getProjectInfo().then(res => {//下拉控件
              console.log("下拉控件");
              console.log(res.cd.listCombox);
               this.listCombox = res.cd.listCombox;
            //    for(let i= 0;i<res.cd.listCombox.length;i++){
            //        this.listCombox.push(res.cd.listCombox[i]);
            //   }
              console.log(this.listCombox);
              //一般控件
              for(let i= 0 ;i<res.cd.listCharts.length;i++){
                  if(res.cd.listCharts[i].key=="StackedLine"){
                    this.listCharts.push(stackedLine(res.cd.listCharts[i].data));
                  }
                  if(res.cd.listCharts[i].key=="BarLabRotation"){
                     this.listCharts.push(barLabRotation(res.cd.listCharts[i].data.data));
                  }
              }
              //列表控件
              for(let i=0;i<res.cd.listList.length;i++){
                   this.listList.push(res.cd.listList[i]);
              }
             
              //行控件
              for(let i=0;i<res.cd.listRowCharts.length;i++){
                  if(res.cd.listRowCharts[i].key=="MixLineBar"){
                      console.log(mixLineBar(res.cd.listRowCharts[i].data,res.cd.listRowCharts[i].seriesData));
                    this.listRowCharts.push(mixLineBar(res.cd.listRowCharts[i].data));
                  }
              }
          });
      },
      handleCommand(command) {
        let [type, data] = command;
        if (type == 'add') {
            this.$router.push({path: data, query: {type: 'add'}});
            return;
        }
      },
      handleItem(type, path, data) {
          if (path == '/addproject') {
              storage.set("curProject", data);
          }
          if (path == '/addversion') {
              storage.set("curVersion", data);
          }
          if (path == '/addcase') {
              storage.set("curCase", data);
          }
        this.$router.push({path: path, query: {type: type}});
      }
  },
  components: {
    'v-chart': ECharts
  }
}
</script>

<style lang="stylus" scoped>
.echarts-bar
    flex 1
.echarts-pie
    flex 1
.car-root
    h5 
        color #777 
.el-form-item
    display inline-block
    margin 0
.el-dropdown-custom
    height 32px
    line-height 32px
    min-width 80px
    text-align center 
    margin-right 50px
    background #ff8400
    position relative
    color #ffffff
    &:first-child
        min-width 100px
    &:after
        position absolute
        right -32px
        top 0
        content ""
        width 0
        height 0
        border 16px solid transparent
        border-left-color #ff8400
    &:not(:first-child):before
        position absolute
        left -32px
        top 0
        content ""
        width 0
        height 0
        border 16px solid #ff8400
        border-left-color #ffffff 
.el-dropdown-link 
    cursor pointer
.pro-name
    margin-right 10px
.el-icon-custom
    padding 0 5px
</style>
