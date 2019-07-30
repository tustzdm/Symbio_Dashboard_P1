<template>
<el-col :span="12" :offset="6">
    <el-card style="padding-bottom:100px;font-family:Poppins;">
        <h2 style="width:100%;padding-left:150px">Add {{pageType}}</h2>
        <el-divider></el-divider>
        <el-form :model="product" ref="product" label-width="300px">
            <p>{{product}}</p>
            <el-form-item v-for="item in uiList" :rules="[
      { required: item.isRequired == 1, message: `Please input ${item.key}`, trigger: 'blur' }
    ]" :key="item.id" :label="item.label+' :'" :prop="item.key">
                <el-col :span="15">
                    <!-- <input v-if="['text','Number'].indexOf(item.type) >= 0" :placeholder="item.placeHolder"> -->
                    <el-input v-model="product[item.key]" maxlength="30" v-if="['text','Number','Text'].indexOf(item.type) >= 0" :placeholder="item.placeHolder"></el-input>
                    <select v-if="['list','SelectList'].indexOf(item.type) >= 0" v-model="product[item.dbField]">
                        <option v-for="item in statusList" :value="item.code" :key="item.id">{{item.value}}</option>
                        <!-- <option value="1">1</option> -->
                    </select>
                    <select v-if="['user','User'].indexOf(item.type) >= 0" v-model="product[tranformStr(item.dbField)]">
                        <option :value="user.id" v-for="user in userList">{{user.fullName}}</option>
                    </select>
                    <el-date-picker v-model="product[item.key]" v-if="item.type === 'DateTime'" placeholder="Choose Date"></el-date-picker>
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
            statusList: '',
            product: {},
            userList: '',
            pageType: '',
            fatherId: ''
        }
    },
    created() {
        this.pageType = this.$route.params.pageType;

        this.Fetch(`/testmgmt/get${this.pageType}UiInfo?token=1&uiInfo=1&id=`, { //将所有的数据集合到一个借口里了，uiInfod对应pageType,id对应Product或者release的值
            method: "GET"
        }).then(res => {
            console.log(res.cd);
            this.statusList = res.cd.statusList;
            this.userList = res.cd.userList;
            this.uiList = res.cd.uiInfo;      
        });
    },
    mounted() {
        let type = this.$route.query.type;
        if (type == 'edit') {
            this.form = storage.get("curProject");
        }
    },
    methods: {
        inputTips(msg, locale) {
            return (locale == 'en_US' || typeof locale == 'undefined') ? "Please input " + msg : "请输入" + msg;
        },
        onSubmit() {
            alert(this.fatherId);
            let url = `/testmgmt/update${this.pageType}?token=111`;
            if (this.pageType == 'Release') {
                this.product.productId = this.fatherId;
                // url = `/testmgmt/update${this.pageType}?token=111&productId=${this.fatherId}`
                // alert(url)
            }
            this.$axios.post(url, this.product).then(res => {
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
