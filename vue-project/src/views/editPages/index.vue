<template>
<div class="main" style="width:75%;margin-left:12.5%">
    <div class="top" style="margin:25px 0">
        <span style="padding-left:80px;font-family:Poppins;">Choose Page:</span>
        <select v-model="page" id="" style="margin-left:20px">
            <option v-for="item in pageList">{{item.value}}</option>
        </select>
        <el-button @click="addRouter" style="float:right;margin-right:10%;color:white;background-color:#7a85a1;line-heigt:32px;width:100px;">
            + Add
        </el-button>
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
                <td v-for="(item,index) in items" v-if="['dbField','page','label','dispStatus','version','validation','display'].indexOf(index) === -1">
                    <p>{{item}}</p>
                </td>
                <td :order="items.idx">
                    <el-button @click="returnTrIndex();editRouter()" :order="items.idx" class="editDeleteBtn" icon="el-icon-edit" circle></el-button>
                    <el-button @click="returnTrIndex();deleteTr()" :order="items.idx" class="editDeleteBtn" type="danger" icon="el-icon-delete" circle></el-button>
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
            page: '',
            trIndex: '123'
        }
    },
    created() {
        this.Fetch('/setting/getUiInfoPage?token=111&type=Page_Element_Setting', {
            method: 'GET'
        }).then(res => {
            this.pageList = res.cd;
            this.page = res.cd[0].value;
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
            this.Fetch(`/ui/getUiInfoList?token=111&page=${this.page}`, {
                method: "GET"
            }).then(data => {
                console.log(data.cd);
                this.tableData = data.cd;
            });
        },

    },
    methods: {
        beforeRemove(file, fileList) {
            return this.$confirm(`确定移除 ${file.name}？`)
        },
        addRouter() {
            this.$router.push({
                path: 'add',
                name: 'add',
                params: {
                    page: this.page
                }
            })
        },
        editRouter() {
            this.$router.push({
                path: 'edit',
                name: 'edit',
                params: {
                    page: this.page,
                    tr: this.tableData[this.trIndex - 1], //这里的数字要根据点击的是第几行
                }
            })
        },
        returnTrIndex() {
            return this.trIndex = event.target.parentNode.getAttribute('order');
        },
        deleteTr() {
            // var tr = this.tableData[this.trIndex - 1].id;
            // console.log(tr);    
            // console.log(typeof(tr));
            this.$axios.post(`/ui/removeUiElement?token=111&id=26`).then(res => {//token先是写死的
                // success callback
                console.log(res);
                var ec = res.data.ec;
                debugger;
                if (ec != '0') {
                    alert(res.ec + ", " + res.em);
                } else {
                    this.$message.success("Delete Sucess！");
                    console.log(this.tableData);
                }
            }).catch(err => {
                alert(err);
            });
        }
    }
}
</script>

<style scoped>
.main {
    height: 600px;
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
