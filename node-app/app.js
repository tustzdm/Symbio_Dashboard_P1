
/**
 * Module dependencies.(模块依赖)
 */

var express = require('express');
var http = require('http');
var routes = require('./routes');
var qo = require('./routes/qualityoverview');
var uiinfo = require('./routes/uiinfo');

var app = express();
// all environments(环境变量)
app.set('port', process.env.PORT || 8100);

app.use(express.cookieParser('likeshan'));  
app.use(express.session({ secret: "andylau" }));
app.use(app.router);

//路径解析
app.get('/menu/getQualityViewLayout' , qo.getQualityViewLayout);
app.get('/menu/getQualityOverview' , qo.getQualityOverview);
app.get('/menu/getQualityOverview2' , qo.getQualityOverview2);

app.get('/menu/getPageList' , uiinfo.getPageList);
app.get('/menu/getUIElementTypeList' , uiinfo.getUIElementTypeList);
app.get('/menu/getProductUIInfoList' , uiinfo.getProductUIInfoList);
app.get('/menu/getProductUiInfo' , uiinfo.getProductUiInfo);


//启动及端口
http.createServer(app).listen(app.get('port'), function(){
  console.log('Express server listening on port ' + app.get('port'));
});