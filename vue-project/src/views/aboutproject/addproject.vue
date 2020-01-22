<template>
<el-col :span="12" :offset="6">
    <el-card style="padding-bottom:100px;font-family:Poppins;">
        <h2 style="width:100%;padding-left:150px">{{$t(`listOfPRT.add${pageType}`)}} </h2>
        <el-divider></el-divider>
        <el-form :model="product" ref="product" label-width="300px">
            <!-- <p>{{product}}</p> -->
            <el-form-item v-for="item in uiList" :rules="[
      { required: item.isRequired == 1, message: `Please input ${item.key}`, trigger: 'blur' }
    ]" :key="item.id" :label="item.label+' :'" :prop="item.key">
                <el-col :span="15">
                    <el-input class="input_select" v-model="product[item.key]" maxlength="30" v-if="['text','Number','Text'].indexOf(item.type) >= 0" :placeholder="item.placeHolder"></el-input>
                    <select class="input_select" v-if="['list','SelectList'].indexOf(item.type) >= 0" v-model="product[item.key]">
                        <option v-for="option in item.data" :value="option.code" :key="option.id">{{option.value}}</option>
                    </select>
                    <select class="input_select" v-if="['productlist'].indexOf(item.type) >= 0" v-model="product.productId" disabled>
                        <option v-for="item in fatherProductList" :value="item.id" :key="item.id">{{item.name}}</option>
                    </select>
                    <select class="input_select" v-if="['releaselist'].indexOf(item.type) >= 0" v-model="product.releaseId" disabled>
                        <option v-for="item in fatherReleaseList" :value="item.id" :key="item.id">{{item.name}}</option>
                    </select>
                    <select class="input_select" v-if="['user','User'].indexOf(item.type) >= 0" v-model="product[tranformStr(item.dbField)]">
                        <option :value="user.id" v-for="user in userList" :key="user.id">{{user.fullName}}</option>
                    </select>
                    <el-date-picker v-model="product[item.key]" v-if="item.type === 'calendar'" placeholder="Choose Date"></el-date-picker>
                    <el-input class="input_select" type="textarea" v-model="product[item.key]" :autosize="{ minRows: 4}" v-if="item.type == 'textarea'" :maxlength="JSON.parse(item.constCondition).maxLength" show-word-limit></el-input>
                </el-col>
            </el-form-item>
        </el-form>
        <div class="sbumitArea">
            <span>
                <el-button class="saveButton" type="primary" @click="onSubmit">{{$t('funcBtns.save')}}</el-button>
                <el-button class="cancelButton" @click="onCancel">{{$t('funcBtns.cancel')}}</el-button>
            </span>
        </div>
    </el-card>
    <el-dialog title="Tips:" :visible.sync="toNextPage" width="30%">
        <span>Operation Success!</span>
        <span slot="footer" class="dialog-footer">
            <el-button @click="continueAdd">Continue to add</el-button>
            <el-button type="primary" @click="toNextPage = false">Back</el-button>
        </span>
    </el-dialog>
</el-col>
</template>

