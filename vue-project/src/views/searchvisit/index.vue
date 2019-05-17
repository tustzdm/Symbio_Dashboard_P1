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
    <el-card shadow="hover" style="margin-bottom:10px" :body-style="{padding: '10px'}">
        <el-button type="danger" size="small" @click="handleBtn('failed')">
            <i class="el-icon-filter el-icon--left"></i>
            Failed
        </el-button>
        <el-button type="danger" size="small" @click="handleBtn('comments')">
            <i class="el-icon-filter el-icon--left"></i>
            Comments
        </el-button>
        <div class="filterbar">
            <el-select v-model="select1" placeholder="Please Select" size="small" class="el-select-custom mg-l" @change="handleSelect">
                <el-option label="Feedback" value="feedback"></el-option>
            </el-select>
            <el-button type="primary" size="small" icon="el-icon-view" class="mg-l" @click="handleBtn('info')">Info</el-button>
            <el-button type="warning" size="small" icon="el-icon-plus" class="mg-l" @click="handleBtn('add')">Add New</el-button>
            <el-button type="primary" size="small" icon="el-icon-upload2" class="mg-l" @click="handleBtn('upload')">Upload</el-button>
            <el-button type="danger" size="small" icon="el-icon-delete" class="mg-l" @click="handleBtn('del')">Delete</el-button>
        </div>
    </el-card>
    <el-card shadow="hover" style="margin-bottom:10px">
        <el-table :data="dataList" height="300" border style="width: 100%;background-color:rgb(240,240,240)" :stripe="true" ref="multipleTable" tooltip-effect="dark" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column type="index"  label="ID" ></el-table-column>
            <el-table-column prop="sourceScreen" label="Source Screen" >
                <template slot-scope="scope">
                    <img :src="scope.row.sourceScreen" alt="" class="el-img-custom">
                    <el-upload class="el-upload-custom" :action="uploadUrl">
                        <el-button size="mini" type="primary" class="el-btn-update" v-if="scope.row.sourceScreen">Update</el-button>
                        <el-button size="mini" type="primary" class="el-btn-update" v-else>Upload</el-button>
                    </el-upload>
                </template>
            </el-table-column>
            <el-table-column prop="targetScreen" label="Target Screen" >
                <template slot-scope="scope">
                    <img :src="scope.row.targetScreen" alt="" class="el-img-custom">
                    <el-upload class="el-upload-custom" :action="uploadUrl">
                        <el-button size="mini" type="primary" class="el-btn-update" v-if="scope.row.targetScreen">Update</el-button>
                        <el-button size="mini" type="primary" class="el-btn-update" v-else>Upload</el-button>
                    </el-upload>
                </template>
            </el-table-column>
            <el-table-column prop="sid" label="SID/Steps" >
                <template slot-scope="scope">
                    <span v-if="!scope.row.editFlag">{{ scope.row.sid }}</span>
                    <span v-if="scope.row.editFlag" class="cell-edit-input"><el-input v-model="record.sid" placeholder="SID/Steps"></el-input></span>
                </template>
            </el-table-column>
            <el-table-column prop="lqacomments" label="LQA Comments" >
                <template slot-scope="scope">
                    <span v-if="!scope.row.editFlag">{{ scope.row.lqacomments }}</span>
                    <span v-if="scope.row.editFlag" class="cell-edit-input"><el-input v-model="record.lqacomments" placeholder="LQA Comments"></el-input></span>
                </template>
            </el-table-column>
            <el-table-column prop="ls" label="LS Review" >
                <template slot-scope="scope">
                    <span v-if="!scope.row.editFlag">{{ scope.row.ls }}</span>
                    <span v-if="scope.row.editFlag" class="cell-edit-input"><el-input v-model="record.ls" placeholder="LS Review"></el-input></span>
                </template>
            </el-table-column>
            <el-table-column prop="status" label="Status" >
                <template slot-scope="scope">
                    <span v-if="!scope.row.editFlag">{{ scope.row.status }}</span>
                    <span v-if="scope.row.editFlag" class="cell-edit-input"><el-input v-model="record.status" placeholder="Status"></el-input></span>
                </template>
            </el-table-column>
            <el-table-column prop="jiraticket" label="JIRA Ticket" >
                 <template slot-scope="scope">
                     <span v-if="!scope.row.editFlag">{{ scope.row.jiraticket }}</span>
                    <span v-if="scope.row.editFlag" class="cell-edit-input"><el-input v-model="record.jiraticket" placeholder="JIRA Ticket"></el-input></span>
                </template>
            </el-table-column>
            <el-table-column label="Operate">
                <template slot-scope="scope">
                    <el-button size="mini" type="primary" @click="handleEdit(scope.$index, scope.row)" class="el-icon-view" v-if="!scope.row.editFlag"></el-button>
                    <el-button size="mini" type="danger" @click="deleteRecords(scope.$index, scope.row)" class="el-icon-delete" v-if="!scope.row.editFlag"></el-button>
                    <el-button size="mini" type="primary" @click="handleBtn('save')" class="el-icon-check" v-else></el-button>
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
import { getReviewPage, getProjectInfo, delRecords, saveRecords, uploadUrl } from '@/api/index'
import storage from "@/utils/storage"
let record = {"sourceScreen": "","targetScreen": "","sid": "","lqacomments": "","ls": "","status": "","jiraticket": "","editFlag": true};
export default {
  name: "searchvisit",
  data() {
    return {
      uploadUrl: uploadUrl,
      projectInfo: [],
      versionInfo: [],
      caseInfo: [],
      proTitle: "项目",
      verTitle: "版本",
      caseTitle: "情况",
      ticketname: "",
      select1: 'feedback',
      filter: "",
      dataList: [],
      pageSize: 10,
      currentPage: 0,
      multipleSelection: [],
      record: record
    }
  },
  components: {},
  mounted() {
      this.initProjet();
      this.getReviewPageInfo();
  },
  methods: {
      initProjet() {
          getProjectInfo().then(res => {
              this.projectInfo = res.data;
          });
      },
      getReviewPageInfo() {
          let params = {
                ticketname: this.ticketname,
                qastatus: this.qastatus,
                filter: this.filter,
                select1: this.select1,
                pageSize: this.pageSize,
                currentPage: this.currentPage
          };
          getReviewPage(params).then(res => {
              this.dataList = res.data;
          });
      },
      handleBtn(type) {
          switch (type) {
              case 'add':
                  this.addRecords();
                  break;
              case 'del':
                  this.deleteRecords();
                  break;
              case 'save':
                  this.saveRecords();
                  break;
              case 'failed':
              case 'comments':
                  this.filter = type;
                  this.getReviewPageInfo();
                  break;
              default:
                  break;
          }
      },
      addRecords() {
          this.dataList.push(this.record);
      },
      saveRecords() {
          saveRecords(this.record).then(res => {
              if (res.code == 200) {
                  this.$message.success("保存成功！");
                  this.record = record;
              }
          });
          
      },
      deleteRecords(index, row) {
          if (row) {
              this.multipleSelection = [row.id];
          }
          if(this.multipleSelection.length <= 0) {
              this.$message.error("请选择一行！");
              return;
          }
          this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                delRecords(this.multipleSelection).then(res => {
                    if (res.code == 200) {
                        this.$message.success("删除成功！");
                    }
                });
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });          
            });
      },
      handleSelect() {
          this.getReviewPageInfo();
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
          this.getReviewPageInfo();
      },
      sizeChange(size) {
          this.pageSize = size;
          this.getReviewPageInfo();
      },
      handleEdit() {

      },
      handleSelectionChange(val) {
          let seleted = [];
          if(val && val.length > 0) {
              val.map(item => {
                  seleted.push(item.id);
              });
          } else {
              this.multipleSelection = [];
          }
          this.multipleSelection = seleted;
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
    width 100px
    height 80px
.filterbar
    float right
.el-upload-custom
    position relative
    & > div.el-upload--picture
        position absolute !important
        bottom 0
        right 0
        z-index 1
.el-icon-filter
    width 12px
    height 12px
    background url('../../assets/images/filter.png') no-repeat
.el-button--warning
    background #ff8400
    border-color #ff8400
</style>
