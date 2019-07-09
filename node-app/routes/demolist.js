

//////////////////////////////////////////////////////////////////
// 4.1	得到Common List测试信息
exports.getListDemo = function(req, res){
	console.log('[URL] === ' + req.url);
	console.log('token === ' + req.query.token);
	
	var result;
	result = {
			"ec":"0",
			"em":"",
			"cd":{
				"pageIndex": 0,
				"pageSize": 20,
				"totalRecord": 100,
				"locale": "en_US",
				"columns": [
					{"key":"id","label":"","type":"hidden","align":1,"field":"id","formatter":""},
					{"key":"product","label":"Product","type":"link","align":1,"field":"page","formatter":"http://127.0.0.1:8100/menu/getProductUIInfoList?token={token}&page={page}"},
					{"key":"key","label":"Key","type":"text","align":1,"field":"key","formatter":""},
					{"key":"isRequired","label":"Is Required","type":"bool","align":2,"field":"is_required","formatter":""},
					{"key":"coldemo","label":"UI Info","type":"uilink","align":1,"field":"id,page,key","formatter":"/menu/getProductUiInfo?token={token}&id={id}&page={page}&key={key}"},
					{"key":"qa","label":"QA Status","type":"autostatus","align":2,"field":"qa","formatter":""},
					{"key":"progress","label":"Progress","type":"percentage","align":3,"field":"progress","formatter":"#.##%"},
					{"key":"locale","label":"Locale","type":"text","field":"en_us"},
					{"key":"due","label":"Due Date","type":"DateTime","align":2,"field":"dueDate","formatter":"yyyy-mm-dd HH:MM"}
				],
				"fields": ["id","page","key","type","data","is_required","en_us","zh_cn","dueDate","progress", "qa"],
				"data": [
					[1,"Product","name","text",null,1,"Project Name","Name_cn","2019-07-09 16:20:18", 0.1234,0],
					[2,"Product","projectOwner","refUser","{\"userId\":1,\"userName\":\"Kevin\",\"email\":\"kevin@symbio.com\",\"avatar\":\"http://www.baidu.com\"}",1,"Owner","Owner_cn","2019-07-10 16:20:18", 0.1,1],
					[3,"Product","dueDate","calendar",null,0,"Due Date","dueDate_cn","2019-07-11 16:20:18", 1,2],
					[4,"Product","Progress","number",null,1,"Progress","进度","2019-07-12 16:20:18", 0,3],
					[5,"Product","QAStatus","text",null,0,"QAStatus","QA状态","2019-07-15 16:16:16", null, null]
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

exports.getListDemo2 = function(req, res){
	console.log('[URL] === ' + req.url);
	console.log('token === ' + req.query.token);
	
	var result;
	result = {
			"ec":"0",
			"em":"",
			"cd":{
				"pageIndex": 0,
				"pageSize": 20,
				"totalRecord": 100,
				"columns": [],
				"fields": ["id","name","","","","","",""],
				"data": [
					[],
					[],
					[],
					[],
					[]
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