<script>
import {
    saveProjectInfo,
    uploadUrl
} from '@/api/index'
import storage from "@/utils/storage"
export default {
    name: 'addProject',
    data() {
        return {
            time: '',
            uploadUrl: uploadUrl,
            uiList: '',
            product: {},
            userList: '',
            pageType: '',
            fatherReleaseList: '',
            fatherProductList: '',
            fatherProductId: '',
            fatherReleaseId: '',
            toNextPage: false,
            newId: '',
            childPage: ["Release", "TestSet"],
            lang:''
        }
    },
    created() {
        this.selfLog(this.$route.query);
        this.pageType = this.$route.query.pageType;
        this.fatherProductId = this.$route.query.productId;
        this.fatherReleaseId = this.$route.query.releaseId;
        this.lang = this.$store.state.app.language=='zh'? 'zh_CN':'en_US';

        this.Fetch(`/testmgmt/get${this.pageType}UiInfo?token=${localStorage.getItem('token')}&locale=${this.lang}&uiInfo=1&id=`, { //将所有的数据集合到一个借口里了，uiInfod对应pageType,id对应Product或者release的值
            method: "GET"
        }).then(res => {
            this.selfLog(res.cd);
            this.userList = res.cd.userList;
            this.uiList = res.cd.uiInfo;
            // this.product = res.cd.data;
            this.product.productId = this.fatherProductId; //add release时带的productID   
             this.selfLog(this.fatherProductId )
            this.product.releaseId = this.fatherReleaseId; //add release时带的productID  
            this.fatherReleaseList = res.cd.releaseList;
            this.fatherProductList = res.cd.productList;
        });
    },
    watch: {
        product: function (val) {
            this.Fetch(`/navigation/getReleaseList?token=${localStorage.getItem('token')}&productId=${this.product.productId}`, {
                method: "GET"
            }).then(res => {
                this.selfLog(res.cd);
                this.fatherReleaseList = res.cd;

            });
        },

    },
    mounted() {
        let type = this.$route.query.type;
        if (type == 'edit') {
            this.form = storage.get("curProject");
        }
    },
    methods: {

        inputTips(msg, locale) {
            return (locale == 'en_US' || typeof locale == 'undefined') ? "Please input " + msg : "Please input" + msg;
        },
        onSubmit() {
            let url = `/testmgmt/update${this.pageType}?token=${localStorage.getItem('token')}`;
            this.$axios.post(url, this.product).then(res => {
                // success callback
                this.selfLog(this.product);
                this.selfLog(res.data);
                var ec = res.data.ec;
                //debugger;
                if (ec != '0') {
                    alert("Error Code:" + res.data.ec + ", Error Message:" + res.data.em);
                } else {
                    this.toNextPage = true;
                    this.newId = res.data.cd.id
                    // this.$message.success("Operation Success！");
                    // this.$router.go(-1);
                }
            }).catch(err => {
                alert(err);
            });
        },
        onCancel() {
            this.$router.go(-1);
        },
        tranformStr(str) {
            var strArr = str.split('_');
            for (var i = 1; i < strArr.length; i++) {
                strArr[i] = strArr[i].charAt(0).toUpperCase() + strArr[i].substring(1);
            }
            return strArr.join('');
        },
        continueAdd() {
            this.toNextPage = false;
            var queryCon = {}
            if (this.pageType == "Product") {
                queryCon = {
                    pageType: 'Release',
                    productId: this.newId
                }
            } else if (this.pageType == "Release") {
                queryCon = {
                    pageType: 'TestSet',
                    productId: this.fatherProductId,
                    releaseId: this.newId
                }
            }
            this.$router.push({
                path: '/addproject/index',
                name: 'addproject',
                query: queryCon
            });

            
            this.pageType = this.$route.query.pageType;
            this.fatherProductId = this.$route.query.productId;
            this.fatherReleaseId = this.$route.query.releaseId;

            this.Fetch(`/testmgmt/get${this.pageType}UiInfo?token=${localStorage.getItem('token')}&uiInfo=1&id=`, { //将所有的数据集合到一个借口里了，uiInfod对应pageType,id对应Product或者release的值
                method: "GET"
            }).then(res => {
                this.selfLog(res.cd);

                this.userList = res.cd.userList;
                this.uiList = res.cd.uiInfo;
                // this.product = res.cd.data;
                this.product.productId = this.fatherProductId; //add release时带的productID   
                this.selfLog('productID'+this.product.productId)

                this.product.releaseId = this.fatherReleaseId; //add release时带的productID  
                this.fatherReleaseList = res.cd.releaseList;
                this.fatherProductList = res.cd.productList;
            }).catch(err => {
                    alert(err);
                });
            // window.location.reload();//这里router跳转之后页面不刷新，暂且使用这种方式

        }
    }
}
</script>

<style lang="stylus" scoped>
.input_select{
    width:400px
}
</style>
