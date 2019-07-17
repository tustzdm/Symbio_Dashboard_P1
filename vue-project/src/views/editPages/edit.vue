<template>
<el-col :span="12" :offset="6">
    <el-card shadow="hover" style="margin-bottom:100px;font-family:Poppins;">
        <el-form ref="form" :model="form" label-width="200px">
            <p>{{form}}</p>
            <el-form-item label="Page:" prop="Page">
                <el-col :span="4">
                    <span>{{form.page}}</span>
                    <span>{{type}}</span>
                </el-col>
            </el-form-item>
            <el-form-item label="Key:" prop="name">
                <el-col :span="16">
                    <el-input v-model="form.key" placeholder="Element Name"></el-input>
                </el-col>
            </el-form-item>
            <el-form-item label="Type:" prop="type">
                <el-col :span="16">
                    <select v-model="form.type" name="" id="">
                        <option v-for="item in typeList" :key="item.code" selected="item.code == form.type">{{item.value}}</option>
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
                    <el-input v-model="form.zhCn" placeholder="Chinese Name"></el-input>
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
            <el-form-item label="Order:" prop="Order">
                <el-col :span="16">
                    <el-input v-model="form.idx" placeholder="Element order"></el-input>
                </el-col>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submit">Save</el-button>
                <el-button @click="onCancel">Cancel</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</el-col>
</template>

<script>
import {
    saveProjectInfo,
} from '@/api/index';
import storage from "@/utils/storage";
export default {
    name: 'edit',
    data() {
        return {
            tableHead: ['Key', 'Type', 'Ext.Data', 'Required', 'Disable', 'en_US', 'zh_CN', 'Place Holder', 'Default Value', 'Constraint', 'Order'],
            radio1: '1',
            radio2: '1',
            form: {},
            typeList: []
        }
    },
    created() {
        this.form = this.$route.params.tr;
        console.log(this.form);
        console.log(this.form)
        this.Fetch("/setting/getDictionary?token=1&type=HtmlType", {
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
        },
        submit() {
            var formData = this.form;
            // this.Fetch('/ui/updateUiElement?token=111&page=product', {
            //     formData
            // }).then(res => {
            //     console.log(res);
            // });
            // axios
            this.$axios.post('/ui/updateUiElement?token=111&page=product', formData).then(res => {
                // success callback
                console.log(formData);
                console.log(res);

                var ec = res.data.ec;
                debugger;
                if (ec != '0') {
                    alert(res.ec + ", " + res.em);
                } else {
                    this.$message.success("保存成功！");
                    this.$router.go(-1);
                }
            }).catch(err => {
                alert(err);
            });
        }
    }
}
</script>

<style lang="stylus" scoped>
</style>
