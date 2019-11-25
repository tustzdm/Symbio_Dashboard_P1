<template>
<el-row class="infoCon">
    <el-col :span="20" :offset="2">
        <div class="manage-charts">

            <chart style="display:inline-block" :options="pie" class="panel" />

            <chart :options="rect" class="panel" />

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
import releaseList from './releaseList'
export default {
    data() {
        return {
            pie: getPie(),
            rect: getRect(),
            productId: ''
        }
    },
    components: {
        releaseList: releaseList,
        chart: ECharts
    },
    created() {
        this.productId = this.$route.query.productId;
    },
    mounted() {},
    methods: {
        initProjet() {
            getProjectInfo().then(res => {
                this.projectInfo = res.data
            })
        },
        add() { //通过这个传给 add页面要取的值的类型比如product release
            this.$router.push({
                path: '/addproject/index',
                name: 'addproject',
                query: {
                    pageType: 'Release',
                    productId: this.productId
                }
            })
        }
    },
}
</script>

<style lang="stylus">

</style>
