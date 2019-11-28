<template>
<div class="report_Pic">
    <!-- {{runId}}{{screenShotId}}
    {{reortForm}} -->
    <el-steps :active="active" finish-status="success">
        <el-step title="Step 1"></el-step>
        <el-step title="Step 2"></el-step>
        <el-step title="Step 3"></el-step>
    </el-steps>
    <keep-alive>
        <el-form v-model="reortForm" ref="reortForm" v-if="active==0" label-width="300px">
            <el-form-item v-for="item in stepFormList1" v-show="item.type!='hidden'" :label="item.label" :key="item.key">
                <el-col :span="8" v-if="item.type=='text'">
                    <el-input v-model="reortForm[item.key]" :placeholder="item.placeHolder"></el-input>
                </el-col>
                <el-col :span="8" v-if="['list'].includes(item.type)&&item.key!='issueReasonId'">
                    <el-select v-model="reortForm[item.key]">
                        <el-option v-for="option in item.data" :key="option.value" :value="option.id" :label="option.value">
                        </el-option>
                    </el-select>
                </el-col>
                <el-col :span="8" v-if="item.key=='issueReasonId'">
                    <el-select v-model="reortForm[item.key]">
                        <el-option v-for="option in item.data" v-show="(option.categoryId==reortForm['issueCategoryId'])" :key="option.value" :value="option.id" :label="option.value">
                        </el-option>
                    </el-select>
                </el-col>
            </el-form-item>
        </el-form>
    </keep-alive>
    <el-form v-model="reortForm" ref="form" v-if="active==1" label-width="300px">
        <el-form-item v-for="item in stepFormList2" v-model="reortForm[item.key]" v-show="item.type!='hidden'" :label="item.label" :key="item.key">
            <el-col :span="8" v-if="item.type=='text'">
                <el-input v-model="reortForm[item.key]" :placeholder="item.placeHolder"></el-input>
            </el-col>
            <el-col :span="8" v-if="['list'].includes(item.type)">
                <el-select v-model="reortForm[item.key]">
                    <el-option v-for="option in item.data" :key="option.code" :value="option.value">
                    </el-option>
                </el-select>
            </el-col>
            <el-col :span="8" v-if="['user'].includes(item.type)">
                <el-select v-model="reortForm[item.key]">
                    <el-option v-for="option in userList" :key="option.id" :value="option.id" :label="option.fullName">
                    </el-option>
                </el-select>
            </el-col>
            <el-col :span="8" v-if="item.type=='textarea'">
                <el-input v-model="reortForm[item.key]" type="textarea" :placeholder="item.placeHolder"></el-input>
            </el-col>
        </el-form-item>
    </el-form>

    <div class="paintCon" style="text-align:center">
        <div id="paint" v-if="active==2" style="display:inline-block;">
            <canvas id="theCanvas" @mousedown="drawAction()" width=300 height=620 style="border:1px solid gray;display:inline-block">
                <textarea name="" id="" cols="30" rows="10"></textarea>
            </canvas>
            <textarea v-if="action=='text'" class="text" name="" @keydown="textEnter()" placeholder="click the postion to text" v-model="text" cols="30" rows="1" style="position:absolute;left:30px;top:85px"></textarea>
            <div style="display:inline-block;vertical-align:top;margin-left:8px">
                <ul>
                    <li class="line" :class="{penActive:lineWidth==2}">
                        <div style="width:19px;height:19px;overflow:hidden" @click="setLineWidth(2)">
                            <div style="display:inlienne-block;width:19px;height:2px;background:black;margin-top:9px"></div>
                        </div>
                    </li>
                    <li class="line" :class="{penActive:lineWidth==8}">
                        <div style="width:19px;height:19px;overflow:hidden" @click="setLineWidth(8)">
                            <div style="display:inlienne-block;width:19px;height:4px;background:black;margin-top:7px"></div>
                        </div>
                    </li>
                    <li class="line" :class="{penActive:lineWidth==13}">
                        <div style="width:19px;height:19px;overflow:hidden" @click="setLineWidth(13)">
                            <div style="display:inlienne-block;width:19px;height:7px;background:black;margin-top:5px"></div>
                        </div>
                    </li>
                </ul>
                <ul>
                    <li style="margin-bottom:6px">
                        <el-button @click="action='line';" style="padding:4px">
                            <i class="el-icon-edit" style="font-size:18px;"></i>
                        </el-button>
                    </li>
                    <li style="margin-bottom:6px">
                        <el-button @click="action='rect';" style="padding:4px">
                            <div style="width:12px;height:12px;padding:2px;border:1px solid black;background:#99E7F7"></div>
                        </el-button>
                    </li>
                    <li>
                        <el-color-picker v-model="color"></el-color-picker>
                    </li>
                    <li style="margin-bottom:6px">
                        <el-button style="padding:3px" @click="text = '';action='text';">
                            <div style="width:19px;height:18px;line-height:16px;font-size:16px">T</div>
                        </el-button>
                    </li>
                    <li style="margin-bottom:6px">
                        <el-button @click="prevStep" style="padding:4px">
                            <i class="el-icon-back" style="font-size:18px;"></i>
                        </el-button>
                    </li>
                    <li style="margin-bottom:6px">
                        <el-button @click="saveUpload" style="padding:4px">
                            <div style="width:19px;height:18px;line-height:16px;font-size:16px">S</div>
                            
                        </el-button>
                    </li>
                </ul>
            </div>

        </div>
    </div>
    <el-button style="margin-top: 12px;" @click="prev" :disabled="active==0">Previous</el-button>
    <el-button style="margin-top: 12px;" @click="next" :disabled="active==2">Next</el-button>

    
