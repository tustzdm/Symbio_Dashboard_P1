<template>
  <div class="sidebar-wrap">
    <link href="https://fonts.googleapis.com/css?family=Poppins" rel="stylesheet">

    <template v-for="(item,index) in routes">
      <!-- 单级 -->
      <router-link
        v-if="onlyOneShowingChildren(item.children) && !item.children[0].children && !item.alwaysShow"
        :key="item.children[0].name"
        :to="item.path+'/'+item.children[0].path"
      >
        <el-menu-item :index="item.path + '/' + item.children[0].path" style="padding: 0px">
          <div class="nav-item">
            <div class="inner-item">
              <div class="nav-item-sidebar" :style="{'background-color':sideBarColor[index]}"></div>
              <div class="nav-item-left">
                <icon
                  v-if="item.children[0].meta && item.children[0].meta.icon"
                  :name="item.children[0].meta.icon"
                  :scale="4"
                ></icon>
              </div>
              <div class="nav-item-right">
                <div
                  class="name"
                  v-if="item.children[0].meta && item.children[0].meta.title"
                >{{getTitle(item.children[0].meta.title)}}</div>

                <div class="progressdata">{{dataprogress[index]}}</div>
                <img src="../../assets/images/dec-selected.png" alt class="dec-selected">
              </div>
            </div>
          </div>
        </el-menu-item>
      </router-link>
    </template>
    <footer class="app-footer">北京信必优信息技术有限公司</footer>
  </div>
</template>

<script>
export default {
  name: 'SidebarItem',
  data: function() {
    return {
      sideBarColor: ['#DE6B58', '#E1A084', '#D2ACA3', '#EBDFDF'],
      dataprogress: ['30%', '45%', '80%', '20%']
    }
  },
  props: {
    routes: {
      type: Array
    }
  },
  computed: {
    sliderState() {
      return this.$store.getters.sidebar.sliderState
    }
  },
  methods: {
    onlyOneShowingChildren(children) {
      return children.filter(item => !item.hidden).length === 1
    },
    getTitle(title) {
      if (this.$te(`route.${title}`)) {
        return this.$t(`route.${title}`)
      }
      return title
    }
  }
}
</script>

<style lang="stylus" scoped>
.sidebar-wrap {
  .el-submenu, .el-menu-item {
    font-weight: bolder;
    position: relative;
    height: 115px;
    line-height: 35px;
    color: #464B5E;
    margin: 15px 3px;
  }

  .is-active {
    font-weight: bolder;
    border-radius: 0.625rem;
    box-shadow: 0 0.46875rem 2.1875rem rgba(90, 97, 105, 0.1), 0 0.9375rem 1.40625rem rgba(90, 97, 105, 0.1), 0 0.25rem 0.53125rem rgba(90, 97, 105, 0.12), 0 0.125rem 0.1875rem rgba(90, 97, 105, 0.1);
    border-color: #FFBC95;
    background-color: white;
    font-size: 16px;
  }

  .el-menu-item:hover {
    background-color: white;
    border-radius: 0.625rem;
    box-shadow: 0 0.46875rem 2.1875rem rgba(90, 97, 105, 0.1), 0 0.9375rem 1.40625rem rgba(90, 97, 105, 0.1), 0 0.25rem 0.53125rem rgba(90, 97, 105, 0.12), 0 0.125rem 0.1875rem rgba(90, 97, 105, 0.1);
  }

  .nav-item {
    .inner-item {
      background-color: white;
      display: inline-block;
      position: absolute;
      width: 215px;
      height: 100px;
      margin: 8px 13px;

      & > div {
        height: 100%;
        display: inline-block;
        position: relative;
      }

      .nav-item-sidebar {
        width: 4px;
      }

      .nav-item-left {
        width: 55px;
        color: #F09648;

        svg {
          margin-top: 28px;
          padding-left: 10px;
        }
      }

      .nav-item-right {
        margin-right: 10px;
        position: relative;
        font-family: 'Poppins';

        .name {
          font-weight: bold;
          white-space: normal;
          word-wrap: break-word;
          padding: 8px 0 0 2px;
          font-size: 14px;
        }

        .progressdata {
          position: absolute;
          bottom: 27px;
          left: 10px;
          font-size: 13px;
        }

        .dec-selected {
          position: absolute;
          bottom: 13px;
        }
      }
    }
  }
}

.app-footer {
  font-size: 12px;
  position: absolute;
  bottom: 8px;
  left: 40px;
}
</style>
