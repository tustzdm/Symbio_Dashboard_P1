<template>
<div class="buy-root" style="width:85%;margin-left:7.5%">
    <!-- 两个部分mabage-top和(表格、翻页)，两者之间都用flex布局-->

    <!--manage-top start,后面单独把manage-top写成一个组件-->
    <el-card class="manage-top" shadow="hover" style="height:40px">
        <div class="SiteSelect select">
            <el-dropdown trigger="click">
                <span class="el-dropdown-link">
                    Site
                    <i class="el-icon-caret-bottom el-icon--right"></i>
                </span>
                <el-dropdown-menu>
                    <el-row class="demo-autocomplete">
                        <el-col :span="12">
                            <el-autocomplete class="inline-input" v-model="state2" :fetch-suggestions="querySearch" placeholder="Search here" :trigger-on-focus="false" @select="handleSelect" style="width:180px"></el-autocomplete>
                        </el-col>
                    </el-row>
                    <el-dropdown-item>
                        <div>
                            <i class="el-icon-plus"></i>
                            <span>
                                <router-link to="/addproject/index">Add Product</router-link>
                            </span>
                        </div>
                    </el-dropdown-item>
                    <el-dropdown-item>
                        <i class="el-icon-edit"></i>Edit Product
                    </el-dropdown-item>
                    <el-dropdown-item style="text-align:center;">
                        <i class="el-icon-more-outline"></i>More
                    </el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
            <span class="sperate-arrow">
                <i class="el-icon-arrow-right"></i>
            </span>
        </div>
        <div class="SiteSelect select">
            <el-dropdown trigger="click">
                <span class="el-dropdown-link">
                    OTP
                    <i class="el-icon-caret-bottom el-icon--right"></i>
                </span>
                <el-dropdown-menu>
                    <input class="select-search" type="text" placeholder="Search here" />
                    <el-dropdown-item>
                        <i class="el-icon-plus"></i>Add Release
                    </el-dropdown-item>
                    <el-dropdown-item>
                        <i class="el-icon-edit"></i>Edit Releases
                    </el-dropdown-item>
                    <el-dropdown-item style="text-align:center;">
                        <i class="el-icon-more-outline"></i>More
                    </el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
            <span class="sperate-arrow">
                <i class="el-icon-arrow-right"></i>
            </span>
        </div>
        <div class="product-select select">
            <el-dropdown trigger="click">
                <span class="el-dropdown-link">
                    LLT-19753 OTP-One time payment
                    <i class="el-icon-caret-bottom el-icon--right"></i>
                </span>
                <el-dropdown-menu>
                    <input class="select-search" type="text" placeholder="Search here" />
                    <el-dropdown-item>
                        <i class="el-icon-plus"></i>Add Test Set
                    </el-dropdown-item>
                    <el-dropdown-item>
                        <i class="el-icon-edit"></i>Edit Test Set
                    </el-dropdown-item>
                    <el-dropdown-item style="text-align:center;">
                        <i class="el-icon-more-outline"></i>More
                    </el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
        <div class="product-select select">
            <el-dropdown trigger="click">
                <span class="el-dropdown-link">
                    ADD
                    <i class="el-icon-plus"></i>
                </span>
                <el-dropdown-menu>
                    <el-dropdown-item>
                        <span>
                            <router-link to="/addproject/index">Add Product</router-link>
                        </span>
                    </el-dropdown-item>
                    <el-dropdown-item>
                        <span>
                            <router-link to="/addversion/index">Add Release</router-link>
                        </span>
                    </el-dropdown-item>
                    <el-dropdown-item>
                        <span>
                            <router-link to="/addcase/index">Add Testset</router-link>
                        </span>
                    </el-dropdown-item>
                    <el-dropdown-item style="text-align:center;">
                        <i class="el-icon-more-outline"></i>More
                    </el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
        <div class="manage-top-right">
            <el-button style="background-color:#c2eaae;color:white" size="mini">Run</el-button>
            <el-button style="background-color:rgb(190, 205, 223);color:white" size="mini">Refresh</el-button>
            <el-button style="background-color:rgb(246, 184, 184);color:white" size="mini">Add Bug</el-button>
            <div class="select" style="float:right; margin-right:30px">
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
    </el-card>
    <!-- manage-top end -->

    <el-card class="caseTabel" shadow="hover" style="border:none">
        <el-table :data="dataList.slice((currentPage-1)*pageSize,currentPage*pageSize)" @selection-change="handleSelectionChange" style="width: 100%;height: 100%;background-color:rgb(240,240,240); text-align:center" :max-height="tableHeight">
            <el-table-column type="selection" width="50px"></el-table-column>
            <el-table-column prop="autoStatus" sortable label="Status">
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
            <el-table-column prop="screenshot" label="ScreenShot">
                <template slot-scope="scope">
                    <img :src="scope.row.screenshot" class="el-img-custom" />
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
import {
    getTestManager,
    getProjectInfo
} from '@/api/index'
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
            currentPage: 1
        }
    },
    components: {
        top: top
    },
    created() {
            this.Fetch("/result/getList", {
                method: "GET",
                body: {
                    "token": "123",
                    "productId": 1,
                    "releaseId": 108
                }
            }).then(res => {
                console.log(res);
               
            })
    },
    mounted() {
        this.getTestManagerInfo()
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
        getTestManagerInfo() {
            let params = {
                autoStatus: this.autoStatus,
                qastatus: this.qastatus,
                columns: this.columns,
                select1: this.select1,
                select2: this.select2,
                select3: this.select3,
                pageSize: this.pageSize,
                currentPage: this.currentPage
            }
            getTestManager(params).then(res => {
                this.dataList = res.data
            })
        },
        handleBtn(type) {
            if (type != 'refresh') {
                this.autoStatus = type
            }
            this.getTestManagerInfo()
        },
        handleSelect() {
            this.getTestManagerInfo()
        },
        handleCommand(command) {
            let [type, data] = command
            if (type == 'add') {
                this.$router.push({
                    path: data,
                    query: {
                        type: 'add'
                    }
                })
                return
            }
            if (type == 'p') {
                this.versionInfo = data.children
                storage.set('curProject', data)
                this.proTitle = data.name
            }
            if (type == 'v') {
                this.caseInfo = data.children
                storage.set('curVersion', data)
                this.verTitle = data.name
            }
            if (type == 'c') {
                this.caseTitle = data.name
                storage.set('curCase', data)
            }
        },
        currentChange(currentPage) {
            this.currentPage = currentPage
            this.getTestManagerInfo()
        },
        sizeChange(size) {
            this.pageSize = size
            this.getTestManagerInfo()
        },
        handleSelectionChange(val) {
            this.multipleSelection = val
        },
        addShow() {
            this.addPartShow = true
        }
    }
}
</script>

<style lang="stylus" scoped>
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
    background: #ccc;
}

.auto_pass {
    background: #adedc5;
}

.auto_failed {
    background: rgb(246, 184, 184);
}
</style>
