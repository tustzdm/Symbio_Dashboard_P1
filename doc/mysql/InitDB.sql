-- 2019-03-27, Ver0.1, for QualityOverviewLayout
-- 2019-04-24 	Ver0.2 	Add Table: base_code
-- 2019-04-30 	Ver0.3	Add Table: dict_table, user, product, release, test_set

Drop Table IF EXISTS `base_code`;
CREATE TABLE `base_code` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `type` varchar(32) NOT NULL COMMENT 'group name',
  `code` varchar(32) NOT NULL COMMENT 'unique code',
  `name` varchar(64) NOT NULL COMMENT 'name for the item',
  `description` varchar(255) NOT NULL COMMENT 'description',
  `validation` smallint(5) unsigned NOT NULL DEFAULT '1' COMMENT '\n 1=valid,\n 0=invalid',
  `order_idx` smallint(5) unsigned NOT NULL COMMENT 'index for the group',
  `parent_id` int(10) unsigned DEFAULT NULL COMMENT 'parent id if has hierachy',
  `spare_smallint` smallint(10) unsigned DEFAULT NULL COMMENT 'spare field for small int type',
  `spare_int` int(10) unsigned DEFAULT NULL COMMENT 'spare field for int type',
  `spare_str100` varchar(100) DEFAULT NULL COMMENT 'spare field for string',
  `spare_str200` varchar(200) DEFAULT NULL COMMENT 'spare field for string',
  `spare_str500` varchar(500) DEFAULT NULL COMMENT 'spare field for string',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

Drop Table IF EXISTS `dictionary`;
CREATE TABLE `dictionary` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `type` varchar(50) DEFAULT NULL COMMENT 'team type',
  `code` varchar(64) NOT NULL COMMENT 'key',
  `value` varchar(512) DEFAULT NULL COMMENT 'value',
--  `zh_cn` varchar(255) DEFAULT NULL COMMENT 'Label for zh_cn'
  `validation` smallint(1) unsigned NOT NULL DEFAULT '1' COMMENT '1-true, 0-false',
  `description` varchar(255) DEFAULT '' COMMENT 'description',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqe_dict_table_type_code` (`type`, `code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

INSERT INTO `dictionary` VALUES (1, 'TemplateCategory', 'product', 'Product', 1, '');
INSERT INTO `dictionary` VALUES (2, 'TemplateCategory', 'release', 'Release', 1, '');
INSERT INTO `dictionary` VALUES (3, 'TemplateCategory', 'testset', 'Test Set', 1, '');
INSERT INTO `dictionary` VALUES (4, 'TemplateCategory', 'tep', 'TEP', 1, 'Testrun Execuation Platform');
INSERT INTO `dictionary` VALUES (5, 'TemplateCategory', 'testcase', 'Test Case', 1, '');
INSERT INTO `dictionary` VALUES (6, 'TemplateCategory', 'testrun', 'Test Run', 1, '');
INSERT INTO `dictionary` VALUES (7, 'TemplateCategory', 'testresult', 'Test Result', 1, '');
INSERT INTO `dictionary` VALUES (20, 'DataType', 'int', 'int', 1, '');
INSERT INTO `dictionary` VALUES (21, 'DataType', 'bool', 'Bool', 1, '1-true, 0-false');
INSERT INTO `dictionary` VALUES (22, 'DataType', 'string', 'String', 1, '');
INSERT INTO `dictionary` VALUES (23, 'DataType', 'date', 'Date', 1, '');
INSERT INTO `dictionary` VALUES (24, 'DataType', 'datetime', 'DateTime', 1, '');
INSERT INTO `dictionary` VALUES (25, 'DataType', 'selectlist', 'SelectList', 1, '');
INSERT INTO `dictionary` VALUES (26, 'DataType', 'dropdownlist', 'DropDownList', 1, '');
INSERT INTO `dictionary` VALUES (27, 'DataType', 'json', 'JSON', 1, '');
-- 2019/7/4
INSERT INTO `dictionary` VALUES (40, 'HtmlType', 'text', 'Text', 1, '');
INSERT INTO `dictionary`(`type`,`code`,`value`) VALUES ('HtmlType', 'int', 'Number');
INSERT INTO `dictionary`(`type`,`code`,`value`) VALUES ('HtmlType', 'label', 'Label');
INSERT INTO `dictionary`(`type`,`code`,`value`) VALUES ('HtmlType', 'bool', 'Option');
INSERT INTO `dictionary`(`type`,`code`,`value`) VALUES ('HtmlType', 'checkbox', 'CheckBox');
INSERT INTO `dictionary`(`type`,`code`,`value`) VALUES ('HtmlType', 'list', 'SelectList');
INSERT INTO `dictionary`(`type`,`code`,`value`) VALUES ('HtmlType', 'dropdownlist', 'DropdownList');
INSERT INTO `dictionary`(`type`,`code`,`value`) VALUES ('HtmlType', 'calendar', 'Calendar');
INSERT INTO `dictionary`(`type`,`code`,`value`) VALUES ('HtmlType', 'picture', 'Picture');
-- 2019/7/12 Start
INSERT INTO `dictionary`(`type`,`code`,`value`) VALUES ('HtmlType', 'user', 'User');
INSERT INTO `dictionary`(`type`,`code`,`value`) VALUES ('HtmlType', 'userlist', 'UserList');
INSERT INTO `dictionary`(`type`,`code`,`value`) VALUES ('HtmlType', 'product', 'Product');
INSERT INTO `dictionary`(`type`,`code`,`value`) VALUES ('HtmlType', 'productlist', 'ProductList');
INSERT INTO `dictionary`(`type`,`code`,`value`) VALUES ('HtmlType', 'release', 'Release');
INSERT INTO `dictionary`(`type`,`code`,`value`) VALUES ('HtmlType', 'releaselist', 'ReleaseList');
INSERT INTO `dictionary`(`type`,`code`,`value`) VALUES ('HtmlType', 'testset', 'TestSet');
INSERT INTO `dictionary`(`type`,`code`,`value`) VALUES ('HtmlType', 'testsetlist', 'TestSetList');
-- 2019/7/4
INSERT INTO `dictionary`(`type`,`code`,`value`, `description`) VALUES ('HtmlType', 'search', 'Search', '["User","Product","Release","TestSet"]');
INSERT INTO `dictionary`(`type`,`code`,`value`, `description`) VALUES ('HtmlType', 'link', 'Link', '["User","Product","Release","TestSet"]');

