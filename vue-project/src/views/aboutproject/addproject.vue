<template>
  <el-card shadow="hover" style="margin-bottom:10px">
    <el-form ref="form" :model="form" label-width="200px">
      <el-form-item label="Name" prop="name">
        <el-col :span="8">
          <el-input v-model="form.name" placeholder="Product Name"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="Logo" prop="logo">
        <el-col :span="8">
          <el-upload
            :action="uploadUrl"
            :before-remove="beforeRemove"
            :on-success="handleSuccess"
          >
            <el-button size="small" icon="el-icon-upload" type="primary">Select Image</el-button>
          </el-upload>
        </el-col>
      </el-form-item>
      <el-form-item label="Product Manager" prop="manager">
        <el-col :span="8">
          <el-input v-model="form.manager"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="LQA Lead" prop="qaleader">
        <el-col :span="8">
          <el-input v-model="form.qaleader"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="LQA User" prop="qauser">
        <el-col :span="8">
          <el-input v-model="form.qauser"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="Ebay & Stubhud" prop="ebay">
        <el-col :span="8">
          <el-input v-model="form.ebay"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="Automation Lead" prop="autoleader">
        <el-col :span="8">
          <el-input v-model="form.autoleader"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="Automation Engineer" prop="autoengineer">
        <el-col :span="8">
          <el-input v-model="form.autoengineer"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="LS" prop="ls">
        <el-col :span="8">
          <el-input v-model="form.ls"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="ICM" prop="icm">
        <el-col :span="8">
          <el-input v-model="form.icm"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="Product User" prop="productuser">
        <el-col :span="8">
          <el-input v-model="form.productuser"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="Desciption">
        <el-col :span="10">
          <el-input type="textarea" v-model="form.desc"></el-input>
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
import { saveProjectInfo, uploadUrl } from '@/api/index'
import storage from "@/utils/storage"
export default {
  name: 'addProject',
  data() {
    return {
      uploadUrl: uploadUrl,
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
      }
    }
  },
  mounted(){
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