</div>
</template>

<script>
export default {
    name: 'paint',
    data() {
        return {
            sss:'sy',
            active: 0,
            lineWidth: 2,
            color: '#F50C04',
            imgEdit: this.url,
            text: '',
            textarea: null,
            ifRect: false,
            textareaX: '',
            textareaY: '',
            canvasHistory: [],
            step: -1, //draw step
            action: 'line',

            // info 
            screenShotId: this.sShotId,
            runId: this.runIndex,
            stepFormList1: '',
            stepFormList2: '',
            userList:{},
            reortForm: this.reportInfo
        }
    },
    props: {
        url: String,
        sShotId: Number,
        runIndex: String,
        reportInfo:Object
    },
    created() {
        console.log(123);
        console.log(this.reportInfo);
        console.log(456);
        console.log(this.runId);
        this.Fetch(`/result/getBugInfo?token=${localStorage.getItem('token')}&id=0&testResultId=1&screenshotId=1300`, {
            method: "GET",
        }).then(res => {
            console.log(res);
            this.stepFormList1 = res.cd.uiInfo.Step1;
            this.stepFormList2 = res.cd.uiInfo.Step2;
            this.userList = res.cd.userList;
        }).catch(err => {
            alert(err);
        });
    },
    mounted() {
       
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
        },
        active: function (val) {
            if (val == 2) {
                let theCanvas = document.querySelector('#theCanvas');
                this.drawPic();
                let context = theCanvas.getContext('2d')
                context.lineWidth = this.lineWidth;
            }
        }
    },
    methods: {
        next() {

            if (this.active > 1){
                return
            }else{
                this.active++
            }
        },
        prev() {
            if (this.active <= 0) {
                return
            }else{
                this.active--
            }
        },
        windowToCanvas: (canvas, x, y) => {
            let rect = canvas.getBoundingClientRect()
            return {
                x: x - rect.left * (canvas.width / rect.width),
                y: y - rect.top * (canvas.height / rect.height)
            }
        },
        initCanvas() {

        },
        drawPic() {
            //add img
            var imgObj = new Image();
            imgObj.src = this.reortForm.fileUrl||this.imgEdit;
            //待图片加载完后，将其显示在canvas上
            imgObj.onload = function () {
                var ctx = theCanvas.getContext('2d');
                // ctx.drawImage(this, 0, 0); //this即是imgObj,保持图片的原始大小：470*480
                theCanvas.width = imgObj.width;
                theCanvas.height = imgObj.height;
                ctx.drawImage(this, 0, 0); //改变图片的大小到1024*768
            }
        },
        drawAction() {
            switch (this.action) {
                case 'line':
                    this.drawLine();
                    break;
                case 'rect':
                    this.drawRect();
                    break;
                case 'text':
                    this.drawText();
                    break;
                default:
                    break;
            }
        },
        drawLine() {
            if (!theCanvas || !theCanvas.getContext) {
                return false
            } else {
                let context = theCanvas.getContext('2d');
                context.beginPath();
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
        drawRect() {

            let theCanvas = document.querySelector('#theCanvas');
            let context = theCanvas.getContext('2d');
            context.closePath();
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
                if (this.action != 'rect') {
                    return //很奇怪，写文字时仍然会以这个方法监听onmouseup事件，所以
                }
                let ele = this.windowToCanvas(theCanvas, e.clientX, e.clientY)
                w = ele.x - x;
                h = ele.y - y;
                console.log(w)
                console.log(h)
                context.rect(x, y, w, h);
                context.stroke();
                // this.setHistory();
                console.log(234234234234121231234)
            }
        },
        drawText() {
            let theCanvas = document.querySelector('#theCanvas');
            let context = theCanvas.getContext('2d');
            theCanvas.onmousedown = (e) => {
                let ele = this.windowToCanvas(theCanvas, e.clientX, e.clientY)
                this.textareaX = ele.x;
                this.textareaY = ele.y;
                document.querySelector('.text').style.left = this.textareaX + 480 + 'px';
                document.querySelector('.text').style.top = this.textareaY + 110 + 'px'
            };
        },
        textEnter(e) {
            let theCanvas = document.querySelector('#theCanvas');
            let context = theCanvas.getContext('2d');
            var evt = window.event || e;
            if (evt.keyCode == 13) {
                context.font = "23px Poppins";
                context.fillStyle = this.color;
                context.fillText(this.text, this.textareaX, this.textareaY);
                this.action = 'line'; 
                this.setHistory();

                // 输入完text后默认设置为画线
            }
        },
        saveUpload() {
            let theCanvas = document.querySelector('#theCanvas');
            let context = theCanvas.getContext('2d');
            var saveImg = theCanvas.toDataURL('image/png');
            
            this.reortForm['filePath'] = saveImg;
            this.reortForm.screenShotId= this.screenShotId;
            console.log(this.reortForm);

            this.$emit('reportForm',this.reortForm)
            console.log(666666666666)
            // this.Fetch(`/result/saveBugInfo?token=${localStorage.getItem('token')}`, {
            //     method: "POST",
            //     body: this.reortForm
            // }).then(res => {
            //     console.log(res)
            // }).catch(err => {
            //     alert(err);
            // });
        },
        setHistory() {
            let theCanvas = document.querySelector('#theCanvas');
            let context = theCanvas.getContext('2d');
            this.step++;
            this.canvasHistory.push(context.getImageData(0, 0, theCanvas.width, theCanvas.height));
            console.log(this.step);
            console.log(this.canvasHistory)
        },
        prevStep() {
            if (this.step <= 0) {
                console.log('this is the first step!!!')
                return
            }
            let theCanvas = document.querySelector('#theCanvas');
            let context = theCanvas.getContext('2d');
            console.log(this.canvasHistory);
            this.step--
            context.putImageData(this.canvasHistory[this.step], 0, 0);
            this.canvasHistory.pop();
            console.log(this.step);
            console.log(this.canvasHistory)
        },
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
    margin-left: 1px;
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
