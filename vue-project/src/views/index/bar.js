export default function getData() {
    props:
    return {
        title: { text: 'Testing type', left: '10px', y: '10px' },
        color: ['#7A85A1', '#C9D4E3', '#E8E8E8', '#9EADC5', '#F3D1CD', '#F9E8E0'],
        legend: { y: '10px', right: '10px', },
        tooltip: {},
        dataset: {
            source: [
                ['Product', 'Unit Testing', 'Integration Testing', 'System Testing'],
                ['Product Venus', ...randomize()],
                ['Product Apollo', ...randomize()],
                ['Product Mars', ...randomize()],
                ['Product Diana', ...randomize()]
            ]
        },
        grid: {
            left: '3%',
            right: '3%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: { type: 'category' },
        yAxis: {},
        series: [{ type: 'bar' }, { type: 'bar' }, { type: 'bar' }]
    }
}

function randomize() {
    return [0, 0, 0].map(v => {
        return Math.round(300 + Math.random() * 700) / 10
    })
}
