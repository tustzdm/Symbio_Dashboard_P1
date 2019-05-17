module.exports = function (color, total, now, height = 5) {
    return {
        color: ['#FFFFFF', color],
        grid: {
            left: '0'
        },
        xAxis: [{
            type: 'value',
            splitLine: { show: false },
            axisLabel: { show: false },
            axisTick: { show: false },
            axisLine: { show: false },
            boundaryGap: false,
        }],
        yAxis: [{
            type: 'category',
            boundaryGap: false,
            position: 'top',
            splitLine: { show: false },
            axisLabel: { show: false },
            axisTick: { show: false },
            axisLine: { show: false },
            data: [""]
        }],
        series: [
            {
                type: 'bar',
                data: [total],
                barWidth: height,
                barGap: '-100%'
            },
            {
                type: 'bar',
                data: [now],
                barWidth: height,
            }
        ]

    }
}