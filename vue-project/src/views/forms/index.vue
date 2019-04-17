<template>
  <div class="container">
    <div class="form-warp">
        <div class="list-column">
            <h3 style="text-align:center">Commom Report</h3>
            <div class="common-ele">
                <draggable v-model="commomReports" :options="dragOptions" :move="onMove" @end="onEnd">
                    <transition-group name="no" class="common-report" tag="ul">
                    <li class="list-group-item list-group-item-common-report" :style="{width: element.type == 1 ? '260px': '140px'}" v-for="element in commomReports" :key="element.idx">{{element.name}}</li>
                    </transition-group>
                </draggable>
            </div>

            <h3 style="text-align:center">List</h3>
            <div class="common-ele">
                <draggable v-model="commomReports" :options="dragOptions" :move="onMove" @end="onEnd">
                    <transition-group name="no" class="common-report" tag="ul">
                    <li class="list-group-item list-group-item-common-report" style="width:'200px'" v-for="element in reportList" :key="element.idx">{{element.name}}</li>
                    </transition-group>
                </draggable>
            </div>

            <h3 style="text-align:center">RowChart Report</h3>
            <div class="common-ele">
                <draggable v-model="commomReports" :options="dragOptions" :move="onMove" @end="onEnd">
                    <transition-group name="no" class="common-report" tag="ul">
                    <li class="list-group-item list-group-item-common-report" style="width:'200px'" v-for="element in rowChartReport" :key="element.idx">{{element.name}}</li>
                    </transition-group>
                </draggable>
            </div>
            
        </div>
        <div class="list-column">
            <h3 style="text-align:center">Report Dropdown List</h3>
            <div class="draggable-ele">
                <draggable v-model="reportList" :options="dragOptions" :move="onMove" @end="onEnd">
                    <transition-group name="no" class="list-group" tag="ul">
                    <li class="list-group-item" v-for="element in reportDropdownList" :key="element.idx">{{element.name}}</li>
                    </transition-group>
                </draggable>
            </div>

            <h3 style="text-align:center">预览效果</h3>
            <div class="common-ele">
                 <el-select v-model="chartGroup" placeholder="请选择">
                <el-option label="common" value="common"></el-option>
                <el-option label="echart4" value="echart4"></el-option>
                <el-option label="echart5" value="echart5"></el-option>
                <el-option label="echart6" value="echart6"></el-option>
            </el-select>
            </div>

        </div>
        <div class="list-column">
            <h3 style="text-align:center">Support Reports</h3>
            <div class="support-ele">
                <draggable v-model="reportPool" :options="dragOptions" :move="onMove" @end="onEnd">
                    <transition-group name="no" class="list-group" tag="ul">
                    <li class="list-group-item" v-for="element in supportReports" :key="element.idx">{{element.name}}</li>
                    </transition-group>
                </draggable>
            </div>
        </div>
    </div>
    <div class="list-group-btn">
        <el-button type="info" @click="init">取消</el-button>
        <el-button type="primary" @click="save">确定</el-button>
    </div>
  </div>
</template>

<script>
import draggable from "vuedraggable";
import { getFormControl, saveFormControl } from '@/api/index'
export default {
  name: "forms",
  components: {
    draggable
  },
  data() {
    return {
      commomReports: [],
      reportList: [],
      rowChartReport: [],
      reportDropdownList: [],   
      supportReports: [],   
      editable: true,
      isDragging: false,
      delayedDragging: false,
      isMove: true
    };
  },
  mounted() {
      this.init();
  },
  methods: {
    init() {
        getFormControl().then(res => {
          if (res.ec == 0) {
              this.commomReports = res.cd.listChartCommon;
              this.reportList = res.cd.listListUsed;
              this.rowChartReport = res.cd.listRowChartUsed;
              this.reportDropdownList = res.cd.listChartOther;
              this.supportReports = res.cd.listSupport;
          }
      });
    },
    onMove({ relatedContext, draggedContext }, { target }) {
      this.isMove = true;
      const relatedElement = relatedContext.element;
      const draggedElement = draggedContext.element;
      const bAddCommon = draggedElement.bAddcomm;
      const targetName = target.className;
      console.log(bAddCommon, targetName);
      if(!bAddCommon && targetName.indexOf('common-report') > 0){
          this.$message.warning("不能移动到commom report");
          this.isMove = false;
      }
      return (
        (!relatedElement || !relatedElement.fixed) && !draggedElement.fixed && this.isMove
      );
    },
    save() {
        let param = {
            commomReports: this.commomReports,
            reportList: this.reportList,
            reportPool: this.reportPool
        };
        saveFormControl(param).then(res => {
            if (res.code == 200) {
                this.$message.success("保存成功！");
            }
        });
    },
    onEnd(a) {
        if(a.to.className == 'common-report') {
        }
    }
  },
  computed: {
    dragOptions() {
      return {
        animation: 0,
        group: "description",
        disabled: !this.editable,
        ghostClass: "ghost"
      };
    }
  },
  watch: {
    isDragging(newValue) {
      if (newValue) {
        this.delayedDragging = true;
        return;
      }
      this.$nextTick(() => {
        this.delayedDragging = false;
      });
    }
  }
};
</script>

<style lang="stylus" scoped>
.form-warp
    display flex
    .list-column
        flex 1
        margin-right 10px
        margin-bottom 10px
        .list-group-item
            background #ff8400
            width 240px
            height 40px
            border-radius 5px
            text-align center
            line-height 40px 
            cursor pointer
            margin auto
            margin-bottom 10px
        .list-group-item-common-report
            float left
            margin 10px
        .draggable-ele
            min-height 440px
            padding 10px
            border 1px dashed #ccc
            border-radius 5px
        .common-ele
            min-height 180px
            padding 10px
            border 1px dashed #ccc
            border-radius 5px
        .support-ele
            min-height 700px
            padding 10px
            border 1px dashed #ccc
            border-radius 5px
.list-group-btn
    text-align center
</style>
