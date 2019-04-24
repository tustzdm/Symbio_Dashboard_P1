

//////////////////////////////////////////////////////////////////
// 4.1	得到QualityView布局信息
exports.getQualityViewLayout = function(req, res){
	console.log('[URL] === ' + req.url);
	
	console.log('token === ' + req.query.token);
	console.log('locale === ' + req.query.locale);
	
	var result;
	result = {
			"ec":"0",
			"em":"",
			"cd":{
					"locale":"en_US",
					"role": 7,
					"listSupport":
						[
						{
							"idx": 2,
							"key": "BarSimple",
							"name": "Bar Simple",
							"type": 1
						},
						{
							"pos": 0,
							"idx": 6,
							"key": "listTestSet",
							"name": "Data for TestSet statistics",
							"type": 3
						},
						{
							"pos": 0,
							"idx": 7,
							"key": "MixLineBarSample",
							"name": "Sample for Other Row Chart",
							"type": 4
						}
						],
					"listChartCommon": [
						{ 
							"pos": [0,0],
							"idx": 0,
							"key": "StackedLine",
							"name": "Stacked Line Chart",
							"type": 1
						},
						{
							"pos": [0,1],	
							"idx": 1,
							"key": "BarLabRotation",
							"name": "Bar Label Rotation",
							"type": 1							
						},
						],
					"listChartOther": [
						{ 
							"idx": 3,
							"key": "PieScrollLegend",
							"name": "Pie with Scrollable Legend",
							"type": 2							
						}
						],
					"listRowChartUsed": [
						{
							"pos": 0,
							"idx": 4,
							"key": "MixLineBar",
							"name": "Mixed Line and bar",
							"type": 4
						}
					],
					"listListUsed": [
						{
							"pos": 0,
							"idx": 5,
							"key": "listProduct",
							"name": "Data for product statistics",
							"type": 3
						}
						]
				}
			}
	res.writeHead(200, {"Content-Type": "application/json"});
	// res.end(callback+"({\"result\":"+JSON.stringify(result)+"});");
	res.end(JSON.stringify(result));
	// console.log("*** "+callback+" *** "+"({\"result\":"+JSON.stringify(result)+"});");
	// console.log("*** "+callback+" *** "+"({\"result\":"+JSON.stringify(result)+"});");
	console.log(JSON.stringify(result));
	console.log();
}

