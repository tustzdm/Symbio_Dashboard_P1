<template>
<el-col :span="12" :offset="6">
    <el-card style="margin-bottom:100px;font-family:Poppins;">
        <h2 style="width:100%;text-align:center">Element of {{form.page}}</h2>
        <el-divider></el-divider>
        <el-form ref="form" :model="form" label-width="300px">
            <el-form-item label="Page:" prop="Page">
                <el-col :span="4">
                    <el-input v-model="form.page" placeholder="Ext Data"></el-input>
                </el-col>
            </el-form-item>
            <el-form-item :rules="[
      { required: true, message: 'Order is required'}
    ]" label="Key:" prop="key">
                <el-col :span="16">
                    <el-input v-model="form.key" placeholder="Element Name"></el-input>
                </el-col>
            </el-form-item>
            <el-form-item label="DbField:" prop="">
                <el-col :span="16">
                    <select v-model="form.dbField">
                        <option v-for="item in dbfieldList">{{item.code}}</option>
                    </select>
                </el-col>
            </el-form-item>
            <el-form-item label="Type:" prop="type">
                <el-col :span="16">
                    <select v-model="form.type" name="" id="">
                        <option v-for="item in typeList">{{item.value}}</option>
                    </select>
                </el-col>
            </el-form-item>
            <el-form-item label="Ext Data:" prop="Ext">
                <el-col :span="16">
                    <el-input v-model="form.data" placeholder="Ext Data"></el-input>
                </el-col>
            </el-form-item>
            <el-form-item label="Required:" prop="Required">
                <el-radio-group v-model="form.isRequired">
                    <el-radio label="1">Yes</el-radio>
                    <el-radio label="0">No</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="Disable:" prop="Disable">
                <el-radio-group v-model="form.isDisable">
                    <el-radio label="1">Yes</el-radio>
                    <el-radio label="0">No</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="en_US:" prop="en_US">
                <el-col :span="16">
                    <el-input v-model="form.enUs" placeholder="English Name"></el-input>
                </el-col>
            </el-form-item>
            <el-form-item label="zh_cn:" prop="zh_CN">
                <el-col :span="16">
                    <el-input v-model="form.zhCn" placeholder="English Name"></el-input>
                </el-col>
            </el-form-item>
            <el-form-item label="Place Holder:" prop="PlaceHolder">
                <el-col :span="16">
                    <el-input v-model="form.placeHolder" placeholder="Element placeholder"></el-input>
                </el-col>
            </el-form-item>
            <el-form-item label="Default Value:" prop="DefaultValue">
                <el-col :span="16">
                    <el-input v-model="form.defaultValue" placeholder="Element DefaultValue"></el-input>
                </el-col>
            </el-form-item>
            <el-form-item label="Constraint Condition:" prop="Constraint">
                <el-col :span="16">
                    <el-input v-model="form.constCondition" placeholder="Max length"></el-input>
                </el-col>
            </el-form-item>
            <el-form-item :rules="[
      { required: true, message: 'Order is required'},
      { type: 'number', message: 'Order must be number'}
    ]" label="Order:" prop="idx">
                <el-col :span="16">
                    <el-input v-model.number="form.idx" placeholder="Element order"></el-input>
                </el-col>
            </el-form-item>
            <div class="sbumitArea">
                <span>
                    <el-button class="saveButton" type="primary" @click="submit">Save</el-button>
                    <el-button class="cancelButton" @click="onCancel">Cancel</el-button>
                </span>
            </div>
        </el-form>
    </el-card>
</el-col>
</div>
</template>

<script>
import {
    saveProjectInfo,
} from '@/api/index';
import storage from "@/utils/storage";
export default {
    name: 'add',
    data() {
        return {
            tableHead: ['Key', 'Type', 'Ext.Data', 'Required', 'Disable', 'en_US', 'zh_CN', 'Place Holder', 'Default Value', 'Constraint', 'Order'],
            radio1: '1',
            radio2: '1',
            form: {
                id: '',
                page: '',
                key: '',
                type: 'text',
                data: '',
                isRequired: '1',
                isDisable: '0',
                enUs: '',
                zhCn: '',
                placeHolder: '',
                defaultValue: '',
                constCondition: '',
                idx: ''
            },
            typeList: '',
            dbfieldList: ''
        }
    },
    created() {
        console.log(this.$route.params)
        this.form.page = this.$route.params.page;
        this.Fetch("/setting/getDictionary?token=1&type=HtmlType", {
            method: "GET"
        }).then(res => {
            console.log(res);
            this.typeList = res.cd;
        });
        let url = `setting/getDBFields?table=${this.form.page.toLowerCase()}&token=1`; //这里取的是page的value值，实际上应该去code，先用value转小写，后面还需要把空格也删了
        this.Fetch(url, {
            method: "GET"
        }).then(res => {
            console.log(res);
            this.dbfieldList = res.cd;
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
        },
        submit() {
            var formData = this.form;
            // var formData = JSON.stringify(this.form);
            console.log(formData);
            this.$axios.post(`/ui/updateUiElement?token=111&page=${this.form.page}`, formData).then(res => {
                // success callback
                console.log(formData);
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
        }
    }
}
</script>

<style lang="stylus">
.el-input__inner {
    color: black;
}
</style>
