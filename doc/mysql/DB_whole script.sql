-- 2019-03-27, Ver0.1, for QualityOverviewLayout
-- 2019-04-24 	Ver0.2 	Add Table: base_code
-- 2019-04-30 	Ver0.3	Add Table: dict_table, user, product, release, test_set
-- 2019-07-15	Ver0.5  Change 'create_user_id' NULL for Product, Release, TestSet etc.
-- 2019-07-19   Ver0.5.1 Add column 'group_id','login' for 'user'
-- 						 Add 'textarea' for dictionary 'HtmlType'

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
INSERT INTO `dictionary` VALUES (20, 'DataType', 'int', 'Int', 1, '');
INSERT INTO `dictionary` VALUES (21, 'DataType', 'bool', 'Bool', 1, '1-true, 0-false');
INSERT INTO `dictionary` VALUES (22, 'DataType', 'string', 'String', 1, '');
INSERT INTO `dictionary` VALUES (23, 'DataType', 'date', 'Date', 1, '');
INSERT INTO `dictionary` VALUES (24, 'DataType', 'datetime', 'DateTime', 1, '');
INSERT INTO `dictionary` VALUES (25, 'DataType', 'selectlist', 'SelectList', 1, '');
INSERT INTO `dictionary` VALUES (26, 'DataType', 'dropdownlist', 'DropDownList', 1, '');
INSERT INTO `dictionary` VALUES (27, 'DataType', 'json', 'JSON', 1, '');
-- 2019/7/4
INSERT INTO `dictionary` VALUES ('40', 'HtmlType', 'text', 'Text', '1', '');
INSERT INTO `dictionary` VALUES ('41', 'HtmlType', 'int', 'Number', '1', '');
INSERT INTO `dictionary` VALUES ('42', 'HtmlType', 'label', 'Label', '0', '');
INSERT INTO `dictionary` VALUES ('43', 'HtmlType', 'bool', 'Option', '1', '');
INSERT INTO `dictionary` VALUES ('44', 'HtmlType', 'checkbox', 'CheckBox', '0', '');
INSERT INTO `dictionary` VALUES ('45', 'HtmlType', 'textarea', 'TextArea', '1', 'Multile Text Input');
INSERT INTO `dictionary` VALUES ('55', 'HtmlType', 'list', 'SelectList', '1', '');
INSERT INTO `dictionary` VALUES ('56', 'HtmlType', 'dropdownlist', 'DropdownList', '0', '');
INSERT INTO `dictionary` VALUES ('72', 'HtmlType', 'calendar', 'Calendar', '1', '');
INSERT INTO `dictionary` VALUES ('74', 'HtmlType', 'picture', 'Picture', '0', '');
INSERT INTO `dictionary` VALUES ('76', 'HtmlType', 'user', 'User', '1', '');
INSERT INTO `dictionary` VALUES ('77', 'HtmlType', 'userlist', 'UserList', '0', '');
INSERT INTO `dictionary` VALUES ('78', 'HtmlType', 'product', 'Product', '0', '');
INSERT INTO `dictionary` VALUES ('79', 'HtmlType', 'productlist', 'ProductList', '0', '');
INSERT INTO `dictionary` VALUES ('80', 'HtmlType', 'release', 'Release', '0', '');
INSERT INTO `dictionary` VALUES ('82', 'HtmlType', 'releaselist', 'ReleaseList', '0', '');
INSERT INTO `dictionary` VALUES ('84', 'HtmlType', 'testset', 'TestSet', '0', '');
INSERT INTO `dictionary` VALUES ('86', 'HtmlType', 'testsetlist', 'TestSetList', '0', '');
INSERT INTO `dictionary` VALUES ('88', 'HtmlType', 'search', 'Search', '0', '[\"User\",\"Product\",\"Release\",\"TestSet\"]');
INSERT INTO `dictionary` VALUES ('90', 'HtmlType', 'link', 'Link', '0', '[\"User\",\"Product\",\"Release\",\"TestSet\"]');

-- 2019/7/15
INSERT INTO `dictionary` VALUES ('100', 'UI_List_Count', '20', '20', '1', '');
INSERT INTO `dictionary` VALUES ('101', 'UI_List_Count', '50', '50', '1', '');
INSERT INTO `dictionary` VALUES ('103', 'UI_List_Count', '100', '100', '1', '');
INSERT INTO `dictionary` VALUES ('104', 'UI_List_Count', '200', '200', '1', '');
INSERT INTO `dictionary` VALUES ('111', 'ProductStatus', '0', 'normal', '1', '');
INSERT INTO `dictionary` VALUES ('112', 'ProductStatus', '1', 'abnormal', '1', '');
INSERT INTO `dictionary` VALUES ('113', 'ProductStatus', '2', 'completed', '1', '');
INSERT INTO `dictionary` VALUES ('114', 'ProductStatus', '3', 'archived', '1', '');
INSERT INTO `dictionary` VALUES ('115', 'ProductStatus', '4', 'blocked', '1', '');
INSERT INTO `dictionary` VALUES ('120', 'ReleaseStatus', 'pending', 'Pending', '1', '');
INSERT INTO `dictionary` VALUES ('121', 'ReleaseStatus', 'in-process', 'IN-Progress', '1', '');
INSERT INTO `dictionary` VALUES ('122', 'ReleaseStatus', 'blocked', 'Blocked', '1', '');
INSERT INTO `dictionary` VALUES ('123', 'ReleaseStatus', 'compwithissue', 'Completed with issue', '1', '');
INSERT INTO `dictionary` VALUES ('124', 'ReleaseStatus', 'atrisk', 'At Risk', '1', '');
INSERT INTO `dictionary` VALUES ('125', 'ReleaseStatus', 'signoff', 'SignOff', '1', '');

