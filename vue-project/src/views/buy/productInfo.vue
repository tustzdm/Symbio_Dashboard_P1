<template>
<div class="buy-root" style="width:85%;margin-left:7.5%">
    <div class="manage-charts">
        <div class="chartContainer" style="text-align:center">
            <div style="display:inline-block">
                <chart style="display:inline-block" :options="pie" class="panel" />
            </div>
        </div>
        <div class="chartContainer">
            <div style="display:inline-block">
                <chart :options="rect" class="panel" />
            </div>
        </div>
    </div>
    <div>
        <el-card class="listHead" shadow="never" style="padding-right:5%">
            <h2 style="float:left;margin:0 0 0 80px;line-height:60px">Release List</h2>
            <el-button @click="add" style="float:right;margin:10px 80px 0 0;background-color:#7a85a1" type="info" size="med">
                + Add Release 
            </el-button>
        </el-card>
    </div>
    <releaseList></releaseList>
</div>
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
import releaseList from './releaseList'
export default {
    data() {
        return {
            pie: getPie(),
            rect: getRect(),
            productId:''
        }
    },
    components: {
        releaseList: releaseList,
        chart: ECharts
    },
    created(){
        this.productId =this.$route.params.productId;
    },
    mounted() {},
    methods: {
        initProjet() {
            getProjectInfo().then(res => {
                this.projectInfo = res.data
            })
        },
        add() {//通过这个传给 add页面要取的值的类型比如product release
            this.$router.push({
                path: '/addproject/index',
                name: 'addproject',
                params: {
                    pageType: 'Release',
                    productId: this.productId
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

.chartContainer {
    width: 50%;
    float: left;
    text-align: center;
}

.buy-root {
    height: 100%;
    display: flex;
    flex-direction: column;
}
</style>
