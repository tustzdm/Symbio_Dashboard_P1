<template>
<div class="buy-root" style="width:85%;margin-left:7.5%">
    <!-- 两个部分mabage-top和(表格、翻页)，两者之间都用flex布局-->
    <top :page="page" @runStatus="runStatus($event)" @getTableData="getTableData($event)"></top>
    <!-- manage-top end -->

    <el-card class="caseTabel" shadow="hover" style="border:none">
        <el-table :data="showed_data" @selection-change="handleSelectionChange" style="width: 100%;height: 100%;" @sort-change="sort_change">
            <el-table-column type="selection" width="50px"></el-table-column>
            <!-- <el-table-column v-if="run" label="" width="80">
                 <template slot-scope="scope">
                    <i v-if="selectIds.includes(scope.row.id) " class="el-icon-loading"></i>
                 </template>
            </el-table-column> -->
            <template v-for="item in tableColownms">
                <el-table-column v-if="item.type!='hidden'" :key="item.id" :prop="item.field" :width="['id'].includes(item.field )? '100px':['status','screenshotFlag','locale','priority','jobStatus'].includes(item.field )? '120px':''" :sortable="['id','status','priority'].includes(item.field)" :align="item.label=='ScreenShot'?'center':'left'" :label="item.label">
                    <template slot-scope="scope">
                        <div v-if="!['status','screenshotFlag','priority','jobStatus'].includes(item.field)">
                            {{scope.row[item.field]}}
                        </div>
                        <span v-if="item.field=='priority'" style="border-radius:3px;padding:2px 6px;margin:10px" :class="scope.row[item.field]">
                            {{scope.row[item.field]}}
                        </span>
                        <div v-if="item.field=='status'">
                            <div style="display:flex;align-items:center">
                                <!-- <span style="height:15px;width:15px;border-radius:50%;display:inline-block;margin-top:4px;margin-right:8px;" :class="{auto_pass:scope.row.status=='1',auto_block:scope.row.status=='0',auto_failed:scope.row.status=='4',auto_skip:scope.row.status=='5'}"></span> -->
                                <span :class="{auto_pass:scope.row.status=='1',auto_block:scope.row.status=='0',auto_failed:scope.row.status=='4',auto_skip:scope.row.status=='5'}" style="position:absolute;border-radius:5px;padding:0 3px;color:white;">{{statusArray[scope.row[item.field]]}}</span>
                            </div>
                        </div>
                        <div v-if="item.field=='screenshotFlag'" align="center">
                            <router-link :to="{ name: 'pictures', query: { status:scope.row.status, caseId:scope.row.caseId,runId:scope.row.id}}">
                                <img v-if="scope.row[item.field]!=0" src="../../assets/images/screenshot-icon2.png" alt="">
                            </router-link>
                        </div>
                        <a v-if="item.field=='jobStatus'" :href="scope.row.jobLink" target="_Blank">{{scope.row[item.field]}}</a>
                    </template>
                </el-table-column>
            </template>
            <el-table-column :label="$t('executeReview.review')" width="80" align="center">
                <template slot-scope="scope">
                    <router-link :to="{ name: 'compare', query: {locale:scope.row.locale, runId:scope.row.id}}">
                        <img style="width:15px" src="../../assets/images/eye.jpeg" alt="">
                    </router-link>
                </template>
            </el-table-column>
        </el-table>
    </el-card>
    <div class="fanye" style="margin:15px 0">
        <el-pagination background layout="total, sizes, prev, pager, next, jumper" :total="totalCount" :page-sizes="[20, 30, 40, 50, 100, 500]" :current-page="currentPage" :page-size="pageSize" style="text-align:center;margin-bottom:30px" @current-change="currentChange" @size-change="sizeChange"></el-pagination>
    </div>
    <div style="width:100%;height:80px"></div>
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
            totalCount: 0,
            pageSize: 20,
            currentPage: 1,
            tableColownms: {},
            statusArray: ['Not Run', 'Success', '', '', 'Fail', 'Skip'],
            run: false,
            multipleSelection: [],
            selectIds: [],
            page: 'executeReview',
            ifSort: false,
             showed_data:[]
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
        // window.onresize = () => {
        //     return (() => {
        //         this.screenHeight = document.body.clientHeight
        //     })()
        // }
        // this.tableHeight =
        // document.getElementsByClassName('main')[0].offsetHeight - 30 - 92 + 'px';
        this.showed_data = this.dataList.slice((this.currentPage-1)*this.pageSize,this.currentPage*this.pageSize);
    },
    // computed:{
    //     showed_data: function(){
    //         return this.dataList.slice((this.currentPage-1)*this.pageSize,this.currentPage*this.pageSize);
    //     }
    // },
    watch: {
        // screenHeight() {
        //     this.tableHeight = this.screenHeight - 80 - 30 - 40 - 52 + 'px'
        //     console.log(`新的tabelHeight${this.tableHeight}`)
        // },
        selectIds() {
            console.log(this.selectIds)
        },
        dataList(){
            this.showed_data = this.dataList.slice((this.currentPage-1)*this.pageSize,this.currentPage*this.pageSize);
        },
        // currentPage(){

        // }
    },
    methods: {
        currentChange(currentPage) {
            this.currentPage = currentPage;
            console.log(this.dataList);
            this.showed_data = this.dataList.slice((this.currentPage-1)*this.pageSize,this.currentPage*this.pageSize);
            console.log(this.showed_data)
        },
        sizeChange(size) {
            this.pageSize = size;
            this.showed_data = this.dataList.slice((this.currentPage-1)*this.pageSize,this.currentPage*this.pageSize);
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
            this.selectIds = [];
            for (let i = 0; i < this.multipleSelection.length; i++) {
                this.selectIds.push(this.multipleSelection[i].id);
            }
            localStorage.setItem('selectedIds', this.selectIds);
        },
        getTableData(val) {
            console.log(this.multipleSelection)
            this.dataList = val.cd.data;
            this.tableColownms = val.cd.columns;
            this.totalCount = val.cd.totalRecord;
            console.log('tablecolumn');
            console.log(this.tableColownms);
            console.log(this.dataList);
            this.run = false;
        },
        runStatus(val) {
            this.run = true;
        },
        sort(obj1, obj2) {
            let val1 = obj1.status;
            let val2 = obj2.status;
            return val1 - val2
        },
        my_desc_sort(a, b) {
            if (a.id > b.id) {
                return -1
            } else if (a.id < b.id) {
                return 1
            } else {
                return 0
            }
        },
        my_asc_sort(a, b) {
            if (a.id > b.id) {
                return 1
            } else if (a.id < b.id) {
                return -1
            } else {
                return 0
            }
        },
        sort_change(column) {
            console.log('sSSSSSirt')
            this.currentPage = 1 // return to the first page after sorting
            console.log(this.currentPage)
            if (column.prop === 'id') {
                if (column.order === 'descending') {
                    this.dataList = this.dataList.sort(this.my_desc_sort)
                    console.log(this.dataList);
                } else if (column.order === 'ascending') {
                    this.dataList = this.dataList.sort(this.my_asc_sort)
                }
            } else if (column.prop === 'col_2') {
                // ... ...
            }
            this.showed_data = this.dataList.slice(0, this.page_size) // show only one page
        }
    }
}
</script>

<style lang="stylus" scoped>
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
    // #02C874 228B22
    background: #1da21d;
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

.P0 {
    color: white;
    background: #FF8247
}

.P1 {
    color: white // #f9e8e0
        background: #FFAEB9
}

.P2 {
    color: white;
    background-color: #8968CD
}

.P3 {
    color: white;
    background-color: #7a85a1
}

.P4 {
    color: white;
    background-color: #9AC0CD
}
</style>
