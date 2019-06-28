export default function getData() {
    return {
        title: {
            text: 'Daily Test Cases',
            x: 'center',
            y: '20px'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        color: ['#C9D4E3', '#F3D1CD'],
        legend: {
            data: ['Passed', 'Failed']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'value'
        },
        yAxis: {
            type: 'category',
            data: ['Mo', 'Tu', 'We', 'Th', 'Fr']
        },
        series: [
            {
                name: 'Passed',
                type: 'bar',
                stack: '总量',
                label: {
                    normal: {
                        show: true,
                        position: 'insideRight'
                    }
                },
                data: [320, 302, 301, 334, 390]
            },
            {
                name: 'Failed',
                type: 'bar',
                stack: '总量',
                label: {
                    normal: {
                        show: true,
                        position: 'insideRight'
                    }
                },
                data: [120, 132, 101, 134, 90]
            }
        ]
    }
}

function randomize() {
    return [0, 0, 0].map(v => {
        return Math.round(300 + Math.random() * 700) / 10
    })
}