INSERT INTO `dictionary`(`id`,`type`,`code`,`value`) VALUES ('150', 'Page_Element_Setting', 'qualityoverviewlayout', 'Quality Overview Layout');
INSERT INTO `dictionary`(`type`,`code`,`value`) VALUES ('Page_Element_Setting', 'product', 'Product');
INSERT INTO `dictionary`(`type`,`code`,`value`) VALUES ('Page_Element_Setting', 'release', 'Release');
INSERT INTO `dictionary`(`type`,`code`,`value`) VALUES ('Page_Element_Setting', 'testset', 'Test Set');
INSERT INTO `dictionary`(`type`,`code`,`value`) VALUES ('Page_Element_Setting', 'testcase', 'Test Case');
-- list name
INSERT INTO `dictionary`(`id`,`type`,`code`,`value`, `description`) VALUES ('180', 'sys_list_setting', 'product', 'Product', 'TestMgmr Product list');
INSERT INTO `dictionary`(`type`,`code`,`value`, `description`) 
	VALUES ('sys_list_setting', 'release', 'Release', 'TestMgmr Release list');
INSERT INTO `dictionary`(`type`,`code`,`value`, `description`) 
	VALUES ('sys_list_setting', 'testset', 'Test Set', 'TestMgmr Test Set list');
INSERT INTO `dictionary`(`type`,`code`,`value`, `description`) 
	VALUES ('sys_list_setting', 'testcase', 'Test Case', 'TestMgmr Test Case list');
INSERT INTO `dictionary`(`type`,`code`,`value`, `description`) 
	VALUES ('sys_list_setting', 'testresult', 'Test Result', 'TestMgmr Test Result list');
INSERT INTO `dictionary`(`type`,`code`,`value`, `description`) 
	VALUES ('sys_list_setting', 'qo_product', 'QualityOverview Product', 'QualityOverview Product list');
INSERT INTO `dictionary`(`type`,`code`,`value`, `description`) 
	VALUES ('sys_list_setting', 'qo_release', 'QualityOverview Release', 'QualityOverview Release list');
INSERT INTO `dictionary`(`type`,`code`,`value`, `description`) 
	VALUES ('sys_list_setting', 'qo_testset', 'QualityOverview Test Set', 'QualityOverview Test Set list');
-- Add ColumnType into dictionary
INSERT INTO `dictionary`(`id`,`type`,`code`,`value`, `description`) 
VALUES ('200', 'ColumnType', 'hidden', 'Hidden', 'column undisplay, just set to hidden field');
INSERT INTO `dictionary`(`type`,`code`,`value`, `description`) 
	VALUES ('ColumnType', 'text', 'Text', 'display as Text');
INSERT INTO `dictionary`(`type`,`code`,`value`, `description`) 
	VALUES ('ColumnType', 'number', 'Number', 'display as Number');
INSERT INTO `dictionary`(`type`,`code`,`value`, `description`) 
	VALUES ('ColumnType', 'link', 'Link', 'ex: http://www.symbio.com/api?token={token}&id={value}');
INSERT INTO `dictionary`(`type`,`code`,`value`, `description`) 
	VALUES ('ColumnType', 'button', 'Button', '');
INSERT INTO `dictionary`(`type`,`code`,`value`, `description`) 
	VALUES ('ColumnType', 'bool', 'Bool', '');
INSERT INTO `dictionary`(`type`,`code`,`value`, `description`) 
	VALUES ('ColumnType', 'checkbox', 'CheckBox', '');
INSERT INTO `dictionary`(`type`,`code`,`value`, `description`) 
	VALUES ('ColumnType', 'percentage', 'Percentage', '');
INSERT INTO `dictionary`(`type`,`code`,`value`, `description`) 
	VALUES ('ColumnType', 'formatter', 'Formatter', '{token}-{value}');
INSERT INTO `dictionary`(`type`,`code`,`value`, `description`) 
	VALUES ('ColumnType', 'uilink', 'UiLink', 'inner vue ui link');
INSERT INTO `dictionary`(`type`,`code`,`value`, `description`) 
	VALUES ('ColumnType', 'autoStatus', 'Auto Status', 'Automation status');
INSERT INTO `dictionary`(`type`,`code`,`value`, `description`) 
	VALUES ('ColumnType', 'qaStatus', 'QA', 'QA status');
INSERT INTO `dictionary`(`type`,`code`,`value`, `description`) 
	VALUES ('ColumnType', 'productStatus', 'Product Status', 'Product Status');
INSERT INTO `dictionary`(`type`,`code`,`value`, `description`) 
	VALUES ('ColumnType', 'releaseStatus', 'Release Status', '');
INSERT INTO `dictionary`(`type`,`code`,`value`, `description`) 
	VALUES ('ColumnType', 'testsetStatus', 'TestSet Status', '');
INSERT INTO `dictionary`(`id`,`type`,`code`,`value`, `description`) 
	VALUES (215, 'ColumnType', 'datetime', 'DateTime', 'DateTime yyyy-mm-dd HH:MM:SS');
INSERT INTO `dictionary`(`id`,`type`,`code`,`value`, `description`) 
	VALUES (216, 'ColumnType', 'date', 'Date', 'DateTime yyyy-mm-dd');
INSERT INTO `dictionary`(`id`,`type`,`code`,`value`, `description`) 
	VALUES (217, 'ColumnType', 'user', 'User', 'User Info');

