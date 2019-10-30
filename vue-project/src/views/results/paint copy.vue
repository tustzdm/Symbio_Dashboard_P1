<template>
<div>
    <div>
       <button @click="setLineWidth(5)">1</button>
       <button @click="setLineWidth(8)">2</button>
       <button @click="setLineWidth(12)">3</button>
       <button @click="setLineWidth(18)">3</button>
       {{lineWidth}}
    </div>
    <canvas  id="theCanvas" width=300 height=620 style="border:1px solid gray"></canvas>
</div>
</template>

<script>
export default {
    name: 'paint',
    data() {
        return {
            lineWidth: 5,
            imgEdit:'https://vue.symbio.com.cn/mock/image/0_480/1076_1077_1/763/de_DE/screenshots/002.png',//imgEdit:this.url,
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
        context.lineWidth=this.lineWidth;
        
        if (!theCanvas || !theCanvas.getContext) {
            return false
        } else {
            let context = theCanvas.getContext('2d')
            let isAllowDrawLine = false
            theCanvas.onmousedown =  (e)=> {
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
    methods: {
        windowToCanvas:(canvas, x, y)=>{
            let rect = canvas.getBoundingClientRect()
            return {
                x: x - rect.left * (canvas.width / rect.width),
                y: y - rect.top * (canvas.height / rect.height)
            }
        },
        setLineWidth(v){
            this.lineWidth=v;
        }
    }
}
</script>

<style>

</style>
