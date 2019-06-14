function createPosition(index) {
    let x, y
    if (index % 2 == 0) { y = 0 } else { y = 1 }
    x = (index - y) / 2;
    return [x, y];
}

function createInstanceBody(value, userIndex, supportCharts, position = false) {
    for (let i in supportCharts) {
        if (supportCharts[i].name == value) {
            let result = {};
            result["key"] = supportCharts[i].key;
            if (position == true) {
                result["pos"] = createPosition(userIndex);
            } else {
                result["pos"] = userIndex;
            }
            return result;
        }
    }
}

function insertBody(chartOutput, supportCharts, position = false) {
    var result = [];
    if (chartOutput.constructor === Object) {
        for (let key in chartOutput) {
            let value = chartOutput[key];
            if (value != "Please select") {
                var userIndex = parseInt(key.replace("select", "")) - 1;
                result.push(createInstanceBody(value, userIndex, supportCharts, position));
            }
        }
        return result;
    } else if (Array.isArray(chartOutput)) {
        for (let index in chartOutput) {
            if (chartOutput[index] != "Please select") {
                result.push(createInstanceBody(chartOutput[index], parseInt(index), supportCharts, position));
            }
        }
        return result;
    }
}

function postMethod([userOutput, supportCharts]) {

    userOutput = JSON.parse(userOutput);
    supportCharts = JSON.parse(supportCharts);

    let body = '{"token": "kevxu","locale": "en_US","listChartCommon": regularChartsOutput,"listChartOther": customChartsOutput,"listRowChart": bottomChartsOutput,"listList": tableOutputs}';
    let mapObject = {
        regularChartsOutput: JSON.stringify(insertBody(userOutput.regularChartsOutput, supportCharts, true)),
        tableOutputs: JSON.stringify(insertBody(userOutput.tableOutputs, supportCharts)),
        bottomChartsOutput: JSON.stringify(insertBody(userOutput.bottomChartsOutput, supportCharts)),
        customChartsOutput: JSON.stringify(insertBody(userOutput.customChartsOutput, supportCharts))
    }

    body = body.replace(/regularChartsOutput|tableOutputs|bottomChartsOutput|customChartsOutput/gi, function (matched) {
        return mapObject[matched];
    })
    alert(body);

}

export { postMethod }