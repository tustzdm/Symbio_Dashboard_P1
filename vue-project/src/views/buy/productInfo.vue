<template>
<el-row class="infoCon">
    <el-col :span="20" :offset="2">
        <div class="manage-charts">

            <chart style="display:inline-block" :options="pie" class="panel" />

            <chart :options="rect" class="panel" />

        </div>

        <releaseList ref="listChild"></releaseList>
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
    mounted() {
        console.log(1111111111)
        console.log(this.$refs.listChild);
    },
    methods: {
        initProjet() {
            getProjectInfo().then(res => {
                this.projectInfo = res.data
            })
        }
    },
}
</script>

<style lang="stylus">

</style>
