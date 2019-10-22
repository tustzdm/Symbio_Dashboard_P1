<template>
<el-row>
    <el-col :span="20" :offset="2">
        <div class="compareCon">
            <el-card class="listHead" shadow="never" style="padding-right:5%">
                <div class="headLeft" style="width:400px;height:100%;float:left">
                    <div class="SiteSelect select">
                        <el-dropdown trigger="click" @command="handleProductCommand">
                            <span class="el-dropdown-link">
                                {{productName}}
                                <i class="el-icon-caret-bottom el-icon--right"></i>
                            </span>
                            <el-dropdown-menu>
                                <el-dropdown-item v-for="item in productList" :class="{selected:item.id==productId}" :command="item.id" :key="item.id">
                                    {{item.name}}
                                </el-dropdown-item>
                                <span>
                                    <router-link :to="{ name: 'addproject', query: { pageType: 'Product'}}" class="add_link"><i class="el-icon-plus"></i> Add Product</router-link>
                                </span>
                            </el-dropdown-menu>
                        </el-dropdown>
                        <span class="sperate-arrow">
                            <i class="el-icon-arrow-right"></i>
                        </span>
                    </div>
                    <div class="SiteSelect select">
                        <el-dropdown trigger="click" @command="handleReleaseCommand">
                            <span class="el-dropdown-link">
                                {{releaseName}}
                                <i class="el-icon-caret-bottom el-icon--right"></i>
                            </span>
                            <el-dropdown-menu>

                                <el-dropdown-item v-for="item in releaseList" :class="{selected:item.id==releaseId}" :command="item.id" :key="item.id">
                                    {{item.name}}
                                </el-dropdown-item>
                                <span>
                                    <router-link :to="{ name: 'addproject', query: { pageType: 'Release',productId: this.productId}}" class="add_link"><i class="el-icon-plus"></i> Add Release</router-link>
                                </span>
                            </el-dropdown-menu>
                        </el-dropdown>
                        <span class="sperate-arrow">
                            <i class="el-icon-arrow-right"></i>
                        </span>
                    </div>
                    <div class="product-select select">
                        <el-dropdown trigger="click" @command="handleTestsetCommand">
                            <span class="el-dropdown-link">
                                {{testSetName}}
                                <i class="el-icon-caret-bottom el-icon--right"></i>
                            </span>
                            <el-dropdown-menu>

                                <el-dropdown-item v-for="item in testSetList" :class="{selected:item.id==testSetId}" :command="item.id" :key="item.id">
                                    {{item.name}}
                                </el-dropdown-item>
                                <span>
                                    <router-link :to="{ name: 'addproject', query: { pageType: 'TestSet',productId: this.productId,releaseId: this.releaseId}}" class="add_link"><i class="el-icon-plus"></i> Add TestSet</router-link>
                                </span>
                            </el-dropdown-menu>
                        </el-dropdown>
                    </div>
                </div>

                <div class="headRight">
                    <span style="font-weight:bold;padding-right:10px">Target Locale:</span>
                    <el-select v-model="locale" style="width:200px;margin-bottom:5px;padding-right:60px" placeholder="Please Choose">
                        <el-option v-for="item in localeList" :key="item.value" :label="item.label" :value="item.code">
                        </el-option>
                    </el-select>
                    <el-button class="btn-top" @click="uploadDialogVisible = true" style="background-color:rgb(246, 184, 184);" size="mini"><i class="el-icon-upload2"></i> Upload</el-button>
                    <el-button class="btn-top" @click="back" style="background-color: rgb(190, 205, 223);" size="mini"><i class="el-icon-back"></i>Back</el-button>
                </div>

            </el-card>
            <el-card>
                <el-table :data="dataList.slice((currentPage-1)*pageSize,currentPage*pageSize)" @selection-change="handleSelectionChange" style="width: 100%;" row-class-name="step">
                    <el-table-column type="selection" width="50"></el-table-column>
                    <template v-for="item in tableColownms">
                        <el-table-column v-if="item.type!='hidden'" :key="item.id" :prop="item.field" sortable :label="item.label">
                            <template slot-scope="scope">
                                <div v-if="!['sourceLocale','targetLocale','status','screenshotFlag'].includes(item.field)">
                                    {{scope.row[item.field]}}
                                </div>
                                <div v-if="item.field=='status'">
                                    <!-- <div style="height:10px;width:10px;border-radius:50%;float:left;display:inline-block;margin-left:60px;margin-top:6px;margin-right:8px" :class="{auto_pass:scope.row.status=='1',auto_block:scope.row.status=='0',auto_failed:scope.row.status=='4',auto_skip:scope.row.status=='5'}"></div> -->
                                    <span style="display:inline-block">{{statusArray[scope.row[item.field]]}}</span>
                                    <!-- <span style="padding-right:10px">
                            <img style="width:100px;height:100px" class="thumbnail" src="../../assets/images/compare/1-en.png" alt="">
                        </span>
                        <span >
                            <img style="width:100px;height:100px" class="thumbnail" src="../../assets/images/compare/1-ch.png" alt="">
                        </span> -->
                                </div>
                                <div v-if="item.field=='sourceLocale'">
                                    <span>
                                        <img style="width:150px;" @click="stepId=scope.row.step;leftImg=scope.row['sourceLocale'].url;rightImg=scope.row['targetLocale'].url;compareDialog=true;" class="thumbnail" :src="scope.row['sourceLocale'].thumbnail" alt="screenShot">
                                    </span>
                                </div>
                                <div v-if="item.field=='targetLocale'">
                                    <span>
                                        <img v-if="scope.row['targetLocale'].thumbnail!=''" style="width:150px;" @click="stepId=scope.row.step;leftImg=scope.row['sourceLocale'].url;rightImg=scope.row['targetLocale'].url;compareDialog=true;" class="thumbnail" :src="scope.row['targetLocale'].thumbnail" alt="screenShot">
                                    </span>
                                </div>
                                <div v-if="item.field=='screenshotFlag'">
                                    <router-link :to="{ name: 'pictures', query: {  caseId:scope.row.caseId}}">
                                        <img v-if="scope.row[item.field]!=0" src="../../assets/images/screenshot-icon2.png" alt="">
                                    </router-link>
                                </div>
                            </template>
                        </el-table-column>
                    </template>

                </el-table>
            </el-card>
            <div style="width:100%;height:150px;"></div>
            <div class="compareDia">
                <el-dialog :visible.sync="compareDialog" fullscreen width="100%" custom-class="compare" style="background:black" :before-close="handleClose" center>
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

                    <span slot="title" class="dialog-footer" center>
                        <span style="position:absolute;float:left;font-size:25px;color:white;left:100px;font-weight:bold">Step:{{stepId}}</span>

                        <el-button icon="el-icon-zoom-in" @click="imgActualSize" style="font-size:20px"> </el-button>
                        <el-button icon="el-icon-zoom-out" @click="imgFitScreen" style="font-size:20px"></el-button>
                        <el-button icon="el-icon-chat-line-round" @click="commentDialog=true" style="font-size:20px"></el-button>
                        <el-button icon="el-icon-upload2 " style="font-size:20px"></el-button>
                    </span>

                    <el-dialog title="Add comment" :visible.sync="commentDialog" center append-to-body width="30%">
                        <!-- <el-input type="textarea" :rows="5" placeholder="Input comment here" v-model="textarea">
                        </el-input> 有bug-->
                        <textarea id="comment_text" name="" rows="10" style="width:100%;height:100%;border-radius:5px;border:1px solid lightgray"></textarea>
                        <span slot="footer" class="dialog-footer">
                            <el-button @click="commentDialog = false">Cancel</el-button>
                            <el-button type="primary" @click="submitComment">Confirm</el-button>
                        </span>
                    </el-dialog>
                </el-dialog>
            </div>
        </div>

        <!-- <div class="fanye" style="padding-bottom:50px;margin-top:15px">
            <el-pagination background layout="total, sizes, prev, pager, next, jumper" :total="dataList.length" :page-sizes="[20, 30, 40, 50, 100, 500]" :page-size="pageSize" style="text-align:center;margin: 10px 0" @current-change="currentChange" @size-change="sizeChange"></el-pagination>
        </div> -->
        <el-dialog title="UpLoad" :visible.sync="uploadDialogVisible" width="30%" center>
            <el-upload class="upload-demo" :on-success="uploadSuccess" ref="upload" :action="`/api/result/uploadTestRunZipFile?token=123`" :on-preview="handlePreview" :on-remove="handleRemove" :file-list="fileList" :auto-upload="false">
                <el-button slot="trigger" size="small" type="primary">Choose File</el-button>
                <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">Upload</el-button>
                <div slot="tip" class="el-upload__tip" style="text-align:center;font-size:16px">.zip file only, less than 50m</div>
            </el-upload>
            <span slot="footer" class="dialog-footer">
                <el-button @click="uploadDialogVisible = false">Cancel</el-button>
                <el-button type="primary" @click="uploadDialogVisible = false">Confirm</el-button>
            </span>
        </el-dialog>
    </el-col>
