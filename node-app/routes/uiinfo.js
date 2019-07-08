

//////////////////////////////////////////////////////////////////
// 4.1	得到UIInfo的Page信息
exports.getPageList = function(req, res){
	console.log('[URL] === ' + req.url);
	console.log('token === ' + req.query.token);
	
	var result;
	result = {
			"ec":"0",
			"em":"",
			"cd":["Quality Overview", "Product", "Release", "Test Set", "Test Case"]
			}

	res.writeHead(200, {"Content-Type": "application/json"});
	// res.end(callback+"({\"result\":"+JSON.stringify(result)+"});");
	res.end(JSON.stringify(result));
	// console.log("*** "+callback+" *** "+"({\"result\":"+JSON.stringify(result)+"});");
	// console.log("*** "+callback+" *** "+"({\"result\":"+JSON.stringify(result)+"});");
	console.log(JSON.stringify(result));
	console.log();
}

exports.getUIElementTypeList = function(req, res){
	console.log('[URL] === ' + req.url);
	console.log('token === ' + req.query.token);
	//console.log('locale === ' + req.query.locale);
	
	var result;
	result = {
			"ec":"0",
			"em":"",
			"cd":[
				{"key": "text","value": "Text"},
				{"key": "number", "value":"Number"},
				{"key": "date,", "value":"Date"},
				{"key": "calendar,", "value":"Calendar"},
				{"key": "bool,", "value":"Bool"},
				{"key":"checkbox", "value":"CheckBox"},
				{"key":"selectlist", "value":"SelectList"},
				{"key":"dropdownlist", "value":"DropDownList"}
			]
	};
	res.writeHead(200, {"Content-Type": "application/json"});
	res.end(JSON.stringify(result));
	console.log(JSON.stringify(result));
	console.log();
}

// 4.3	得到Product的UiInfo信息
exports.getProductUIInfoList = function(req, res){
	console.log('[URL] === ' + req.url);
	console.log('token === ' + req.query.token);
	//console.log('locale === ' + req.query.locale);
	
	var result;
	result = {
			"ec":"0",
			"em":"",
			"cd":{
					"token": "123",
					"page": "Product",
					"locale":"en_US",
					"role": 7,
					"data": [
					{
							"id": 1,
							"key": "name",
							"type": "selectlist",
							"data": "",
							"is_required": 1,
							"is_disable": 0,
							"en_us": "Product Name",
							"zh_cn": "产品名称",
							"place_holder": "Product Name",
							"default_value": "",
							"constraint_condition": "",
							"idx": 1,
							"validation": 1
					},{
							"id": 2,
							"key": "projectOwner",
							"type": "text",
							"data": "[\"Paypal\",\"eBay\",\"Intuit\"]",
							"is_required": 1,
							"is_disable": 0,
							"en_us": "Project Owner",
							"zh_cn": "产品所有者",
							"place_holder": "Project Owner",
							"default_value": "eBay",
							"constraint_condition": "",
							"idx": 2,
							"validation": 1
						},{
							"id": 3,
							"key": "dueDate",
							"type": "calendar",
							"data": "",
							"is_required": 1,
							"is_disable": 0,
							"en_us": "Due Date",
							"zh_cn": "截至日期",
							"place_holder": "Due Date",
							"default_value": "",
							"constraint_condition": "",
							"idx": 3,
							"validation": 1
						},{
							"id": 4,
							"key": "Progress",
							"type": "number",
							"data": "",
							"is_required": 1,
							"is_disable": 0,
							"en_us": "Nmber",
							"zh_cn": "数量",
							"place_holder": "Progress",
							"default_value": "",
							"constraint_condition": "",
							"idx": 4,
							"validation": 1
						}
					]
			}
	};
	res.writeHead(200, {"Content-Type": "application/json"});
	res.end(JSON.stringify(result));
	console.log(JSON.stringify(result));
	console.log();
}

exports.getProductUiInfo = function(req, res){
	console.log('[URL] === ' + req.url);
	console.log('token === ' + req.query.token);
	console.log('id === ' + req.query.id);
	//console.log('locale === ' + req.query.locale);
	
	var result;
	result = {
			"ec":"0",
			"em":"",
			"cd":{
					"token": "123",
					"locale":"en_US",
					"role": 7,
					"data": {
						"id": 1,
						"key": "name",
						"type": "selectlist",
						"data": "",
						"is_required": 1,
						"is_disable": 0,
						"en_us": "Product Name",
						"zh_cn": "产品名称",
						"place_holder": "Product Name",
						"default_value": "",
						"constraint_condition": "",
						"idx": 1,
						"validation": 1
					}
			}
	};
	res.writeHead(200, {"Content-Type": "application/json"});
	res.end(JSON.stringify(result));
	console.log(JSON.stringify(result));
	console.log();
}
