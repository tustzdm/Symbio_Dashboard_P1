<template>
  <div class="el-skin">
      <div class="el-skin__box" @click="centerDialogVisible = true">
        <icon class="el-skin__icon" name="skin" :scale="2.9"></icon>
      </div>
      <el-dialog title="皮肤" :visible.sync="centerDialogVisible" width="500px" center>
        <el-card class="box-card" v-for="item in skinList" :key="item.sid" :body-style="cardBody">
          <div class="text-item" @click="setSkin(item)">
            <div class="headr-color" :style="{'background-color': item.headerBg}"></div>
            <div class="slider-color" :style="{'background-color': item.sliderBg}"></div>
            {{item.name}}
          </div>
        </el-card>
      </el-dialog>
  </div>
</template>

<script>
import skinList from '@/config/skin.js'
export default {
  name: "skin",
  data() {
    return {
      centerDialogVisible: false,
      skinList: skinList,
      cardBody: {
            'padding': '10px',
            'text-align': 'center',
            'line-height': '100px',
            'font-size': '16px'
      }
    }
  },
  computed: {
    
  },
  methods: {
    setSkin(item) {
        this.$store.dispatch('setSkinId', item.skinId);
        this.centerDialogVisible = false;
        this.$message({
            message: "皮肤切换成功！",
            type: 'success',
            duration: 1000
        })
    }
  }
};
</script>

<style lang="stylus" scoped>
.el-skin
  padding 0 10px
  &__box
    cursor pointer
  &__icon
    vertical-align middle
    color #ffffff
    transition color .28s
    &:hover
      color #41b883
  .box-card
    width 200px
    height 150px
    display inline-block
    margin-left 10px
    cursor pointer
    .el-card__body
      padding 0
      background red
      .text-item
        height 100%
        .headr-color
          width 100%
          height 30px
        .slider-color
          width 30px
          height 100px
          float left
</style>
