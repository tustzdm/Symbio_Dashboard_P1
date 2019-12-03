<template>
<el-row>
    <el-col :span="20" :offset="2">
        <div class="compareCon">
            <el-card class="listHead" shadow="never" style="padding-right:5%">
                <div class="headLeft" style="width:500px;height:40px;float:left;box-sizing:border-box">
                    <el-tooltip :disabled="productName.length<17" class="item" effect="light" :content="productName" placement="bottom">
                        <span class="SiteSelect select">
                            {{productName}}
                        </span>
                    </el-tooltip>
                    <el-tooltip :disabled="releaseName.length<17" class="item" effect="light" :content="releaseName" placement="bottom">
                        <span class="SiteSelect select">
                             <i style="font-weight:bolder;margin-right:10px" class="el-icon-arrow-right"></i>{{releaseName}}
                        </span>
                    </el-tooltip>
                    <el-tooltip :disabled="testSetName.length<17" class="item" effect="light" :content="testSetName" placement="bottom">
                        <span class="product-select select">
                             <i style="font-weight:bolder;margin-right:10px" class="el-icon-arrow-right"></i>{{testSetName}}
                        </span>
                    </el-tooltip>
                </div>
                <div class="headRight">
                    <span style="font-weight:bold;padding-right:10px">{{$t('comparePage.targetLocale')}} :</span>
                    <el-select v-model="locale" style="width:200px;margin-bottom:5px;padding-right:60px" placeholder="Please Choose">
                        <el-option v-for="item in localeList" :key="item.value" :label="item.label" :value="item.code">
                        </el-option>
                    </el-select>
                    <el-button class="btn-top" @click="uploadDialogVisible = true" style="background-color:rgb(246, 184, 184);" size="mini"><i class="el-icon-upload2"></i> {{$t('funcBtns.upload')}}</el-button>
                    <el-button class="btn-top" @click="back" style="background-color: rgb(190, 205, 223);" size="mini"><i class="el-icon-back"></i>{{$t('funcBtns.back')}}</el-button>
                </div>

            </el-card>
            <el-card>
                <el-table :data="dataList.slice((currentPage-1)*pageSize,currentPage*pageSize)" @selection-change="handleSelectionChange" style="width: 100%;" row-class-name="step">
                    <el-table-column type="selection" width="50"></el-table-column>
                    <template v-for="item in tableColownms">
                        <el-table-column v-if="item.type!='hidden'" :key="item.id" :prop="item.field" sortable :label="item.label">
                            <template slot-scope="scope">
                                <div v-if="!['sourceLocale','targetLocale','status','screenshotFlag','jiraTicketId','jiraTicket'].includes(item.field)">
                                    {{scope.row[item.field]}}
                                </div>
                                <div v-if="item.field=='status'">
                                    <span style="display:inline-block">{{getValueBycode(scope.row[item.field],statusList) }}</span>
                                </div>
                                <div v-if="item.field=='sourceLocale'">
                                    <span>
                                        <img style="width:150px;" @click="stepId=scope.row.step;leftImg=scope.row['sourceLocale'].url;rightImg=scope.row['targetLocale'].url;compareDialog=true;" class="thumbnail" :src="scope.row['sourceLocale'].thumbnail" alt="screenShot">
                                    </span>
                                </div>
                                <div v-if="item.field=='targetLocale'">
                                    <span>
                                        <img v-if="scope.row['targetLocale'].thumbnail!=''" style="width:150px;" @click="stepId=scope.row.step;screenShotId=scope.row['targetLocale'].id;leftImg=scope.row['sourceLocale'].url;rightImg=scope.row['targetLocale'].url;compareDialog=true;" class="thumbnail" :src="scope.row['targetLocale'].thumbnail" alt="screenShot">
                                    </span>
                                </div>
                                <div v-if="item.field == 'jiraTicket'">
                                    <a @click="stepId=scope.row.step;screenShotId=scope.row['targetLocale'].id;leftImg=scope.row['sourceLocale'].url;rightImg=scope.row['targetLocale'].url;compareDialog=true;jiraId=scope.row['jiraTicketId'];goJira()"  style="background:lightblue">{{scope.row[item.field]}}</a>
                                </div>
                            </template>
                        </el-table-column>
                    </template>

                </el-table>
            </el-card>
            <div style="width:100%;height:150px;"></div>
            <div class="compareDia">
                <el-dialog :visible.sync="compareDialog" fullscreen width="100%" custom-class="compare" style="background:black" center>
                    <div class="picBox">
                        <div class="left" style="width:50%;float:left">
                            <div class="iBox" style="float:left;position:fixed;top:45%;left:0">
                                <i @click="beforeStep" style="color:white;font-size:45px;font-weight:bold" class="el-icon-arrow-left beforeStep"></i>
                            </div>
                            <div style="width:85%;height:100%;float:right;padding-right:4px;box-sizing:border-box;">
                                <img class="bigImg" style="float:right;height:100%" :src="leftImg" alt="screenShot">
                            </div>
                        </div>
                        <div class="right" style="width:50%;float:left">
                            <div style="width:85%;height:100%;float:left;padding-left:4px;box-sizing:border-box;">
                                <img v-if="rightImg!=''" class="bigImg" style="float:left" :src="rightImg" alt="screenShot">
                            </div>

                            <div class="iBox" style="float:right;top:45%;right:0;position:fixed">
                                <i @click="nextStep" style="color:white;font-size:45px;font-weight:bold" class="el-icon-arrow-right"></i>
                            </div>
                        </div>
                    </div>
                    <span slot="title" class="dialog-footer" center>
                        <span style="position:absolute;float:left;font-size:25px;color:white;left:100px;font-weight:bold">Step:&nbsp;{{stepId}}</span>
                        <el-button class="operationBtn" icon="el-icon-zoom-in" @click="imgActualSize" > </el-button>
                        <el-button class="operationBtn" icon="el-icon-zoom-out" @click="imgFitScreen"></el-button>
                        <el-button class="operationBtn" icon="el-icon-chat-line-round" @click="openComment" ></el-button>
                        <el-button class="operationBtn"  @click="reportDialog=true" ><img style="width:18px;height:18px;margin-bottom:-3px;padding:1px"  src="../../assets/images/bug.jpeg"></el-button>
                        <el-button class="operationBtn"  @click="hoverMsg('success', 'Pass');passOrFail('Pass')" >Pass</el-button>
                        <el-button class="operationBtn"  @click="hoverMsg('error', 'Fail');passOrFail('Failed')" >Fail</el-button>
                        <!-- <el-button icon="el-icon-upload2" @click="hoverMsg('info', 'Upload or replace image for the locale')" ></el-button> -->
                    </span>

                    <el-dialog title="Report Bug" :visible.sync="reportDialog" center append-to-body width="90%">
                        <paint :reportInfo="reportInfo" v-if="reportDialog" @reportForm="reportForm($event)" ref="paintChild" :sShotId="screenShotId" :url="rightImg" :runIndex="runId"></paint>
                        <span slot="footer" class="dialog-footer">
                            <el-button @click="reportDialog = false">{{$t('funcBtns.cancel')}}</el-button>
                            <el-button type="primary" @click="reportConfirm">{{$t('funcBtns.confirm')}}</el-button>
                        </span>
                    </el-dialog>
                    <el-dialog title="Add comment" :visible.sync="commentDialog" center append-to-body width="30%">
                        <!-- <el-input type="textarea" :rows="5" placeholder="Input comment here" v-model="textarea">
                        </el-input> 有bug-->
                        <textarea id="comment_text" name="" v-model="comment_text" rows="10" style="width:100%;height:100%;border-radius:5px;border:1px solid lightgray"></textarea>
                        <span slot="footer" class="dialog-footer">
                            <el-button @click="commentDialog = false">{{$t('funcBtns.cancel')}}</el-button>
                            <el-button type="primary" @click="submitComment">{{$t('funcBtns.confirm')}}</el-button>
                        </span>
                    </el-dialog>
                </el-dialog>
            </div>
        </div>

        <!-- <div class="fanye" style="padding-bottom:50px;margin-top:15px">
            <el-pagination background layout="total, sizes, prev, pager, next, jumper" :total="dataList.length" :page-sizes="[20, 30, 40, 50, 100, 500]" :page-size="pageSize" style="text-align:center;margin: 10px 0" @current-change="currentChange" @size-change="sizeChange"></el-pagination>
        </div> -->
        <el-dialog :title="$t('executeReview.uploadDialog')" :visible.sync="uploadDialogVisible" width="30%" center>
            <el-upload class="upload-demo" :on-success="uploadSuccess" ref="upload" :action="`/api/result/uploadTestRunZipFile?token=${this.token}`" :on-preview="handlePreview" :on-remove="handleRemove" :file-list="fileList" :auto-upload="false">
                <el-button slot="trigger" size="small" type="primary">{{$t('funcBtns.chooseFile')}}</el-button>
                <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">{{$t('funcBtns.upload')}}</el-button>
                <div slot="tip" class="el-upload__tip" style="text-align:center;font-size:16px">{{$t('tips.maxZipFileSize')}}</div>
            </el-upload>
            <span slot="footer" class="dialog-footer">
                <el-button @click="uploadDialogVisible = false">{{$t('funcBtns.cancel')}}</el-button>
                <el-button type="primary" @click="uploadDialogVisible = false">{{$t('funcBtns.confirm')}}</el-button>
            </span>
        </el-dialog>
    </el-col>
