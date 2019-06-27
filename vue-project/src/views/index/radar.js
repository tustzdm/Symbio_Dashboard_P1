export default {
    color: ['#7A85A1', '#F3D1CD'],
    title: {
        text: 'Pass rate per Product', left: '10px', y: '10px'
    },
    tooltip: {},
    legend: {
        data: ['Total cases', 'Passed cases'], y: '10px', right: '10px', orient: 'vertical'
    },
    radar: {
        // shape: 'circle',
        name: {
            textStyle: {
                color: '#fff',
                backgroundColor: '#7A85A1',
                borderRadius: 3,
                padding: [3, 5]
            }
        },
        indicator: [
            { name: 'Product Ceres', max: 6500 },
            { name: 'Product Vulcanus', max: 16000 },
            { name: 'Product Juno', max: 30000 },
            { name: 'Product Mercury', max: 38000 },
            { name: 'Product Vesta', max: 52000 },
            { name: 'Product Neptunus', max: 25000 }
        ]
    },
    series: [{
        name: 'Total cases vs Passed cases）',
        type: 'radar',
        data: [
            {
                value: [6300, 16000, 28000, 35000, 50000, 22000],
                name: 'Total cases'
            },
            {
                value: [5000, 14000, 28000, 31000, 42000, 21000],
                name: 'Passed cases'
            }
        ]
    }]
};