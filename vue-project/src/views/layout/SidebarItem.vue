<template>
  <div class="sidebar-wrap">
    <template v-for="item in routes" v-if="!item.hidden && item.children">
      <!-- 单级 -->
      <router-link v-if="onlyOneShowingChildren(item.children) && !item.children[0].children && !item.alwaysShow" :key="item.children[0].name" :to="item.path+'/'+item.children[0].path">
        <el-menu-item :index="item.path + '/' + item.children[0].path" style="padding: 0 10px">
            <div class="nav-item">
                <div class="nav-item-left">
                    <icon v-if="item.children[0].meta && item.children[0].meta.icon"  :name="item.children[0].meta.icon" :scale="2.5"></icon>
                    <div v-if="item.children[0].meta && item.children[0].meta.title" class="name">{{getTitle(item.children[0].meta.title)}}</div>
                </div>
                <div class="nav-item-right">
                    <div class="number">{{item.children[0].count.now}}</div>
                    <div class="percentage" :style="{'background-color': item.children[0].count.color}">{{item.children[0].count.now/item.children[0].count.total*100+'%'}}</div>
                    <div class="progress">
                        <div class="sliderbar" :style="{width: item.children[0].count.now/item.children[0].count.total*100 + '%','background-color': item.children[0].count.color}"></div>
                    </div>
                </div>
            </div>
        </el-menu-item>
      </router-link>
    </template>
  </div>
</template>

<script>
export default {
  name: "SidebarItem",
  props: {
    routes: {
      type: Array
    }
  },
  computed: {
    sliderState() {
      return this.$store.getters.sidebar.sliderState;
    }
  },
  methods: {
    onlyOneShowingChildren(children) {
      return children.filter(item => !item.hidden).length === 1;
    },
    getTitle(title) {
      if (this.$te(`route.${title}`)) {
        return this.$t(`route.${title}`);
      }
      return title
    }
  }
}
</script>

<style lang="stylus" scoped>
.sidebar-wrap
    .el-submenu,
    .el-menu-item
        font-size 0
        position relative
        height 70px
        line-height 35px
    .is-active
        font-weight bolder
        color rgb(255, 152, 0) !important
        box-sizing border-box
        border 3px solid
        .nav-item-right
            .percentage
                background-color  rgb(255, 152, 0) !important
            .progress
                .sliderbar
                    background-color  rgb(255, 152, 0) !important
    .nav-item
        display inline-block
        width 100%
        height 100%
        & > div
            height 100%
            display inline-block
            position relative
        .nav-item-left
            width 80px
            margin-right 19px
            text-align center
            svg
                vertical-align top
                margin-top 8px
            & > div
                font-size 12px
                line-height 15px
                white-space normal
                word-wrap break-word
        .nav-item-right
            width 145px
            .number
                font-size 14px
                position relative
                top 15px
            .percentage
                height 10px
                line-height 10px
                font-size 10px
                position absolute
                top 25px
                right 0
                color white
                padding 2px 8px
                border-radius 5px
            .progress
                width 100%
                height 6px
                position absolute
                top 45px
                background-color white
                border-radius 3px
                .sliderbar
                    height 6px
                    border-radius 3px
                    transition 0.2s
</style>
