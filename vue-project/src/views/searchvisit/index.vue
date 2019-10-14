<template>
<div class="buy-root" style="width:85%;margin-left:7.5%">
    <!-- 两个部分mabage-top和(表格、翻页)，两者之间都用flex布局-->
    <top @runStatus="runStatus($event)" @getTableData="getTableData($event)"></top>
    <!-- manage-top end -->

    <el-card class="caseTabel" shadow="hover" style="border:none">
        <el-table :data="dataList.slice((currentPage-1)*pageSize,currentPage*pageSize)" @selection-change="handleSelectionChange" style="width: 100%;height: 100%;text-align:center">
            <el-table-column type="selection" width="50px"></el-table-column>
            <!-- <el-table-column prop="status" sortable label="Status">
                <template slot-scope="scope">
                    <div>
                        <div style="height:18px;width:18px;border-radius:9px;float:left;margin-left:30px" :class="{auto_pass:scope.row.autoStatus=='Pass',auto_block:scope.row.autoStatus=='NotRun',auto_failed:scope.row.autoStatus=='Failed'}"></div>
                        {{scope.row.autoStatus}}
                    </div>
                </template>
            </el-table-column>
            <el-table-column prop="generate" sortable label="Generate"></el-table-column>
            <el-table-column prop="caseId" sortable label="Case ID"></el-table-column>
            <el-table-column prop="featureArea" label="Feature Area"></el-table-column>
            <el-table-column prop="qastatus" label="QA Status"></el-table-column>
            <el-table-column prop="locale" label="Locale"></el-table-column>
            <el-table-column prop="screenshotFlag" label="ScreenShot">
                <template slot-scope="scope">
                    <img :src="scope.row.screenshot" class="el-img-custom" />
                </template>
            </el-table-column> -->
            <el-table-column v-if="run" label="" width="80">
                <i class="el-icon-loading"></i>
            </el-table-column>
            <el-table-column v-for="item in tableColownms" :key="item.id" :prop="item.field" sortable :label="item.label">
                <template slot-scope="scope">
                    <div v-if="!['status','screenshotFlag'].includes(item.field)">
                        {{scope.row[item.field]}}
                    </div>
                    <div v-if="item.field=='status'">
                        <div style="height:10px;width:10px;border-radius:50%;float:left;display:inline-block;margin-left:60px;margin-top:6px;margin-right:8px" :class="{auto_pass:scope.row.status=='1',auto_block:scope.row.status=='0',auto_failed:scope.row.status=='4',auto_skip:scope.row.status=='5'}"></div>
                        <span style="float:left;display:inline-block">{{statusArray[scope.row[item.field]]}}</span>
                    </div>
                    <div v-if="item.field=='screenshotFlag'">
                        <router-link :to="{ name: 'pictures', query: { status:scope.row.status, caseId:scope.row.caseId}}">
                            <img v-if="scope.row[item.field]!=0" src="../../assets/images/screenshot-icon2.png" alt="">
                        </router-link>
                    </div>
                </template>
            </el-table-column>
            <el-table-column label="Review" width="80">
                <template slot-scope="scope">
                    <router-link :to="{ name: 'compare', query: {locale:scope.row.locale, runId:scope.row.id}}">
                        sss
                    </router-link>
                </template>
            </el-table-column>
        </el-table>
    </el-card>
    <div class="fanye">
        <el-pagination background layout="total, sizes, prev, pager, next, jumper" :total="dataList.length" :page-sizes="[20, 30, 40, 50, 100, 500]" :page-size="pageSize" style="text-align:center;margin: 10px 0" @current-change="currentChange" @size-change="sizeChange"></el-pagination>
    </div>
    <!-- tabel和翻页要写到一个div里然后和manage-top flex布局 -->

</div>
</template>

<script>
// import {
//     getTestManager,
//     getProjectInfo
// } from '@/api/index'
import storage from '@/utils/storage'
import top from './top'
export default {
    data() {
        return {
            addPartShow: false, //this value created in parent
            tableHeight: '',
            screenHeight: '',
            dataList: [],
            pageSize: 20,
            currentPage: 1,
            tableColownms: {},
            statusArray: ['Not Run', 'Success', '', '', 'Fail', 'Skip'],
            run: false,
            multipleSelection: [1, 2]
        }
    },
    components: {
        top: top
    },
    created() {
        // this.$axios.post("/result/getList",{body:{"token": "123",
        //     "productId": 1,
        //     "releaseId": 108}}).then(res => {
        //           // success callback
        //           console.log(res);
        //           var ec = res.data.ec;
        //           //debugger;
        //           if (ec != '0') {
        //               alert("Error Code:" + res.data.ec + ", Error Message:" + res.data.em);
        //           } else {
        //               this.$message.success("Operation Success！");
        //               this.$router.go(-1);
        //           }
        //       }).catch(err => {
        //           alert(err);
        //       });
    },
    mounted() {
        window.onresize = () => {
            return (() => {
                this.screenHeight = document.body.clientHeight
            })()
        }
        this.tableHeight =
            document.getElementsByClassName('main')[0].offsetHeight - 30 - 92 + 'px'
    },
    watch: {
        screenHeight() {
            this.tableHeight = this.screenHeight - 80 - 30 - 40 - 52 + 'px'
            console.log(`新的tabelHeight${this.tableHeight}`)
        }
    },
    methods: {
        currentChange(currentPage) {
            this.currentPage = currentPage
        },
        sizeChange(size) {
            this.pageSize = size
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
            console.log('select++++++++++++++');
            console.log(this.multipleSelection);
        },
        getTableData(val) {
            this.dataList = val.cd.data;
            this.tableColownms = val.cd.columns;
            console.log('tablecolumn');
            console.log(this.tableColownms);
            console.log(this.dataList)
            this.run = false;
        },
        runStatus(val) {
            // if(this.multipleSelection.length<=0){
            //     alert(1111)
            // }
            this.run = true;
        }
    }
}
</script>

<style lang="stylus">
.buy-root {
    height: 100%;
    display: flex;
    flex-direction: column;
}

.buy-root .manage-top {
    width: 100%;
    height: 40px;
    flex-grow: 0;
    flex-shrink: 0;
}

.buy-root .caseTabel {
    flex-grow: 1;
    flex-shrink: 1;
    height: auto;
}

.buy-root .fanye {
    flex-grow: 0;
    flex-shrink: 0;
    height: 52px;
}

.el-dropdown-link {
    cursor: pointer;
}

.select {
    float: left;
    height: 40px;
    line-height: 40px;
    margin-left: 20px;
    font-family: Poppins;
}

.select-search {
    margin: 3px 12px;
    width: 150px;
    font-famliy: Poppins;
}

.product-select {
    width: 300px;
}

.sperate-arrow {
    margin-left: 10px;
}

.manage-top-right {
    width: 360px;
    height: 40px;
    line-height: 40px;
    float: right;
    margin-right: 20px;
}

/*
   多行注释
   只有在compress选项未启用的时候才会被输出
*/
.el-img-custom {
    width: 20px;
    height: 20px;
}

/* 控制不同状态case的颜色 */
.auto_block {
    background: #8E8E8E;
}

.auto_pass {
    background: #02C874;
}

.auto_failed {
    background: #FF5151;
}

.auto_skip {
    background: #0072E3;
}

/* 控制tabelHead 里的字体颜色 */
th .cell {
    font-weight: bold;
    font-size: 15px;
    color: #5B5B5B;
}
</style>
