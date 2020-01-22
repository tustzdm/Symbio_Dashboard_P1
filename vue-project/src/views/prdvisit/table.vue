<template>
<div class="tableContainer">
    <el-table :data="tableData" style="width: 100%">
        <template v-for="item in tableColownms">
        <el-table-column  :key="item.field" :prop="item.field" :label="item.label" width="150" v-if="item.type!='hidden'">
            <template  slot-scope="scope" >
                <a :href="scope.row[item.field]"  target="_Blank" v-if="item.field == 'thumbnailUrl'">{{$t('bugsoverview.image')}}</a>
                <a :href="scope.row[item.field]"  target="_Blank" v-if="item.field == 'fileUrl'">{{$t('bugsoverview.image')}}</a>
                <span v-if="!['thumbnailUrl','fileUrl'].includes(item.field)">{{scope.row[item.field]}}</span>
            </template> 
        </el-table-column>
        </template>
    </el-table>
    <div class="fanye" style="margin:15px 0">
        <el-pagination background layout="total, sizes, prev, pager, next, jumper" :total="tableData.length" :page-sizes="[20, 30, 40, 50, 100, 500]" :page-size="pageSize" style="text-align:center;margin-bottom:30px" @current-change="currentChange" @size-change="sizeChange"></el-pagination>
    </div>
</div>
</template>

<script>
import data from './data'
export default {
    data() {
        return {
            tableData: [],
            tableColownms: {},
            token: '',
            sss:'',
            lang:'',
            pageSize:20
        }
    },
    created() {
        this.lang = this.$store.state.app.language=='zh'? 'zh_CN':'en_US';
        this.token = localStorage.getItem('token');
        this.$axios.get(`/bug/getList?token=${this.token}&locale=${this.lang}`).then(res => {
            this.selfLog(3333333333333)
            this.selfLog(res);
            this.tableColownms=res.data.cd.columns; 
            this.tableData = res.data.cd.data;          
            this.selfLog(3333333333333)
        }).catch(err => {
            this.selfLog(err);
        });
        
        // this.Fetch(`/bug/getList?token=${this.token}`, {
        //     method: "GET"
        // }).then(res => {
        //     this.selfLog(2222222222222)
        //     this.selfLog(res);
        //     this.selfLog(3333333333333)
        // }).catch(err => {
        //     alert(err);
        // });
    },
    watch: {
    },
    computed: {
    },
    methods: {
       currentChange(currentPage) {
            this.currentPage = currentPage
        },
        sizeChange(size) {
            this.pageSize = size
        },
    }
}
</script>

<style scoped>
</style>
