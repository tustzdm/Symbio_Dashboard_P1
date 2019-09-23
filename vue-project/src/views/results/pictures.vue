<template>
<el-row>
    <el-col :span="20" :offset="2">
        <div>
            <el-card class="listHead" shadow="never" style="padding-right:5%">
                <h2 style="float:left;margin:0 0 0 80px;line-height:60px">Test Result Detail
                    <i @click="show3 = !show3" class="el-icon-caret-bottom"></i>
                </h2>
                <div class="headRight">
                    <el-button class="btn-top" @click="runDialogVisible = true" style="background-color:#FF9797;" size="mini"><i class="el-icon-back"></i> Back</el-button>
                    <el-button class="btn-top" style="background-color:#73BF00;" size="mini"><i class="el-icon-edit"></i> Edit</el-button>
                </div>
            </el-card>

            <el-collapse-transition>
                <div v-show="show3">
                    <div class="transition-box" style="width:100%;height:200px;background:gray">
                        <ul>
                            <li>sdf</li>
                        </ul>
                    </div>
                </div>
            </el-collapse-transition>

            <div class="pictures">
                <ul>
                  <li v-for="item in screenshotList" :key="item.id">
                    <img src="https://vue.symbio.com.cn/mock/image/0_433/434_435_1/182/en_US/screenshots/thumbnail/f57ecee2-d500-44bf-ad34-9eb2d8c2b136.png" alt="">
                  </li>
                </ul>
            </div>
        </div>
    </el-col>
</el-row>
</template>

<script>
export default {
    name: 'resultPictures',
    data() {
        return {
            show3: true,
            screenshotList:[],
        }
    },
    created(){
      this.Fetch(`http://192.168.170.100:9090/result/getTestResultInfo?token=1&testRunId=483`, {
                method: "GET"
            }).then(res => {
                console.log(res);
                this.screenshotList = res.cd.listScreenShots;
            });
    },
    computed: {}
}
</script>

<style lang="stylus" scoped>
.headRight{
  float:right;
}
.btn-top {
    color: white;
    font-size: 15px;
    font-weight: bold;
    margin-top 15px
}
</style>
