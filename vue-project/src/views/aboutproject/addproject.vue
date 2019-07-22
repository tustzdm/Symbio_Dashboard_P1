<template>
<el-col :span="12" :offset="6">
    <el-card style="padding-bottom:100px;font-family:Poppins;">
        <h2 style="width:100%;padding-left:150px">Add Product</h2>
        <el-divider></el-divider>
        <el-form :model="product" ref="product" label-width="300px">
            <el-form-item v-for="item in uiList" :rules="[
      { required: item.isRequired == 1, message: `Please input ${item.key}`, trigger: 'blur' }
    ]" :key="item.id" :label="item.key+' :'" :prop="item.key">
                <el-col :span="15">
                    <!-- <input v-if="['text','Number'].indexOf(item.type) >= 0" :placeholder="item.placeHolder"> -->
                    <el-input v-model="product[item.key]" maxlength="30" v-if="['text','Number','user'].indexOf(item.type) >= 0" :placeholder="item.placeHolder"></el-input>
                    <select v-if="['list'].indexOf(item.type) >= 0" >
                        <option v-for="item in statusList">{{item.value}}</option>
                    </select>
                    <el-date-picker v-model="time" v-if="item.type === 'DateTime'" placeholder="Choose Date"></el-date-picker>
                    <el-input type="textarea" :autosize="{ minRows: 4}" v-model="product.description" v-if="item.type == 'textarea'" :maxlength="JSON.parse(item.constCondition).maxLength" show-word-limit></el-input>
                </el-col>
            </el-form-item>
        </el-form>
        <div class="sbumitArea">
            <span>
                <!-- <button class="saveButton" style="border-radius:10px"> asdfas</button> -->
                <el-button class="saveButton" type="primary" @click="onSubmit">Save</el-button>
                <el-button  class="cancelButton" @click="onCancel">Cancel</el-button>
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
            statusList:'',
            product: {
                name: '',
                locale: '',
                qalead: '',
                status: '',
                description: ''
            }
        }
    },
    created() {
        this.Fetch(`/ui/getUiInfoList?token=111&page=product`, {
            method: "GET"
        }).then(res => {
            console.log(res.cd);
            this.uiList = res.cd;
        });
        this.Fetch(`/setting/getDictionary?token=1&type=ProductStatus`, {
            method: "GET"
        }).then(res => {
            console.log(res.cd);
            this.statusList = res.cd;
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
            saveProjectInfo(this.form).then(res => {
                if (res.code == 200) {
                    this.$message.success("保存成功！")
                }
            });
        },
        beforeRemove(file, fileList) {
            return this.$confirm(`确定移除 ${file.name}？`)
        },
        handleSuccess(res) {
            this.form.logo = res.url;
        },
        onCancel() {
            this.$router.go(-1);
        }
    }
}
</script>

<style lang="stylus" scoped>
</style>
