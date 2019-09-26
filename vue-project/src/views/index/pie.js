function randomize() {
    return Math.round(300 + Math.random() * 700) / 10
}

export default function getData() {
    return {
        title: { text: 'Platforms', x: 'center', y: '10px' },
        tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
            orient: 'vertical',
            data: ['Android', 'iOS', 'Windows', 'Mac', 'Tablet'],
            left: '10px',
            y: '10px'
        },
        series: [
            {
                name: 'Sample Data',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: [
                    { value: randomize(), name: 'Android' },
                    { value: randomize(), name: 'iOS' },
                    { value: randomize(), name: 'Windows' },
                    { value: randomize(), name: 'Mac' },
                    { value: randomize(), name: 'Tablet' }
                ],
                color: ['#F9E8E0', '#F3D1CD', '#7A85A1', '#9EADC5', '#C9D4E3', '#E8E8E8'],
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
