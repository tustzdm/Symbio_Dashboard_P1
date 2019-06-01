export default {
    title: {
        text: 'Platforms',
        x: 'center',
        y: '20px'
    },
    tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c} ({d}%)'
    },
    legend: {
        orient: 'vertical',
        data: ['Android', 'iOS', 'Windows', 'Mac', 'Web'],
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
                { value: 335, name: 'Android' },
                { value: 310, name: 'iOS' },
                { value: 234, name: 'Windows' },
                { value: 135, name: 'Mac' },
                { value: 1548, name: 'Web' }
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
