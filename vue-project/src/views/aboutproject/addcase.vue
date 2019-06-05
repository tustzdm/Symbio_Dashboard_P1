<template>
  <el-card shadow="hover" ref="form" style="margin-bottom:10px">
    <el-form ref="form" :model="form" label-width="200px">
      <el-form-item label="Release">
        <el-col :span="8">
          <el-input v-model="form.release" disabled></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="LQA Ticket Name" prop="lqaname" required>
        <el-col :span="8">
          <el-input v-model="form.lqaname"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="Screen/Plateform" prop="plateform">
        <el-col :span="8">
          <el-input v-model="form.plateform"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="ZIP Name Format" prop="zip">
        <el-col :span="8">
          <el-input v-model="form.zip"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="Status">
        <el-select v-model="form.status" placeholder="Please Select">
          <el-option label="Start" value="start"></el-option>
          <el-option label="In Progress" value="doing"></el-option>
          <el-option label="End" value="end"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="LQA Lead" prop="qaleader">
        <el-col :span="8">
          <el-input v-model="form.qaleader"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="LQA Start Data">
        <el-col :span="6">
          <el-date-picker
            type="date"
            placeholder="LQA Start Data"
            v-model="form.lqastartData"
            style="width: 100%;"
            value-format="timestamp"
          ></el-date-picker>
        </el-col>
      </el-form-item>
      <el-form-item label="XXX LQA Configuration">
        <el-col :span="6">
          <el-date-picker
            type="date"
            placeholder="XXX LQA Configuration"
            v-model="form.configurationData"
            style="width: 100%;"
            value-format="timestamp"
          ></el-date-picker>
        </el-col>
      </el-form-item>
      <el-form-item label="LIS Review Due Data">
        <el-col :span="6">
          <el-date-picker
            type="date"
            placeholder="LIS Review Due Data"
            v-model="form.lisreviewData"
            style="width: 100%;"
            value-format="timestamp"
          ></el-date-picker>
        </el-col>
      </el-form-item>
      <el-form-item label="LQA End Data">
        <el-col :span="6">
          <el-date-picker
            type="date"
            placeholder="LQA End Data"
            v-model="form.lqaendData"
            style="width: 100%;"
            value-format="timestamp"
          ></el-date-picker>
        </el-col>
      </el-form-item>
      <el-form-item label="XXX Hub ICa">
        <el-col :span="8">
          <el-input v-model="form.ica" placeholder="XXX Hub ICa"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="Labels">
        <el-col :span="8">
          <el-input v-model="form.labels" placeholder="Labels"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="Jre Project xxx Issure">
        <el-col :span="8">
          <el-input v-model="form.jre1" placeholder="Jre Project xxx Issure"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="Jre Project xxxx Issure">
        <el-col :span="8">
          <el-input v-model="form.jre2" placeholder="Jre Project xxxx Issure"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="Functional bug assaign">
        <el-col :span="8">
          <el-input v-model="form.assaign" placeholder="Functional bug assaign"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="Axxxx xxxx">
        <el-col :span="8">
          <el-input v-model="form.axxxxx" placeholder="Axxxx xxxx"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="Desciption">
        <el-col :span="8">
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
import { saveCaseInfo } from '@/api/index'
export default {
  name: 'addCase',
  data() {
    return {
      form: {
        product: '',
        release: '',
        lqaname: '',
        plateform: '',
        zip: '',
        status: '',
        qaleader: '',
        lqastartData: '',
        configurationData: '',
        lisreviewData: '',
        lqaendData: '',
        ica: '',
        labels: '',
        jre1: '',
        jre2: '',
        assaign: '',
        axxxxx: '',
        desc: ''
      }
    }
  },
  mounted() {
    let type = this.$route.query.type
    let curCase = storage.get('curCase')
    let curVersion = storage.get('curVersion')
    if (type == 'edit') {
      this.form = curCase
    } else if (type == 'add') {
      this.form.release = curVersion.name
    } else {
      this.form.release = curCase.release
    }
  },
  methods: {
    onSubmit() {
      saveCaseInfo(this.form).then(res => {
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
