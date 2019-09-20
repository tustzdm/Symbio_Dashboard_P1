<template>
<el-col :span="12" :offset="6">
    <el-card style="padding-bottom:100px;font-family:Poppins;">
        <h2 style="width:100%;padding-left:150px">Edit {{editPageType}}</h2>
        <el-divider></el-divider>
        <el-form :model="product" ref="product" label-width="300px">
            <!-- <p>{{product}}</p> -->
            <el-form-item v-for="item in uiList" :rules="[
      { required: item.isRequired == 1, message: `Please input ${item.key}`, trigger: 'blur' }
    ]" :key="item.id" :label="item.label" :prop="item.key">
                <el-col :span="15">

                    <el-input v-model="product[item.key]" maxlength="30" v-if="['text','Number','Text'].indexOf(item.type) >= 0" :placeholder="item.placeHolder"></el-input>
                    <select v-if="['list','SelectList'].indexOf(item.type) >= 0" v-model="product[item.key]">
                        <option v-for="option in item.data" :value="option.code" :key="option.id">{{option.value}}</option>
                    </select>
                    <select v-if="['productlist'].indexOf(item.type) >= 0" v-model="product.productId">
                        <option v-for="item in fatherProductList" :value="item.id" :key="item.id">{{item.name}}</option>
                    </select>
                    <select v-if="['releaselist'].indexOf(item.type) >= 0" v-model="product.releaseId">
                        <option v-for="item in fatherReleaseList" :value="item.id" :key="item.id">{{item.name}}</option>
                    </select>
                    <select v-if="['user','User'].indexOf(item.type) >= 0" v-model="product[tranformStr(item.dbField)]">
                        <option :value="user.id" v-for="user in userList">{{user.fullName}}</option>
                    </select>
                    <input type="text" name="" id="" v-if="item.type === 'calender'">
                    <p v-if="item.type === 'calender'"></p>
                    <el-date-picker v-model="product[item.key]" v-if="item.type === 'calendar'" placeholder="Choose Date"></el-date-picker>
                    <el-input type="textarea" v-model="product[item.key]" :autosize="{ minRows: 4}" v-if="item.type == 'textarea'" :maxlength="JSON.parse(item.constCondition).maxLength" show-word-limit></el-input>
                </el-col>
            </el-form-item>
        </el-form>
        <div class="sbumitArea">
            <span>
                <!-- <button class="saveButton" style="border-radius:10px"> asdfas</button> -->
                <el-button class="saveButton" type="primary" @click="onSubmit">Save</el-button>
                <el-button class="cancelButton" @click="onCancel">Cancel</el-button>
            </span>
        </div>
    </el-card>
</el-col>
</template>

<script>
import storage from "@/utils/storage"
export default {
    name: 'addProject',
    data() {
        return {
            time: '',
            uiList: '',
            id: '',
            userList: '',
            editPageType: '',
            product: {}, //contaniner of product release testset;
            fatherReleaseList: '',
            fatherProductList: ''
        }
    },
    created() {
        this.id = this.$route.query.id;
        this.editPageType = this.$route.query.editPageType;
        this.Fetch(`/testmgmt/get${this.editPageType}UiInfo?token=1&uiInfo=1&id=${this.id}`, { //将所有的数据集合到一个借口里了，id对应Product或者release的值
            method: "GET"
        }).then(res => {

            console.log('starting.....');
            console.log(res.cd);

            this.userList = res.cd.userList;
            console.log(this.userList);

            this.uiList = res.cd.uiInfo;
            console.log(this.uiList);

            this.product = res.cd.data;
            console.log(this.product);

            this.fatherReleaseList = res.cd.releaseList;
            this.fatherProductList = res.cd.productList;

        });

        // this.Fetch(`/ui/getUiInfoList?token=111&page=product`, {
        //     method: "GET"
        // }).then(res => {
        //     console.log(res.cd);
        //     this.uiList = res.cd;
        // });
        // this.Fetch(`/setting/getDictionary?token=1&type=ProductStatus`, {
        //     method: "GET"
        // }).then(res => {
        //     console.log(res.cd);
        //     this.statusList = res.cd;
        // });
        // this.Fetch(`/setting/getUserList?token=1`, {
        //     method: "GET"
        // }).then(res => {
        //     console.log(res.cd.cd);
        //     this.userList = res.cd.cd;
        // for(let fullName in this.userList){
        //     this.userList[fullName].id =this.userList.id;
        // }
        // });

    },
    watch: {
        'product.productId': function (val) {
            this.Fetch(`/navigation/getReleaseList?token=1&productId=${val}`, { 
                method: "GET"
            }).then(res => {

                console.log(res.cd);
                this.fatherReleaseList = res.cd;

            });
        },

    },
    methods: {
        onSubmit() {
            this.$axios.post(`/testmgmt/update${this.editPageType}?token=111`, this.product).then(res => {
                // success callback
                console.log(this.product);
                console.log(res.data);
                var ec = res.data.ec;
                //debugger;
                if (ec != '0') {
                    alert("Error Code:" + res.data.ec + ", Error Message:" + res.data.em);
                } else {
                    this.$message.success("Operation Success！");
                    this.$router.go(-1);
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
        }
    }
}
</script>

<style lang="stylus" scoped>
</style>