DROP TABLE IF EXISTS `report_chart`;
CREATE TABLE `report_chart` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `page` VARCHAR(45) NOT NULL COMMENT 'QualityOverview, BugReport',
  `key` VARCHAR(45) NOT NULL,
  `name` VARCHAR(60) NOT NULL DEFAULT '{"zh_cn": "","en_us":""}',
  `type` int(10) NOT NULL COMMENT '1: common chart,2: other chart,3: list,4: row chart' DEFAULT 1,
  `idx` INT NOT NULL,
  `search` VARCHAR(1024) NULL COMMENT 'json format for search condition defined for elements',
  `validation` INT NOT NULL COMMENT '0: disabel,1: available' DEFAULT 1,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

INSERT INTO `report_chart` VALUES (1, 'QualityOverview', 'StackedLine', '{\"zh_cn\": \"标准线形图\",\"en_us\":\"Stacked Line Chart\"}', 1, 1, NULL, 1);
INSERT INTO `report_chart` VALUES (2, 'QualityOverview', 'BarLabRotation', '{\"zh_cn\": \"柱状图\",\"en_us\":\"Bar Label Rotation\"}', 1, 2, NULL, 1);
INSERT INTO `report_chart` VALUES (3, 'QualityOverview', 'BarSimple', '{\"zh_cn\": \"简单柱状图\",\"en_us\":\"Bar Simple\"}', 1, 3, NULL, 1);
INSERT INTO `report_chart` VALUES (4, 'QualityOverview', 'PieScrollLegend', '{\"zh_cn\": \"饼图\",\"en_us\":\"Pie with Scrollable Legend\"}', 2, 5, NULL, 1);
INSERT INTO `report_chart` VALUES (5, 'QualityOverview', 'MixLineBar', '{\"zh_cn\": \"线形柱状图\",\"en_us\":\"Mixed Line and bar\"}', 4, 6, NULL, 1);

DROP TABLE IF EXISTS `setting_layout`;
CREATE TABLE `setting_layout` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `page` VARCHAR(45) NOT NULL COMMENT 'QualityOverview, BugReport',
  `type` INT NOT NULL COMMENT '0: System support 1: Admin Setting' DEFAULT 1,
  `locale` VARCHAR(45) NOT NULL COMMENT 'zh_CN, en_US',
  `layout` VARCHAR(1024) NULL COMMENT 'JSON format',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time for the record',
  `create_time` DATETIME NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `ui_info`;
