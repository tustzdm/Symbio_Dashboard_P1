import data from './data'

let dataXY = ['Priority', 'Status']

function objectArrayReturnTable(data, filterX, filterY) {
    //[{},{},{}] return a table
    let result = [], X = [], Y = [];
    data.forEach(item => {
        if (X.includes(item[filterX]) == false) {
            X.push(item[filterX]);
        }
        if (Y.includes(item[filterY]) == false) {
            Y.push(item[filterY]);
        }
    })
    X.sort()
    Y.sort()
    data.forEach(item => {
        let index = X.indexOf(item[filterX]) + Y.indexOf(item[filterY]) * X.length
        if (result[index] == undefined) { result[index] = 1 } else { result[index] += 1 }

    })
    result = Array.from(result, item => item || 0);
    return [X, Y, result]
}

function makingTable([x, y, result], xName, getSeries = false) {
    if (getSeries == true) {
        let item = { type: 'bar' }
        let data = []
        for (let i = 0; i < x.length; i++) {
            data.push(item)
        }
        return data
    } else {
        let data = []
        x.unshift(xName)
        data.push(x);
        y.forEach((item, index) => {
            let row = result.slice(index * (x.length - 1), (index + 1) * (x.length - 1))
            row.unshift(item)
            data.push(row)
        })
        return data
    }
}


export default function getData([filterX, filterY] = dataXY) {
    return {
        title: {
            text: filterX + ' per ' + filterY,
            x: '20px',
            y: '5px'
        },
        color: ['#7A85A1', '#C9D4E3', '#E8E8E8', '#9EADC5', '#F3D1CD', '#F9E8E0'],
        legend: { y: '30px', right: '20px' },
        tooltip: {},
        dataset: {
            source: makingTable(objectArrayReturnTable(data, filterX.toLowerCase(), filterY.toLowerCase()), filterX)
        },
        grid: {
            left: '3%',
            right: '3%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: { type: 'category' },
        yAxis: {},
        series: makingTable(objectArrayReturnTable(data, filterX.toLowerCase(), filterY.toLowerCase()), filterX, true)
    }
}
