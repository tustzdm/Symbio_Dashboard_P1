<template>
<el-row>
    <el-col :span="20" :offset="2">
        <div>
            <el-card class="listHead" shadow="never" style="padding-right:5%">
                <h2 style="float:left;margin:0 0 0 80px;line-height:60px">Test Result Detail
                    <i @click="show3 = !show3;arrowDown=!arrowDown" v-show="arrowDown" class="el-icon-caret-bottom"></i>
                    <i @click="show3 = !show3;arrowDown=!arrowDown" v-show="!arrowDown" class="el-icon-caret-top"></i>
                </h2>
                <div class="headRight">
                    <el-button class="btn-top" @click="back" style="background-color: rgb(190, 205, 223);" size="mini"><i class="el-icon-back"></i> Back</el-button>
                    <el-button class="btn-top" style="background-color:rgb(246, 184, 184);" size="mini"><i class="el-icon-edit"></i> Edit</el-button>
                </div>
            </el-card>

            <el-collapse-transition>
                <div v-show="show3">
                    <div class="transition-box" style="width:100%;overflow:hidden">
                        <div class="ulCon">
                            <ul class="leftUl">
                                <li>Auto Status :</li>
                                <li>Case ID :</li>
                                <li>Priority :</li>
                                <li>Feture Area :</li>
                                <li>Case Type:</li>
                                <!-- <li v-if="item!=null" v-for="(item,index) in testcase" :key="item">{{index}}:</li> -->
                            </ul>
                            <ul class="rightUl">
                                <li>
                                    <el-select v-model="status" placeholder="请选择">
                                        <el-option label="Success" value="1">
                                        </el-option>
                                        <el-option label="Not Run" value="0">
                                        </el-option>
                                        <el-option label="Skip" value="5">
                                        </el-option>
                                        <el-option label="Fail" value="4">
                                        </el-option>
                                    </el-select>
                                    <!-- <span :class="{auto_pass:status=='1',auto_block:status=='0',auto_failed:status=='4',auto_skip:status=='5'}">{{statusArray[status]}}</span> -->
                                </li>
                                <li>{{caseId}}</li>
                                <li>{{testcase.priority}}</li>
                                <li>{{testcase.featureArea}}</li>
                                <li>{{caseTypeArray[testcase.caseType]}}</li>
                                <!-- <li v-if="item!=null" v-for="(item) in testcase" :key="item">{{item}}</li> -->
                            </ul>
                        </div>
                        <div class="ulCon">
                            <ul class="leftUl">
                                <li>Test Run ID :</li>
                                <li>Locale :</li>
                                <li>QA Status :</li>
                                <li>Bug Id:</li>
                                <li>Bug Report :</li>
                            </ul>
                            <ul class="rightUl">
                                <!-- <li v-if="item!=null" v-for="(item) in testrun" :key="item">{{item}}</li> -->
                                <li>{{testrun.id}}</li>
                                <li>{{testrun.locale}}</li>
                                <li><el-select v-model="qaStatus" placeholder="请选择">
                                        <el-option label="Success" value="1">
                                        </el-option>
                                        <el-option label="Not Run" value="0">
                                        </el-option>
                                        <el-option label="Skip" value="5">
                                        </el-option>
                                        <el-option label="Fail" value="4">
                                        </el-option>
                                    </el-select>{{testresult.qaStatus}}</li>
                                <li>{{testresult.bugReportId}}</li>
                                <li>{{testresult.bugReportTitle}}</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </el-collapse-transition>

            <div class="pictures" style="padding-bottom:150px;overflow:hidden;">
                <ul>
                    <li v-for="item in screenshotList" :key="item.id" style="float:left;display:block;width:25%">
                        <img class="thumbnail" :src="item.sourceLocale.url" alt="">
                    </li>

                    <!-- <li style="float:left;display:block;width:25%">
                        <img class="thumbnail" src="../../assets/images/vivo/mobile/1.png" alt="">
                    </li>
                    <li style="float:left;display:block;width:25%">
                        <img class="thumbnail" src="../../assets/images/vivo/mobile/2.png" alt="">
                    </li>
                    <li style="float:left;display:block;width:25%">
                        <img class="thumbnail" src="../../assets/images/vivo/mobile/3.png" alt="">
                    </li>
                    <li style="float:left;display:block;width:25%">
                        <img class="thumbnail" src="../../assets/images/vivo/mobile/4.png" alt="">
                    </li>
                    <li style="float:left;display:block;width:25%">
                        <img class="thumbnail" src="../../assets/images/vivo/mobile/5.png" alt="">
                    </li>
                    <li style="float:left;display:block;width:25%">
                        <img class="thumbnail" src="../../assets/images/vivo/mobile/6.png" alt="">
                    </li>
                    <li style="float:left;display:block;width:25%">
                        <img class="thumbnail" src="../../assets/images/vivo/mobile/7.png" alt="">
                    </li> -->
                </ul>
            </div>
        </div>
    </el-col>
</el-row>
</template>

<script>
export default {
    name: 'resultPictures',
    data() {
        return {
            show3: true,
            screenshotList: [],
            testcase: {},
            testrun: {},
            testresult: {},
            arrowDown: true,
            status: '',
            caseId: '',
            statusArray: ['Not Run', 'Success', '', '', 'Fail', 'Skip'],
            caseTypeArray: ['Automation Test', 'Automation Test', 'API Test', 'Performance Test'],
            runId: '',
            qaStatus:'1'
        }
    },
    created() {
        this.runId = this.$route.query.runId;
        this.Fetch(`/result/getTestResultInfo?token=${localStorage.getItem('token')}&testRunId=${this.runId}`, {
            method: "GET"
        }).then(res => {
            console.log(res);
            this.screenshotList = res.cd.listScreenShots;
            this.testcase = res.cd.testCase;
            this.testrun = res.cd.testRun
            this.testresult = res.cd.testResult;
            this.status = this.$route.query.status;
            this.caseId = this.$route.query.caseId;

            this.Fetch(`/result/getReviewList?token=${localStorage.getItem('token')}&testRunId=${this.testrun.id}&trlocale=${this.testrun.locale}`, {
                method: "POST",
                // body: {
                //     "token": "123",
                //     "productId": 1,
                //     "releaseId": 1,
                //     "testSetId": 4
                // }
            }).then(res => {
                console.log(res)
                this.screenshotList = res.cd.data;
            }).catch(err => {
                alert(err);
            });
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
.headRight {
    float: right;
}

.btn-top {
    color: white;
    font-size: 15px;
    font-weight: bold;
    margin-top 15px
}
.transition-box{
    display:flex;
}

.ulCon {
    width: 50%;
    float left margin-top 15px
}

.ulCon .leftUl {
    width: 35%;
    float left
}

.ulCon .rightUl {
    width: 65%;
    float left
}

.ulCon li {
    height 35px;
    line-height 35px;
}

.ulCon .leftUl li {
    text-align: right;
    padding-right: 10px
}

.pictures {
    margin-top: 10px
}

.thumbnail {
    width: 100%;
    border-right: 15px solid white;
    box-sizing: border-box;
}

/* 控制不同状态case的颜色 */
.auto_block {
    background: rgb(122, 133, 161);
    border-radius: 3px;
    padding: 0 5px;
}

.auto_pass {
    background: #02C874;
    border-radius: 3px;
    padding: 0 5px;
}

.auto_failed {
    background: #FF5151;
    border-radius: 3px;
    padding: 0 5px;
}

.auto_skip {
    background: #0072E3;
    border-radius: 3px;
    padding: 0 5px;
}
</style>
