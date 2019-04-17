<template>
    <el-card shadow="hover">
        <el-form ref="areaForm" :model="areaForm" size="mini">
            <el-form-item label="省" label-width="20px">
                <el-select v-model="areaForm.proKey" filterable placeholder="请选择省" @change="changeHandle('city')">
                    <el-option v-for="item in Object.keys(provList)" :key="item" :label="provList[item]" :value="item"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="市" label-width="80px">  
                <el-select v-model="areaForm.cityKey" filterable placeholder="请选择市" @change="changeHandle">
                    <el-option v-for="item in Object.keys(cityList)" :key="item" :label="cityList[item]" :value="item"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="全部" label-width="80px">
                <el-select v-model="areaForm.setAll" filterable placeholder="请选择" @change="changeHandle">
                    <el-option v-for="item in Object.keys(allSelet)" :key="item" :label="allSelet[item]" :value="item"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label-width="50px">
                <el-button type="primary" @click="searchForm()">查询</el-button>
            </el-form-item>   
        </el-form>
    </el-card>
</template>
<script>
import areaData from 'china-area-data'
import areaUtil from '@/utils/area'
const dataTypeArr = areaUtil.dataType
export default {
  name: 'areapicker',
  props: {
      dataType: {
            type: String,
            default: 'all',
            validator: (val) => {
                return areaUtil.oneOf(val, dataTypeArr);
            }
        }
  },
  data() {
    return {
        areaForm: {
            proKey: '',
            cityKey: '',
            setAll: 'allpro'
        },
        provList: areaData[86],
        cityList: [],
        allSelet: {
            '': '请选择',
            allpro: '所有省',
            allcity: '所有市'
        }
    }
  },
  methods: {
      changeHandle(level) {
          if(level == 'city'){
            this.areaForm.cityKey = "";
            this.cityList = areaData[this.areaForm.proKey];
          }
          this.returnResArr();
      },
      returnResArr() {
          let res = {};
          if (this.dataType === 'name') {
                res = {
                  pro: this.provList[this.areaForm.proKey],
                  city: this.cityList[this.areaForm.cityKey], 
                  setAll: this.allSelet[this.areaForm.setAll]
                }
          } else if(this.dataType === 'code') {
              res = {
                  pro: this.areaForm.proKey,
                  city: this.areaForm.cityKey,
                  setAll: this.areaForm.setAll == "allpro" ? "86" : (this.areaForm.setAll == "allcity" ? this.areaForm.cityKey : '')
              }
          } else {
              res = {
                  pro: {
                      code: this.areaForm.proKey,
                      name: this.provList[this.areaForm.proKey]
                  },
                  city: {
                      code: this.areaForm.cityKey,
                      name: this.cityList[this.areaForm.cityKey]
                  },
                  setAll: {
                      code: this.areaForm.setAll == "allpro" ? "86" : (this.areaForm.setAll == "allcity" ? this.areaForm.proKey : ''),
                      name: this.allSelet[this.areaForm.setAll]
                  }
              }
          }
          this.$emit('input', res);
          this.$emit('on-change', res);
      },
      searchForm() {
        this.$emit("search");
      }
  }
}
</script>

<style lang="stylus" scoped>
.el-form-item {
    display inline-block
    margin 0
}
</style>
