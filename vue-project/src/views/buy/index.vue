<template>
<el-row class="infoCon">
    <el-col :span="20" :offset="2">
        <div class="manage-charts">

            <chart style="display:inline-block" :options="pie" class="panel" />

            <chart :options="rect" class="panel" />

        </div>
        <div>
            <el-card class="listHead" shadow="never" style="padding-right:5%;">
                <h2 style="float:left;margin:0 0 0 80px;line-height:60px">Product List</h2>
                <el-button @click="add" style="float:right;margin:10px 80px 0 0;background-color:#7a85a1" type="info" size="med">
                    + Add Product
                </el-button>
            </el-card>
        </div>
        <productList></productList>
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
import productList from './productList'
export default {
    data() {
        return {
            pie: getPie(),
            rect: getRect()
        }
    },
    components: {
        productList,
        chart: ECharts
    },
    mounted() {
        // document.getElementsByClassName('echarts')[0].style.width = document.body.clientWidth * (11 / 27) + 'px';
        // document.getElementsByClassName('echarts')[1].style.width = document.body.clientWidth * (11 / 27) + 'px';
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
                    pageType: 'Product'
                }
            })
        }
    }
}
</script>

<style lang="stylus">
.infoCon {//没有设置scoped所以全局有效，但是给外层加了infoCon所以只是class为infoCon下面的才会生效
    .manage-charts {
        width: 100%;
        display: flex;
        justify-content: space-between;
        margin-bottom:20px;
    }

    .panel {
        margin: 0;
        padding: 0;
    }

    .panel {
        width: auto;
        float: left;
        text-align: center;
        flex-basis: 49%
    }
}
</style>
