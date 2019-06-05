<template>
  <div class="notice-area">
    <el-badge is-dot>
      <icon name="notice" :scale="2.8"></icon>
    </el-badge>
  </div>
</template>

<script>
export default {
  name: 'demo'
}
</script>

<style lang="stylus" scoped>
.notice-area {
  width: 26px;
  height: 34px;
  cursor: pointer;
  padding: 0 10px;
  margin-top 15px;
  transition: color 0.28s;
}

.el-badge__content {
  right: 10px !important;
}

svg {
  color: #ffffff;
  transition: color 0.28s;

  &:hover {
    color: #41b883;
  }
}
</style>
