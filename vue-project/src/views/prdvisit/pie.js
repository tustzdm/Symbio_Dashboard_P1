import data from './data'

let defaultValue = 'Priority'

function descendingSort(array, filter) {
    array.sort((a, b) => {
        if (a[filter] < b[filter]) return 1
        if (a[filter] > b[filter]) return -1
        return 0
    })
};

function sortData(data, filter, getName = false) {
    let name = [], value = [], result = [];
    data.forEach((item) => {
        if (name.includes(item[filter]) == false) {
            name.push(item[filter]);
            value.push(1);
        } else {
            let x = name.indexOf(item[filter]);
            value[x] += 1;
        }
    });
    name.forEach((item, i) => {
        let x = {};
        x["name"] = item;
        x["value"] = value[i];
        result.push(x);
    });
    descendingSort(result, "value")
    if (getName == true) {
        return name
    } else { return result }

}

export default function getData(filter = defaultValue) {
    return {
        title: {
            text: filter,
            x: 'center',
            y: '20px'
        },
        tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
            orient: 'vertical',
            data: sortData(data, filter.toLowerCase(), true).sort(),
            x: '10px',
            y: '10px'
        },
        series: [
            {
                name: 'Bugs by ' + filter,
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: sortData(data, filter.toLowerCase()),
                color: ['#7A85A1', '#9EADC5', '#C9D4E3', '#E8E8E8', '#F3D1CD', '#F9E8E0'],
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
