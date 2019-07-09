<template>
<div class="main" style="width:75%;margin-left:12.5%">
    <div class="top" style="margin:25px 0">
        <span style="padding-left:80px;font-family:Poppins;">Choose Page:</span>
        <select   v-model="page"  id="" style="margin-left:20px">
          <option v-for="item in pageList">{{item}}</option>
        </select>
        <router-link to="/editPages/edit">
            <el-button style="float:right;margin-right:10%;color:white;background-color:#7a85a1;line-heigt:32px;width:100px;">
                + Add
            </el-button>
        </router-link>
    </div>
    <table class="common-table" style="width:100%">
        <thead class="tableHead">
            <tr>
                <th v-for="item in tableHead">
                    {{item}}
                </th>
            </tr>
        </thead>
        <tbody>
            <tr class="tableBody" v-for="items in tableData">
                <td v-for="item in items">
                    <p>{{item}}</p>
                </td>
                <td>
                    <router-link to="/editPages/edit">
                        <el-button class="editDeleteBtn" icon="el-icon-edit" circle></el-button>
                    </router-link>
                    <el-button class="editDeleteBtn" type="danger" icon="el-icon-delete" circle></el-button>
                </td>
            </tr>
        </tbody>
    </table>
</div>
</template>

<script>
export default {
    name: 'editPages',
    data() {
        return {
            tableHead: ['No', 'Key', 'Type', 'Ext.Data', 'Required', 'Disable', 'en_US', 'zh_CN', 'PlaceHolder', 'DefaultValue', 'Constraint', 'Order', 'Operation'],
            pageList: [],
            tableData: [],
            page: ''
        }
    },
    created() {
        this.Fetch('/menu/getPageList', {
            method: 'get'
        }).then(res =>{
          this.pageList = res.cd;
          this.page = res.cd[0];
        });
        // Fetch("/mock/menu/getPageList", {
        //     method: "GET"
        // }).then(result => {
        //     return result.json();
        // }).then(res => {
        //     this.pageList = res.cd;
        //     //设置默认的page为pageList的第一项
        //     this.page = this.pageList[0];
        // });

        // this.$axios.get("http://127.0.0.1:8100/menu/getProductUIInfoList")
        //     .then((data) => {
        //       alert(12)
        //         console.log(data)
        //     })
    },
    watch: {
        page: function (val) {
            console.log(val);
            //get the page UIUIInfoList
            this.Fetch("/menu/getProductUIInfoList", {
                method: "GET"
            }).then(data => {
                console.log(data);
                this.tableData = data.cd.data;
            });
        }
    },
    methods: {}
}
</script>

<style scoped>
.main {
    height: 600px;
    border: 1px solid red;
}

.chooseTd {
    text-align: center
}

.chooseArea {
    width: 100px;
    display: inline-block;
}

.chooseArea .chooseY {
    float: left;
}

.chooseArea .chooseN {
    float: right;
}
</style>