INSERT INTO `dictionary`(`id`,`type`,`code`,`value`, `description`) 
	VALUES (260, 'ColumnTypeEnd', 'coltypeend', 'Column Type End', 'place holder for last of col type');


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
  `disp_status` SMALLINT(5) DEFAULT 0 COMMENT 'Display status for the element. 0-edit only, 1-user defined',
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
  
  `create_time` datetime DEFAULT NULL COMMENT 'create time',
  `create_user` int(10) unsigned DEFAULT NULL COMMENT 'user id for creation',
  `create_user_name` varchar(32) DEFAULT NULL COMMENT 'user name',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'udate datetime',
  `update_user` int(10) unsigned DEFAULT NULL COMMENT 'user id updated',
  `update_user_name` varchar(32) DEFAULT NULL COMMENT 'user name',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqe_ui_info_page_key` (`page`, `key`),
  UNIQUE KEY `uniqe_ui_info_page_dbfield` (`page`, `db_field`)
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

-- Ver2019-07-19
Drop Table IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL COMMENT 'Nickname',
  `email` varchar(32) NULL COMMENT 'Email of user',
  `passwd` varchar(32) NOT NULL COMMENT 'password for local system',
  `first_name` varchar(32) DEFAULT NULL COMMENT 'Last mame',
  `last_name` varchar(32) DEFAULT NULL COMMENT 'Last mame',
  `full_name` varchar(32) DEFAULT NULL COMMENT 'Last mame+First name',
  `portrait_id` int unsigned DEFAULT NULL COMMENT 'avatar id',
  `mobile` varchar(11) DEFAULT NULL COMMENT 'phone number',
  `locale` varchar(6) NOT NULL DEFAULT 'en_US' COMMENT 'default locale ',
  `status` smallint(5) NOT NULL DEFAULT '1' COMMENT  'Status. 0-inactive，1-active, 4-delete',
  `disable` smallint(5) NOT NULL DEFAULT '0' COMMENT  'Account forbidden or not. 0-no，1-yes',
  `channel` smallint(5) NOT NULL DEFAULT '0' COMMENT 'Channel that user logged in. 0 - local, 1 - LDAP',
  `group_id` int unsigned DEFAULT NULL COMMENT 'Group ID.',
  `level_id` int unsigned DEFAULT '0' COMMENT '用户等级Id. SystemAdmin(99), Client, Tester(10), Engineer, Lead, Manager, Admin(1), User(0)',
  `login` datetime DEFAULT NULL COMMENT 'login time',
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
  UNIQUE KEY `unique_user_nickName` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

Drop Table IF EXISTS `sys_list_setting`;
CREATE TABLE `sys_list_setting` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(32) NOT NULL COMMENT 'List Name. product, release, testset, testcase, testresult, qo_product',
  `key` varchar(32) NOT NULL COMMENT 'column key',
  `label` VARCHAR(128) NOT NULL DEFAULT '{"en_us":"","zh_cn":""}' COMMENT 'column label',
  `type` varchar(32) NOT NULL COMMENT 'column type',
  `display` smallint(5) NOT NULL DEFAULT 1 COMMENT '0-undisplay, 1-show, 2-hide',
  `align` smallint(5) unsigned NOT NULL DEFAULT '1' COMMENT '1-left,2-center,3-right',
  `field` varchar(32) NOT NULL DEFAULT '' COMMENT 'correspond to table field.',
  `formatter` varchar(256) DEFAULT NULL COMMENT 'Column formatter',
  `idx` smallint(5) NOT NULL DEFAULT 99 COMMENT 'index for the list column',
  `is_entity` smallint(5) NOT NULL DEFAULT '1' COMMENT 'Column data comes from db entity or not. 1-db entity, 2-statistics data, 3-virtual key',
  `attribute_field` smallint(5) NOT NULL DEFAULT '0' COMMENT 'Field is table entity field or attribute field. 0-false, 1-true',

  `custfld_int1` int(10) DEFAULT NULL COMMENT 'field int',
  `custfld_int2` int(10) DEFAULT NULL COMMENT 'field int',
  `custfld_int3` int(10) DEFAULT NULL COMMENT 'field int',
  `custfld_str1` varchar(255) DEFAULT NULL COMMENT 'field string1',
  `custfld_str2` varchar(255) DEFAULT NULL COMMENT 'field string2',
  `custfld_str3` varchar(255) DEFAULT NULL COMMENT 'field string3',
  `custfld_str4` varchar(255) DEFAULT NULL COMMENT 'field string4',
  `custfld_str5` varchar(255) DEFAULT NULL COMMENT 'field string5',
  `custfld_str6` varchar(255) DEFAULT NULL COMMENT 'field string6',

  `validation` smallint(5) unsigned NOT NULL DEFAULT '1' COMMENT '1-valid,0-invalid',
  `description` varchar(255) DEFAULT NULL COMMENT 'description',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_sys_list_setting_name_field` (`name`, `field`)
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
  `create_user` int(10) unsigned DEFAULT NULL COMMENT 'user id for creation',
  `create_user_name` varchar(32) DEFAULT NULL COMMENT 'user name',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'udate datetime',
  `update_user` int(10) unsigned DEFAULT NULL COMMENT 'user id updated',
  `update_user_name` varchar(32) DEFAULT NULL COMMENT 'user name',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_product_name` (`name`)
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
  `create_user` int(10) unsigned DEFAULT NULL COMMENT 'user id for creation',
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
  `display` smallint(5) NOT NULL DEFAULT '1' COMMENT 'Display or not. 0-hide, 1-show',
  `start_time` datetime DEFAULT NULL COMMENT 'Start time. YYYY-MM-DD 00:00:00',
  `end_time` datetime DEFAULT NULL COMMENT 'End time. YYYY-MM-DD 23:59:59',
  `test_owner` int(10) unsigned NULL COMMENT 'Test Owner',
  `jira_project` varchar(32) DEFAULT NULL COMMENT 'JIRA project for the bug',
  `bug_assignee` int(10) unsigned NULL COMMENT 'User ID of bug assignee',
  `description` varchar(255) DEFAULT NULL COMMENT 'Description',
  `locales` varchar(255) DEFAULT NULL COMMENT 'locales for the test set',  

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
  `create_user` int(10) unsigned DEFAULT NULL COMMENT 'user id for creation',
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
  `product_id` int unsigned NOT NULL DEFAULT '0' COMMENT 'Product Id',
  
  `case_id` varchar(32) NOT NULL COMMENT 'Case ID',
  `case_type` int unsigned NOT NULL DEFAULT '1' COMMENT 'Case Type.1-Automation,2-Manual Test,4-API Test,8-Performance Test',
  `support_locales` varchar(255) DEFAULT NULL COMMENT 'locales for the test case. EN_US,ZH_CN',
  `summary` varchar(255) DEFAULT NULL COMMENT 'Case summary',
  `priority` varchar(32) DEFAULT NULL COMMENT 'P0,P1,P2,P3, (blank)',
  `feature_area` varchar(255) DEFAULT NULL COMMENT 'Feature',
  `sub_feature_area` varchar(255) DEFAULT NULL COMMENT 'Sub feature',
  
  `test_scenario` varchar(5000) DEFAULT NULL COMMENT 'Test Case Description or test scenario',
  `detail_steps` varchar(5000) DEFAULT NULL COMMENT 'steps in detail',
  `expected_results` varchar(5000) DEFAULT NULL COMMENT 'Expected result',
  
  -- Automation Case
  `class_name` varchar(255) DEFAULT NULL COMMENT 'className',
  `case_parameter` varchar(255) DEFAULT NULL COMMENT 'parameters',
  `template_id` varchar(32) NULL COMMENT 'Template ID',
  
  -- Manual Case
  `notes` varchar(255) DEFAULT NULL COMMENT 'Notes',
  `method` varchar(255) DEFAULT NULL COMMENT 'Test method',
  
  `case_owner` varchar(32) DEFAULT NULL COMMENT 'TestCase owner',
  `description` varchar(255) DEFAULT NULL COMMENT 'Description',
  
  `case_status` int(10) unsigned DEFAULT '0' COMMENT 'case status: 0-Normal，1-Disable, 2-Delete',
  `display` smallint(5) unsigned DEFAULT '1' COMMENT 'Display or not.0-no,1-yes',
  `validation` smallint(5) unsigned NOT NULL DEFAULT '1' COMMENT '0-invalid,1-valid,4-delete,8-archived',
  
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

  `create_user_id` int(10) unsigned DEFAULT NULL COMMENT 'user id',
  `create_user_name` varchar(32) DEFAULT NULL COMMENT 'user name',
  `create_time` datetime DEFAULT NULL,
  `update_user_id` int(10) unsigned DEFAULT NULL COMMENT 'user id',
  `update_user_name` varchar(32) DEFAULT NULL COMMENT 'user name',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'udate datetime',
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
  
  `status` int(10) unsigned NOT NULL DEFAULT '0' COMMENT 'Last test result status. 0-Not Run/unkown',
  `screenshot_flag` int(10) unsigned NOT NULL DEFAULT '0' COMMENT 'The flag of last test result that has screenshot or not. 0-unkown, 1-yes',
  
  `app_path` varchar(1024) DEFAULT NULL,
  `run_engineer_id` int(10) unsigned DEFAULT NULL COMMENT 'user id',
  `run_qa_id` int(10) unsigned DEFAULT NULL COMMENT 'user id',
  `run_tep_id` int(10) unsigned DEFAULT NULL COMMENT 'TEP id',
 
  `trunfield_bool1` smallint(5) DEFAULT NULL COMMENT 'TestCase field bool. 1-true,0-false',
  `trunfield_bool2` smallint(5) DEFAULT NULL COMMENT 'TestCase field bool. 1-true,0-false',
  `trunfield_bool3` smallint(5) DEFAULT NULL COMMENT 'TestCase field bool. 1-true,0-false',
  `trunfield_int1` int(10) DEFAULT NULL COMMENT 'Field int',
  `trunfield_int2` int(10) DEFAULT NULL COMMENT 'Field int',
  `trunfield_int3` int(10) DEFAULT NULL COMMENT 'Field int',
  `trunfield_int4` int(10) DEFAULT NULL COMMENT 'Field int',
  `trunfield_int5` int(10) DEFAULT NULL COMMENT 'Field int',
  `trunfield_str1` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `trunfield_str2` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `trunfield_str3` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `trunfield_str4` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `trunfield_str5` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `trunfield_str6` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `trunfield_str7` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `trunfield_str8` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `trunfield_str9` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `trunfield_str10` varchar(255) DEFAULT NULL COMMENT 'Field string',
  
  `validation` smallint(5) unsigned NOT NULL DEFAULT '1' COMMENT '0-invalid,1-valid,4-delete,8-archived',
  
  `create_time` datetime DEFAULT NULL COMMENT 'create time',
  `create_user` int(10) unsigned DEFAULT NULL COMMENT 'user id for creation',
  `create_user_name` varchar(32) DEFAULT NULL COMMENT 'user name',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'udate datetime',
  `update_user` int(10) unsigned DEFAULT NULL COMMENT 'user id updated',
  `update_user_name` varchar(32) DEFAULT NULL COMMENT 'user name',
  
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_testrun_testset_case_locale` (`testset_id`,`testcase_id`, `locale`, `validation`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- Test Result
DROP TABLE IF EXISTS `test_result`;
CREATE TABLE `test_result` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Key id',  
  `test_set_id` int(10) unsigned NOT NULL COMMENT 'FK: [test_set].id',
  `test_run_id` int(10) unsigned NOT NULL COMMENT 'FK: [test_run].id',

  `auto_run_status` int(10) unsigned DEFAULT NULL COMMENT 'Auomation Status：0-Not RUN, 1-AUTOMATION SUCCESS,2-Automation FAILURE,3-Automation SKIP,4-Automation SUCCESS WITHIN PERCENTAGE,16-Automation STARTED, 101-TestNotRequired',
  `job_weather` int(10) unsigned DEFAULT '0' COMMENT 'job status：0-unkown,1-sunny,2-cloud,3-dull,4-fog,5-rainy',

  `start_time` datetime DEFAULT NULL COMMENT 'start Locale time',
  `end_time` datetime DEFAULT NULL COMMENT 'end Locale Time',
  `start_utc_time` datetime DEFAULT NULL COMMENT 'start Time',
  `time_zone` float DEFAULT NULL COMMENT 'Timezone',
  `exec_duration` int(10) unsigned DEFAULT NULL COMMENT 'execute duration seconds.',
  `note` varchar(255) DEFAULT NULL COMMENT 'Note or Bug',
  `exception_desc` varchar(8000) DEFAULT NULL COMMENT 'exception',
  `text_exception` text,
  `text_stacktrace` text,

  `qa_status` varchar(32) DEFAULT '0' COMMENT 'Reference to dictionary.code. 0-(blank),1-pass,2-conditional pass,4-fail,5-Testing Not Required',
  `browser` int(10) unsigned DEFAULT 0 COMMENT '0-firefox,1-chrome',
  `browsers_vesion` varchar(16) DEFAULT '' COMMENT 'version of browser',

  `bug_report_id` int(10) unsigned DEFAULT NULL COMMENT 'bug id',
  `bug_report_content` varchar(1024) DEFAULT '' COMMENT 'JIRA link of bug',
  `bug_report_title` varchar(255) DEFAULT '' COMMENT 'bug title',
  `issue_category_id` int(10) unsigned DEFAULT NULL COMMENT 'category id',
  `issue_reason_id` int(10) unsigned DEFAULT NULL COMMENT 'reason id',
  `mobile_device` varchar(255) DEFAULT NULL,

  `tresult_field_bool1` smallint(5) DEFAULT NULL COMMENT 'TestResult field bool. 1-true,0-false',
  `tresult_field_bool2` smallint(5) DEFAULT NULL COMMENT 'TestResult field bool. 1-true,0-false',
  `tresult_field_bool3` smallint(5) DEFAULT NULL COMMENT 'TestResult field bool. 1-true,0-false',
  `tresult_field_int1` int(10) DEFAULT NULL COMMENT 'TestResult field int',
  `tresult_field_int2` int(10) DEFAULT NULL COMMENT 'TestResult field int',
  `tresult_field_int3` int(10) DEFAULT NULL COMMENT 'TestResult field int',
  `tresult_field_int4` int(10) DEFAULT NULL COMMENT 'TestResult field int',
  `tresult_field_int5` int(10) DEFAULT NULL COMMENT 'TestResult field int',
  `tresult_field_str1` varchar(255) DEFAULT NULL COMMENT 'TestResult field string',
  `tresult_field_str2` varchar(255) DEFAULT NULL COMMENT 'TestResult field string',
  `tresult_field_str3` varchar(255) DEFAULT NULL COMMENT 'TestResult field string',
  `tresult_field_str4` varchar(255) DEFAULT NULL COMMENT 'TestResult field string',
  `tresult_field_str5` varchar(255) DEFAULT NULL COMMENT 'TestResult field string',
  `tresult_field_str6` varchar(255) DEFAULT NULL COMMENT 'TestResult field string',
  `tresult_field_str7` varchar(255) DEFAULT NULL COMMENT 'TestResult field string',
  `tresult_field_str8` varchar(255) DEFAULT NULL COMMENT 'TestResult field string',
  `tresult_field_str9` varchar(255) DEFAULT NULL COMMENT 'TestResult field string',
  `tresult_field_str10` varchar(255) DEFAULT NULL COMMENT 'TestResult field string',
  
  `last_run_time` datetime DEFAULT NULL COMMENT 'last run time',
  `display` int(10) unsigned DEFAULT '1' COMMENT 'Display or not.0-no,1-yes',
  `validation` smallint(5) unsigned NOT NULL DEFAULT '1' COMMENT '0-invalid,1-valid,4-delete,8-archived',
  
  `create_time` datetime DEFAULT NULL COMMENT 'create time',
  `create_user` int(10) unsigned DEFAULT NULL COMMENT 'user id for creation',
  `create_user_name` varchar(32) DEFAULT NULL COMMENT 'user name',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'udate datetime',
  `update_user` int(10) unsigned DEFAULT NULL COMMENT 'user id updated',
  `update_user_name` varchar(32) DEFAULT NULL COMMENT 'user name',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_testresult_testset_testrun_display_validation` (`test_set_id`,`test_run_id`, `display`, `validation`)
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
  `description` varchar(255) DEFAULT NULL COMMENT 'Config description',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_project_config_name` (`configName`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- 2019/7/24
Drop Table IF EXISTS `result_message`;
CREATE TABLE `result_message` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(16) NOT NULL COMMENT 'Error code',
  `en_us` VARCHAR(255) NOT NULL COMMENT 'Message for en_US',
  `zh_cn` VARCHAR(255) NOT NULL COMMENT 'Message for zh_cn',
  `formatter` varchar(255) NULL COMMENT 'Formatter for locales if needed',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_result_message_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- 2019/8/2
DROP TABLE IF EXISTS `function_info`;
CREATE TABLE `function_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `name` varchar(32) NOT NULL COMMENT 'Function unique Name',
  `fvalue` int(10) unsigned DEFAULT NULL COMMENT 'Function value',
  `description` varchar(255) DEFAULT NULL COMMENT 'description',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Create datetime',
  PRIMARY KEY (`id`),
  UNIQUE KEY `function_info_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of function_info
-- ----------------------------
INSERT INTO `function_info` VALUES ('1', 'Edit', '1', null, '2019-08-02 09:00:00');
INSERT INTO `function_info` VALUES ('2', 'Create', '2', null, '2019-08-02 09:00:00');
INSERT INTO `function_info` VALUES ('3', 'Delete', '4', null, '2019-08-02 09:00:00');
INSERT INTO `function_info` VALUES ('4', 'Copy', '8', null, '2019-08-02 09:00:00');
INSERT INTO `function_info` VALUES ('5', 'Export', '16', null, '2019-08-02 09:00:00');
INSERT INTO `function_info` VALUES ('6', 'Import', '32', null, '2019-08-02 09:00:00');
INSERT INTO `function_info` VALUES ('8', 'Run', '128', null, '2019-08-02 09:00:00');
INSERT INTO `function_info` VALUES ('9', 'Email', '256', null, '2019-08-02 09:00:00');

DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `name` varchar(32) NOT NULL COMMENT 'Name',
  `groupName` varchar(64) DEFAULT NULL COMMENT 'Group name',
  `logo` varchar(255) DEFAULT NULL COMMENT 'logo',
  `url` varchar(255) DEFAULT NULL COMMENT 'url',
  `idx` smallint(5) unsigned NOT NULL DEFAULT '99' COMMENT 'order',
  `description` varchar(255) DEFAULT NULL COMMENT 'description',
  `parentId` int(10) unsigned DEFAULT NULL COMMENT 'Parent Id',
  `createTime` datetime DEFAULT NULL COMMENT 'Create Time',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Upate datetime',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_menu_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', 'LayOut', 'Setting', null, '', '1', null, null, '2019-08-02 17:16:42', '2019-08-02 13:35:04');
INSERT INTO `menu` VALUES ('2', 'Page Element', 'Setting', null, '', '2', null, null, '2019-08-02 17:16:42', '2019-08-02 13:35:04');
INSERT INTO `menu` VALUES ('3', 'PRODUCT', 'Menu', null, 'product', '11', null, null, '2019-08-02 17:16:42', '2019-08-02 13:35:09');
INSERT INTO `menu` VALUES ('4', 'RELEASE', 'Menu', null, 'release', '12', null, null, '2019-08-02 11:50:30', '2019-08-02 10:34:39');
INSERT INTO `menu` VALUES ('5', 'TEST RESULT', 'Menu', null, 'testresult', '13', null, null, '2019-08-02 11:50:30', '2019-08-02 11:54:10');
INSERT INTO `menu` VALUES ('6', 'BUG', 'Menu', null, 'buglist', '14', null, null, '2019-08-02 11:50:30', '2019-08-02 13:59:27');
INSERT INTO `menu` VALUES ('7', 'REPORT', 'Menu', null, 'report', '16', null, null, '2019-08-02 11:50:30', '2019-08-02 11:54:14');
INSERT INTO `menu` VALUES ('8', 'ISSUE TYPE', 'Admin', null, 'issuetypelist', '60', null, null, '2019-08-02 11:50:30', '2019-08-02 14:29:08');
INSERT INTO `menu` VALUES ('9', 'FILE INFORMATION', 'Admin', null, 'fileinfo', '62', null, null, '2019-08-02 15:39:40', '2019-08-02 14:31:48');
INSERT INTO `menu` VALUES ('10', 'SETTING', 'Admin', '', 'setting', '64', '', null, '2019-08-02 10:31:55', '2019-08-02 10:37:17');
INSERT INTO `menu` VALUES ('11', 'TEPLIST', 'Admin', '', 'teplist', '66', '', null, '2019-08-02 10:31:55', '2019-08-02 10:37:17');

DROP TABLE IF EXISTS `test_exec_platform`;
CREATE TABLE `test_exec_platform` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `productId` int(10) unsigned NOT NULL DEFAULT '0' COMMENT 'FK: product.id',
  `testSetType` int(10) unsigned NOT NULL DEFAULT '1' COMMENT 'FK: [test_set].type. Current: 1-Automation,2-Manual Test,4-API Test,8-Performance Test',
  `jenkinsId` int(10) unsigned NOT NULL COMMENT 'FK: jenkins_info.id',
  `name` varchar(32) NOT NULL COMMENT 'TEP name',
  `idx` smallint(5) NOT NULL DEFAULT 99 COMMENT 'TEP index',
  `display` int(10) unsigned NOT NULL DEFAULT '1' COMMENT 'display or not. 0-hide,1-show',
  `createTime` datetime DEFAULT NULL COMMENT 'create time',
  `createUserId` int(10) unsigned NOT NULL COMMENT 'create user',
  `createUserName` varchar(32) DEFAULT NULL COMMENT 'create user name',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
  `updateUserId` int(10) unsigned DEFAULT NULL COMMENT 'update user',
  `updateUserName` varchar(32) DEFAULT NULL COMMENT 'update user name',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_TEP_product_jenkins_id` (`productId`, `jenkinsId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `jenkins_svr_info`;
CREATE TABLE `jenkins_svr_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `url` varchar(128) DEFAULT NULL COMMENT 'jenkins url',
  `username` varchar(32) NOT NULL COMMENT 'username',
  `password` varchar(32) NOT NULL COMMENT 'password',
  `jobname` varchar(64) NOT NULL COMMENT 'jobname',
  `jobview` varchar(500) DEFAULT NULL COMMENT 'jobview',
  `jobConfigXml` text,
  `display` int(10) unsigned NOT NULL DEFAULT '1' COMMENT 'display or not. 0-hide,1-show',
  `createTime` datetime DEFAULT NULL COMMENT 'create time',
  `createUserId` int(10) unsigned NOT NULL COMMENT 'create user',
  `createUserName` varchar(32) DEFAULT NULL COMMENT 'create user name',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
  `updateUserId` int(10) unsigned DEFAULT NULL COMMENT 'update user',
  `updateUserName` varchar(32) DEFAULT NULL COMMENT 'update user name',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_JSI_url_jobname` (`url`, `jobname`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `jenkins_job_parameter`;
CREATE TABLE `jenkins_job_parameter` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `jsiId` int(10) unsigned NOT NULL COMMENT 'jenkins svr id. FK: jenkins_svr_info.id',
  `paramName` varchar(32) NOT NULL COMMENT 'Parameter name',
  `refType` varchar(32) NOT NULL COMMENT 'Item Type. 0-boolean,1-string,2-number,3-choice,4-file',
  `choiceContent` varchar(255) DEFAULT NULL COMMENT 'Choice items if refType is 3. Separated by comma',
  `defaultValue` varchar(64) DEFAULT NULL,
  `lastRunValue` varchar(64) DEFAULT NULL COMMENT 'Last item run value',
  `description` varchar(1024) DEFAULT NULL,
  `displayMode` smallint(10) unsigned NOT NULL DEFAULT '1' COMMENT 'JJP default display mode. 0-not display, 1-display',
  `idx` smallint(5) NOT NULL DEFAULT 99 COMMENT 'JJP index',
  `validation` int(10) unsigned NOT NULL DEFAULT '1' COMMENT 'validation or not. 0-hidden ,1-show, 4-delete',
  `createTime` datetime DEFAULT NULL COMMENT 'create time',
  `createUserId` int(10) unsigned NOT NULL COMMENT 'create user',
  `createUserName` varchar(32) DEFAULT NULL COMMENT 'create user name',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
  `updateUserId` int(10) unsigned DEFAULT NULL COMMENT 'update user',
  `updateUserName` varchar(32) DEFAULT NULL COMMENT 'update user name',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_JJP_url_jobname` (`jsiId`, `paramName`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `jenkins_job_history_main`;
CREATE TABLE `jenkins_job_history_main` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `testSetId` int(10) unsigned NOT NULL COMMENT 'FK: [test_run].testset_id',
  `tepId` int(10) DEFAULT NULL COMMENT 'test execplat form Id. FK: test_exec_platform.id',
  `jsiId` int(10) unsigned NOT NULL COMMENT 'jenkins svr id. FK: jenkins_svr_info.id',
  `jobname` varchar(255) DEFAULT NULL,
  `jobpath` varchar(500) DEFAULT NULL,
  `jenkinsJobParameter` varchar(4096) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `buildId` int(10) DEFAULT NULL COMMENT 'buildId',
  `parseCount` int(10) unsigned DEFAULT NULL,
  `description` varchar(200) NOT NULL COMMENT 'description',
  
  `display` int(10) NOT NULL DEFAULT '1' COMMENT '.0-not，1-yes',
  `createTime` datetime DEFAULT NULL COMMENT 'create time',
  `createUserId` int(10) unsigned NOT NULL COMMENT 'create user',
  `createUserName` varchar(32) DEFAULT NULL COMMENT 'name',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `jenkins_job_history_detail`;
CREATE TABLE `jenkins_job_history_detail` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `mainId` int(10) unsigned NOT NULL COMMENT 'FK: [jenkins_job_history_main].id',  

  `testSetId` int(10) unsigned NOT NULL COMMENT 'FK: [test_run].testset_id',
  `testRunId` int(10) NOT NULL COMMENT 'Test run Id.FK: [test_run].id',
  `testCaseId` int(10) NOT NULL COMMENT 'Test Case Id',
  `locale` varchar(8) DEFAULT '' COMMENT 'locale EN-US',  
  `display` int(10) NOT NULL DEFAULT '1' COMMENT '.0-not，1-yes',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- 2019/8/12
Drop Table IF EXISTS `setting_excel_import`;
CREATE TABLE `setting_excel_import` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `productId` int(10) unsigned NOT NULL DEFAULT '0' COMMENT 'FK: product.id',
  `case_type` varchar(32) NOT NULL COMMENT 'Which type the test case belongs to. correspond to [TestSet].[type]',
  `name` varchar(32) NOT NULL COMMENT 'Column name in excel file',
  `field` varchar(32) NOT NULL DEFAULT '' COMMENT 'correspond to table field',
  `idx` smallint(5) NOT NULL DEFAULT 99 COMMENT 'index for the list column',
  `validation` smallint(5) unsigned NOT NULL DEFAULT '1' COMMENT '1-valid,0-invalid',
  `formatter` varchar(256) DEFAULT NULL COMMENT 'Column formatter',
  `description` varchar(255) DEFAULT NULL COMMENT 'description',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_setting_excel_import_product_casetype_name_field` (`productId`, `case_type`, `name`, `field`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- 2019/8/20
DROP TABLE IF EXISTS `parse_result_summary`;
CREATE TABLE `parse_result_summary` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `batchNo` varchar(32) DEFAULT NULL COMMENT 'batch no',
  `batchId` int(10) unsigned DEFAULT NULL COMMENT 'batch id',  
  `filePath` varchar(255) DEFAULT NULL COMMENT 'File path',
  `fileName` varchar(64) DEFAULT NULL COMMENT 'File Name',
  `fileSize` int(10) unsigned DEFAULT NULL COMMENT 'Size of file',
  `fileWorkPath` varchar(255) DEFAULT NULL COMMENT 'File work path',
  `fileBackupPath` varchar(255) DEFAULT NULL COMMENT 'File backup path',
  `parseStatus` int(10) unsigned DEFAULT NULL COMMENT 'Status。0-undo,2-Success,3-Exception,4-Fail,10-unzip,15-testrun,20-testResult,25-clean',
  `parseCount` int(10) unsigned DEFAULT NULL COMMENT 'count',
  `parseErrorCode` varchar(32) DEFAULT NULL COMMENT 'EC',
  `parseErrorMsg` varchar(255) DEFAULT NULL COMMENT 'EM', 
  `description` varchar(255) DEFAULT NULL COMMENT 'description',
  `reportSummary` varchar(255) DEFAULT NULL COMMENT 'JSON data from report file',  
  `receiveTime` datetime DEFAULT NULL COMMENT 'Receive Time',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='Parse file info';

-- 2019/8/22
DROP TABLE IF EXISTS `jenkins_label_info`;
CREATE TABLE `jenkins_label_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK: Label ID',
  `jjhMainId` int(10) unsigned NOT NULL COMMENT 'FK:[jenkins_job_history_main].id',  
  `name` varchar(64) DEFAULT NULL COMMENT 'Label name',
  `labelFormat` varchar(32) DEFAULT '' COMMENT 'Label format',
  `labelStatus` int(10) DEFAULT '0' COMMENT 'status',
  `testRunCount` int(10) unsigned DEFAULT '0' COMMENT 'Count of this test set TestRuns',
  `locales` varchar(255) DEFAULT NULL,  
  `startTime` datetime DEFAULT NULL,
  `endTime` datetime DEFAULT NULL,  
  `description` varchar(255) DEFAULT NULL,
  `display` int(10) unsigned DEFAULT '1' COMMENT 'display or not. 0-hide,1-show',
  `createTime` datetime DEFAULT NULL COMMENT 'create time',
  `createUserId` int(10) unsigned DEFAULT NULL COMMENT 'create user',
  `createUserName` varchar(32) DEFAULT NULL COMMENT 'create user name',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
  `updateUserId` int(10) unsigned DEFAULT NULL COMMENT 'update user',
  `updateUserName` varchar(32) DEFAULT NULL COMMENT 'update user name',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='Label info for jenkins job trigger';

DROP TABLE IF EXISTS `relation_test_case_method`;
CREATE TABLE `relation_test_case_method` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `testCaseId` int(10) unsigned NOT NULL COMMENT 'FK:[test_case].id',  
  `packageInfo` varchar(64) DEFAULT NULL,
  `className` varchar(64) DEFAULT NULL,
  `methodName` varchar(64) DEFAULT NULL,
  `srcFile` varchar(255) DEFAULT NULL,
  `locales` varchar(64) DEFAULT NULL,
  `display` int(10) unsigned DEFAULT '1' COMMENT 'display or not. 0-hide,1-show',
  `createTime` datetime DEFAULT NULL COMMENT 'create time',
  `createUserId` int(10) unsigned DEFAULT NULL COMMENT 'create user',
  `createUserName` varchar(32) DEFAULT NULL COMMENT 'create user name',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
  `updateUserId` int(10) unsigned DEFAULT NULL COMMENT 'update user',
  `updateUserName` varchar(32) DEFAULT NULL COMMENT 'update user name',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_test_case_id_method_name` (`packageInfo`, `className`, `methodName`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='Relationship for method and test case id';

-- 2019/8/23
DROP TABLE IF EXISTS `issue_category`;
CREATE TABLE `issue_category` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK id',
  `product_id` int(10) unsigned NOT NULL COMMENT 'FK:[product].id. 0:common, Non_zero: private',
  `case_type` int(10) unsigned NOT NULL COMMENT 'FK:[test_case].case_type.1-Automation,2-Manual Test,4-API Test,8-Performance Test',
  `category` varchar(64) DEFAULT NULL COMMENT 'Name of issue category',
  `idx` smallint(5) unsigned NOT NULL COMMENT 'List order',
  `description` varchar(255) DEFAULT NULL COMMENT 'Description',
  `createTime` datetime DEFAULT NULL COMMENT 'Create time',
  `createUserId` int(10) unsigned DEFAULT NULL COMMENT 'create user',
  `createUserName` varchar(32) DEFAULT NULL COMMENT 'create user name',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_issue_category` (`product_id`,`case_type`,`category`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='Issue category';

DROP TABLE IF EXISTS `issue_reason`;
CREATE TABLE `issue_reason` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK id',
  `issue_category_id` int(10) unsigned NOT NULL COMMENT 'FK:[issue_category].id',
  `reason` varchar(3000) DEFAULT NULL,
	`issue_level` smallint(5) unsigned DEFAULT NULL COMMENT 'Bug level for this issue.',
  `idx` smallint(5) unsigned NOT NULL COMMENT 'Reason order',
  `description` varchar(255) DEFAULT NULL COMMENT 'Description',
  `createTime` datetime DEFAULT NULL COMMENT 'Create time',
  `createUserId` int(10) unsigned DEFAULT NULL COMMENT 'Create user',
  `createUserName` varchar(32) DEFAULT NULL COMMENT 'Create user name',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='Issue reason';

-- 2019/8/26
DROP TABLE IF EXISTS `screen_shot`;
CREATE TABLE `screen_shot` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `testRunId` int(10) unsigned NOT NULL COMMENT 'FK: [test_run].id',
  `status` int(10) unsigned DEFAULT NULL COMMENT 'status. 0-unReviewed,1-pass,4-fail',
  `step` int(10) unsigned DEFAULT '0' COMMENT 'index of ss in the report file. 1,2,3....',
  `source` varchar(128) DEFAULT NULL COMMENT 'filename. sources/GUID.source.txt',
  `image` varchar(128) DEFAULT NULL COMMENT 'filename. screenshots/GUID.png',
  `message` varchar(255) DEFAULT NULL,
  `httpFilePath` varchar(255) DEFAULT NULL COMMENT '/imgs/restrun/2016-05-23/....png',
  `sourceFilePath` varchar(255) DEFAULT NULL COMMENT 'File path',
  `sourceFileName` varchar(255) DEFAULT NULL,
  `sourceFileOriginalName` varchar(128) DEFAULT NULL COMMENT 'File name',
  `sourceFileSize` int(10) unsigned DEFAULT NULL COMMENT 'file size',
  `originalName` varchar(128) DEFAULT NULL COMMENT '文件名',
  `filePath` varchar(255) DEFAULT NULL COMMENT '文件路径',
  `fileName` varchar(64) DEFAULT NULL COMMENT '文件名',
  `fileSize` int(10) unsigned DEFAULT NULL COMMENT '照片大小，单位：字节',
  `thumbnailFilePath` varchar(255) DEFAULT NULL COMMENT 'thumbnail file path',
  `thumbnailFileName` varchar(64) DEFAULT NULL COMMENT 'thumbnail file name',
  `thumbnailFileSize` int(10) unsigned DEFAULT NULL COMMENT 'thumbnail file size',
  `description` varchar(255) DEFAULT NULL COMMENT 'Description',
  `jiraTicketId` varchar(32) DEFAULT NULL,
  `jiraTicket` varchar(3000) DEFAULT NULL,
  `qaComment` varchar(3000) DEFAULT NULL,

  `ssfield_int1` int(10) DEFAULT NULL COMMENT 'Field int',
  `ssfield_int2` int(10) DEFAULT NULL COMMENT 'Field int',
  `ssfield_int3` int(10) DEFAULT NULL COMMENT 'Field int',
  `ssfield_int4` int(10) DEFAULT NULL COMMENT 'Field int',
  `ssfield_int5` int(10) DEFAULT NULL COMMENT 'Field int',
  `ssfield_str1` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `ssfield_str2` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `ssfield_str3` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `ssfield_str4` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `ssfield_str5` varchar(255) DEFAULT NULL COMMENT 'Field string',
  `ssfield_review1` varchar(3000) DEFAULT NULL COMMENT 'Field string',
  `ssfield_review2` varchar(3000) DEFAULT NULL COMMENT 'Field string',
  `ssfield_review3` varchar(3000) DEFAULT NULL COMMENT 'Field string',

  `createTime` datetime DEFAULT NULL COMMENT 'Create time',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time', 
  PRIMARY KEY (`id`),
  KEY `unique_screen_shot_testrunid_step` (`testRunId`, `step`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
