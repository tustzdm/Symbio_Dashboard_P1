function randomize() {
    return Math.round(300 + Math.random() * 700) / 10
}

export default function getData() {
    return {
        title: {
            text: 'Product Workload',
            x: 'center',
            y: '20px'
        },
        tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
            orient: 'vertical',
            data: ['NewAccountSignUp', 'RiskTransfer', 'PersonalAddPhone', 'PersonalDeletePhone', 'GameSurface'],
            x: '10px',
            y: '10px'
        },
        series: [
            {
                name: 'Sample Data',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: [
                    { value: randomize(), name: 'NewAccountSignUp' },
                    { value: randomize(), name: 'RiskTransfer' },
                    { value: randomize(), name: 'PersonalAddPhone' },
                    { value: randomize(), name: 'PersonalDeletePhone' },
                    { value: randomize(), name: 'GameSurface' }
                ],
                color: ['#484891', '#FFBB77', '#7A85A1', '#9EADC5', '#408080'],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    }
}
