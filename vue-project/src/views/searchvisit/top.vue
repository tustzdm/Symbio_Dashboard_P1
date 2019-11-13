<template>
<el-card class="manage-top" shadow="hover" style="height:40px">
    <div>
        <div class="SiteSelect select">
            <el-dropdown trigger="click" @command="handleProductCommand">
                <span class="el-dropdown-link">
                    {{productName}}
                    <i class="el-icon-caret-bottom el-icon--right"></i>
                </span>
                <el-dropdown-menu>
                    <!-- <el-row class="demo-autocomplete">
                        <el-col :span="12">
                            <el-autocomplete class="inline-input" v-model="state2" :fetch-suggestions="querySearch" placeholder="Search here" :trigger-on-focus="false" @select="handleSelect" style="width:180px"></el-autocomplete>
                        </el-col>
                    </el-row> -->
                    <!-- <el-dropdown-item>
                        <div>
                            <i class="el-icon-plus"></i>
                            <span>
                                <router-link to="/addproject/index">Add Product</router-link>
                            </span>
                        </div>
                    </el-dropdown-item>
                    <el-dropdown-item>
                        <i class="el-icon-edit"></i>Edit Product
                    </el-dropdown-item> -->
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
        <div class="select">
            <el-dropdown trigger="click" @command="handleTestsetCommand">
                <span class="el-dropdown-link">
                    {{testSetName}}
                    <i class="el-icon-caret-bottom el-icon--right"></i>
                </span>
                <el-dropdown-menu>
                    <!-- <input class="select-search" type="text" placeholder="Search here" /> -->
                    <!-- <el-dropdown-item>
                        <i class="el-icon-plus"></i>Add Test Set
                    </el-dropdown-item>
                    <el-dropdown-item>
                        <i class="el-icon-edit"></i>Edit Test Set
                    </el-dropdown-item> -->
                    <el-dropdown-item v-for="item in testSetList" :class="{selected:item.id==testSetId}" :command="item.id" :key="item.id">
                        {{item.name}}
                    </el-dropdown-item>
                    <span>
                        <router-link :to="{ name: 'addproject', query: { pageType: 'TestSet',productId: this.productId,releaseId: this.releaseId}}" class="add_link"><i class="el-icon-plus"></i> Add TestSet</router-link>
                    </span>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
        <div class="manage-top-right">
            <el-button class="btn-top" @click="runDialogVisible=true" style="background-color:#5CADAD" size="mini"><i class="el-icon-caret-right"></i> Run</el-button>
            <el-button class="btn-top" style="background-color:rgb(246, 184, 184)" size="mini">Add Bug</el-button>
            <el-button class="btn-top" style="background-color:rgb(190, 205, 223);" size="mini">Refresh</el-button>
            <el-button class="btn-top" @click="centerDialogVisible = true" style="background-color:#FF8040;" size="mini">Import</el-button>
            <div class="select" style="float:right; margin-left:30px">
                <el-dropdown trigger="click">
                    <span class="el-dropdown-link">
                        Filter
                        <i class="el-icon-caret-bottom el-icon--right"></i>
                    </span>
                    <el-dropdown-menu>
                        <el-dropdown-item>Method</el-dropdown-item>
                        <el-dropdown-item>Ownner</el-dropdown-item>
                        <el-dropdown-item>Bug ID</el-dropdown-item>
                        <el-dropdown-item>Testing Notes</el-dropdown-item>
                        <el-dropdown-item>Notes</el-dropdown-item>
                        <el-dropdown-item>Issue Peason</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>
        </div>
    </div>
    <!-- import dialog -->
    <el-dialog title="Import" :visible.sync="centerDialogVisible" width="30%" center>
        <el-upload class="upload-demo" :on-success="uploadSuccess" ref="upload" :action="`/api/result/upload?token=1&testSetId=${this.testSetId}`" :on-preview="handlePreview" :on-remove="handleRemove" :file-list="fileList" :auto-upload="false">
            <el-button slot="trigger" size="small" type="primary">Choose File</el-button>
            <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">Upload</el-button>
            <div slot="tip" class="el-upload__tip" style="text-align:center;font-size:16px">.excel file only, less than 500m</div>
        </el-upload>
        <span slot="footer" class="dialog-footer">
            <el-button @click="centerDialogVisible = false">Cancel</el-button>
            <el-button type="primary" @click="centerDialogVisible = false">Confirm</el-button>
        </span>
    </el-dialog>
    <!-- run dialog -->
    <el-dialog title="Select TEP" :visible.sync="runDialogVisible" width="50%" center>
        <div class="top" style="margin:25px 0;">

            <!-- <ul class="runUl" style="text-align:center">
                <li>
                    <span style="font-family:Poppins;">TEP Name</span>
                    <select value="1" id="" style="margin-left:20px;height:30px;width:300px">
                        <option v-for="item in tepnameList" :key="item.id">{{item.name}}</option>
                    </select>
                </li>
                
                <li>
                    <span style="font-family:Poppins;">BRANCH</span>
                    <el-input placeholder="repository" style="margin-left:20px;width:300px" value="develop"></el-input>
                </li>
                <li>
                    <span style="font-family:Poppins;">TestBool</span>
                    <el-radio-group style="margin-left:20px;width:300px">
                        <el-radio label="1">Yes</el-radio>
                        <el-radio label="0">No</el-radio>
                    </el-radio-group>
                </li> 
                <li>
                    <span style="font-family:Poppins;">Suite XML file</span>
                    <el-input placeholder="xml file" style="margin-left:20px;width:300px" value="SampleSuite.xml"></el-input>
                </li>
                <li>
                    <span style="font-family:Poppins;">TestRun ID</span>
                    <el-input placeholder="Id of TestRun" style="margin-left:20px;width:300px" value="268">268</el-input>
                </li>
            </ul> -->

            <table style="width: 100%;">
                <tr>
                    <td>
                        <div>
                            <div style="width: 40%; float:left; text-align: right; margin-right:10px;margin-top:8px;height:30px;">
                                <span style="font-family:Poppins;">TEP Name</span>
                            </div>
                            <div style="float:left; text-align:left;">
                                <select v-model="tepId" id="" style="margin-left:20px;height:30px;width:300px">
                                    <option v-for="item in tepnameList" :key="item.id" :value="item.id">{{item.name}}</option>
                                </select>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr v-for="item in testSelectList" :key="item.id">
                    <td>
                        <div>
                            <div style="width: 40%; float:left; text-align: right; margin-right:10px;margin-top:8px;height:30px;">
                                <span style="font-family:Poppins;">{{item.name}}</span>
                            </div>
                            <div style="float:left; text-align:left;">
                                <el-input placeholder="repository" style="margin-left:20px;width:300px" :value="item.defaultValue"></el-input>
                            </div>
                        </div>
                    </td>
                </tr>
            </table>

        </div>
        <span slot="footer" class="dialog-footer">
            <el-button @click="runDialogVisible = false">Cancel</el-button>
            <el-button type="primary" @click="runConfirm">Run</el-button>
        </span>
    </el-dialog>
