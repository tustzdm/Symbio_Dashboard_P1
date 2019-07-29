<template>
<div class="main" style="width:75%;margin-left:12.5%">
    <div class="top" style="margin:25px 0">
        <span style="padding-left:80px;font-family:Poppins;">Choose Page:</span>
        <select v-model="page" id="" style="margin-left:20px;height:30px">
            <option v-for="item in pageList">{{item.value}}</option>
        </select>
        <el-button @click="addRouter" style="float:right;margin-right:10%;color:white;background-color:#7a85a1;heigt:27px;width:150px;border:none">
            + Add Element
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
                <td v-for="(item,index) in items" v-if="['page','label','dispStatus','version','validation','display'].indexOf(index) === -1">
                    <p v-if="['isRequired', 'isDisable'].indexOf(index) == -1">{{item}}</p>
                    <el-switch key="dsfg" :model="item" v-if="['isRequired', 'isDisable'].indexOf(index) != -1" disabled active-color="#13ce66" inactive-color="#ff4949" active-value=1 inactive-value=0>
                    </el-switch>
                    <!-- <p v-if="index == 'constCondition'">{{JSON.parse(item.constCondition).maxLength}}</p> -->
                </td>
                <td :formId="items.id">
                    <el-button @click="editRouter()" :formId="items.id" class="editDeleteBtn" icon="el-icon-edit" circle></el-button>
                    <el-button v-if="items.dispStatus ==1" @click="deleteTr()" :formId="items.id" class="editDeleteBtn" type="danger" icon="el-icon-delete" circle></el-button>
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
            tableHead: ['ID', 'Key', 'DbFeild', 'Type', 'Ext.Data', 'Required', 'Disable', 'en_US', 'zh_CN', 'PlaceHolder', 'DefaultValue', 'Constraint', 'Order', 'Operation'],
            pageList: [],
            tableData: [],
            page: '',
            trIndex: '1'
        }
    },
    created() {
        this.Fetch('/setting/getUiInfoPage?token=111&type=Page_Element_Setting', {
            method: 'GET'
        }).then(res => {
            this.pageList = res.cd;
            this.page = res.cd[1].value;
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
            this.getTableData();
        },

    },
    methods: {
        getTableData(){
            this.Fetch(`/ui/getUiInfoList?token=111&page=${this.page}`, {
                method: "GET"
            }).then(data => {
                console.log(data.cd);
                this.tableData = data.cd;
            });
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
            this.returnTrIndex();
            
            this.$router.push({
                path: 'edit',
                name: 'edit',
                params: {
                    tr: this.tableData[this.trIndex], //Pass the tr data to next router
                }
            })
        },
        returnTrIndex() {
            this.trIndex = event.target.parentNode.getAttribute('formId');
            for (var i = 0; i < this.tableData.length; i++) {
                if (this.tableData[i].id == this.trIndex) {
                    this.trIndex = i;
                }
            }
            // var nodes = document.getElementsByTagName("tr");
            // for (var i = 0; i < nodes.length; i += 1) {
            //     nodes[i].onclick = (function (i) {
            //         return function () {
            //             this.trIndex = i-1;
            //             alert('trindex is' + this.trIndex)
            //         }
            //     })(i);
            // }
        },
        deleteTr() {
            this.returnTrIndex();
            this.$confirm('Confirm to delete?', {
                confirmButtonText: 'Confirm',
                cancelButtonText: 'Cancel',
                type: 'warning'
            }).then(() => {
                 this.$axios.post(`/ui/removeUiElement?token=111&id=${this.tableData[this.trIndex].id}`).then(res => {
                    // success callback
                    console.log(res);
                    var ec = res.data.ec;
                    if (ec != '0') {
                        alert(res.dat.ec + ", " + res.data.em);//弹出错误
                    } else {
                        this.$message.success("Delete Sucess！");
                        this.getTableData();
                        console.log(this.tableData);
                    }
                }).catch(err => {
                    alert(err);
                });
            })
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
td{
    width: 7%
}
</style>