</el-row>
</template>

<script>
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
            commentDialog: false
        }
    },
    created() {
        console.log(22134654649687)
        console.log(this.uploadDialogVisible);
        this.runId = this.$route.query.runId;
        this.locale = this.$route.query.locale;
        this.getTableData();
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
            this.Fetch(`/result/getReviewList?token=123&testRunId=${this.runId}&trlocale=${this.locale}`, {
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
                this.localeList = res.cd.listLocales;
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
            } else {
                this.stepId++;
                this.leftImg = this.dataList[this.stepId].sourceLocale.url;
                this.rightImg = this.dataList[this.stepId].targetLocale.url;
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
                this.leftImg = this.dataList[this.stepId].sourceLocale.url;
                this.rightImg = this.dataList[this.stepId].targetLocale.url;
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
            var winHeight = document.body.clientHeight || document.documentElement.clientHeight;
            document.getElementsByClassName('bigImg')[0].style.height = winHeight - 105 + 'px'
            document.getElementsByClassName('bigImg')[1].style.height = winHeight - 105 + 'px'
        },
        imgActualSize() {
            document.getElementsByClassName('bigImg')[0].style.height = '100%';
            document.getElementsByClassName('bigImg')[1].style.height = '100%';
        },

        //submitComment
        submitComment() {
            var trId = this.stepId - 1;
            this.commentDialog = false;
            var comment_text = document.getElementById('comment_text').value;
            document.getElementsByClassName('step')[trId].getElementsByTagName('td')[4].innerHTML = `<textarea name="" id="" rows="5" style="width:95%;margin-top:0;height:100%;border-radius:3px;border:1px solid lightgray">${comment_text}</textarea>`;
            this.$message({
                message: 'Comment add successfully',
                type: 'warning',
                duration: 1300
            });
        }
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
        float: left;
        height: 40px;
        line-height: 40px;
        margin-left: 20px;
        font-family: Poppins;
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
}
</style>
