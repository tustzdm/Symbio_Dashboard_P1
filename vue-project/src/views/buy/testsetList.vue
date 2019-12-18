<template>
<div>
    
    <el-card class="manage-tabel" shadow="never">
        <div>
            <el-card class="listHead" shadow="never" style="padding-right:5%;">
                <h2 style="float:left;margin:0 0 0 80px;line-height:60px"> {{$t('listOfPRT.testsetList')}}</h2>
                <el-button v-if="checkRole(3)" @click="add"  style="float:right;margin:10px 80px 0 0;background-color:#7a85a1" type="info" size="med">
                    + {{$t('listOfPRT.addTestSet')}}
                </el-button>
            </el-card>
        </div>
        <!-- <el-table :data="productList"> -->
        <el-table :data="productList">
            <el-table-column prop="name" :label="$t('testManagement.name')" align="center" :width="0.5*tabelWidth">
                <template slot-scope="scope">
                    <div>
                        <router-link :to="{ name: 'testsetInfo', query: { productId:productId, releaseId:scope.row.id, testsetId:scope.row.id}}">{{scope.row.name}}</router-link>
                    </div>
                    <div>{{scope.row.description}}</div>
                </template>
            </el-table-column>
            <el-table-column prop="progress" align="center" :label="$t('testManagement.progress')" :width="0.25*tabelWidth">
                <template slot-scope="scope">
                    <div style="float:left;border:1px solid white;width:80%;height:100%;border-radius:10px;background-color:#e8ebf1">
                        <div v-bind:style="{width:scope.row.progress.progress}" style="float:left;background-color:#b1c9f7;border-radius:10px"><span v-if="scope.row.progress.done/scope.row.progress.total >1 ">{{scope.row.progress.done}}</span>{{scope.row.progress.done}}</div>
                        {{scope.row.total}}
                    </div>
                    {{scope.row.progress.progress}}
                    <!-- <el-progress :text-inside="true" :stroke-width="20" :percentage="90"></el-progress> -->
                </template>
            </el-table-column>
            <el-table-column align="right" :label="$t('testManagement.operation')">
                <template slot-scope="scope">
                    <el-button v-if="checkRole(2)" :formId="scope.row.id" class="editDeleteBtn" @click="editRouter" icon="el-icon-edit" circle></el-button>
                    <el-button v-if="checkRole(3)" :formId="scope.row.id" class="editDeleteBtn" @click="deleteTr" type="danger" icon="el-icon-delete" circle></el-button>
                </template>
            </el-table-column>
        </el-table>
    </el-card>

    <div class="fanye" style="padding-bottom:100px">
        <el-pagination background layout="total, sizes, prev, pager, next, jumper" :total="productList.length" :page-sizes="[20, 30, 40, 50, 100, 500]" :page-size="pageSize" style="text-align:center;margin: 10px 0" @current-change="currentChange" @size-change="sizeChange"></el-pagination>
    </div>
</div>
</template>

<script>
import storage from '@/utils/storage'

export default {
    data() {
        return {
            screenWidth: '',
            tabelWidth: '',
            pageSize: 20,
            currentPage: 1,
            productList: '',
            trIndex: '',
            productId:'',
            releaseId: '',
            role:''
        }
    },
    created() {
        this.productId = this.$route.query.productId;
        this.releaseId = this.$route.query.releaseId;
        this.getProductList();
    },
    mounted() {
        (window.onresize = () => {
            return (() => {
                this.screenWidth = document.body.clientWidth
            })()
        }),
        (this.tabelWidth = document.getElementsByClassName(
            'el-table'
        )[0].clientWidth)
    },
    watch: {
        screenWidth() {
            this.tabelWidth = this.screenWidth * 0.85
        }
    },
    components: {},
    methods: {
        getProductList() {
            this.Fetch(`/testmgmt/getTestSetList?token=${localStorage.getItem('token')}&releaseId=${this.releaseId}`, {//需要改成${this.releaseId}
                method: "GET"
            }).then(res => {
                console.log(res);
                this.role = res.cd.role;
                this.productList = res.cd.data;
                console.log(this.productList);
            });
        },
        returnTrIndex() {
            this.trIndex = event.target.parentNode.getAttribute('formId') || event.target.getAttribute('formId');
            for (var i = 0; i < this.productList.length; i++) {
                if (this.productList[i].id == this.trIndex) {
                    this.trIndex = i;
                }
            }
        },
        editRouter() {
            this.returnTrIndex();
            this.$router.push({
                path: 'editproject',
                name: 'editproject',
                query: {
                    id: this.productList[this.trIndex].id, //Pass the tr data to next router
                    editPageType: 'TestSet'
                }
            })
        },
        deleteTr() {
            this.returnTrIndex();
            console.log(this.productList[this.trIndex]);
            this.$confirm('Confirm to delete?', {
                confirmButtonText: 'Confirm',
                cancelButtonText: 'Cancel',
                type: 'warning'
            }).then(() => {
                this.$axios.post(`/testmgmt/removeTestSet?token=${localStorage.getItem('token')}&id=${this.productList[this.trIndex].id}`).then(res => {
                    // success callback
                    console.log(res);
                    var ec = res.data.ec;
                    if (ec != '0') {
                        alert(res.dat.ec + ", " + res.data.em); //弹出错误
                    } else {
                        this.$message.success("Delete Sucess！");
                        this.getProductList();
                        console.log(this.productList);
                    }
                }).catch(err => {
                    alert(err);
                });
            })
        },
        checkRole(x){
           return this.isRoleEnable(this.role,x);
        },
         add() {
             alert(this.productId)
            this.$router.push({
                path: '/addproject/index',
                name: 'addproject',
                query: {
                    pageType: 'TestSet',
                    productId: this.productId,
                    releaseId: this.releaseId
                }
            })
        },
        currentChange(currentPage) {
            this.currentPage = currentPage
        },
        sizeChange(size) {
            this.pageSize = size
        }
    }
}
</script>

<style lang="stylus" scoped>
.el-table__row td:first-child .cell div:first-child {
    padding-left: 60px;
    text-align: left;
}

.el-table__row td:first-child .cell div:first-child a {
    font-size: 16px;
    font-family: Poppins;
    color: #396ed4;
}

.el-table__row td:first-child .cell div:last-child {
    font-family: Poppins;
    text-align: left;
    padding-left: 60px;
}

.editDeleteBtn {}
</style>
