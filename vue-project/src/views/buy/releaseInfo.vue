<template>
<el-row class="infoCon">
    <el-col :span="20" :offset="2">
        <div class="manage-charts">

            <chart style="display:inline-block" :options="pie" class="panel" />

            <chart :options="rect" class="panel" />

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
            productId: '',
            releaseId: ''
        }
    },
    components: {
        testsetList: testsetList,
        chart: ECharts
    },
    mounted() {
    },
    created() {
        this.productId = this.$route.query.productId;
        this.releaseId = this.$route.query.releaseId;
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
</style>