CREATE TABLE `ui_info` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `page` VARCHAR(30) NOT NULL COMMENT 'QualityOverviewLayout',
  `key` VARCHAR(30) NOT NULL,
  `db_field` varchar(32) DEFAULT NULL COMMENT 'DB field. Defined in different table according to page if needed',
  `type` VARCHAR(45) NOT NULL COMMENT 'text, number, bool, checkbox, list' DEFAULT 'text',
  `data` VARCHAR(512) DEFAULT NULL COMMENT 'For List, Using JSON format.',
  `disp_status` SMALLINT(5) DEFAULT 0 COMMENT 'Display status for the element. 0-Add/Edit 1-Only add, 2-Only Edit',
  `is_required` TINYINT(1) DEFAULT true COMMENT 'The element is required or not. 1-True, 0-False',
  `is_disable` TINYINT(1) DEFAULT false COMMENT 'The element is disable or not. 1-True, 0-False',
  `en_us` VARCHAR(128) DEFAULT NULL COMMENT 'Label for en_US',
  `zh_cn` VARCHAR(128) DEFAULT NULL COMMENT 'Label for zh_cn',
  `place_holder` varchar(32) DEFAULT '' COMMENT 'Place holder',
  `label` VARCHAR(128) NOT NULL DEFAULT '{"key":""}' COMMENT 'For locale extend',
  `default_value` VARCHAR(45) NULL COMMENT 'default content for the element',
  `const_condition` VARCHAR(255) DEFAULT NULL COMMENT 'constraint condition if needed',
  `idx` SMALLINT NOT NULL DEFAULT 99,
  `version` VARCHAR(45) NULL,
  `validation` SMALLINT NOT NULL COMMENT '0: false, 1: true' DEFAULT 1,
	`display` SMALLINT NOT NULL DEFAULT '1' COMMENT '1: enable, 4: removed' DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqe_ui_info_page_key` (`page`, `key`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

INSERT INTO `ui_info`(`id`,`page`,`key`,`type`,`en_us`,`zh_cn`,`label`,`idx` ) 
VALUES (1, 'QualityOverviewLayout', 'CommChart', 'label', 'Regular Charts', '通用控件', '{\"zh_cn\": \"通用控件\",\"en_us\":\"Common Chart\"}', 1);
INSERT INTO `ui_info`(`id`,`page`,`key`,`type`,`en_us`,`zh_cn`,`label`,`idx` ) 
VALUES (2, 'QualityOverviewLayout', 'OtherChart', 'label', 'Other Charts', '其他报表控件', '{\"zh_cn\": \"其他报表控件\",\"en_us\":\"Other Chart\"}', 2);
INSERT INTO `ui_info`(`id`,`page`,`key`,`type`,`en_us`,`zh_cn`,`label`,`idx` ) 
VALUES (3, 'QualityOverviewLayout', 'ListList', 'label', 'Tables', '列表设置', '{\"zh_cn\": \"列表设置：\",\"en_us\":\"List\"}',3);
INSERT INTO `ui_info`(`id`,`page`,`key`,`type`,`en_us`,`zh_cn`,`label`,`idx` ) 
VALUES (4, 'QualityOverviewLayout', 'RowChart', 'label', 'Bottom Charts', '行控件设置', '{\"zh_cn\": \"行控件设置：\",\"en_us\":\"Row Chart:\"}', 4);
INSERT INTO `ui_info`(`id`,`page`,`key`,`type`,`en_us`,`zh_cn`,`label`,`idx` ) 
VALUES (5, 'QualityOverviewLayout', 'Preview', 'label', 'Preview', '下拉框预览', '{\"zh_cn\": \"下拉框预览：\",\"en_us\":\"Preview\"}', 5);

-- Product Page
INSERT INTO `ui_info`(`id`,`page`,`key`,`db_field`, `type`, `const_condition`, `en_us`, `zh_cn`, `place_holder` ,`idx` ) 
VALUES (10, 'Product', 'name', 'name', 'text', '{\"maxLength\": 32}', 'Name', '项目名称',  'Product Name', 1);
INSERT INTO `ui_info`(`page`,`key`,`db_field`,`type`,`const_condition`,`en_us`,`zh_cn`,`place_holder`,`idx` ) 
VALUES ('Product', 'Product Owner', 'owner', 'user', NULL, 'Product Owner', '项目所有者',  'Product Owner', 2);
INSERT INTO `ui_info`(`page`,`key`,`db_field`,`type`,`const_condition`,`en_us`,`zh_cn`,`place_holder`,`idx` ) 
VALUES ('Product', 'Product Manager', 'manager', 'user', NULL, 'Product Manager', '项目管理',  'Product Manager', 3);
INSERT INTO `ui_info`(`page`,`key`,`db_field`,`type`,`const_condition`,`en_us`,`zh_cn`,`place_holder`,`idx` )
VALUES ('Product', 'QA Lead', 'qa_lead', 'user', NULL, 'QA Lead', 'QA人员',  'QA Lead', 4);
INSERT INTO `ui_info`(`page`,`key`,`db_field`,`type`, `data`, `const_condition`,`en_us`,`zh_cn`,`place_holder`,`idx` ) 
VALUES ('Product', 'status', 'status', 'list', '[{"0","Pending"},{"1","In-Progress"},{"2","Block"},{"3","AtRisk"},{"4","Completed"}]', NULL, 'Status', '项目状态',  'Product Status', 5);
INSERT INTO `ui_info`(`page`,`key`,`db_field`,`type`,`const_condition`,`en_us`,`zh_cn`,`place_holder`,`idx` ) 
VALUES ('Product', 'locale', 'locale', 'text', '{\"maxLength\": 64}', 'Locales', '语种',  'Support locales', 6);
INSERT INTO `ui_info`(`page`,`key`,`db_field`,`type`,`const_condition`,`en_us`,`zh_cn`,`place_holder`,`idx` ) 
VALUES ('Product', 'description', 'description', 'text', '{\"maxLength\": 255}', 'Description', '项目描述',  'Product description', 20);

Drop Table IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'key',
  `email` varchar(32) NULL COMMENT 'Email of user',
  `passwd` varchar(32) NULL COMMENT 'password for local system',
  `full_name` varchar(32) DEFAULT NULL COMMENT 'Last mame+First name',
  `nick_name` varchar(32) NOT NULL COMMENT 'Nickname',
  `portrait_id` int unsigned DEFAULT NULL COMMENT 'avatar id',
  `mobile` varchar(11) DEFAULT NULL COMMENT 'phone number',
  `locale` varchar(6) NOT NULL DEFAULT 'en_US' COMMENT 'default locale ',
  `status` smallint(5) NOT NULL DEFAULT '1' COMMENT  'Status. 0-inactive，1-active, 4-delete',
  `disable` smallint(5) NOT NULL DEFAULT '0' COMMENT  'Account forbidden or not. 0-no，1-yes',
  `channel` smallint(5) NOT NULL DEFAULT '0' COMMENT 'Channel that user logged in. 0 - local, 1 - LDAP',
  `level_id` int unsigned DEFAULT '10' COMMENT '用户等级Id. SystemAdmin, Client, Tester(10), Engineer, Lead, Manager, Admin',
  `description` varchar(255) DEFAULT NULL COMMENT 'Description of user',

  `userfld_int1` int(10) DEFAULT NULL COMMENT 'Product field int',
  `userfld_int2` int(10) DEFAULT NULL COMMENT 'Product field int',
  `userfld_int3` int(10) DEFAULT NULL COMMENT 'Product field int',
  `userfld_str1` varchar(255) DEFAULT NULL COMMENT 'Product field string',
  `userfld_str2` varchar(255) DEFAULT NULL COMMENT 'Product field string',
  `userfld_str3` varchar(255) DEFAULT NULL COMMENT 'Product field string',
  `userfld_str4` varchar(255) DEFAULT NULL COMMENT 'Product field string',
  `userfld_str5` varchar(255) DEFAULT NULL COMMENT 'Product field string',
  `userfld_str6` varchar(255) DEFAULT NULL COMMENT 'Product field string',

  `create_time` datetime DEFAULT NULL COMMENT 'create time',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_nickName` (`nick_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

Drop Table IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'key',
  `name` varchar(32) NOT NULL COMMENT 'product name',
  `owner` int(10) unsigned NULL COMMENT 'product owner',
  `manager` int(10) unsigned NULL COMMENT 'product manager',
  `qa_lead` int(10) unsigned NULL COMMENT 'QA leader',
  `dev_lead` int(10) unsigned NULL COMMENT 'Develop leader',
  `logo_id` int unsigned DEFAULT NULL COMMENT 'pic id for logo',
  `logo_url` varchar(128) DEFAULT NULL COMMENT 'url for log. /img/product/symbio.jpg,  http://url:9999/...',
  `status` int unsigned NOT NULL DEFAULT '0' COMMENT 'Project status. 0-normal,1-abnormal,2-completed,3-archived,4-blocked',
  `display` int unsigned NOT NULL DEFAULT '1' COMMENT 'Display or not. 0-hide, 1-show',
  `description` varchar(255) DEFAULT NULL COMMENT 'Description',
  `locale` varchar(64) DEFAULT NULL COMMENT 'support locales',
  
  `prodfld_int1` int(10) DEFAULT NULL COMMENT 'Product field int',
  `prodfld_int2` int(10) DEFAULT NULL COMMENT 'Product field int',
  `prodfld_int3` int(10) DEFAULT NULL COMMENT 'Product field int',
  `prodfld_int4` int(10) DEFAULT NULL COMMENT 'Product field int',
  `prodfld_int5` int(10) DEFAULT NULL COMMENT 'Product field int',
  `prodfld_str1` varchar(255) DEFAULT NULL COMMENT 'Product field string',
  `prodfld_str2` varchar(255) DEFAULT NULL COMMENT 'Product field string',
  `prodfld_str3` varchar(255) DEFAULT NULL COMMENT 'Product field string',
  `prodfld_str4` varchar(255) DEFAULT NULL COMMENT 'Product field string',
  `prodfld_str5` varchar(255) DEFAULT NULL COMMENT 'Product field string',
  `prodfld_str6` varchar(255) DEFAULT NULL COMMENT 'Product field string',
  `prodfld_str7` varchar(255) DEFAULT NULL COMMENT 'Product field string',
  `prodfld_str8` varchar(255) DEFAULT NULL COMMENT 'Product field string',
  `prodfld_str9` varchar(255) DEFAULT NULL COMMENT 'Product field string',
  `prodfld_str10` varchar(255) DEFAULT NULL COMMENT 'Product field string',
  
  `create_time` datetime DEFAULT NULL COMMENT 'create time',
  `create_user` int(10) unsigned NOT NULL COMMENT 'user id for creation',
  `create_user_name` varchar(32) DEFAULT NULL COMMENT 'user name',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'udate datetime',
  `update_user` int(10) unsigned DEFAULT NULL COMMENT 'user id updated',
  `update_user_name` varchar(32) DEFAULT NULL COMMENT 'user name',
  PRIMARY KEY (`id`),
  UNIQUE KEY `product_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

Drop Table IF EXISTS `release`;
CREATE TABLE `release` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'key',
  `product_id` int unsigned NOT NULL COMMENT 'Project Id',
  `name` varchar(64) NOT NULL COMMENT 'Release name',
  `status` int unsigned NOT NULL DEFAULT '0' COMMENT 'Status：0-pending, 1-In Progress，2-Blocked, 3-completed with issue, 4-atrisk, 5-signoff',
  `start_time` datetime DEFAULT NULL COMMENT 'Start time. YYYY-MM-DD 00:00:00',
  `end_time` datetime DEFAULT NULL COMMENT 'End time. YYYY-MM-DD 23:59:59',
  `display` int unsigned NOT NULL DEFAULT '1' COMMENT 'Display or not. 0-hide, 1-show',
  `remark` varchar(255) DEFAULT NULL COMMENT 'remark',

  `relfld_int1` int(10) DEFAULT NULL COMMENT 'Field int',
  `relfld_int2` int(10) DEFAULT NULL COMMENT 'Field int',
  `relfld_int3` int(10) DEFAULT NULL COMMENT 'Field int',
  `relfld_int4` int(10) DEFAULT NULL COMMENT 'Field int',
  `relfld_int5` int(10) DEFAULT NULL COMMENT 'Field int',
  `relfld_str1` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `relfld_str2` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `relfld_str3` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `relfld_str4` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `relfld_str5` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `relfld_str6` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `relfld_str7` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `relfld_str8` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `relfld_str9` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `relfld_str10` varchar(255) DEFAULT NULL COMMENT 'Field string',

  `create_time` datetime DEFAULT NULL COMMENT 'create time',
  `create_user` int(10) unsigned NOT NULL COMMENT 'user id for creation',
  `create_user_name` varchar(32) DEFAULT NULL COMMENT 'user name',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'udate datetime',
  `update_user` int(10) unsigned DEFAULT NULL COMMENT 'user id updated',
  `update_user_name` varchar(32) DEFAULT NULL COMMENT 'user name',
  PRIMARY KEY (`id`),
  UNIQUE KEY `release_productid_name` (`product_id`,`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

Drop Table IF EXISTS `test_set`;
CREATE TABLE `test_set` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'key',
  `release_id` int unsigned NOT NULL COMMENT 'Release Id',
  `name` varchar(32) NOT NULL COMMENT 'Test set name',
  `type` int unsigned NOT NULL DEFAULT '0' COMMENT '1-Automation,2-Manual Test,4-API Test,8-Performance Test',
  `status` int unsigned NOT NULL DEFAULT '0' COMMENT 'Status：0-open,1-completed',
  `start_time` datetime DEFAULT NULL COMMENT 'Start time. YYYY-MM-DD 00:00:00',
  `end_time` datetime DEFAULT NULL COMMENT 'End time. YYYY-MM-DD 23:59:59',
  `test_owner` int(10) unsigned NULL COMMENT 'Test Owner',
  `jira_project` varchar(32) DEFAULT NULL COMMENT 'JIRA project for the bug',
  `bug_assignee` int(10) unsigned NULL COMMENT 'User ID of bug assignee',
  `description` varchar(255) DEFAULT NULL COMMENT 'Description',
  `locales` varchar(255) DEFAULT NULL COMMENT 'locales for the test set',  

  `testset_field_bool1` smallint(5) DEFAULT NULL COMMENT 'TestSet field bool. 1-true,0-false',
  `testset_field_bool2` smallint(5) DEFAULT NULL COMMENT 'TestSet field bool. 1-true,0-false',
  `testset_field_int1` int(10) DEFAULT NULL COMMENT 'TestSet field int',
  `testset_field_int2` int(10) DEFAULT NULL COMMENT 'TestSet field int',
  `testset_field_int3` int(10) DEFAULT NULL COMMENT 'TestSet field int',
  `testset_field_int4` int(10) DEFAULT NULL COMMENT 'TestSet field int',
  `testset_field_int5` int(10) DEFAULT NULL COMMENT 'TestSet field int',
  `testset_field_str1` varchar(255) DEFAULT NULL COMMENT 'TestSet field string',
  `testset_field_str2` varchar(255) DEFAULT NULL COMMENT 'TestSet field string',
  `testset_field_str3` varchar(255) DEFAULT NULL COMMENT 'TestSet field string',
  `testset_field_str4` varchar(255) DEFAULT NULL COMMENT 'TestSet field string',
  `testset_field_str5` varchar(255) DEFAULT NULL COMMENT 'TestSet field string',

  `tsfield_int1` int(10) DEFAULT NULL COMMENT 'Field int',
  `tsfield_int2` int(10) DEFAULT NULL COMMENT 'Field int',
  `tsfield_int3` int(10) DEFAULT NULL COMMENT 'Field int',
  `tsfield_int4` int(10) DEFAULT NULL COMMENT 'Field int',
  `tsfield_int5` int(10) DEFAULT NULL COMMENT 'Field int',
  `tsfield_str1` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `tsfield_str2` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `tsfield_str3` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `tsfield_str4` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `tsfield_str5` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `tsfield_str6` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `tsfield_str7` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `tsfield_str8` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `tsfield_str9` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `tsfield_str10` varchar(255) DEFAULT NULL COMMENT 'Field string',

  `create_time` datetime DEFAULT NULL COMMENT 'create time',
  `create_user` int(10) unsigned NOT NULL COMMENT 'user id for creation',
  `create_user_name` varchar(32) DEFAULT NULL COMMENT 'user name',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'udate datetime',
  `update_user` int(10) unsigned DEFAULT NULL COMMENT 'user id updated',
  `update_user_name` varchar(32) DEFAULT NULL COMMENT 'user name',
  PRIMARY KEY (`id`),
  UNIQUE KEY `testset_release_name` (`release_id`, `name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

Drop Table IF EXISTS `test_case`;
CREATE TABLE `test_case` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'key',
  `product_id` int unsigned NOT NULL COMMENT 'Product Id',
  `case_id` varchar(32) NULL COMMENT 'Case ID',
  `case_type` int unsigned NOT NULL DEFAULT '1' COMMENT '1-Automation,2-Manual Test,4-API Test,8-Performance Test',
  `summary` varchar(255) DEFAULT NULL COMMENT 'Case summary',
  `priority` varchar(32) DEFAULT NULL COMMENT 'P0,P1,P2,P3, (blank)',
  `feature_area` varchar(255) DEFAULT NULL COMMENT 'Feature',
  `sub_feature_area` varchar(255) DEFAULT NULL COMMENT 'Sub feature',
  `case_status` int(10) unsigned DEFAULT '0' COMMENT 'case status: 0-Normal，1-Disable, 2-Delete',
  `class_name` varchar(255) DEFAULT NULL COMMENT 'className',
  `case_parameter` varchar(255) DEFAULT NULL COMMENT 'parameters',
  `template_id` varchar(32) NULL COMMENT 'Template ID',
  `detail_steps` varchar(5000) DEFAULT NULL COMMENT 'steps in detail',
  `expected_results` varchar(5000) DEFAULT NULL COMMENT 'Expected result',
  `support_locales` varchar(255) DEFAULT NULL COMMENT 'locales for the test case. EN_US,ZH_CN',
  `display` smallint(5) unsigned DEFAULT '1' COMMENT 'Display or not.0-no,1-yes',
  `case_owner` varchar(32) DEFAULT NULL COMMENT 'TestCase owner',
  `description` varchar(255) DEFAULT NULL COMMENT 'Description',
  `validation` smallint(5) unsigned NOT NULL DEFAULT '1' COMMENT '0-invalid,1-valid,4-delete,8-archived',
  
  `testcase_field_bool1` smallint(5) DEFAULT NULL COMMENT 'TestCase field bool. 1-true,0-false',
  `testcase_field_bool2` smallint(5) DEFAULT NULL COMMENT 'TestCase field bool. 1-true,0-false',
  `testcase_field_bool3` smallint(5) DEFAULT NULL COMMENT 'TestCase field bool. 1-true,0-false',
  `testcase_field_int1` int(10) DEFAULT NULL COMMENT 'TestCase field int',
  `testcase_field_int2` int(10) DEFAULT NULL COMMENT 'TestCase field int',
  `testcase_field_int3` int(10) DEFAULT NULL COMMENT 'TestCase field int',
  `testcase_field_str1` varchar(255) DEFAULT NULL COMMENT 'TestCase field string',
  `testcase_field_str2` varchar(255) DEFAULT NULL COMMENT 'TestCase field string',
  `testcase_field_str3` varchar(255) DEFAULT NULL COMMENT 'TestCase field string',
  `testcase_field_str4` varchar(255) DEFAULT NULL COMMENT 'TestCase field string',
  `testcase_field_str5` varchar(255) DEFAULT NULL COMMENT 'TestCase field string',

  `tcfield_bool1` smallint(5) DEFAULT NULL COMMENT 'TestCase field bool. 1-true,0-false',
  `tcfield_bool2` smallint(5) DEFAULT NULL COMMENT 'TestCase field bool. 1-true,0-false',
  `tcfield_bool3` smallint(5) DEFAULT NULL COMMENT 'TestCase field bool. 1-true,0-false',
  `tcfield_int1` int(10) DEFAULT NULL COMMENT 'Field int',
  `tcfield_int2` int(10) DEFAULT NULL COMMENT 'Field int',
  `tcfield_int3` int(10) DEFAULT NULL COMMENT 'Field int',
  `tcfield_int4` int(10) DEFAULT NULL COMMENT 'Field int',
  `tcfield_int5` int(10) DEFAULT NULL COMMENT 'Field int',
  `tcfield_str1` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `tcfield_str2` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `tcfield_str3` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `tcfield_str4` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `tcfield_str5` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `tcfield_str6` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `tcfield_str7` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `tcfield_str8` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `tcfield_str9` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `tcfield_str10` varchar(255) DEFAULT NULL COMMENT 'Field string',

  `create_time` datetime DEFAULT NULL,
  `create_user_id` int(10) unsigned DEFAULT NULL COMMENT 'user id',
  `create_user_name` varchar(32) DEFAULT NULL COMMENT 'user name',
  `update_time` datetime DEFAULT NULL,
  `update_user_id` int(10) unsigned DEFAULT NULL COMMENT 'user id',
  `update_user_name` varchar(32) DEFAULT NULL COMMENT 'user name',
  `record_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record update time',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_testcase_product_caseid_status` (`product_id`,`case_id`, `case_status`, `validation`),
  KEY `idx_test_case_productId_caseId` (`product_id`,`case_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- Test RUN
DROP TABLE IF EXISTS `test_run`;
CREATE TABLE `test_run` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Key id',
  `testset_id` int(10) unsigned NOT NULL COMMENT 'FK: [test_set].id',
  `testcase_id` int(10) unsigned NOT NULL COMMENT 'FK: [test_case].id',
  `locale` varchar(8) DEFAULT NULL COMMENT 'Locale. ex: en_US',
  `display` int(10) unsigned DEFAULT '1' COMMENT 'Display or not.0-no,1-yes',
  `description` varchar(255) DEFAULT NULL COMMENT 'Description',
 
  `run_model` int unsigned DEFAULT NULL COMMENT 'TestRun case type. 10-Automation, 11-Automated web, 12-Automated ios, 13-Automated android, 20-Manual, 30-Performance, 40-Unit',
  `app_path` varchar(1024) DEFAULT NULL,
  `run_engineer_id` int(10) unsigned NOT NULL COMMENT 'user id',
  `run_qa_id` int(10) unsigned NOT NULL COMMENT 'user id',
  `run_tep_id` int(10) unsigned NOT NULL COMMENT 'TEP id',

  `testrun_field_bool1` smallint(5) DEFAULT NULL COMMENT 'TestRun field bool. 1-true,0-false',
  `testrun_field_bool2` smallint(5) DEFAULT NULL COMMENT 'TestRun field bool. 1-true,0-false',
  `testrun_field_bool3` smallint(5) DEFAULT NULL COMMENT 'TestRun field bool. 1-true,0-false',
  `testrun_field_int1` int(10) DEFAULT NULL COMMENT 'TestRun field int',
  `testrun_field_int2` int(10) DEFAULT NULL COMMENT 'TestRun field int',
  `testrun_field_int3` int(10) DEFAULT NULL COMMENT 'TestRun field int',
  `testrun_field_int4` int(10) DEFAULT NULL COMMENT 'TestRun field int',
  `testrun_field_int5` int(10) DEFAULT NULL COMMENT 'TestRun field int',
  `testrun_field_str1` varchar(255) DEFAULT NULL COMMENT 'TestRun field string',
  `testrun_field_str2` varchar(255) DEFAULT NULL COMMENT 'TestRun field string',
  `testrun_field_str3` varchar(255) DEFAULT NULL COMMENT 'TestRun field string',
  `testrun_field_str4` varchar(255) DEFAULT NULL COMMENT 'TestRun field string',
  `testrun_field_str5` varchar(255) DEFAULT NULL COMMENT 'TestRun field string',
  `testrun_field_str6` varchar(255) DEFAULT NULL COMMENT 'TestRun field string',
  `testrun_field_str7` varchar(255) DEFAULT NULL COMMENT 'TestRun field string',
  `testrun_field_str8` varchar(255) DEFAULT NULL COMMENT 'TestRun field string',
  `testrun_field_str9` varchar(255) DEFAULT NULL COMMENT 'TestRun field string',
  `testrun_field_str10` varchar(255) DEFAULT NULL COMMENT 'TestRun field string',
  
  `validation` smallint(5) unsigned NOT NULL DEFAULT '1' COMMENT '0-invalid,1-valid,4-delete,8-archived',
  
  `create_time` datetime DEFAULT NULL COMMENT 'create time',
  `create_user` int(10) unsigned NOT NULL COMMENT 'user id for creation',
  `create_user_name` varchar(32) DEFAULT NULL COMMENT 'user name',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'udate datetime',
  `update_user` int(10) unsigned DEFAULT NULL COMMENT 'user id updated',
  `update_user_name` varchar(32) DEFAULT NULL COMMENT 'user name',
  
  PRIMARY KEY (`id`),
--  KEY `idx_test_run_testset_id` (`testset_id`),
  UNIQUE KEY `unique_testrun_testset_case_locale` (`testset_id`,`testcase_id`, `locale`, `validation`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- Test Result
DROP TABLE IF EXISTS `test_result`;
CREATE TABLE `test_result` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Key id',
  
  `testset_id` int(10) unsigned NOT NULL COMMENT 'FK: [test_set].id',
  `test_run_id` int(10) unsigned NOT NULL COMMENT 'FK: [test_run].id',

  `auto_run_status` int(10) unsigned DEFAULT NULL COMMENT 'Auomation Status：0-Not RUN, 1-AUTOMATION SUCCESS,2-Automation FAILURE,3-Automation SKIP,4-Automation SUCCESS WITHIN PERCENTAGE,16-Automation STARTED, 101-TestNotRequired',
  `job_weather` int(10) unsigned DEFAULT '0' COMMENT 'job status：0-unkown,1-sunny,2-cloud,3-dull,4-fog,5-rainy',

  `startTime` datetime DEFAULT NULL COMMENT 'start Locale time',
  `endTime` datetime DEFAULT NULL COMMENT 'end Locale Time',
  `startUTCTime` datetime DEFAULT NULL COMMENT 'start Time',
  `timezone` float DEFAULT NULL COMMENT 'Timezone',
  `execDuration` int(10) unsigned DEFAULT NULL COMMENT 'execute duration seconds.',
  `note` varchar(255) DEFAULT NULL COMMENT 'Note or Bug',
  `exceptionDesc` varchar(8000) DEFAULT NULL COMMENT 'exception',
  `exception` text,
  `stacktrace` text,

  `qa_status` varchar(32) DEFAULT '0' COMMENT 'Reference to dictionary.code. 0-(blank),1-pass,2-conditional pass,4-fail,5-Testing Not Required',
  `browser` int(10) unsigned DEFAULT 0 COMMENT '0-firefox,1-chrome',
  `browsersVesion` varchar(16) DEFAULT '' COMMENT 'version of browser',
  `notes` varchar(255) DEFAULT '' COMMENT 'notes',

  `bug_report_id` int(10) unsigned DEFAULT NULL COMMENT 'bug id',
  `bug_report_content` varchar(1024) DEFAULT '' COMMENT 'JIRA link of bug',
  `bug_report_title` varchar(255) DEFAULT '' COMMENT 'bug title',
  `issue_category_id` int(10) unsigned DEFAULT NULL COMMENT 'category id',
  `issue_reason_id` int(10) unsigned DEFAULT NULL COMMENT 'reason id',
  `mobile_device` varchar(255) DEFAULT NULL,

  `testresult_field_bool1` smallint(5) DEFAULT NULL COMMENT 'TestResult field bool. 1-true,0-false',
  `testresult_field_bool2` smallint(5) DEFAULT NULL COMMENT 'TestResult field bool. 1-true,0-false',
  `testresult_field_bool3` smallint(5) DEFAULT NULL COMMENT 'TestResult field bool. 1-true,0-false',
  `testresult_field_int1` int(10) DEFAULT NULL COMMENT 'TestResult field int',
  `testresult_field_int2` int(10) DEFAULT NULL COMMENT 'TestResult field int',
  `testresult_field_int3` int(10) DEFAULT NULL COMMENT 'TestResult field int',
  `testresult_field_int4` int(10) DEFAULT NULL COMMENT 'TestResult field int',
  `testresult_field_int5` int(10) DEFAULT NULL COMMENT 'TestResult field int',
  `testresult_field_str1` varchar(255) DEFAULT NULL COMMENT 'TestResult field string',
  `testresult_field_str2` varchar(255) DEFAULT NULL COMMENT 'TestResult field string',
  `testresult_field_str3` varchar(255) DEFAULT NULL COMMENT 'TestResult field string',
  `testresult_field_str4` varchar(255) DEFAULT NULL COMMENT 'TestResult field string',
  `testresult_field_str5` varchar(255) DEFAULT NULL COMMENT 'TestResult field string',
  `testresult_field_str6` varchar(255) DEFAULT NULL COMMENT 'TestResult field string',
  `testresult_field_str7` varchar(255) DEFAULT NULL COMMENT 'TestResult field string',
  `testresult_field_str8` varchar(255) DEFAULT NULL COMMENT 'TestResult field string',
  `testresult_field_str9` varchar(255) DEFAULT NULL COMMENT 'TestResult field string',
  `testresult_field_str10` varchar(255) DEFAULT NULL COMMENT 'TestResult field string',
  
  `last_run_time` datetime DEFAULT NULL COMMENT 'last run time',
  `display` int(10) unsigned DEFAULT '1' COMMENT 'Display or not.0-no,1-yes',
  `validation` smallint(5) unsigned NOT NULL DEFAULT '1' COMMENT '0-invalid,1-valid,4-delete,8-archived',
  
  `create_time` datetime DEFAULT NULL COMMENT 'create time',
  `create_user` int(10) unsigned NOT NULL COMMENT 'user id for creation',
  `create_user_name` varchar(32) DEFAULT NULL COMMENT 'user name',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'udate datetime',
  `update_user` int(10) unsigned DEFAULT NULL COMMENT 'user id updated',
  `update_user_name` varchar(32) DEFAULT NULL COMMENT 'user name',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

Drop Table IF EXISTS `attribute_template`;
CREATE TABLE `attribute_template` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `category_id` int unsigned NOT NULL COMMENT 'Template Category Id', 
  `name` varchar(32) NOT NULL COMMENT 'Name',  
  `datatype_id` int unsigned NOT NULL COMMENT 'Date Type Id', 
  `list_id` int unsigned DEFAULT NULL COMMENT 'Lookup List Id', 
  `display_name` varchar(255) DEFAULT NULL COMMENT 'display name',
  `customer_help` varchar(2048) DEFAULT NULL COMMENT 'description',
  `description` varchar(255) DEFAULT '' COMMENT 'description',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqe_category_name` (`category_id`, `name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

Drop Table IF EXISTS `attribute_values`;
CREATE TABLE `attribute_values` (
  `id` int(10) unsigned NOT NULL COMMENT 'Template Category Table Key ID',
  `attr_templ_id` int unsigned NOT NULL COMMENT 'Attribute Template Id', 
  `datatype` varchar(16) NULL COMMENT 'Datatype in dictionary. type = DataType',
  `val_bool` smallint(5) DEFAULT NULL COMMENT 'DataType-bool. 1-true,0-false',
  `val_int` int(10) DEFAULT NULL COMMENT 'DataType-int',
  `val_str` varchar(255) DEFAULT NULL COMMENT 'DataType-string',
  `val_list` varchar(32) DEFAULT NULL COMMENT 'code of list item. DataType-selectlist,dropdownlist',
  `val_datetime` datetime DEFAULT NULL COMMENT 'DataType-date,datetime',
  `val_json` varchar(2048) DEFAULT NULL COMMENT 'DataType-json',
  `val_variant` varchar(512) DEFAULT NULL COMMENT 'unsupport DataType or complex data',
  PRIMARY KEY (`id`, `attr_templ_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

Drop Table IF EXISTS `attribute_list`;
CREATE TABLE `attribute_list` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(64) NOT NULL COMMENT 'name of list item',
  `description` varchar(256) DEFAULT NULL COMMENT 'Description for the item',
  `validation` smallint(1) unsigned NOT NULL DEFAULT '1' COMMENT '1-true, 0-false',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqe_attr_list_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

Drop Table IF EXISTS `attribute_listitem`;
CREATE TABLE `attribute_listitem` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `list_id` int unsigned NOT NULL COMMENT 'Lookup list Id', 
  `code` varchar(32) NOT NULL COMMENT 'key of list item',
  `name` varchar(64) NOT NULL COMMENT 'name of list item',
  `jsonname` varchar(255) NULL COMMENT 'json formatter for locales.{"zh_cn":,"en_us":}',
  `description` varchar(256) DEFAULT NULL COMMENT 'Description for the item',
  `validation` smallint(1) unsigned NOT NULL DEFAULT '1' COMMENT 'Valid or not. 1-true, 0-false',
  `order_idx` smallint(1) unsigned NOT NULL DEFAULT '99' COMMENT 'Display sequence',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqe_attr_listitem_code` (`list_id`, `code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `project_config`;
CREATE TABLE `project_config` (
  `id` int(8) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Key',
  `configName` varchar(64) NOT NULL COMMENT 'Config name',
  `configValue` varchar(255) NOT NULL COMMENT 'Config item',
  `Description` varchar(255) DEFAULT NULL COMMENT 'Config description',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_project_config_name` (`configName`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
