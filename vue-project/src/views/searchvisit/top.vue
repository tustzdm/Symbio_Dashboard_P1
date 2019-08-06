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
            <el-dropdown trigger="click" @command="handleReleaseCommand">
                <span class="el-dropdown-link">
                    {{releaseName}}
                    <i class="el-icon-caret-bottom el-icon--right"></i>
                </span>
                <el-dropdown-menu>
                    <!-- <input class="select-search" type="text" placeholder="Search here" /> -->
                    <!-- <el-dropdown-item>
                        <i class="el-icon-plus"></i>Add Release
                    </el-dropdown-item>
                    <el-dropdown-item>
                        <i class="el-icon-edit"></i>Edit Releases
                    </el-dropdown-item> -->
                    <el-dropdown-item v-for="item in releaseList" :class="{selected:item.id==releaseId}" :command="item.id"  :key="item.id">
                        {{item.name}}
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
                    <el-dropdown-item v-for="item in testSetList"  :class="{selected:item.id==testSetId}" :command="item.id" :key="item.id">
                        {{item.name}}
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
    </div>
</el-card>
<!-- manage-top end -->
</template>

<script>
export default {
    name: 'top',
    data() {
        return {
            productName:'Product',
            releaseName:'Release',
            testSetName:'TestSet',
            productId: '',
            releaseId: '',
            testSetId: '',
            productList: '',
            releaseList: '',
            testSetList: ''
        }
    },
    created() {
        this.getNavgationList();
    },
    mounted() {

    },
    watch:{
        productId:function (val) {
            this.releaseName = 'Release';
            this.testSetName = 'TestSet';
             this.releaseId = '';
            this.testSetId = '';
            this.getNavgationList();
           
        },
        releaseId:function (val) {
            this.testSetName = 'TestSet';
             this.testSetId = '';
            this.getNavgationList();
           
        }
    },
    methods: {
        handleProductCommand(command) {
            this.productId = command;//下拉菜单点了productList的内容时会改变productID的值
        },
         handleReleaseCommand(command) {
            this.releaseId = command;
        },
        handleTestsetCommand(command) {
            this.testSetId = command;
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
                for (let i in this.productList){ 
                    if(this.productList[i].id == this.productId){
                        this.productName = this.productList[i].name;
                    }
                }
                this.releaseList = res.cd.releaseList;
                for (let i in this.releaseList){ 
                    if(this.releaseList[i].id == this.releaseId){
                        this.releaseName = this.releaseList[i].name;
                    }
                }
                this.testSetList = res.cd.testSetList;
                for (let i in this.testSetList){ 
                    if(this.testSetList[i].id == this.testSetId){
                        this.testSetName = this.testSetList[i].name;
                    }
                }
                console.log(this.releaseList);
            }).catch(err => {
                alert(err);
            });
        }
    }
}
</script>

<style lang="stylus" scoped>
.selected {
    background-color: #ccc
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
</style>
