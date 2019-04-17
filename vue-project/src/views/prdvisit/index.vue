<template>
  <div class="bugs-root">
    <el-card shadow="hover" style="margin-bottom: 20px">
      <el-form ref="bugForm" :model="bugForm" size="mini" :inline="true">
        <el-form-item label="Project Type" label-width="100px">
          <el-select v-model="bugForm.proType" filterable clearable placeholder="Please Select"  @change="changeHandle('pro')" class="el-select--custom">
              <el-option v-for="item in proTypeOpt" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="Release" label-width="80px">
          <el-select v-model="bugForm.release" filterable clearable placeholder="Please Select"  @change="changeHandle('release')" class="el-select--custom">
              <el-option v-for="item in releaseOpt" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="Test set" label-width="80px">
          <el-select v-model="bugForm.testset" filterable clearable placeholder="Please Select"  @change="changeHandle('bug')" class="el-select--custom">
              <el-option v-for="item in testSetOpt" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-button type="primary" size="mini" class="mg-l" @click="handleBtn('search')" style="margin-left:10px">Search</el-button>
        <el-button type="warning" size="mini" icon="el-icon-refresh" class="mg-l" @click="handleBtn('refresh')">Refresh</el-button>
      </el-form>
    </el-card>
    <template v-if="showProType">
        <el-card shadow="hover" style="margin-bottom:10px">
            <el-table :data="dataSet.project" height="300" border style="width: 100%;background-color:rgb(240,240,240)" :stripe="true" ref="multipleTable" tooltip-effect="dark" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55"></el-table-column>
                <el-table-column type="index"  label="ID" ></el-table-column>
                <el-table-column prop="sourceScreen" label="Source Screen" >
                    <template slot-scope="scope">
                        <img :src="scope.row.sourceScreen" alt="" class="el-img-custom">
                        <el-upload class="el-upload-custom" :action="uploadUrl">
                            <el-button size="mini" type="primary" class="el-btn-update" v-if="scope.row.sourceScreen">Update</el-button>
                            <el-button size="mini" type="primary" class="el-btn-update" v-else>Upload</el-button>
                        </el-upload>
                    </template>
                </el-table-column>
                <el-table-column prop="targetScreen" label="Target Screen" >
                    <template slot-scope="scope">
                        <img :src="scope.row.targetScreen" alt="" class="el-img-custom">
                        <el-upload class="el-upload-custom" :action="uploadUrl">
                            <el-button size="mini" type="primary" class="el-btn-update" v-if="scope.row.targetScreen">Update</el-button>
                            <el-button size="mini" type="primary" class="el-btn-update" v-else>Upload</el-button>
                        </el-upload>
                    </template>
                </el-table-column>
                <el-table-column prop="pro-field-1" label="pro-field-1" >
                    <template slot-scope="scope">
                        <span v-if="!scope.row.editFlag">{{ scope.row['pro-field-1'] }}</span>
                        <span v-if="scope.row.editFlag" class="cell-edit-input"><el-input v-model="scope.row['pro-field-1']" placeholder="SID/Steps"></el-input></span>
                    </template>
                </el-table-column>
                <el-table-column prop="pro-field-2" label="pro-field-2" >
                    <template slot-scope="scope">
                        <span v-if="!scope.row.editFlag">{{ scope.row['pro-field-2'] }}</span>
                        <span v-if="scope.row.editFlag" class="cell-edit-input"><el-input v-model="scope.row['pro-field-2']" placeholder="SID/Steps"></el-input></span>
                    </template>
                </el-table-column>
                <el-table-column prop="pro-field-3" label="pro-field-3" >
                    <template slot-scope="scope">
                        <span v-if="!scope.row.editFlag">{{ scope.row['pro-field-3'] }}</span>
                        <span v-if="scope.row.editFlag" class="cell-edit-input"><el-input v-model="scope.row['pro-field-3']" placeholder="SID/Steps"></el-input></span>
                    </template>
                </el-table-column>
                <el-table-column prop="pro-field-4" label="pro-field-4" >
                    <template slot-scope="scope">
                        <span v-if="!scope.row.editFlag">{{ scope.row['pro-field-4'] }}</span>
                        <span v-if="scope.row.editFlag" class="cell-edit-input"><el-input v-model="scope.row['pro-field-4']" placeholder="SID/Steps"></el-input></span>
                    </template>
                </el-table-column>
                <el-table-column prop="pro-field-5" label="pro-field-5" >
                    <template slot-scope="scope">
                        <span v-if="!scope.row.editFlag">{{ scope.row['pro-field-5'] }}</span>
                        <span v-if="scope.row.editFlag" class="cell-edit-input"><el-input v-model="scope.row['pro-field-5']" placeholder="SID/Steps"></el-input></span>
                    </template>
                </el-table-column>
                <el-table-column label="Operate" width="120">
                    <template slot-scope="scope">
                        <el-button size="mini" type="primary" @click="handleBtn('edit', scope.row)" class="el-icon-edit" v-if="!scope.row.editFlag"></el-button>
                        <el-button size="mini" type="danger" @click="handleBtn('del', 'project')" class="el-icon-delete" v-if="!scope.row.editFlag"></el-button>
                        <el-button size="mini" type="primary" @click="handleBtn('save', scope.row)" class="el-icon-check" v-else></el-button>
                    </template>
            </el-table-column>
            </el-table>
        </el-card>
        <el-pagination background layout="total, sizes, prev, pager, next, jumper" 
            :total="dataSet.project.length" 
            :page-sizes="[10, 20, 30, 40, 50]"
            :page-size="bugForm.pageSize"
            style="text-align:center;margin: 10px 0"
            @current-change="currentChange"
            @size-change="sizeChange">
        </el-pagination> 
    </template>
    <template v-if="showRelease">
        <el-card shadow="hover" style="margin-bottom:10px">
            <el-table :data="dataSet.release" height="300" border style="width: 100%;background-color:rgb(240,240,240)" :stripe="true" ref="multipleTable" tooltip-effect="dark" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55"></el-table-column>
                <el-table-column type="index"  label="ID" ></el-table-column>
                <el-table-column relp="sourceScreen" label="Source Screen" >
                    <template slot-scope="scope">
                        <img :src="scope.row.sourceScreen" alt="" class="el-img-custom">
                        <el-upload class="el-upload-custom" :action="uploadUrl">
                            <el-button size="mini" type="primary" class="el-btn-update" v-if="scope.row.sourceScreen">Update</el-button>
                            <el-button size="mini" type="primary" class="el-btn-update" v-else>Upload</el-button>
                        </el-upload>
                    </template>
                </el-table-column>
                <el-table-column relp="targetScreen" label="Target Screen" >
                    <template slot-scope="scope">
                        <img :src="scope.row.targetScreen" alt="" class="el-img-custom">
                        <el-upload class="el-upload-custom" :action="uploadUrl">
                            <el-button size="mini" type="primary" class="el-btn-update" v-if="scope.row.targetScreen">Update</el-button>
                            <el-button size="mini" type="primary" class="el-btn-update" v-else>Upload</el-button>
                        </el-upload>
                    </template>
                </el-table-column>
                <el-table-column relp="rel-field-1" label="rel-field-1" >
                    <template slot-scope="scope">
                        <span v-if="!scope.row.editFlag">{{ scope.row['rel-field-1'] }}</span>
                        <span v-if="scope.row.editFlag" class="cell-edit-input"><el-input v-model="scope.row['rel-field-1']" placeholder="SID/Steps"></el-input></span>
                    </template>
                </el-table-column>
                <el-table-column relp="rel-field-2" label="rel-field-2" >
                    <template slot-scope="scope">
                        <span v-if="!scope.row.editFlag">{{ scope.row['rel-field-2'] }}</span>
                        <span v-if="scope.row.editFlag" class="cell-edit-input"><el-input v-model="scope.row['rel-field-2']" placeholder="SID/Steps"></el-input></span>
                    </template>
                </el-table-column>
                <el-table-column relp="rel-field-3" label="rel-field-3" >
                    <template slot-scope="scope">
                        <span v-if="!scope.row.editFlag">{{ scope.row['rel-field-3'] }}</span>
                        <span v-if="scope.row.editFlag" class="cell-edit-input"><el-input v-model="scope.row['rel-field-3']" placeholder="SID/Steps"></el-input></span>
                    </template>
                </el-table-column>
                <el-table-column relp="rel-field-4" label="rel-field-4" >
                    <template slot-scope="scope">
                        <span v-if="!scope.row.editFlag">{{ scope.row['rel-field-4'] }}</span>
                        <span v-if="scope.row.editFlag" class="cell-edit-input"><el-input v-model="scope.row['rel-field-4']" placeholder="SID/Steps"></el-input></span>
                    </template>
                </el-table-column>
                <el-table-column relp="rel-field-5" label="rel-field-5" >
                    <template slot-scope="scope">
                        <span v-if="!scope.row.editFlag">{{ scope.row['rel-field-5'] }}</span>
                        <span v-if="scope.row.editFlag" class="cell-edit-input"><el-input v-model="scope.row['rel-field-5']" placeholder="SID/Steps"></el-input></span>
                    </template>
                </el-table-column>
                <el-table-column label="Operate" width="120">
                    <template slot-scope="scope">
                        <el-button size="mini" type="primary" @click="handleBtn('edit', scope.row)" class="el-icon-edit" v-if="!scope.row.editFlag"></el-button>
                        <el-button size="mini" type="danger" @click="handleBtn('del', 'release')" class="el-icon-delete" v-if="!scope.row.editFlag"></el-button>
                        <el-button size="mini" type="primary" @click="handleBtn('save', scope.row)" class="el-icon-check" v-else></el-button>
                    </template>
            </el-table-column>
            </el-table>
        </el-card>
        <el-pagination background layout="total, sizes, prev, pager, next, jumper" 
            :total="dataSet.release.length" 
            :page-sizes="[10, 20, 30, 40, 50]"
            :page-size="bugForm.pageSize"
            style="text-align:center;margin: 10px 0"
            @current-change="currentChange"
            @size-change="sizeChange">
        </el-pagination> 
    </template>
    <template v-if="showTestset">
        <el-card shadow="hover" style="margin-bottom:10px">
            <el-table :data="dataSet.testSet" height="300" border style="width: 100%;background-color:rgb(240,240,240)" :stripe="true" ref="multipleTable" tooltip-effect="dark" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55"></el-table-column>
                <el-table-column type="index"  label="ID" ></el-table-column>
                <el-table-column bugp="sourceScreen" label="Source Screen" >
                    <template slot-scope="scope">
                        <img :src="scope.row.sourceScreen" alt="" class="el-img-custom">
                        <el-upload class="el-upload-custom" :action="uploadUrl">
                            <el-button size="mini" type="primary" class="el-btn-update" v-if="scope.row.sourceScreen">Update</el-button>
                            <el-button size="mini" type="primary" class="el-btn-update" v-else>Upload</el-button>
                        </el-upload>
                    </template>
                </el-table-column>
                <el-table-column bugp="targetScreen" label="Target Screen" >
                    <template slot-scope="scope">
                        <img :src="scope.row.targetScreen" alt="" class="el-img-custom">
                        <el-upload class="el-upload-custom" :action="uploadUrl">
                            <el-button size="mini" type="primary" class="el-btn-update" v-if="scope.row.targetScreen">Update</el-button>
                            <el-button size="mini" type="primary" class="el-btn-update" v-else>Upload</el-button>
                        </el-upload>
                    </template>
                </el-table-column>
                <el-table-column bugp="bug-field-1" label="bug-field-1" >
                    <template slot-scope="scope">
                        <span v-if="!scope.row.editFlag">{{ scope.row['bug-field-1'] }}</span>
                        <span v-if="scope.row.editFlag" class="cell-edit-input"><el-input v-model="scope.row['bug-field-1']" placeholder="SID/Steps"></el-input></span>
                    </template>
                </el-table-column>
                <el-table-column bugp="bug-field-2" label="bug-field-2" >
                    <template slot-scope="scope">
                        <span v-if="!scope.row.editFlag">{{ scope.row['bug-field-2'] }}</span>
                        <span v-if="scope.row.editFlag" class="cell-edit-input"><el-input v-model="scope.row['bug-field-2']" placeholder="SID/Steps"></el-input></span>
                    </template>
                </el-table-column>
                <el-table-column bugp="bug-field-3" label="bug-field-3" >
                    <template slot-scope="scope">
                        <span v-if="!scope.row.editFlag">{{ scope.row['bug-field-3'] }}</span>
                        <span v-if="scope.row.editFlag" class="cell-edit-input"><el-input v-model="scope.row['bug-field-3']" placeholder="SID/Steps"></el-input></span>
                    </template>
                </el-table-column>
                <el-table-column bugp="bug-field-4" label="bug-field-4" >
                    <template slot-scope="scope">
                        <span v-if="!scope.row.editFlag">{{ scope.row['bug-field-4'] }}</span>
                        <span v-if="scope.row.editFlag" class="cell-edit-input"><el-input v-model="scope.row['bug-field-4']" placeholder="SID/Steps"></el-input></span>
                    </template>
                </el-table-column>
                <el-table-column bugp="bug-field-5" label="bug-field-5" >
                    <template slot-scope="scope">
                        <span v-if="!scope.row.editFlag">{{ scope.row['bug-field-5'] }}</span>
                        <span v-if="scope.row.editFlag" class="cell-edit-input"><el-input v-model="scope.row['bug-field-5']" placeholder="SID/Steps"></el-input></span>
                    </template>
                </el-table-column>
                <el-table-column label="Operate" width="120">
                    <template slot-scope="scope">
                        <el-button size="mini" type="primary" @click="handleBtn('edit', scope.row)" class="el-icon-edit" v-if="!scope.row.editFlag"></el-button>
                        <el-button size="mini" type="danger" @click="handleBtn('del', 'testSet')" class="el-icon-delete" v-if="!scope.row.editFlag"></el-button>
                        <el-button size="mini" type="primary" @click="handleBtn('save', scope.row)" class="el-icon-check" v-else></el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-card>
        <el-pagination background layout="total, sizes, prev, pager, next, jumper" 
            :total="dataSet.testSet.length" 
            :page-sizes="[10, 20, 30, 40, 50]"
            :page-size="bugForm.pageSize"
            style="text-align:center;margin: 10px 0"
            @current-change="currentChange"
            @size-change="sizeChange">
        </el-pagination> 
    </template>
  </div>
