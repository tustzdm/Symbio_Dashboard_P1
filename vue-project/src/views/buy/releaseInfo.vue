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
import storage from '@/utils/storage'
import testsetList from './testsetList'
export default {
    data() {
        return {
            pie: {},
            rect: {},
            productId: '',
            releaseId: '',
            lang:''
        }
    },
    components: {
        testsetList: testsetList,
        chart: ECharts
    },
    mounted() {
        // i18n
        // this.pie.title.text = this.$t('chart.productWorkload');
        // this.rect.title.text = this.$t('chart.dailyTestCases');

        // this.rect.legend.data =  [this.$t('terms.pass'), this.$t('terms.failed')];
        // this.rect.series[0].name =  this.$t('terms.pass');
        // this.rect.series[1].name =  this.$t('terms.failed');
    },
    created() {
        this.productId = this.$route.query.productId;
        this.releaseId = this.$route.query.releaseId;

        this.lang = this.$store.state.app.language=='zh'? 'zh_CN':'en_US';
        this.$axios({
            method: "get",
            url: `/testmgmt/getProductChart?token=${localStorage.getItem('token')}&locale=${this.lang}&releaseId=${this.releaseId}`
        }).then(res => {
            this.pie = res.data.cd[0].data;
            this.rect = res.data.cd[1].data;
        });
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
