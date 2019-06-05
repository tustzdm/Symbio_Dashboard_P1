module.exports = function (title, xAxis, yAxis) {
    let color = '#01949B';
    let indicator = [];
    yAxis.map((el, i) => {
        let ind = {
            text: xAxis[i],
            max: el * 1.5,
            color: color
        };
        indicator.push(ind);
    });
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
            top: '2%',
            left: '3%',
            right: '12%',
            bottom: '3%',
            containLabel: true
        },
        polar: [
            {
                indicator: indicator,
                radius: '60%'
            }
        ],
        series: [
            {
                type: 'radar',
                itemStyle: {
                    normal: { color: color },
                },
                data: [
                    {
                        value: yAxis
                    }
                ]
            }
        ]
    };

}