</el-row>
</template>

<script>
function isStrEmpty(str) {
    return (str == null || str == '' || typeof str == 'undefined');
};

import paint from './paint'
export default {
    name: 'compare',
    data() {
        return {
            runId: '',
            locale: '',
            dataList: [],
            pageSize: 20,
            currentPage: 1,
            tableColownms: {},
            statusArray: ['Not Run', 'Success', '', '', 'Fail', 'Skip'],
            localeList: '',
            srcc: 'https://vue.symbio.com.cn/mock/image/0_433/434_435_1/182/en_US/screenshots/thumbnail/af1a574c-b318-4f6b-8d22-9a96e0039fff.png',
            compareDialog: false,
            stepId: '',
            screenShotId: '',
            leftImg: 'ssss',
            rightImg: 'sssss',
            productName: 'Product',
            releaseName: 'Release',
            testSetName: 'TestSet',
            productId: '480',
            releaseId: '',
            testSetId: '',
            productList: '',
            releaseList: '',
            testSetList: '',
            uploadDialogVisible: false,
            fileList: [],
            commentDialog: false,
            reportDialog: false,
            statusList: [],
            reportInfo: {}, //在编辑完信息和图片后会传到这里，用reportInfo接受
            multipleSelection:[],
            comment_text:'1231234',
            token:'',
            jiraId:'',
            lang:''
        }
    },
    components: {
        paint: paint
    },
    created() {
        console.log(2131231231234231)
        this.lang = this.$store.state.app.language=='zh'? 'zh_CN':'en_US';
        this.token = localStorage.getItem('token');
        this.productName = localStorage.getItem('result_productName');
        this.releaseName = localStorage.getItem('result_releaseName');
        this.testSetName = localStorage.getItem('result_testSetName');
        console.log(22134654649687)
        console.log(this.uploadDialogVisible);
        this.runId = this.$route.query.runId;
        this.locale = this.$route.query.locale;
        this.getTableData();
    },
    mounted() {
        console.log(222222222266666666)
    },
    computed: {},
    watch: {
        locale: function (val) {
            this.getTableData();
        }
    },
    methods: {
        back() {
            this.$router.go(-1)
        },
        getTableData() {
            this.Fetch(`/result/getReviewList?token=${localStorage.getItem('token')}&testRunId=${this.runId}&trlocale=${this.locale}&locale=${this.lang}`, {
                method: "POST",
                // body: {
                //     "token": "123",
                //     "productId": 1,
                //     "releaseId": 1,
                //     "testSetId": 4
                // }
            }).then(res => {
                console.log(res);
                this.tableColownms = res.cd.columns;
                this.dataList = res.cd.data;
                console.log(11111111)
                console.log(this.dataList);
                this.localeList = res.cd.listLocales;

                this.statusList = res.cd.listStatus;
            }).catch(err => {
                alert(err);
            });
        },
        nextStep() {
            console.log(this.stepId)
            if (this.stepId == this.dataList.length) {
                this.$message({
                    message: 'It is the last',
                    type: 'warning',
                    duration: 1000
                });
                return
            } else {
                this.stepId++;
                this.leftImg = this.dataList[this.stepId - 1].sourceLocale.url;
                this.rightImg = this.dataList[this.stepId - 1].targetLocale.url;
            }
        },
        beforeStep() {
            console.log(this.stepId);
            if (this.stepId == 1) {
                this.$message({
                    message: 'This is the first',
                    type: 'warning',
                    duration: 1000
                });
                return
            } else {
                this.stepId--;
                this.leftImg = this.dataList[this.stepId - 1].sourceLocale.url;
                this.rightImg = this.dataList[this.stepId - 1].targetLocale.url;
            }
        },
        //import method
        submitUpload() {
            this.$refs.upload.submit();
        },
        handleRemove(file, fileList) {
            console.log(file, fileList);
        },
        handlePreview(file) {
            console.log(file);
        },
        uploadSuccess(res) {
            this.centerDialogVisible = false;
            console.log('asdfasdfasdfasdfasdf')
            console.log(res)
            if (res.ec == 0) {
                this.$message({
                    message: 'Import Success',
                    type: 'success'
                });

            } else {
                this.$message({
                    message: `Error Code:${res.data.ec}, Error Message:"${res.data.em}`,
                    type: 'warning'
                });
            }
            // this.getNavgationList();
        },
        //调整图片的大小，目前是手机尺寸的类型
        imgFitScreen() {
            let leftImgObj = new Image();
            leftImgObj.src = this.leftImg;
            let rightImgObj = new Image();
            rightImgObj.src = this.rightImg;
            if (leftImgObj.width > leftImgObj.height) {
                document.getElementsByClassName('bigImg')[0].style.width = '100%';
                document.getElementsByClassName('bigImg')[1].style.width = '100%';
            } else {
                var winHeight = document.body.clientHeight || document.documentElement.clientHeight;
                document.getElementsByClassName('bigImg')[0].style.height = winHeight - 105 + 'px'
                document.getElementsByClassName('bigImg')[1].style.height = winHeight - 105 + 'px'
            }

        },
        imgActualSize() {
            let leftImgObj = new Image();
            leftImgObj.src = this.leftImg;
            console.log(leftImgObj.width)
            let rightImgObj = new Image();
            rightImgObj.src = this.rightImg;
            if (leftImgObj.width > leftImgObj.height) {
                document.getElementsByClassName('bigImg')[0].style.width = leftImgObj.width + 'px';
                document.getElementsByClassName('bigImg')[1].style.width = rightImgObj.width + 'px';
            } else {
                document.getElementsByClassName('bigImg')[0].style.height = '100%';
                document.getElementsByClassName('bigImg')[1].style.height = '100%';
            }
        },
        openComment() {
            this.commentDialog = true
            this.Fetch(`/result/screenShot/getComment?token=${localStorage.getItem('token')}&screenShotId=${this.screenShotId}`, {
                method: "GET"
            }).then((res) => {
                console.log(2222222);
                console.log(res);
                this.comment_text = res.cd.comment; //获得comment内容赋给接受的变量
            }).catch(err => {
                alert(err);
            });
        },
        //submitComment
        submitComment() {
            this.commentDialog = false;
            this.Fetch(`/result/screenShot/comment?token=${localStorage.getItem('token')}&screenShotId=${this.screenShotId}&content=${this.comment_text}`, {
                method: "POST"
            }).then((res) => {
                console.log(2222222);
                console.log(res);
                this.$message({
                    message: 'Comment add successfully',
                    type: 'success',
                    duration: 1300
                });
            }).catch(err => {
                alert(err);
            });
        },
        //getValueBycode
        getValueBycode(code, list) {
            for (var i = 0; i < list.length; i++) {
                if (code == list[i].code) {
                    return list[i].value;
                }
            }
        },
        passOrFail(status) {
            this.Fetch(`/result/screenShot/${this.screenShotId}?token=${localStorage.getItem('token')}&status=${status}`, {
                method: "POST"
            }).then(res => {
                console.log(res)
            }).catch(err => {
                alert(err);
            });
        },
        hoverMsg(type, msg) {
            this.$message({
                message: msg,
                type: type,
                duration: 800
            });
            return;
        },
        reportForm(val) {
            console.log(1234);
            console.log(val);
            this.reportInfo = val;
        },
        reportConfirm() {
            this.Fetch(`/result/saveBugInfo?token=${localStorage.getItem('token')}`, {
                method: "POST",
                body: this.reportInfo
            }).then(res => {
                console.log(this.reportInfo);
                console.log(res);
            }).catch(err => {
                alert(err);
            });
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
            console.log(this.multipleSelection);
        },
        goJira(){
            
            this.Fetch(`/result/getBugInfo?token=${localStorage.getItem('token')}&id=${this.jiraId}`, {
                method: "GET"
            }).then(res => {
                console.log('jijjjjjjjj')
                console.log(res);
                this.reportInfo=res.cd.data; 
            }).catch(err => {
                alert(err);
            });
            setTimeout(() => {
                this.reportDialog=true;
            }, 600);
        },
    }
}
</script>

<style lang="stylus">
.compareCon {
    .listHead {
        margin-top: 10px;
        height: 40px;
    }

    .select {
        font-weight: bold;
        float: left;
        max-width: 150px;
        line-height: 40px;
        height: 40px;
        margin-left: 15px;
        font-family: Poppins;
        text-overflow: ellipsis;
        white-space: nowrap;
        overflow: hidden;
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

    .compareDia .el-dialog {
        background-color: black;
        height auto
    }

    .compareDia .el-dialog__header {
        border: 0
    }

    .el-dropdown-link {
        cursor: pointer;
        font-weight: bold;
        font-famliy: Poppins;
        color: #272727;
        font-size: 15px;
    }

    /* 设置图片翻页按钮的动态效果 */
    .left .iBox:active {
        background-color: lightblue
    }

    .right .iBox:active {
        background-color: lightblue
    }

    .operationBtn{
        font-size:20px;
        padding:6px 9px;
    }
}
</style>