// 4.3	得到QualityView信息
exports.getQualityOverview = function(req, res){
	console.log('[URL] === ' + req.url);
	console.log('token === ' + req.query.token);
	console.log('locale === ' + req.query.locale);
	
	var result;
	result = {
			"ec":"0",
			"em":"",
			"cd":{
					"token": "123",
					"locale":"en_US",
					"role": {"product":7,"release":7,"testset":7},
					"search": {
						"isCommon": 0,
						"common": {
							"product": "",
							"release": "",
							"testset": "",
						},
						"other": null
					},
					"listCombox":
						[
						{ 
							"key": "common",
							"name": "Common"
						},
						{ 
							"key": "PieScrollLegend",
							"name": "Pie with Scrollable Legend",
							"condition": [
								{
									"name": "product",
									"type": "list",
									"value": "all",
									"data": [
										{
											"key": "all",
											"value": "All"
										},
										{
											"key": "prd1",
											"value": "Product 1"
										},
										{
											"key": "prd2",
											"value": "Product 2"
										},
										{
											"key": "prd3",
											"value": "Product 3"
										}
									]
								},
								{
									"name": "release",
									"type": "list",
									"value": "all",
									"data": [
										{
											"key": "all",
											"value": "All"
										},
										{
											"key": "release1",
											"value": "Release 1"
										},
										{
											"key": "release2",
											"value": "Release 2"
										},
										{
											"key": "release3",
											"value": "Release 3"
										}
									]
								},
								{
									"name": "testset",
									"type": "list",
									"value": "testset2",
									"data": [
										{
											"key": "all",
											"value": "All"
										},
										{
											"key": "testset1",
											"value": "Test Set 1"
										},
										{
											"key": "testset2",
											"value": "Test Set 2"
										},
										{
											"key": "testset3",
											"value": "Test Set 3"
										}
									]
								},
							]							
						}
						],
					"listCharts": [
						{ 
							"pos": [0,0],
							"key": "StackedLine",
							"data": {
								"legend": ['Pass','Fail','Skip','Success'],
								"name": ['TestSet1','TestSet2','TestSet3'],
								"groupby": 0,
								"data": {
											"TestSet1": [10,2,1,30],
											"TestSet2": [1,2,3,4],
											"TestSet3": [5,6,7,8]
										}

							}
						},
						{ 
							"pos": [0,1],
							"key": "StackedLine",
							"data": {
								"legend": ['Pass','Fail','Skip','Success'],
								"name": ['TestSet1','TestSet2','TestSet3'],
								"groupby": 1,
								"data": {
											"Pass": [10,1,5],
											"Fail": [2,2,6],
											"Skip": [1,3,7],
											"Success": [30,4,8]
										}

							}
						},
						{ 
							"pos": [1,0],
							"key": "BarLabRotation",
							"data": {
								"data": {
									"color": ['#003366', '#006699', '#4cabce', '#e5323e'],
									"legend": ['Forest', 'Steppe', 'Desert', 'Wetland'],
									"xData": ['2012', '2013', '2014', '2015', '2016'],
									"groupby": 1,
									"seriesData": {
										'Forest': [320, 332, 301, 334, 390], 
										'Steppe': [220, 182, 191, 234, 290],
										'Desert': [150, 232, 201, 154, 190], 
										'Wetland':[98, 77, 101, 99, 40]
										}
								}
							}
						}
						],
					"listList": [
						{
							"pos": 0,
							"key": "listProductStatistics",
							"data": {
								"pageIndex": 0,
								"pageSize": 50,
								"totalRecord": 2,
								"columns": [
									{
										"key":"ProductIDLink",
										"label":"Product",
										"type":"uilink",
										"align":2,
										"field":"id",
										"formatter":null
									},
									{
										"key":"",
										"label":"Test Case",
										"type":"text",
										"align":1,
										"field":"name",
										"formatter":null
									},
									{
										"key":"AutoStatus",
										"label":"Auto Status",
										"type":"autostatus",
										"align":2,
										"field":"auto",
										"formatter":null
									},
									{
										"key":"QAStatus",
										"label":"QA Status",
										"type":"qastatus",
										"align":2,
										"field":"qa",
										"formatter":null
									},
									{
										"key":"Percentage",
										"label":"Progress",
										"type":"qastatus",
										"align":2,
										"field":"progress",
										"formatter":"#.##%"
									},
									{
										"key":"User",
										"label":"Auto Engineer",
										"type":"text",
										"align":2,
										"field":"engineer",
										"formatter":null
									},
									{
										"key":"",
										"label":"Comment",
										"type":"number",
										"align":3,
										"field":"comment"
									},
									{
										"key":"testLink",
										"label":"Test Set Link",
										"type":"formatter",
										"align":2,
										"field":"id",
										"formatter":"http://127.0.0.1/Testset/{value}?token={token}"
									}
								],
								"fields": ["id","name","auto","qa","progress","engineer","comment"],
								"data": [
									//{1,'Quality Overview report', 1, 1, 12.34, 'User1', 100},
									//{2,"Test Management", 3, 0, 100, "User2", 1.23}
									{"id":1,"name":'Quality Overview report', "auto":1, "qa":1, "progress":12.34, "engineer":'User1', "comment":100},
									{"id":2,"name":"Test Management", "auto":3, "qa":0, "progress":100, "engineer":"User2", "comment":1.23}
									
								]
							}
						}
					],
					"listRowCharts": [
						{ 
							"pos": 0,
							"key": "MixLineBar",
							"data": {
								"legend": ['蒸发量','降水量','平均温度'],
								"xData": ['1月','2月','3月'],
								"yData": [
											{
												"name": "水量",
												"formatter": "{value} ml"
											},
											{
												"name": "温度",
												"formatter": "{value} °C"
											}
										],
								"groupby": 1,
								"seriesData": [
										{
											"name": "蒸发量",
											"data": [2.0, 4.9, 7.0]
										},
										{
											"name": "降水量",
											"data": [2.6, 5.9, 9.0]
										},
										{
											"name": "平均温度",
											"data": [2.0, 2.2, 3.3]
										}
									]
							}
						}
						]
				}
			}
	res.writeHead(200, {"Content-Type": "application/json"});
	res.end(JSON.stringify(result));
	console.log(JSON.stringify(result));
	console.log();
}

// 4.3	得到QualityView信息
exports.getQualityOverview2 = function(req, res){
	console.log('[URL] === ' + req.url);
	
	console.log('token === ' + req.query.token);
	console.log('locale === ' + req.query.locale);
	
	var result;
	result = {
			"ec":"0",
			"em":"",
			"cd":{
					"locale":"es_EN",
					"role": {"product":1,"release":1,"testset":1},
					"listCombox":
						[
						{ 
							"key": "common",
							"name": "Common",
							"condition": null
						},
						{ 
							"key": "PieScrollLegend",
							"name": "Pie with Scrollable Legend",
							"condition": {}							
						}
						],
					"listCharts": [],
					"listList": [
						{
							"pos": 0,
							"key": "listTestSet",
							"columns": ["Product","Release","Test Set","Pass","Fail","Bug", "progress"],
							"data": [{},{},{},{},{},{}]
						}],
					"listRowCharts": []
				}
			}
	res.writeHead(200, {"Content-Type": "application/json"});
	res.end(JSON.stringify(result));
	console.log(JSON.stringify(result));
	console.log();
}
