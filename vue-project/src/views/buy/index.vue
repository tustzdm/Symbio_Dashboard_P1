<template>
  <div class="buy-root">
    <el-card shadow="hover" style="margin-bottom:10px" :body-style="{padding: '10px'}">
        <el-dropdown @command="handleCommand" class="el-dropdown-custom" trigger="click">
            <span class="el-dropdown-link">{{proTitle}}<i class="el-icon-arrow-down el-icon--right"></i></span>
            <el-dropdown-menu slot="dropdown">
                <el-dropdown-item v-for="project in projectInfo" :command="['p', project]" :key="project.id">{{project.name}}</el-dropdown-item>
                <el-dropdown-item :command="['add', '/addproject']" v-if="projectInfo.length>0">Add Project</el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
        <el-dropdown @command="handleCommand" class="el-dropdown-custom" trigger="click">
            <span class="el-dropdown-link">{{verTitle}}<i class="el-icon-arrow-down el-icon--right"></i></span>
            <el-dropdown-menu slot="dropdown">
                <el-dropdown-item v-for="version in versionInfo" :command="['v', version]" :key="version.id">{{version.name}}</el-dropdown-item>
                <el-dropdown-item :command="['add', '/addversion']" v-if="versionInfo.length>0">Add Version</el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
        <el-dropdown @command="handleCommand" class="el-dropdown-custom" trigger="click">
            <span class="el-dropdown-link">{{caseTitle}}<i class="el-icon-arrow-down el-icon--right"></i></span>
            <el-dropdown-menu slot="dropdown">
                <el-dropdown-item v-for="caseItem in caseInfo" :command="['c', caseItem]" :key="caseItem.id">{{caseItem.name}}</el-dropdown-item>
                <el-dropdown-item :command="['add', '/addcase']" v-if="caseInfo.length>0">Add Case</el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
        <el-input v-model="ticketname" placeholder="search by ticket name" style="width:20%">
            <el-button slot="append" icon="el-icon-search"></el-button>
        </el-input>
    </el-card>
    <el-card shadow="hover" style="margin-bottom:10px">
        <el-button type="success" size="small" @click="handleBtn('passed')">PASSED</el-button>
        <el-button type="danger" size="small" @click="handleBtn('failed')">FAILED</el-button>
        <el-button type="primary" size="small" @click="handleBtn('notrun')">NOTRUN</el-button>
        <el-button type="info" size="small" @click="handleBtn('')">Clear</el-button>
        <el-select v-model="select1" placeholder="Please Select" size="small" class="el-select-custom mg-l" @change="handleSelect">
            <el-option label="All" value="all"></el-option>
        </el-select>
        <el-select v-model="select2" placeholder="Please Select" size="small" class="el-select-custom mg-l" @change="handleSelect">
            <el-option label="QAStatus" value="qastatus"></el-option>
        </el-select>
        <el-select v-model="select3" placeholder="Please Select" size="small" class="el-select-custom mg-l" @change="handleSelect">
            <el-option label="Columns" value="col"></el-option>
        </el-select>
        <el-button type="warning" size="small" icon="el-icon-refresh" class="mg-l" @click="handleBtn('refresh')">Refresh</el-button>
    </el-card>
    <el-card shadow="hover" style="margin-bottom:10px">
        <el-table :data="dataList" max-height="300" border style="width: 100%;background-color:rgb(240,240,240)" :stripe="true">
            <el-table-column prop="caseId"  label="Case ID" ></el-table-column>
            <el-table-column prop="autoStatus" label="Auto Status" ></el-table-column>
            <el-table-column prop="featureArea" label="Feature Area" ></el-table-column>
            <el-table-column prop="featureSubArea" label="Feature SubArea" ></el-table-column>
            <el-table-column prop="qastatus" label="QA Status" ></el-table-column>
            <el-table-column prop="summury" label="Summary" ></el-table-column>
            <el-table-column prop="locale" label="Locale" ></el-table-column>
            <el-table-column prop="screenshot" label="ScreenShot" >
                <template slot-scope="scope">
                    <img :src="scope.row.screenshot" class="el-img-custom">
                </template>
            </el-table-column>
        </el-table>
        <el-pagination background layout="total, sizes, prev, pager, next, jumper" 
            :total="dataList.length" 
            :page-sizes="[10, 20, 30, 40, 50]"
            :page-size="pageSize"
            style="text-align:center;margin: 10px 0"
            @current-change="currentChange"
            @size-change="sizeChange"></el-pagination> 
    </el-card>
  </div>
</template>
<script>
import { getTestManager, getProjectInfo } from '@/api/index'
import storage from "@/utils/storage"
export default {
  data() {
    return {
      projectInfo: [],
      versionInfo: [],
      caseInfo: [],
      proTitle: "项目",
      verTitle: "版本",
      caseTitle: "情况",
      ticketname: "",
      autoStatus: "",
      qastatus: "",
      columns: "",
      select1: 'all',
      select2: 'qastatus',
      select3: 'col',
      dataList: [],
      pageSize: 10,
      currentPage: 0
    }
  },
  components: {},
  mounted() {
      this.initProjet();
      this.getTestManagerInfo();
  },
  methods: {
      initProjet() {
          getProjectInfo().then(res => {
              this.projectInfo = res.data;
          });
      },
      getTestManagerInfo() {
          let params = {
                autoStatus: this.autoStatus,
                qastatus: this.qastatus,
                columns: this.columns,
                select1: this.select1,
                select2: this.select2,
                select3: this.select3,
                pageSize: this.pageSize,
                currentPage: this.currentPage
          };
          getTestManager(params).then(res => {
              this.dataList = res.data;
          });
      },
      handleBtn(type) {
          if (type != 'refresh') {
            this.autoStatus = type;
          }
          this.getTestManagerInfo();
      },
      handleSelect() {
          this.getTestManagerInfo();
      },
      handleCommand(command) {
        let [type, data] = command;
        if (type == 'add') {
            this.$router.push({path: data, query: {type: 'add'}});
            return;
        }
        if (type == 'p') {
            this.versionInfo = data.children;
            storage.set("curProject", data);
            this.proTitle = data.name;
        }
        if (type == 'v') {
            this.caseInfo = data.children;
            storage.set("curVersion", data);
            this.verTitle = data.name;
        }
        if (type == 'c') {
            this.caseTitle = data.name;
            storage.set("curCase", data);
        }
      },
      currentChange(currentPage) {
          this.currentPage = currentPage;
          this.getTestManagerInfo();
      },
      sizeChange(size) {
          this.pageSize = size;
          this.getTestManagerInfo();
      }
  },
}
</script>

<style lang="stylus" scoped>
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
.el-select-custom
    max-width 150px
.mg-l
    margin-left 10px
.el-img-custom
    width 64px
    height 64px
.el-button--warning
    background #ff8400
    border-color #ff8400
</style>
