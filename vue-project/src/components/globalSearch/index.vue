<template>
  <div>
    <input @focus="openInput()" @blur="closeInput()" :placeholder= val :style="{'width':inputwidth} " v-model="userData" @keyup="userInput()">
    <a v-if="isActive" @click="clear()">clear</a>
  </div>
</template>

<script>
export default {
    data(){
        return{
            isActive:false,
            val:"Search..",
            inputwidth: "100px",
            userData:""
        }
    },
    methods:{
            closeInput(){
                if(this.userData==""){
                    this.inputwidth = "100px";
                    this.val='Search..';
                    this.isActive = false;
                    }
            },
            openInput(){
                this.inputwidth = "200px";
                this.val="Search your data..";
            },
            userInput(){
                this.isActive = true;
            },
            clear(){
                this.userData = "";
                this.closeInput();
                this.isActive = false;
            }
    }
}
</script>

<style scoped>
div{
    background-color: #3F4044;
    border-radius: 4px;
}
input{
background:url(../../assets/images/globalsearch.png);
background-size: 25px;
background-repeat: no-repeat;
background-position: left 5px center;
border: 0px;
color: white;
padding:3px 0 3px 30px;
}
input:focus { 
  outline: 0px;
}
a{
    font-size: 12px;
    background-color: aliceblue;
    border-radius: 4px;
    padding: 1px;
    margin-right: 5px;
    display: inline-block;
}
</style>
