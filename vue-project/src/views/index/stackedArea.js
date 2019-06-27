function DateX() {
    let time = new Date();
    let output = [];
    let mapString = { 0: "Jan.", 1: "Feb.", 2: "Mar.", 3: "Apr.", 4: "May", 5: "Jun.", 6: "Jul.", 7: "Aug.", 8: "Sep.", 9: "Oct.", 10: "Nov.", 11: "Dec." }
    for (let i = 0; i < 7; i++) {
        output.push(mapString[time.getUTCMonth()] + time.getDate())
        time.setDate(time.getDate() - 1);
    }
    return output.reverse()
}

export default function getData() {
    return {
        color: ['#7A85A1', '#C9D4E3', '#E8E8E8', '#9EADC5', '#F3D1CD', '#F9E8E0'],
        title: { text: 'Test Run vs Bug', left: '10px', y: '10px' },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                label: {
                    backgroundColor: '#6a7985'
                }
            }
        },
        legend: { data: ['Test Runs executed', 'Bugs reported'], y: '10px', right: '10px' },
        grid: {
            left: '4%',
            right: '5%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: [
            {
                type: 'category',
                boundaryGap: false,
                data: DateX()
            }
        ],
        yAxis: [{ type: 'value' }],
        series: [
            {
                name: 'Bugs reported',
                type: 'line',
                stack: 'Amount',
                areaStyle: {},
                data: [120, 132, 101, 134, 90, 230, 210]
            },
            {
                name: 'Test Runs executed',
                type: 'line',
                stack: 'Amount',
                areaStyle: {},
                data: [220, 182, 191, 234, 290, 330, 310]
            }
        ]
    }
}
