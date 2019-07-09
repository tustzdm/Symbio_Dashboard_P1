<template>
<el-card shadow="hover" style="margin-bottom:100px">
    <el-form ref="form" :model="form" label-width="200px">
        <el-form-item label="Key:" prop="name">
            <el-col :span="8">
                <el-input v-model="form.name" placeholder="Element Name"></el-input>
            </el-col>
        </el-form-item>
        <el-form-item label="Type:" prop="type">
            <el-col :span="8">
                <select name="" id="">
                    <option value="" v-for="item in typeList">{{item.key}}</option>
                </select>
            </el-col>
        </el-form-item>
        <el-form-item label="Ext Data:" prop="Ext">
            <el-col :span="8">
                <el-input v-model="form.Ext" placeholder="Ext Data"></el-input>
            </el-col>
        </el-form-item>
        <el-form-item label="Required:" prop="Required">
            <div>
                <el-radio v-model="radio1" label="1">Y</el-radio>
                <el-radio v-model="radio1" label="2">N</el-radio>
            </div>
        </el-form-item>
        <el-form-item label="Disable:" prop="Disable">
            <div>
                <el-radio v-model="radio2" label="1">Y</el-radio>
                <el-radio v-model="radio2" label="2">N</el-radio>
            </div>
        </el-form-item>
        <el-form-item label="en_US:" prop="en_US">
            <el-col :span="8">
                <el-input v-model="form.name" placeholder="English Name"></el-input>
            </el-col>
        </el-form-item>
        <el-form-item label="zh_CN:" prop="zh_CN">
            <el-col :span="8">
                <el-input v-model="form.name" placeholder="Chinese Name"></el-input>
            </el-col>
        </el-form-item>
        <el-form-item label="Place Holder:" prop="PlaceHolder">
            <el-col :span="8">
                <el-input v-model="form.name" placeholder="Element placeholder"></el-input>
            </el-col>
        </el-form-item>
        <el-form-item label="Default Value:" prop="DefaultValue">
            <el-col :span="8">
                <el-input v-model="form.name" placeholder="Element DefaultValue"></el-input>
            </el-col>
        </el-form-item>
       <el-form-item label="Constraint Condition:" prop="Constraint">
            <el-col :span="8">
                <el-input v-model="form.name" placeholder="Max length"></el-input>
            </el-col>
        </el-form-item>
        <el-form-item label="Order:" prop="Order">
            <el-col :span="8">
                <el-input v-model="form.name" placeholder="Element order"></el-input>
            </el-col>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onSubmit">Save</el-button>
            <el-button @click="onCancel">Cancel</el-button>
        </el-form-item>
    </el-form>
</el-card>
</template>

<script>
import {
    saveProjectInfo,
} from '@/api/index';
import storage from "@/utils/storage";
export default {
    name: 'addProject',
    data() {
        return {
            radio1: '1',
            radio2: '1',
            form: {
                name: '',
                manager: '',
                logo: '',
                qaleader: '',
                qauser: '',
                ebay: '',
                autoleader: '',
                autoengineer: '',
                ls: '',
                icm: '',
                productuser: '',
                desc: ''
            },
            typeList:[]
        }
    },
    created() {
        this.Fetch("/menu/getUIElementTypeList", {
            method: "GET"
        }).then(res => {
            console.log(res);
            this.typeList = res.cd;
        });
    },
    mounted() {
        let type = this.$route.query.type;
        if (type == 'edit') {
            this.form = storage.get("curProject");
        }
    },
    methods: {
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
