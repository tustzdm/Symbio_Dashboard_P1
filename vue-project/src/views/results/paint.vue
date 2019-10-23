<template>
<div>
    <div>
        <span>直线</span> <span>矩形</span>
    </div>
    <canvas id="canvas" width=300 height=620></canvas>
</div>
</template>

<script>
export default {
    name: 'paint',
    data() {
        return {
            lineWidth: 5,
            canvasHistory: [],
            step: -1,
        }
    },
    created() {

    },
    mounted() {
        const canvas = document.querySelector('#canvas')
        // this.context = canvas.getContext('2d')
        var imgObj = new Image();
        imgObj.src = "https://vue.symbio.com.cn/mock/image/0_480/1076_1077_1/762/en_US/screenshots/001.png";
        //待图片加载完后，将其显示在canvas上
        imgObj.onload = function () {
            var context = canvas.getContext('2d');
            // context.drawImage(this, 0, 0); //this即是imgObj,保持图片的原始大小：470*480
            context.drawImage(this, 0, 0, 300, 620); //改变图片的大小到1024*768
            // context.width = imgObj.width;
            // context.height= imgObj.height;

            context.fillStyle = "red";
            context.fillRect(20, 20, 200, 200);
            context.strokeStyle = "yellow";
            context.moveTo(5, 50);
            context.lineTo(200, 200);
            context.stroke();
        }
        this.drawLine();
    },
    destroyed() {},
    computed: {

    },
    methods: {
        // drawCircle(x, y) {
        //     context.beginPath()
        //     context.fillStyle = 'black'
        //     context.arc(x, y, 23, 0, Math.PI * 2);
        //     context.fill()
        // },
        drawLine(x1, y1, x2, y2) {
            context.beginPath();
            context.moveTo(x1, y1) // 起点
            context.lineWidth = lineWidth
            context.lineTo(x2, y2) // 终点
            context.stroke()
            context.closePath()
        }

    }
}
</script>

<style>

</style>
