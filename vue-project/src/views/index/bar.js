export default function getData() {
    return {
        color: ['#7A85A1', '#C9D4E3', '#E8E8E8', '#C9D4E3', '#F3D1CD', '#F9E8E0'],
        legend: { y: '5px' },
        tooltip: {},
        dataset: {
            source: [
                ['Product', '2015', '2016', '2017'],
                ['Matcha Latte', ...randomize()],
                ['Milk Tea', ...randomize()],
                ['Cheese Cocoa', ...randomize()],
                ['Walnut Brownie', ...randomize()]
            ]
        },
        grid: {
            left: '5%',
            right: '5%',
            bottom: '5%',
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
