<template>
<div id="paint">
    <canvas id="theCanvas" @mousedown="drawLine()" width=300 height=620 style="border:1px solid gray;display:inline-block">
        <textarea name="" id="" cols="30" rows="10"></textarea>
    </canvas>
    <textarea v-if="ifText" class="text" name="" @keydown="textEnter()" v-model="text" cols="30" rows="2" style="position:absolute"></textarea>
    <div style="display:inline-block;vertical-align:top;margin-left:8px">
        <ul>
            <li class="line" :class="{penActive:lineWidth==3}">
                <div style="width:19px;height:19px;overflow:hidden" @click="setLineWidth(3)"><div style="display:inlienne-block;width:19px;height:2px;background:black;margin-top:9px"></div></div>
            </li>
            <li class="line" :class="{penActive:lineWidth==8}">
                <div style="width:19px;height:19px;overflow:hidden" @click="setLineWidth(8)"><div style="display:inlienne-block;width:19px;height:4px;background:black;margin-top:7px"></div></div>
            </li>
            <li class="line" :class="{penActive:lineWidth==13}">
                <div style="width:19px;height:19px;overflow:hidden" @click="setLineWidth(13)"><div style="display:inlienne-block;width:19px;height:7px;background:black;margin-top:5px"></div></div>
            </li>
            <!-- <li><i class=" pen" :class="{penActive:lineWidth==8}" @click="setLineWidth(8)" style="font-size:15px;padding:2px"></i></li>
            <li><i class=" pen" :class="{penActive:lineWidth==13}" @click="setLineWidth(13)" style="font-size:18px;"></i></li> -->
        </ul>
        <ul>
            <li style="margin-bottom:6px">
                <el-button @click="ifRect=false" style="padding:4px">
                    <i class="el-icon-edit"   style="font-size:18px;"></i>
                </el-button>
            </li>
            <li style="margin-bottom:6px">
                <el-button @click="ifRect=true" style="padding:4px">
                    <div style="width:12px;height:12px;padding:2px;border:1px solid black;background:#99E7F7"></div>
                </el-button>
            </li>
            <li>
                <el-color-picker v-model="color"></el-color-picker>
            </li>
            <li style="margin-bottom:6px">
                <el-button style="padding:3px" @click="drawText()">
                    <div style="width:19px;height:18px;line-height:16px;font-size:16px">T</div>
                </el-button>
            </li>
            <li style="margin-bottom:6px">
                <el-button @click="prevStep" style="padding:4px">
                    <i class="el-icon-upload2 "   style="font-size:18px;"></i>
                </el-button>
            </li>
            <li style="margin-bottom:6px">
                <el-button @click="saveUpload" style="padding:4px">
                    <i class="el-icon-upload2 "   style="font-size:18px;"></i>
                </el-button>
            </li>
        </ul>
    </div>

</div>
</template>

