<template>
<el-row>
    <el-col :span="20" :offset="2">
        <div>
            <el-card class="listHead" shadow="never" style="padding-right:5%">
                <h2 style="float:left;margin:0 0 0 80px;line-height:60px">Test Result Detail
                    <i @click="show3 = !show3;arrowDown=!arrowDown" v-show="arrowDown" class="el-icon-caret-bottom"></i>
                    <i @click="show3 = !show3;arrowDown=!arrowDown"  v-show="!arrowDown" class="el-icon-caret-top"></i>
                </h2>
                <div class="headRight">
                    <el-button class="btn-top" @click="runDialogVisible = true" style="background-color:#FF9797;" size="mini"><i class="el-icon-back"></i> Back</el-button>
                    <el-button class="btn-top" style="background-color:#73BF00;" size="mini"><i class="el-icon-edit"></i> Edit</el-button>
                </div>
            </el-card>

            <el-collapse-transition>
                <div v-show="show3">
                    <div class="transition-box" style="width:100%;border:1px solid red;overflow:hidden">
                        <div class="ulCon">
                            <ul class="leftUl">
                                <li v-if="item!=null" v-for="(item,index) in testcase" :key="item">{{index}}:</li>
                            </ul>
                            <ul class="rightUl">
                               <li v-if="item!=null" v-for="(item) in testcase" :key="item">{{item}}</li>
                            </ul>
                        </div>
                        <div class="ulCon">
                            <ul class="leftUl">
                                <li v-if="item!=null" v-for="(item,index) in testrun" :key="item">{{index}}:</li>
                            </ul>
                            <ul class="rightUl">
                                <li v-if="item!=null" v-for="(item) in testrun" :key="item">{{item}}</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </el-collapse-transition>

            <div class="pictures">
                <ul>
                    <li v-for="item in screenshotList" :key="item.id" style="float:left;display:block;width:25%">
                        <img class="thumbnail" src="../../assets/images/screenshots/thumbnail/44c8a33a-fce9-422a-87d4-1170c1d6c6c0.png" alt="">
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
            screenshotList: [],
            testcase:{},
            testrun:{},
            arrowDown:true
        }
    },
    created() {
        this.Fetch(`http://192.168.170.100:9090/result/getTestResultInfo?token=1&testRunId=483`, {
            method: "GET"
        }).then(res => {
            console.log(res);
            this.screenshotList = res.cd.listScreenShots;
            this.testcase = res.cd.testCase;
            this.testrun = res.cd.testRun
        });
    },
    computed: {}
}
</script>

<style lang="stylus" scoped>
.headRight {
    float: right;
}

.btn-top {
    color: white;
    font-size: 15px;
    font-weight: bold;
    margin-top 15px
}

.ulCon{
  width:50%;
  float left
}
.ulCon .leftUl{
  width:30%;
  float left
}
.ulCon .rightUl{
  width:70%;
  float left
}
.ulCon .leftUl li{
  text-align:right;
  padding-right:10px
}

.pictures {
    margin-top: 10px
}

.thumbnail {
    width: 100%;
    border-right: 10px solid white;
    box-sizing: border-box;
}

li:last-child .thumbnail {
    border: none
}
</style>