</template>

<script>
import { getBugSelect, getBugData, uploadUrl, saveBugs, delBugs} from '@/api/index';
export default {
  name: 'bugs',
  data() {
    return {
        proTypeOpt: [],
        releaseOpt: [],
        testSetOpt: [],
        otherOpt: [],
        bugForm: {
            proType: 1,
            release: "",
            testset: "",
            pageSize: 10,
            currentPage: 0
        },
        showProType: true,
        showRelease: false,
        showTestset: false,
        dataSet: {},
        multipleSelection: [],
        uploadUrl: uploadUrl,
     
    }
  },
  mounted() {
    this.initSelect();
    this.searchData();
  },
  methods: {
    initSelect() {
        getBugSelect().then(res => {
            this.proTypeOpt = res.data;
            this.changeHandle('pro');
        })
    },
    changeHandle(type) {
        if (type == 'pro') {
            this.bugForm.release = "";
            this.bugForm.testset = "";
            let proType = this.proTypeOpt.filter(p => p.id == this.bugForm.proType);
            this.releaseOpt = proType[0].release;
        }
        if (type == 'release') {
            this.bugForm.testset = "";
            let release = this.releaseOpt.filter(r => r.id == this.bugForm.release);
            this.testSetOpt = release[0].testSet;
            this.testSetOpt.unshift({id: -1, name: "All"});
        }
    },
    searchData() {
        getBugData(this.bugForm).then(res => {
            this.dataSet = res.data;
        });
    },
    handleBtn(type, data) {
        if (type == 'edit') {
            data.editFlag = true;
        }
        if (type == 'save') {
            data.editFlag = false;
            saveBugs(data).then(res => {
            });
        }
        if (type == 'del') {
            this.deleteBugs(data);
        }
        if (type == 'search') {
            if (!this.bugForm.release && !this.bugForm.testset) {
                this.showProType = true;
                this.showRelease = false;
                this.showTestset = false;
                return;
            }
            if (this.bugForm.release && !this.bugForm.testset) {
                this.showProType = false;
                this.showRelease = true;
                this.showTestset = false;
                return;
            }
            if (this.bugForm.testset) {
                this.showProType = false;
                this.showRelease = false;
                this.showTestset = true;
                return;
            }
        }
        if (type == 'refresh') {
            this.searchData();
        }
    },
    currentChange(val) {
        if (val == this.bugForm.currentPage) {return;}
        this.bugForm.currentPage = val;
    },
    sizeChange(val) {
        if (val == this.bugForm.pageSize) {return;}
        this.bugForm.pageSize = val;
    },
    handleSelectionChange(val) {
        let seleted = [];
        if(val && val.length > 0) {
            val.map(item => {
                seleted.push(item.id);
            });
        } else {
            this.multipleSelection = [];
        }
        this.multipleSelection = seleted;
    },
    deleteBugs(data) {
          if(this.multipleSelection.length <= 0) {
              this.$message.error("请选择一行！");
              return;
          };
          this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
              confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.dataSet[data] = this.dataSet[data].filter(item => this.multipleSelection.indexOf(item.id) < 0);
                delBugs(this.multipleSelection).then(res => {
                    if (res.code == 200) {
                        this.$message.success("删除成功！");
                    }
                });
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });          
            });
      }
  }
}
</script>
<style lang="stylus" scoped>
.el-form-item
    display inline-block
    margin 0
.el-button--warning
    background #ff8400
    border-color #ff8400
.el-img-custom
    width 100px
    height 80px
.el-select--custom
    max-width 130px
</style>
