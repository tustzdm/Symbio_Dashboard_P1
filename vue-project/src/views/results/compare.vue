<template>
<el-row>
    <el-col :span="20" :offset="2" >
        <el-card class="listHead" shadow="never" style="padding-right:5%">
            <select value="1" id="" style="margin-left:20px;height:30px;width:200px;margin-top:5px">
                <!-- <option v-for="item in tepnameList" :key="item.id">{{item.name}}</option> -->
            </select>
            {{runId}}{{locale}}
            <div class="headRight">
                <el-button class="btn-top" style="background-color:rgb(246, 184, 184);" size="mini"><i class="el-icon-upload2"></i> Upload</el-button>
                <el-button class="btn-top" @click="back" style="background-color: rgb(190, 205, 223);" size="mini"><i class="el-icon-back"></i>Back</el-button>
            </div>

        </el-card>
        <el-card>
        <el-table :data="dataList.slice((currentPage-1)*pageSize,currentPage*pageSize)" @selection-change="handleSelectionChange" style="width: 100%;text-align:center">
            <el-table-column type="selection" width="50"></el-table-column>
            <el-table-column v-for="item in tableColownms" :key="item.id" :prop="item.field" sortable :label="item.label" >
                <template slot-scope="scope">
                    <div v-if="!['status','screenshotFlag'].includes(item.field)">
                        {{scope.row[item.field]}}
                    </div>
                    <div v-if="item.field=='status'">
                        <div style="height:10px;width:10px;border-radius:50%;float:left;display:inline-block;margin-left:60px;margin-top:6px;margin-right:8px" :class="{auto_pass:scope.row.status=='1',auto_block:scope.row.status=='0',auto_failed:scope.row.status=='4',auto_skip:scope.row.status=='5'}"></div>
                        <span style="float:left;display:inline-block">{{statusArray[scope.row[item.field]]}}</span>
                        <!-- <span style="padding-right:10px">
                            <img style="width:100px;height:100px" class="thumbnail" src="../../assets/images/compare/1-en.png" alt="">
                        </span>
                        <span >
                            <img style="width:100px;height:100px" class="thumbnail" src="../../assets/images/compare/1-ch.png" alt="">
                        </span> -->
                    </div>
                    <div v-if="item.field=='screenshotFlag'">
                        <router-link :to="{ name: 'pictures', query: {  caseId:scope.row.caseId}}">
                            <img v-if="scope.row[item.field]!=0" src="../../assets/images/screenshot-icon2.png" alt="">
                        </router-link>
                    </div>
                </template>
            </el-table-column>
        </el-table>
        </el-card>
        <div class="fanye" style="padding-bottom:50px;margin-top:15px">
            <el-pagination background layout="total, sizes, prev, pager, next, jumper" :total="dataList.length" :page-sizes="[20, 30, 40, 50, 100, 500]" :page-size="pageSize" style="text-align:center;margin: 10px 0" @current-change="currentChange" @size-change="sizeChange"></el-pagination>
        </div>
    </el-col>
</el-row>
</template>

<script>
export default {
    name: 'compare',
    data() {
        return {
            runId:'',
            locale:'sss',
            dataList: [],
            pageSize: 20,
            currentPage: 1,
            tableColownms: {},
            statusArray: ['Not Run', 'Success', '', '', 'Fail', 'Skip'],
        }
    },
    created() {
        this.runId = this.$route.query.runId;
        this.locale = this.$route.query.locale;

        this.Fetch(`/result/getReviewList?token=123&testRunId=1075&trlocale=pt_BR`, {
                method: "POST",
                // body: {
                //     "token": "123",
                //     "productId": 1,
                //     "releaseId": 1,
                //     "testSetId": 4
                // }
            }).then(res => {
                console.log(11111111)
                console.log(res);
                this.tableColownms = res.cd.columns;
                this.dataList = res.cd.data;
                console.log(11111111)
                console.log(this.dataList);
            }).catch(err => {
                alert(err);
            });
    },
    computed: {},
    methods: {
        back() {
            this.$router.go(-1)
        }
    }
}
</script>

<style lang="stylus" scoped>
body{
    overflow auto 
}
.listHead {
    margin-top: 10px;
    height: 40px;
}

.headRight {
    float: right;
    margin-top: 5px;
}

.btn-top {
    color: white;
    font-size: 15px;
    font-weight: bold;
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
</style>
