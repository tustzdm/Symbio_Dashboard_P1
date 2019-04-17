module.exports = function (title, data) {
    return {
        backgroundColor: 'rgb(245,245,245)',
        tooltip: {
            formatter: "{a} <br/>{b} : {c}"
        },
        title: {
            text: title,
            x: 'center',
            top: '5',
            textStyle: {
                color: "#000",
            }
        },
        grid: {
            top: '2%',
            left: '3%',
            right: '12%',
            bottom: '3%',
            containLabel: true
        },
        toolbox: {
            feature: {
                restore: {},
                saveAsImage: {}
            }
        },
        series: [
            {
                name: title,
                type: 'gauge',
                detail: { formatter: '{value}' },
                data: [{ value: data, name: title }]
            }
        ]
    };
}