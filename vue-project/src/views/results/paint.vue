<template>
<div>
    <div>
        <i class="el-icon-edit"  @click="setLineWidth(3)" style="font-size:10px;padding:none"></i>
        <i class="el-icon-edit"  @click="setLineWidth(8)" style="font-size:15px;padding:none"></i>
        <i class="el-icon-edit"  @click="setLineWidth(13)" style="font-size:18px;padding:none"></i>
         <el-color-picker v-model="color"></el-color-picker>
        <!-- <el-button class="sss" circle="" icon="el-icon-edit" @click="imgActualSize" style="font-size:10px;padding:none" size="mini"> </el-button>
        <el-button  circle="" icon="el-icon-edit" @click="imgActualSize" style="font-size:15px;padding:none" size="mini"> </el-button>
        <el-button circle="" icon="el-icon-edit" @click="imgActualSize" style="font-size:18px;padding:none" size="mini"> </el-button> -->
        {{lineWidth}}{{color}}
    </div>
    <canvas id="theCanvas" width=300 height=620 style="border:1px solid gray"></canvas>
</div>
</template>

<script>
export default {
    name: 'paint',
    data() {
        return {
            lineWidth: 3,
            color:'#000000',
            imgEdit:this.url,
        }
    },
    props: {
        url: String,
    },
    created() {

    },
    mounted() {
        let theCanvas = document.querySelector('#theCanvas')
        //add img
        var imgObj = new Image();
        imgObj.src = this.imgEdit;
        //待图片加载完后，将其显示在canvas上
        imgObj.onload = function () {
            var ctx = theCanvas.getContext('2d');
            // ctx.drawImage(this, 0, 0); //this即是imgObj,保持图片的原始大小：470*480
            ctx.drawImage(this, 0, 0, 300, 600); //改变图片的大小到1024*768
        }
        let context = theCanvas.getContext('2d')
        context.lineWidth = this.lineWidth;

        if (!theCanvas || !theCanvas.getContext) {
            return false
        } else {
            let context = theCanvas.getContext('2d')
            let isAllowDrawLine = false
            theCanvas.onmousedown = (e) => {
                context.lineWidth = this.lineWidth
                isAllowDrawLine = true
                let ele = this.windowToCanvas(theCanvas, e.clientX, e.clientY)
                let {
                    x,
                    y
                } = ele
                context.moveTo(x, y)
                theCanvas.onmousemove = (e) => {
                    if (isAllowDrawLine) {
                        let ele = this.windowToCanvas(theCanvas, e.clientX, e.clientY)
                        let {
                            x,
                            y
                        } = ele
                        context.lineTo(x, y)
                        context.stroke()
                    }
                }
            }
            theCanvas.onmouseup = function () {
                isAllowDrawLine = false
            }
        }
    },
    destroyed() {},
    computed: {

    },
    watch:{
        color:function(){//监测color的
            let theCanvas = document.querySelector('#theCanvas');
            let context = theCanvas.getContext('2d');
            context.beginPath();
            context.strokeStyle= this.color;
        }
    },
    methods: {
        windowToCanvas: (canvas, x, y) => {
            let rect = canvas.getBoundingClientRect()
            return {
                x: x - rect.left * (canvas.width / rect.width),
                y: y - rect.top * (canvas.height / rect.height)
            }
        },
        setLineWidth(v) {
            let theCanvas = document.querySelector('#theCanvas');
            let context = theCanvas.getContext('2d');
            console.log(1111)
            context.beginPath();
            console.log(2222);
            this.lineWidth = v;
            console.log(context.lineWidth);
        },
         setColor() {
            
        }
    }
}
</script>

<style scoped>
    .sss{
        padding:4px
    }
</style>>

