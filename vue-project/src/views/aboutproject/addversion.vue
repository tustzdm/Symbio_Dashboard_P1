<template>
  <el-card shadow="hover" :rules="rules" ref="form" style="margin-bottom:10px">
    <el-form ref="form" :model="form" label-width="200px">
      <el-form-item label="Product">
        <el-input v-model="form.product" disabled></el-input>
      </el-form-item>
      <el-form-item label="Release Number" prop="releaseNum" required>
        <el-col :span="14">
          <el-input v-model="form.releaseNum"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="Status">
        <el-select v-model="form.status" placeholder="Please Select">
          <el-option label="Start" value="start"></el-option>
          <el-option label="In Progress" value="doing"></el-option>
          <el-option label="End" value="end"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="Release Data" required>
        <el-col :span="6">
          <el-date-picker
            type="date"
            placeholder="Start Data"
            v-model="form.startData"
            style="width: 100%;"
            value-format="timestamp"
          ></el-date-picker>
        </el-col>
        <el-col style="text-align:center" :span="2">-</el-col>
        <el-col :span="6">
          <el-date-picker
            type="date"
            placeholder="End Data"
            v-model="form.endData"
            style="width: 100%;"
            value-format="timestamp"
          ></el-date-picker>
        </el-col>
      </el-form-item>
      <el-form-item label="Desciption">
        <el-col :span="14">
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
import storage from '@/utils/storage'
import { saveVersionInfo } from '@/api/index'
export default {
  name: 'addRelease',
  data() {
    return {
      form: {
        product: '',
        releaseNum: '',
        startData: '',
        endData: '',
        status: '',
        desc: ''
      },
      rules: {
        releaseNum: [
          { required: true, message: '请输入发布编号！', trigger: 'blur' },
          { min: 6, max: 32, message: '32个字符左右！', trigger: 'blur' }
        ]
      }
    }
  },
  mounted() {
    let type = this.$route.query.type
    let curVersion = storage.get('curVersion')
    let curProject = storage.get('curProject')
    if (type == 'edit') {
      this.form = curVersion
    } else if (type == 'add') {
      this.form.product = curProject.name
    } else {
      this.form.product = curVersion.product
    }
  },
  methods: {
    onSubmit() {
      saveVersionInfo(this.form).then(res => {
        if (res.code == 200) {
          this.$message.success('保存成功！')
        }
      })
    },
    onCancel() {
      this.$router.go(-1)
    }
  }
}
</script>

<style lang="stylus" scoped>
</style>
