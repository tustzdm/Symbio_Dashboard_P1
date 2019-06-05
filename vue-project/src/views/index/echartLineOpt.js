module.exports = function (title, xAxis, yAxis) {
    return {
        backgroundColor: 'rgb(245,245,245)',
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        title: {
            text: title,
            x: 'center',
            top: '5',
            textStyle: {
                color: "#000",
            }
        },
        grid: {
            left: '3%',
            right: '12%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: [{
            type: 'category',
            data: xAxis
        }],
        yAxis: [{
            type: 'value',
            axisLabel: {
                formatter: '{value}'
            }
        }],
        series: [{
            type: 'line',
            smooth:true,
            itemStyle: {
                normal: { color: '#01949B' },
            },
            markPoint: {
                data: [
                    { type: 'max', name: '最大值' },
                    { type: 'min', name: '最小值' }
                ]
            },
            markLine: {
                data: [
                    { type: 'average', name: '平均值' }
                ]
            },
            data: yAxis,
            barMaxWidth: 20
        }]
    }
}