<template>
  <div class="info-area">
    <el-dropdown trigger="hover" @command="dropdownComm" class="info-area__dropdown">
      <span class="el-dropdown-link">
        {{username}}
        <i class="el-icon-arrow-down el-icon--right"></i>
      </span>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item v-if="this.user!='15340193294'" command="forms">{{$t('sidebarDropDown.form')}}</el-dropdown-item>
        <el-dropdown-item v-if="this.user!='15340193294'" command="EditPages">{{$t('sidebarDropDown.pageElement')}}</el-dropdown-item>
        <el-dropdown-item command="logout">{{$t('sidebarDropDown.logout')}}</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
    <!-- <div class="info-area__avatar">
      <img :src="avatarUrl" />
    </div>-->
  </div>
</template>

<script>
import storage from '@/utils/storage'
export default {
  name: 'navbarInfoDrop',
  data() {
    return {
      user:localStorage.getItem('loginUser')
    }
  },
  computed: {
    username() {
      // return this.$store.state.user.name || storage.get('USER').name
      return  storage.get('username')//
    },
    avatarUrl() {
      return this.$store.state.user.avatar || storage.get('USER').avatar
    }
  },
  methods: {
    dropdownComm(command) {
      switch (command) {
        case 'logout':
          this.logouthandle()
          break
        case 'forms':
          this.jumpToFroms()
          break
        case 'EditPages':
          this.editPages()
          break  
      }
    },
    async logouthandle() {
      await this.$store.dispatch('logout')
      this.$router.push({
        path: '/'
      })
    },
    jumpToFroms() {
      this.$router.push({ path: '/forms' })
    },
    editPages() {
      this.$router.push({ path: '/editPages' })
    }
  }
}
</script>

<style lang="stylus" scoped>
.inline-block-box {
  display: inline-block;
  vertical-align: middle;
}

.info-area {
  &__dropdown {
    @extend .inline-block-box;
    cursor: pointer;
    color: #ff8400;
  }

  &__avatar {
    @extend .inline-block-box;
    padding-left: 10px;
    width: 40px;
    height: 40px;
    overflow: hidden;
    cursor: pointer;

    img {
      border-radius: 50%;
      width: 40px;
      height: 40px;
    }
  }
}
</style>