<script>
export default {
    name: 'paint',
    data() {
        return {
            lineWidth: 2,
            color: '#F50C04',
            imgEdit: this.url,
            text: '',
            textarea: null,
            commentClicked: false,
            ifRect: false,
            ifText: false,
            textareaX: '',
            textareaY: '',
            canvasHistory:[],
            step:-1,
        }
    },
    props: {
        url: String,
    },
    created() {

    },
    mounted() {
        let theCanvas = document.querySelector('#theCanvas');
        this.drawPic();

        let context = theCanvas.getContext('2d')
        context.lineWidth = this.lineWidth;

    },
    destroyed() {},
    computed: {

    },
    watch: {
        color: function () { //监测color的
            let theCanvas = document.querySelector('#theCanvas');
            let context = theCanvas.getContext('2d');
            context.beginPath();
            context.strokeStyle = this.color;
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
        drawPic() {
            // //add img
            // var imgObj = new Image();
            // imgObj.src = this.imgEdit;
            // //待图片加载完后，将其显示在canvas上
            // imgObj.onload = function () {
            //     var ctx = theCanvas.getContext('2d');
            //     // ctx.drawImage(this, 0, 0); //this即是imgObj,保持图片的原始大小：470*480

            //     theCanvas.width = imgObj.width;
            //     theCanvas.height = imgObj.height;
            //     ctx.drawImage(this, 0, 0); //改变图片的大小到1024*768
            // }
        },
        drawLine() {
            if (this.ifRect) { //加一个判断，当不画矩形的时候继续画线，画矩形这里就返回不继续执行
                this.drawRect()
                return
            } else if (this.ifText) {
                return
            }
            if (!theCanvas || !theCanvas.getContext) {
                return false
            } else {
                let context = theCanvas.getContext('2d');
                context.strokeStyle = this.color;
                let isAllowDrawLine = false
                theCanvas.onmousedown = (e) => {
                    context.lineWidth = this.lineWidth
                    isAllowDrawLine = true
                    let ele = this.windowToCanvas(theCanvas, e.clientX, e.clientY)
                    let {
                        x,
                        y
                    } = ele;
                    console.log(ele)
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
                theCanvas.onmouseup = (e) => {
                    isAllowDrawLine = false
                    let ele = this.windowToCanvas(theCanvas, e.clientX, e.clientY)
                    let {
                        x,
                        y
                    } = ele;
                    console.log(ele)
                    this.setHistory();
                }
                return
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
        setText() {
            let theCanvas = document.querySelector('#theCanvas');
            let context = theCanvas.getContext('2d');
            context.beginPath();
            context.font = "50px"
            context.fillText(this.text, 10, 60)
        },
        commentClick() {
            this.commentClicked = true;
            let theCanvas = document.querySelector('#theCanvas');
            let context = theCanvas.getContext('2d');
            var textarea = null;
            if (!textarea) {
                theCanvas.addEventListener('click', (e) => {
                    textarea = document.createElement('textarea');
                    textarea.className = 'comment';
                    document.body.appendChild(textarea);
                    let ele = this.windowToCanvas(theCanvas, e.clientX, e.clientY)
                    let {
                        positionx,
                        positiony
                    } = ele
                    console.log(ele)
                    textarea.style.top = positiony + 'px';
                    textarea.style.left = positionx + 'px';
                })

            }

        },
        drawRect() {
            let theCanvas = document.querySelector('#theCanvas');
            let context = theCanvas.getContext('2d');
            context.strokeStyle = this.color;
            context.beginPath();
            let x, y, w, h;

            theCanvas.onmousedown = (e) => {
                context.lineWidth = this.lineWidth
                let ele = this.windowToCanvas(theCanvas, e.clientX, e.clientY)
                x = ele.x;
                y = ele.y;
                console.log(x)
                console.log(y)
            }
            theCanvas.onmouseup = (e) => {
                let ele = this.windowToCanvas(theCanvas, e.clientX, e.clientY)
                w = ele.x - x;
                h = ele.y - y;
                console.log(w)
                console.log(h)
                context.rect(x, y, w, h);
                console.log(x, y, w, h)
                context.stroke();
                this.setHistory();
            }
        },
        drawText() {
            this.text = '';
            this.ifRect = false;
            this.ifText = true;
            let theCanvas = document.querySelector('#theCanvas');
            let context = theCanvas.getContext('2d');

            theCanvas.onmousedown = (e) => {
                let ele = this.windowToCanvas(theCanvas, e.clientX, e.clientY)
                this.textareaX = ele.x;
                this.textareaY = ele.y;
                document.querySelector('.text').style.left = this.textareaX + 25 + 'px';
                document.querySelector('.text').style.top = this.textareaY + 50 + 'px'
                this.setHistory();
            }
        },
        textEnter(e) {
            let theCanvas = document.querySelector('#theCanvas');
            let context = theCanvas.getContext('2d');

            var evt = window.event || e;
            if (evt.keyCode == 13) {
                this.ifText = false;
            }

            context.font = "23px Poppins";
            context.fillStyle = this.color;
            context.fillText(this.text, this.textareaX, this.textareaY)
        },
        saveUpload(){
            let theCanvas = document.querySelector('#theCanvas');
            let context = theCanvas.getContext('2d');
            var saveImg = theCanvas.toDataURL('image/png');
            alert(1111)
        },
        setHistory(){
            let theCanvas = document.querySelector('#theCanvas');
            let context = theCanvas.getContext('2d');
            this.step++;
            this.canvasHistory.push(context.getImageData(0,0,theCanvas.width,theCanvas.height));
            console.log(this.step);
            console.log(this.canvasHistory)
        },
        prevStep(){
            let theCanvas = document.querySelector('#theCanvas');
            let context = theCanvas.getContext('2d');
            console.log(this.canvasHistory);
            this.step--
            context.putImageData(this.canvasHistory[this.step],0,0);
            this.canvasHistory.pop();
            console.log(this.step);
            console.log(this.canvasHistory)
        }
    }
}
</script>

<style>
.line {
    width: 26px;
    height: 26px;
    padding: 2px;
    text-align: center;
    vertical-align: bottom;
    box-sizing: border-box;
    margin-bottom: 3px;
    margin-left:1px;
}

.penActive {
    border: 1px solid #fa7b30
}

.el-color-picker--small .el-color-picker__trigger {
    height: 27px;
    width: 27px;

}

.el-color-picker__trigger {
    padding: 0;
    border: none;
    margin-bottom: 5px;
}
</style>>
