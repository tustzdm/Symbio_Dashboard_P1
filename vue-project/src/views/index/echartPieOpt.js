module.exports = function (title, data) {
    return {
        backgroundColor: "rgb(245,245,245)",
        title: {
            text: title,
            x: 'center',
            top: '5',
            textStyle: {
                color: "#000",
            }
        },
        grid: {
            top: 100,
            containLabel: false
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },

        series: [
            {
                type: 'pie',    //图表类型，柱状图：bar
                radius: '55%',    //半径
                center: ['50%', '50%'],
                label: { formatter: "{b}+{d}%" },
                data: data,
                itemStyle: {  //itemStyle有正常显示：normal，有鼠标hover的高亮显示：emphasis
                    emphasis: {  //normal显示阴影,与shadow有关的都是阴影的设置
                        shadowBlur: 10,  //阴影大小
                        shadowOffsetX: 0,  //阴影水平方向上的偏移
                        shadowColor: 'rgba(0, 0, 0, 0.5)'  //阴影颜色
                    }
                }
            }
        ]
    };
};