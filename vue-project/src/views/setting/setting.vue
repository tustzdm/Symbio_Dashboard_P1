<template>
<el-col :span="12" :offset="6">
    <el-card style="padding-bottom:100px;font-family:Poppins;">
        <h2 style="width:100%;padding-left:150px">Settings</h2>
        <el-divider></el-divider>
        <el-col :span="23" :offset="1">
            <el-form label-position="left" label-width="80px">
                <el-form-item label="Role:">
                    <el-select v-model="role" placeholder="">
                        <el-option v-for="item in roleList" :key="item.id" :label="item.name" :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="Page:">
                    <el-select v-model="page" placeholder="">
                        <el-option v-for="item in pageList" :key="item.id" :label="item.name" :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="Function:">
                    <el-checkbox-group v-model="functions">
                        <el-checkbox v-for="item in functionList" :label="item.value" :key="item.id">{{item.name}}</el-checkbox>
                    </el-checkbox-group>
                </el-form-item>
            </el-form>
        </el-col>
        <div class="sbumitArea">
            <span>
                <el-button class="saveButton" type="primary" @click="onSubmit">{{$t('funcBtns.save')}}</el-button>
                <el-button class="cancelButton" @click="onCancel">{{$t('funcBtns.cancel')}}</el-button>
            </span>
        </div>
        {{functions}} {{sumFunctions}}
    </el-card>
</el-col>
</template>

<script>
export default {
    name: 'setting',
    data() {
        return {
            token: '',
            roleList: [],
            functionList: [],
            pageList: [],
            functions: [],
            page: '',
            role: '',
            sumFunctions: ''
        }
    },
    created() {
        this.token = localStorage.getItem('token');
        this.$axios({
            method: "get",
            url: `/setting/getRole?token=${this.token}`
        }).then(res => {
            this.selfLog(11111)
            this.selfLog(res);
            const data = res.data;
            this.roleList = res.data.cd.roleList;
            this.pageList = res.data.cd.menuList;
            this.functionList = res.data.cd.functionList;
        });
    },
    computed: {},
    watch: {
        functions: function () {
            this.sumFunctions = 0;
            for (var i = this.functions.length - 1; i >= 0; i--) {
                this.sumFunctions += this.functions[i];
            }
        },
        page: function () {
            if (this.role == '') {
                this.role=1;
            }
            this.functions=[];
            this.$axios({
                    method: "get",
                    url: `/setting/getRoleDetailInfo?token=${this.token}&menuId=${this.page}&roleId=${this.role}`
                }).then(res => {
                    this.selfLog(8888888)
                    this.selfLog(res)
                    let value = res.data.cd.value;
                    alert(value)
                    this.selfLog(this.functions)
                    let fns = [64, 32, 16, 8, 4, 2, 1];
                    for (let i = 0; i < fns.length; i++) {
                        if (value & fns[i] > 0) {
                            alert(fns[i])
                             this.functions.push(fns[i])
                        }
                    }
                    this.selfLog(this.functions)
                });
        },
        role: function () {
            if (this.page == '') {
                this.page = 1;
            }
            this.functions = [];
            this.$axios({
                method: "get",
                url: `/setting/getRoleDetailInfo?token=${this.token}&menuId=${this.page}&roleId=${this.role}`
            }).then(res => {
                this.selfLog(8888888)
                this.selfLog(res)
                let value = res.data.cd.value;
                let fns = [64, 32, 16, 8, 4, 2, 1];
                for (let i = 0; i < fns.length; i++) {
                    if (value & fns[i] > 0 ) {
                        this.functions.push(fns[i])
                    }
                }
            });
        }
    },
    methods: {
        onSubmit() {
            this.$axios({
                method: "post",
                url: `/setting/saveRole?token=${this.token}&menuId=${this.page}&value=${this.sumFunctions}&roleId=${this.role}`
            }).then(res => {
                this.selfLog(11111)
                this.selfLog(res);
            });
        },
        onCancel() {
            this.$router.go(-1);
        }
    }
}
</script>

<style lang="stylus" scoped>

</style>
