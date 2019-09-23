<template>
<el-row>
    <el-col :span="20" :offset="2">
        <div class="manage-charts">
            <div class="chartContainer" style="text-align:center">
                <div style="display:inline-block">
                    <chart style="display:inline-block" :options="pie" class="panel" />
                </div>
            </div>
            <div class="chartContainerRight">
                <div style="display:inline-block">
                    <chart :options="rect" class="panel" />
                </div>
            </div>
        </div>
        <div>
            <el-card class="listHead" shadow="never" style="padding-right:5%">
                <h2 style="float:left;margin:0 0 0 80px;line-height:60px">Testcase List</h2>
                <el-button @click="add" style="float:right;margin:10px 80px 0 0;background-color:#7a85a1" type="info" size="med">
                    + Import Testcase
                </el-button>
            </el-card>
        </div>
        <el-card shadow="hover" style="border:none">
            <el-table :data="dataList" @selection-change="handleSelectionChange" style="width: 100%;height: 100%;text-align:center">
                <el-table-column v-for="item in tableColownms" :key="item.id" :prop="item.field" sortable :label="item.label">
                    <template slot-scope="scope">
                        <div v-if="item.field!='detailSteps'">
                            {{scope.row[item.field]}}
                        </div>
                        <div v-if="item.field=='detailSteps'">
                            <el-button type="text" @click="stepVisible = true">Steps</el-button>
                            <el-dialog title="Steps Detail" :visible.sync="stepVisible" width="30%" :before-close="handleClose">
                                {{scope.row[item.field]}}
                                <span slot="footer" class="dialog-footer">
                                    <el-button type="primary" @click="stepVisible = false">OK</el-button>
                                </span>
                            </el-dialog>
                        </div>
                    </template>
                </el-table-column>
            </el-table>
        </el-card>
        <div class="fanye">
            <el-pagination background layout="total, sizes, prev, pager, next, jumper" :total="dataList.length" :page-sizes="[20, 30, 40, 50, 100, 500]" :page-size="pageSize" style="text-align:center;margin: 10px 0" @current-change="currentChange" @size-change="sizeChange"></el-pagination>
        </div>
        <div style="height:100px;width:100%;background:white"></div>
        <el-dialog title="Import" :visible.sync="centerDialogVisible" width="30%" center>
            <el-upload class="upload-demo" :on-success="uploadSuccess" ref="upload" :action="`/api/result/upload?token=1&testSetId=${this.testSetId}`" :on-preview="handlePreview" :on-remove="handleRemove" :file-list="fileList" :auto-upload="false">
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
            centerDialogVisible:false
        }
    },
    components: {
        testsetList: testsetList,
        chart: ECharts
    },
    mounted() {
        document.getElementsByClassName('echarts')[0].style.width = document.body.clientWidth * (11 / 27) + 'px';
        document.getElementsByClassName('echarts')[1].style.width = document.body.clientWidth * (11 / 27) + 'px';
    },
    created() {
        this.productId = this.$route.query.productId;
        this.releaseId = this.$route.query.releaseId;
        this.testsetId = this.$route.query.testsetId;
        this.Fetch(`/testmgmt/getTestCaseList?token=1&testSetId=${this.testsetId}`, {
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
            this.centerDialogVisible=true;
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
            this.Fetch(`/testmgmt/getTestCaseList?token=1&testSetId=${this.testsetId}`, {
                method: "GET",
            }).then(res => {
                console.log(111111)
            });
        }
    },
}
</script>

<style lang="stylus">
.manage-charts {
    width: 100%;
}

.panel {
    margin: 0;
    padding: 0;
}

.chartContainer {
    width: 48%;
    float: left;
    text-align: center;
}

.chartContainerRight {
    width: 47%;
    float: left;
    margin-left 2.3%;
    text-align: center;
}

.has-gutter th .cell {
    font-family Poppins
}
</style>
