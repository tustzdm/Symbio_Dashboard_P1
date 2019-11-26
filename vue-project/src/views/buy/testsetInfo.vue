<template>
<el-row class="infoCon">
    <el-col :span="20" :offset="2">
        <div class="manage-charts">

            <chart :options="pie" class="panel" />

            <chart :options="rect" class="panel" />

        </div>
        <div>
            <el-card class="listHead" shadow="never" style="padding-right:5%">
                <h2 style="float:left;margin:0 0 0 80px;line-height:60px">Test Case List</h2>
                <el-button @click="add" style="float:right;margin:10px 80px 0 0;background-color:#7a85a1" type="info" size="med">
                    + Import Test Case
                </el-button>
            </el-card>
        </div>
        <el-card shadow="hover" style="border:none">
            <el-table :data="dataList" @selection-change="handleSelectionChange" style="width: 100%;height: 100%;text-align:center">
                <el-table-column v-for="item in tableColownms" :key="item.id" :prop="item.field" :width="['caseId', 'caseType'].includes(item.field )? '150px':['priority'].includes(item.field )? '120px':''" sortable :label="item.label">
                    <template slot-scope="scope">
                        <div v-if="!['detailSteps','caseType','priority'].includes(item.field)">
                            {{scope.row[item.field]}}
                        </div>
                        <span v-if="item.field=='priority'" style="border-radius:3px;padding:2px 6px;margin:10px" :class="scope.row[item.field]">
                            {{scope.row[item.field]}}
                        </span>
                        <div v-if="item.field=='caseType'">
                            {{caseTypeList[scope.row[item.field]]}}
                        </div>
                        <div v-if="item.field=='detailSteps'">
                            <el-button type="text" @click="stepVisible = true;stepContent=JSON.parse(scope.row[item.field])">Steps</el-button>
                        </div>
                    </template>
                </el-table-column>
            </el-table>
        </el-card>
        <div class="fanye">
            <el-pagination background layout="total, sizes, prev, pager, next, jumper" :total="dataList.length" :page-sizes="[20, 30, 40, 50, 100, 500]" :page-size="pageSize" style="text-align:center;margin: 10px 0" @current-change="currentChange" @size-change="sizeChange"></el-pagination>
        </div>
        <div style="height:100px;width:100%;background:white"></div>

        <el-dialog id="stepCon" title="Steps Detail" :visible.sync="stepVisible" width="30%">
            <!-- {{JSON.parse(scope.row[item.field])}} -->
            <ul>
                <li v-for="item in stepContent" :key="item">{{item}}</li>
            </ul>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="stepVisible = false;">OK</el-button>
            </span>
        </el-dialog>

        <el-dialog title="Import" :visible.sync="centerDialogVisible" width="30%" center>
            <el-upload class="upload-demo" :on-success="uploadSuccess" ref="upload" :action="`/api/result/upload?token=${this.token}&testSetId=${this.testsetId}`" :on-preview="handlePreview" :on-remove="handleRemove" :file-list="fileList" :auto-upload="false">
                <el-button slot="trigger" size="small" type="primary">Choose File</el-button>
                <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">Upload</el-button>
                <div slot="tip" class="el-upload__tip" style="text-align:center;font-size:16px">.excel file only, less than 500m</div>
            </el-upload>
            <span slot="footer" class="dialog-footer">
                <el-button @click="centerDialogVisible = false">cancel</el-button>
                <el-button type="primary" @click="centerDialogVisible = false">Confirm</el-button>
            </span>
        </el-dialog>
    </el-col>
</el-row>
</template>

<script>
import ECharts from 'vue-echarts'
import 'echarts/lib/chart/pie'
import 'echarts/lib/chart/bar'
import 'echarts/lib/component/legend'
import 'echarts/lib/component/dataset'
import getPie from './pie'
import getRect from './rect'
import {
    getTestManager,
    getProjectInfo
} from '@/api/index'
import storage from '@/utils/storage'
import testsetList from './testsetList'
export default {
    data() {
        return {
            pie: getPie(),
            rect: getRect(),
            productId: '',
            releaseId: '',
            testsetId: '',
            dataList: [],
            tableColownms: {},
            stepVisible: false,
            centerDialogVisible: false,
            caseTypeList: ['', 'Automation TestCase', 'Manual TestCase', '', 'API TestCase', '', '', '', 'Performance TestCase'],
            stepContent: {},
            index: 0,
            token:''
        }
    },
    components: {
        testsetList: testsetList,
        chart: ECharts
    },
    mounted() {},
    created() {
        this.token=localStorage.getItem('token');
        this.productId = this.$route.query.productId;
        this.releaseId = this.$route.query.releaseId;
        this.testsetId = this.$route.query.testsetId;
        this.Fetch(`/testmgmt/getTestCaseList?token=${localStorage.getItem('token')}&testSetId=${this.testsetId}`, {
            method: "GET",
        }).then(res => {
            this.dataList = res.cd.data;
            console.log(this.dataList)
            this.tableColownms = res.cd.columns;
            console.log(this.tableColownms)
        });
    },
    methods: {
        initProjet() {
            getProjectInfo().then(res => {
                this.projectInfo = res.data
            })
        },
        add() {
            this.centerDialogVisible = true;
            // this.$router.push({
            //     path: '/addproject/index',
            //     name: 'addproject',
            //     query: {
            //         pageType: 'TestSet',
            //         productId: this.productId,
            //         releaseId: this.releaseId
            //     }
            // })
        },
        getTestCases() {
            this.Fetch(`/testmgmt/getTestCaseList?token=${localStorage.getItem('token')}&testSetId=${this.testsetId}`, {
                method: "GET",
            }).then(res => {
                this.dataList = res.cd.data;
                console.log(this.dataList)
                this.tableColownms = res.cd.columns;
                console.log(this.tableColownms)
            });
        },

        //import method
        submitUpload() {
            this.$refs.upload.submit();
        },
        handleRemove(file, fileList) {
            console.log(file, fileList);
        },
        handlePreview(file) {
            console.log(file);
        },
        uploadSuccess(res) {
            console.log(res);
            this.centerDialogVisible = false;
            console.log(res)
            if (res.ec == 0) {
                this.$message({
                    message: 'Import Success',
                    type: 'success'
                });

            } else {
                this.$message({
                    message: `Error Code:${res.data.ec}, Error Message:"${res.data.em}`,
                    type: 'warning'
                });
            }
            this.getTestCases();
        },
        getStepContent() {
            document.getElementById('stepCon').innerHTML = val;
        }
    },
}
</script>

<style lang="stylus">

.has-gutter th .cell {
    font-family Poppins
}

.P0{
    color:white
    background: #FF8247
}
.P1{
    color:white
    // #f9e8e0
    background: #FFAEB9
}
.P2{
    color:white
    background-color: #8968CD
}
.P3{
    color:white
    background-color: #7a85a1
}
.P4{
    color:white
    background-color: #9AC0CD
}
</style>
