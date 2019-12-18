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
// import getPie from './pie'
// import getRect from './rect'
// import {
//     getTestManager,
//     getProjectInfo
// } from '@/api/index'
import storage from '@/utils/storage'
import releaseList from './releaseList'
export default {
    data() {
        return {
            pie: {},
            rect: {},
            productId: '',
            lang:''
        }
    },
    components: {
        releaseList: releaseList,
        chart: ECharts
    },
    created() {
        this.productId = this.$route.query.productId;
        this.lang = this.$store.state.app.language=='zh'? 'zh_CN':'en_US';
        this.$axios({
            method: "get",
            url: `/testmgmt/getProductChart?token=${localStorage.getItem('token')}&locale=${this.lang}&productId=${this.productId}`
        }).then(res => {
            this.pie = res.data.cd[0].data;
            this.rect = res.data.cd[1].data;
        });
    },
    mounted() {
        console.log(1111111111)
        console.log(this.$refs.listChild);
        // i18n
        // this.pie.title.text = this.$t('chart.productWorkload');
        // this.rect.title.text = this.$t('chart.dailyTestCases');

        // this.rect.legend.data =  [this.$t('terms.pass'), this.$t('terms.failed')];
        // this.rect.series[0].name =  this.$t('terms.pass');
        // this.rect.series[1].name =  this.$t('terms.failed');
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