</el-card>
</template>

<script>
export default {
    name: 'top',
    data() {
        return {
            upLoadUrl: '/api/result/upload',
            productName: 'Product',
            releaseName: 'Release',
            testSetName: 'TestSet',
            productId: '480',
            releaseId: '',
            testSetId: '',
            productList: '',
            releaseList: '',
            testSetList: '',
            tableColumn: '',
            tableData: '',
            centerDialogVisible: false,
            runDialogVisible: false,
            tepnameList: [],
            tepId: '5',
            testSelectList: [],
            fatherSelectList: '',
            fileList: []
        }
    },
    props: ['selectList'],
    created() {
        this.productId=localStorage.getItem('result_productId');
        this.releaseId=localStorage.getItem('result_releaseId');
        this.testSetId=localStorage.getItem('result_testSetId');

        this.fatherSelectList = this.selectList;
        this.getNavgationList();
        this.Fetch(`/result/getTEPInfo?token=1&testSetId=1`, {
            method: "GET"
        }).then(res => {
            console.log(res);
            this.tepnameList = res.cd.nameList;
            console.log(this.tepnameList);
            this.testSelectList = res.cd.data;
        });

        
    },
    mounted() {},
    watch: {
        tepId: function (val) {
            this.Fetch(`/result/getTEPInfo?token=1&testSetId=1&tepId=${val}`, {
                method: "GET"
            }).then(res => {
                console.log(res);
                this.tepnameList = res.cd.nameList;
                console.log(this.tepnameList)
                this.testSelectList = res.cd.data
            });
        },
        productId:function (val) {
            localStorage.setItem('result_productId',val)
        },
        releaseId:function (val) {
            localStorage.setItem('result_releaseId',val)
        },
        testSetId:function (val) {  
            localStorage.setItem('result_testSetId',val)
        }
    },
    methods: {
        handleProductCommand(command) {
            this.productId = command; //下拉菜单点了productList的内容时会改变productID的值
            this.releaseName = 'Release';
            this.testSetName = 'TestSet';
            this.releaseId = '';
            this.testSetId = '';
            this.getNavgationList();
        },
        handleReleaseCommand(command) {
            this.releaseId = command;
            this.testSetName = 'TestSet';
            this.testSetId = '';
            this.getNavgationList();
        },
        handleTestsetCommand(command) {
            this.testSetId = command;
            this.getNavgationList();
        },
        getNavgationList() {
            this.Fetch(`/result/getList`, {
                method: "POST",
                body: {
                    "token": "123",
                    "productId": this.productId,
                    "releaseId": this.releaseId,
                    "testSetId": this.testSetId
                }
            }).then(res => {
                console.log(res);
                this.productId = res.cd.productId;
                this.releaseId = res.cd.releaseId;
                this.testSetId = res.cd.testSetId;
                this.productList = res.cd.productList;
                for (let i in this.productList) {
                    if (this.productList[i].id == this.productId) {
                        this.productName = this.productList[i].name;
                    }
                }
                this.releaseList = res.cd.releaseList;
                for (let i in this.releaseList) {
                    if (this.releaseList[i].id == this.releaseId) {
                        this.releaseName = this.releaseList[i].name;
                    }
                }
                this.testSetList = res.cd.testSetList;
                for (let i in this.testSetList) {
                    if (this.testSetList[i].id == this.testSetId) {
                        this.testSetName = this.testSetList[i].name;
                    }
                }
                this.tableColumn = res.cd.columns;
                this.tableData = res.cd.data;
                console.log(this.tableData);
                this.$emit('getTableData', res);
            }).catch(err => {
                alert(err);
            });
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
            this.getNavgationList();
        },
        // checkrun() {
        //     console.log('2222222222123123123123')
        //     console.log(this.fatherSelectList);
        //     if (this.fatherSelectList.length == 0) {
        //         alert('Select one option at least');
        //         return;
        //     } else {
        //         this.runDialogVisible = true;
        //     }
        // },
        //run method
        runConfirm() {
            this.runDialogVisible = false;
            this.Fetch(`/result/run`, {
                method: "POST",
                body: {
                    "token": "123",
                    "productId": this.productId,
                    "releaseId": this.releaseId,
                    "testSetId": this.testSetId
                }
            }).then(res => {
                this.$emit('runStatus', true);
                setTimeout(() => {
                    this.$emit('getTableData', res);
                }, 3000);
            })
        }
    }
}
</script>

<style lang="stylus" scoped>
.selected {
    background-color: #97CBFF
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

.sperate-arrow {
    margin-left: 10px;
}

.manage-top-right {
    width: auto;
    height: 40px;
    line-height: 40px;
    float: right;
    margin-right: 30px;
}

.btn-top {
    color: white;
    font-size: 15px;
    font-weight: bold;
}

.btn-top:hover {
    color: white;
}

.el-dropdown-link {
    cursor: pointer;
    font-weight: bold;
    font-famliy: Poppins;
    color: #272727;
    font-size: 15px;
}

.add_link {
    font-size: 13px;
    line-height: 27px;
    padding: 0 15px;
    font-weight: bold;
}

.runUl li {
    height 50px line-height 50px color black
}
</style>
