module.exports = function (data) {
    let color = [];
    let legend = [];
    let xAxis = [];
    let series = [];
    color = data.color;
    legend = data.legend;
    xAxis = data.xData;
    if(data.groupby==1){
        Object.values(legend).map(s => {
            let serie ={
                name: s,
                type:'bar',
                data:data.seriesData[s]
            }
            series.push(serie);
        });
    }
    return{
        color:color,
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        legend: {
            data: legend
        },
  
        calculable: true,
        xAxis: [
            {
                type: 'category',
                axisTick: {show: false},
                data: xAxis
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: [
            {
                name: 'Forest',
                type: 'bar',
                barGap: 0,
                data: [320, 332, 301, 334, 390]
            },
            {
                name: 'Steppe',
                type: 'bar',
                data: [220, 182, 191, 234, 290]
            },
            {
                name: 'Desert',
                type: 'bar',
                data: [150, 232, 201, 154, 190]
            },
            {
                name: 'Wetland',
                type: 'bar',
                data: [98, 77, 101, 99, 40]
            }
        ]
    }
}