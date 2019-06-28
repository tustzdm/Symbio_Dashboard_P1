
<template>
  <div>
    <el-card class="listHead" shadow="never" style="padding-right:5%">
      <h2 style="float:left;margin:0 0 0 80px;line-height:60px">Product List</h2>
      <el-button
        style="float:right;margin:10px 80px 0 0;background-color:#7a85a1"
        type="info"
        size="med"
      >
        <router-link to="/addproject/index" style="color:white">+ Add Product</router-link>
      </el-button>
    </el-card>
    <el-card class="manage-tabel" shadow="never">
      <el-table :data="tableData">
        <el-table-column prop="product" label="Name" :width="0.5*tabelWidth">
          <template slot-scope="scope">
            <div>
              <router-link :to="{path:'/product/'+scope.row.product}">{{scope.row.product}}</router-link>
            </div>
            <div>{{scope.row.description}}</div>
          </template>
        </el-table-column>
        <el-table-column prop="progress" label="Progress" :width="0.25*tabelWidth">
          <template slot-scope="scope">
            <div
              style="float:left;border:1px solid white;width:80%;height:100%;border-radius:10px;background-color:#e8ebf1"
            >
              <div
                v-bind:style="{width:scope.row.progress}"
                style="float:left;background-color:#b1c9f7;border-radius:10px 0 0 10px"
              >{{scope.row.finished}}</div>
              {{scope.row.all}}
            </div>
            {{scope.row.progress}}
            <!-- <el-progress :text-inside="true" :stroke-width="20" :percentage="scope.row.progress"></el-progress> -->
          </template>
        </el-table-column>
        <el-table-column prop="address" label="Operation">
          <el-button class="editDeleteBtn" icon="el-icon-edit" circle></el-button>
          <el-button class="editDeleteBtn" type="danger" icon="el-icon-delete" circle></el-button>
        </el-table-column>
      </el-table>
    </el-card>

    <div class="fanye" style="padding-bottom:100px">
      <el-pagination
        background
        layout="total, sizes, prev, pager, next, jumper"
        :total="tableData.length"
        :page-sizes="[20, 30, 40, 50, 100, 500]"
        :page-size="pageSize"
        style="text-align:center;margin: 10px 0"
        @current-change="currentChange"
        @size-change="sizeChange"
      ></el-pagination>
    </div>
  </div>
</template>
<script>
import storage from '@/utils/storage'

export default {
  data() {
    return {
      screenWidth: '',
      tabelWidth: '',
      pageSize: 20,
      currentPage: 1,
      tableData: [
        {
          product: 'MerchantSignUp',
          progress: '39%',
          finished: 300,
          all: 342,
          description:
            'Description of product1, for merchant account ,function and performance test'
        },
        {
          product: 'RiskTransfer',
          progress: '29%',
          finished: 300,
          all: 342,
          description:
            'Description of product1, for merchant account ,function and performance test'
        },
        {
          product: 'PersonalAddPhone',
          progress: '69%',
          finished: 300,
          all: 342,
          description:
            'Description of product1, for merchant account ,function and performance test'
        },
        {
          product: 'PersonalDeletePhone',
          progress: '89%',
          finished: 300,
          all: 342,
          description:
            'Description of product1, for merchant account ,function and performance test'
        },
        {
          product: 'GameSurface',
          progress: '89%',
          finished: 300,
          all: 342,
          description:
            'Description of product1, for merchant account ,function and performance test'
        },
        {
          product: 'GameSurface',
          progress: '89%',
          finished: 300,
          all: 342,
          description:
            'Description of product1, for merchant account ,function and performance test'
        },
        {
          product: 'GameSurface',
          progress: '89%',
          finished: 300,
          all: 342,
          description:
            'Description of product1, for merchant account ,function and performance test'
        },
        {
          product: 'GameSurface',
          progress: '89%',
          finished: 300,
          all: 342,
          description:
            'Description of product1, for merchant account ,function and performance test'
        },
        {
          product: 'GameSurface',
          progress: '89%',
          finished: 300,
          all: 342,
          description:
            'Description of product1, for merchant account ,function and performance test'
        },
        {
          product: 'GameSurface',
          progress: '89%',
          finished: 300,
          all: 342,
          description:
            'Description of product1, for merchant account ,function and performance test'
        },
        {
          product: 'GameSurface',
          progress: '89%',
          finished: 300,
          all: 342,
          description:
            'Description of product1, for merchant account ,function and performance test'
        }
      ]
    }
  },
  mounted() {
    ;(window.onresize = () => {
      return (() => {
        this.screenWidth = document.body.clientWidth
      })()
    }),
      (this.tabelWidth = document.getElementsByClassName(
        'el-table'
      )[0].clientWidth)
  },
  watch: {
    screenWidth() {
      this.tabelWidth = this.screenWidth * 0.85
    }
  },
  components: {},
  methods: {}
}
</script>

<style lang="stylus" scoped>
.el-table__row td:first-child .cell div:first-child {
  padding-left: 60px;
  text-align: left;
}

.el-table__row td:first-child .cell div:first-child a {
  font-size: 16px;
  font-family: Poppins;
  color: #396ed4;
}

.el-table__row td:first-child .cell div:last-child {
  font-family: Poppins;
  text-align: left;
  padding-left: 60px;
}

.editDeleteBtn {
}
</style>
