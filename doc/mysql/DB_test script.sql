


-- ----------------------------
-- Records of user (Ver2019-07-19)
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', 'admin@symbio.com', 'abc123', 'Lee', 'John', 'John Lee', null, null, 'en_US', '1', '1', '0', null, '1', null, null, null, null, null, null, null, null, null, null, null, '2019-07-19 12:15:08', '2019-07-19 12:23:50');
INSERT INTO `user` VALUES ('2', 'mike', 'mike@symbio.com', '1', 'Li', 'Mike', 'Mike Li', null, null, 'en_US', '1', '0', '0', null, '0', null, null, null, null, null, null, null, null, null, null, null, null, '2019-07-19 12:13:56');
INSERT INTO `user` VALUES ('3', 'laura', 'lucy@symbio.com', '1', 'Li', 'Laura', 'Laura Li', null, null, 'en_US', '1', '0', '0', null, '0', null, null, null, null, null, null, null, null, null, null, null, null, '2019-07-19 12:20:28');
INSERT INTO `user` VALUES ('4', 'mona', 'mona@symbio.com', '1', 'Guan', 'Mona', 'Mona Guan', null, null, 'en_US', '1', '0', '0', null, '0', null, null, null, null, null, null, null, null, null, null, null, null, '2019-07-19 12:21:03');
INSERT INTO `user` VALUES ('5', 'Daniel', 'danie@symbio.com', '1', 'Sun', 'Daniel', 'Daniel Sun', null, null, 'en_US', '1', '0', '0', null, '0', null, null, null, null, null, null, null, null, null, null, null, null, '2019-07-19 12:22:24');
INSERT INTO `user` VALUES ('6', 'Kevin', 'kevin@symbio.com', '1', 'Xu', 'Kevin', 'Kevin Xu', null, null, 'en_US', '1', '0', '0', null, '0', null, null, null, null, null, null, null, null, null, null, null, null, '2019-07-19 12:22:29');
INSERT INTO `user` VALUES ('7', 'abby', 'abby@symbio.com', '1', '', 'Abby', 'Abby', null, null, 'en_US', '1', '0', '0', null, '0', null, null, null, null, null, null, null, null, null, null, null, null, '2019-07-19 12:23:10');
INSERT INTO `user` VALUES ('8', 'shawn', 'shawn@symbio.com', '1', 'Liu', 'Shawn', 'Shawn Liu', null, null, 'en_US', '1', '0', '0', null, '0', null, null, null, null, null, null, null, null, null, null, null, null, '2019-07-19 12:23:39');
INSERT INTO `user` VALUES ('9', 'longjiang', 'longjiang@symbio.com', '1', 'Zhang', 'LongJiang', 'LongJiang Zhang', null, null, 'en_US', '1', '0', '0', null, '0', null, null, null, null, null, null, null, null, null, null, null, null, '2019-07-19 12:24:30');
INSERT INTO `user` VALUES ('10', 'danny', 'danny@symbio.com', '1', 'Chen', 'Danny', 'Danny Chen', null, null, 'en_US', '1', '0', '0', null, '0', null, null, null, null, null, null, null, null, null, null, null, null, '2019-07-19 12:24:50');


-- ----------------------------
-- Records of result_message(Ver2019-08-02)
-- ----------------------------
INSERT INTO `result_message` VALUES ('1', '000001', 'Parameter could not be empty', '参数不能为空', null);
INSERT INTO `result_message` VALUES ('2', '000002', 'Exception happened', '处理异常', null);
INSERT INTO `result_message` VALUES ('3', '000010', 'DB query failure', '数据库查询失败', null);
INSERT INTO `result_message` VALUES ('4', '000011', 'Could not find any data in the db.', '没有找到任何数据', null);
INSERT INTO `result_message` VALUES ('5', '000012', 'DB operation failure', '数据库操作失败', null);
INSERT INTO `result_message` VALUES ('6', '000013', 'Has no privilege', '没有访问权限', null);
INSERT INTO `result_message` VALUES ('7', '000014', 'Has no privilege for operation', '没有操作权限', null);
INSERT INTO `result_message` VALUES ('11', '000101', 'Parameter [%s] could not be empty', '参数【%s】不能为空', null);
INSERT INTO `result_message` VALUES ('12', '000102', 'Exception happened while %s', '处理【%s】异常', '');
INSERT INTO `result_message` VALUES ('20', '000120', 'Could not get %s info', '不能得到%s信息', null);
INSERT INTO `result_message` VALUES ('21', '000121', '%s list\'s columns info is empty.  Please contact administrator', '【%s】列表的标题信息不能为空。请联系管理员', null);
INSERT INTO `result_message` VALUES ('22', '000122', '%s does not exist', '【%s】不存在', null);
INSERT INTO `result_message` VALUES ('23', '000123', '%s already exists', '【%s】已存在', null);
INSERT INTO `result_message` VALUES ('24', '000124', 'Could not get dictionary data. Type = [%s]', '没有取得Type =【%s】字典数据', null);
INSERT INTO `result_message` VALUES ('160', '001001', 'Could not get Table[%s] field info', '不能得到【%s】表的字段信息', null);
INSERT INTO `result_message` VALUES ('161', '001003', 'Could not find table name.  Page = [%s]', '找不到对应页【%s】的数据库表名信息', null);
INSERT INTO `result_message` VALUES ('500', '200100', 'Product', '项目', null);
INSERT INTO `result_message` VALUES ('501', '200200', 'Release', '版本', null);
INSERT INTO `result_message` VALUES ('502', '200300', 'Test Set', '测试集', null);
INSERT INTO `result_message` VALUES ('1000', '100001', 'Quality Overview query failure ', 'Quality Overview 查询失败', null);
INSERT INTO `result_message` VALUES ('2000', '200001', 'Get Page Element Info Exception', '得到页面信息异常', null);
INSERT INTO `result_message` VALUES ('2001', '200110', 'Product update failure', '项目保存失败', null);
INSERT INTO `result_message` VALUES ('2002', '200111', 'Get Product Info Interface Exception', '处理项目信息异常', null);
INSERT INTO `result_message` VALUES ('2003', '200102', 'Getting %s failure', '取得【%s】失败', '');
INSERT INTO `result_message` VALUES ('2004', '200104', 'Exception happened while %s', '处理【%s】异常', null);
INSERT INTO `result_message` VALUES ('2006', '200106', 'Test Message id=[%d], Name=[%s]', '测试消息： id=[%d], Name=[%s]', 'getResultArgs(\"zh_CN\", \"200106\", 100, \"Symbio\")');
INSERT INTO `result_message` VALUES ('2007', '200201', 'Product Id could not be empty', 'Product Id不能为空', null);