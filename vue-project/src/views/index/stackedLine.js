module.exports = function (lineData) {
    let legend = [];
    let xAxis = [];
    let series = [];
    legend = lineData.legend
    xAxis = lineData.name;
    if(lineData.groupby == 1){
        Object.values(legend).map(s => {
            let serie ={
               name: s,
               type:'line',
               data:lineData.data[s]
           }
           series.push(serie);
       });
       console.log(series);
    }else if(lineData.groupby == 0){
        xAxis =  ["TestSet1", "TestSet2","TestSet3"];
        Object.values(legend).map((el,l) => {
            let elData = []
            Object.values(xAxis).map(x => {
                elData.push(lineData.data[x][l]);
            });
            let serie ={
                name: el,
                type:'line',
                data:elData
            }
            series.push(serie);
        })
    }
    return {
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data:legend
        },
        xAxis: {
            data: xAxis
        },
        yAxis: {
            type: 'value'
        },
        series: series
    }
}