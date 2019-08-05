<template>
<el-card class="manage-top" shadow="hover" style="height:40px">
    <div class="SiteSelect select">
        <el-dropdown trigger="click">
            <span class="el-dropdown-link">
                Site
                <i class="el-icon-caret-bottom el-icon--right"></i>
            </span>
            <el-dropdown-menu>
                <el-row class="demo-autocomplete">
                    <el-col :span="12">
                        <el-autocomplete class="inline-input" v-model="state2" :fetch-suggestions="querySearch" placeholder="Search here" :trigger-on-focus="false" @select="handleSelect" style="width:180px"></el-autocomplete>
                    </el-col>
                </el-row>
                <el-dropdown-item>
                    <div>
                        <i class="el-icon-plus"></i>
                        <span>
                            <router-link to="/addproject/index">Add Product</router-link>
                        </span>
                    </div>
                </el-dropdown-item>
                <el-dropdown-item>
                    <i class="el-icon-edit"></i>Edit Product
                </el-dropdown-item>
                <el-dropdown-item style="text-align:center;">
                    <i class="el-icon-more-outline"></i>More
                </el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
        <span class="sperate-arrow">
            <i class="el-icon-arrow-right"></i>
        </span>
    </div>
    <div class="SiteSelect select">
        <el-dropdown trigger="click">
            <span class="el-dropdown-link">
                OTP
                <i class="el-icon-caret-bottom el-icon--right"></i>
            </span>
            <el-dropdown-menu>
                <input class="select-search" type="text" placeholder="Search here" />
                <el-dropdown-item>
                    <i class="el-icon-plus"></i>Add Release
                </el-dropdown-item>
                <el-dropdown-item>
                    <i class="el-icon-edit"></i>Edit Releases
                </el-dropdown-item>
                <el-dropdown-item style="text-align:center;">
                    <i class="el-icon-more-outline"></i>More
                </el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
        <span class="sperate-arrow">
            <i class="el-icon-arrow-right"></i>
        </span>
    </div>
    <div class="product-select select">
        <el-dropdown trigger="click">
            <span class="el-dropdown-link">
                LLT-19753 OTP-One time payment
                <i class="el-icon-caret-bottom el-icon--right"></i>
            </span>
            <el-dropdown-menu>
                <input class="select-search" type="text" placeholder="Search here" />
                <el-dropdown-item>
                    <i class="el-icon-plus"></i>Add Test Set
                </el-dropdown-item>
                <el-dropdown-item>
                    <i class="el-icon-edit"></i>Edit Test Set
                </el-dropdown-item>
                <el-dropdown-item style="text-align:center;">
                    <i class="el-icon-more-outline"></i>More
                </el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
    </div>
    <div class="product-select select">
        <el-dropdown trigger="click">
            <span class="el-dropdown-link">
                ADD
                <i class="el-icon-plus"></i>
            </span>
            <el-dropdown-menu>
                <el-dropdown-item>
                    <span>
                        <router-link to="/addproject/index">Add Product</router-link>
                    </span>
                </el-dropdown-item>
                <el-dropdown-item>
                    <span>
                        <router-link to="/addversion/index">Add Release</router-link>
                    </span>
                </el-dropdown-item>
                <el-dropdown-item>
                    <span>
                        <router-link to="/addcase/index">Add Testset</router-link>
                    </span>
                </el-dropdown-item>
                <el-dropdown-item style="text-align:center;">
                    <i class="el-icon-more-outline"></i>More
                </el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
    </div>
    <div class="manage-top-right">
        <el-button style="background-color:#c2eaae;color:white" size="mini">Run</el-button>
        <el-button style="background-color:rgb(190, 205, 223);color:white" size="mini">Refresh</el-button>
        <el-button style="background-color:rgb(246, 184, 184);color:white" size="mini">Add Bug</el-button>
        <div class="select" style="float:right; margin-right:30px">
            <el-dropdown trigger="click">
                <span class="el-dropdown-link">
                    Filter
                    <i class="el-icon-caret-bottom el-icon--right"></i>
                </span>
                <el-dropdown-menu>
                    <el-dropdown-item>Method</el-dropdown-item>
                    <el-dropdown-item>Ownner</el-dropdown-item>
                    <el-dropdown-item>Bug ID</el-dropdown-item>
                    <el-dropdown-item>Testing Notes</el-dropdown-item>
                    <el-dropdown-item>Notes</el-dropdown-item>
                    <el-dropdown-item>Issue Peason</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
    </div>
</el-card>
<!-- manage-top end -->
</template>
<script>
export default {
  name: 'top',
  data() {
    return {
    }
  },
  mounted() {

  },
  methods: {

  }
}
</script>

<style lang="stylus" scoped>
</style>