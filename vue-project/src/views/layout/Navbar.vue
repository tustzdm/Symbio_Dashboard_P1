<template>
  <div class="sidebar-wrap">
    <div class="left-area">
      <img src="../../assets/images/logo.png" alt="" class="logo">
    </div>

    <div class="middle-area">
      <div class="middle-area__cgwrap" @click="handleSwitchNavbar">
        <icon class="category-icon" name="index" :scale="2.8"></icon>
      </div>

      <div class="middle-area__msglist">
        <breadcrumb/>
      </div>
    </div>

    <div class="right-area">
      <!-- <lang-select class="lang" /> -->
      <notice/>
      <fullscreen class="fullscreen"/>
      <skin/>
      <infodrop/>
    </div>
  </div>
</template>

<script>
import fullscreen from '@/components/fullscreen'
import notice from '@/components/notice'
import langselect from '@/components/langselect'
import infodrop from './NavbarInfoDrop'
import seamlessList from '@/components/seamlessList'
import breadcrumb from '@/components/breadcrumb'
import skin from '@/components/skin'
import dayjs from 'dayjs'

export default {
  name: 'Sidebar',
  components: {
    fullscreen,
    notice,
    infodrop,
    breadcrumb,
    'lang-select': langselect,
    'seamless-list': seamlessList,
    skin
  },
  mounted() {
    if (!this.$store.getters.sidebar.sliderState) {
      this.$store.dispatch('setSliderState', 'full')
    }
  },
  data() {
    return {
      system_version: this.$store.state.app.version
    }
  },
  computed: {},
  methods: {
    handleSwitchNavbar() {
      this.$store.dispatch('toggleSideBar')
    }
  }
}
</script>

<style lang="stylus" scoped>
@import '../../assets/styl/variables.styl';

right-area-width = 250px;

.el-header {
  position: relative;

  .sidebar-wrap {
    display: flex;
  }

  .left-area {
    min-width: 304px;
    padding-left: 12px;
    box-sizing: border-box;
    height 64px
    .logo{
        width 150px
        height 38px
        margin 10px auto !important
    }
  }

  .middle-area {
    height: 64px;
    flex: 1;

    &__cgwrap {
      float: left;
      height: 100%;
      width: 60px;

      .category-icon {
        color: #ffffff;
        cursor: pointer;
        height: 100%;
        padding: 0 15px;
        transition: color 0.28s;

        &:hover {
          color: #41b883;
        }
      }
    }

    &__msglist {
      float: left;
      height: 50px;
      width: 'calc(%s - %s)' % (100% 50px);
      color: #808080;
      font-weight: 500;
      padding-left: 10px;
      box-sizing: border-box;
    }

    &:after {
      content: '';
      clear: both;
      display: block;
      width: 100%;
    }
  }

  .right-area {
    {flex-center};
    padding-right: 16px;
    height: 64px;
    min-width: right-area-width;

    .fullscreen {
      padding: 0 10px;
    }

    .lang {
      height: 64px;
    }
    
  }

  &__title, &__icon {
    vertical-align: middle;
  }

  &__title {
    height 30px
    font-size: 20px;
    font-weight: bold;
    .version-wrap {
      background: #eaeaea;
      padding: 3px 8px;
      font-size: 12px;
      margin-left: 8px;
      border-radius: 3px;
      vertical-align: top;
    }
  }
}
</style>
