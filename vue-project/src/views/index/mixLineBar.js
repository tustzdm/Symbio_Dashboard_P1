module.exports = function (listRowCharts) {
    let legend = [];
    let xAxis = [];
    let yAxis = [];
    let series = [];
    legend = listRowCharts.legend;
    xAxis = listRowCharts.xData;
    listRowCharts.yData.map((el, i) => {
        let ind = {
            name: listRowCharts.yData[i].name,
            axisLabel:{
                formatter:listRowCharts.yData[i].formatter
            }
        };
        yAxis.push(ind);
    });
    
    listRowCharts.seriesData.map((el, i) => {
        let ind = {
            name: listRowCharts.seriesData[i].name,
            type:'bar',
            data:listRowCharts.seriesData[i].data
        };
        series.push(ind);
    });
    return {
        legend: {
            data:legend
        },
        xAxis: [
            {
                type: 'category',
                data: xAxis,
                axisPointer: {
                    type: 'shadow'
                }
            }
        ],
        yAxis:yAxis,
        series:series
    }
}