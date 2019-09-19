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
            <h2 style="float:left;margin:0 0 0 80px;line-height:60px">Testset List</h2>
            <el-button @click="add" style="float:right;margin:10px 80px 0 0;background-color:#7a85a1" type="info" size="med">
                + Add Testset
            </el-button>
        </el-card>
    </div>
    <testsetList></testsetList>
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
            productId:'',
            releaseId:''
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
    created(){
        this.productId =this.$route.query.productId;
        this.releaseId =this.$route.query.releaseId;
    },
    methods: {
        initProjet() {
            getProjectInfo().then(res => {
                this.projectInfo = res.data
            })
        },
        add() {
            this.$router.push({
                path: '/addproject/index',
                name: 'addproject',
                query: {
                    pageType: 'TestSet',
                    productId: this.productId,
                    releaseId: this.releaseId
                }
            })
        }
    },
}
</script>

<style lang="stylus" scoped>
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
</style>
