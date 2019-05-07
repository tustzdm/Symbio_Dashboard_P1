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

DROP TABLE IF EXISTS `language_ui`;
CREATE TABLE `language_ui` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `page` VARCHAR(30) NOT NULL COMMENT 'QualityOverviewLayout',
  `key` VARCHAR(30) NOT NULL,
  `type` VARCHAR(45) NOT NULL COMMENT 'text, number, bool, checkbox, list' DEFAULT 'text',
  `data` VARCHAR(512) NULL COMMENT 'For List, Using JSON format.',
  `label` VARCHAR(128) NOT NULL DEFAULT '{"zh_cn": "","en_us": ""}',
  `default_value` VARCHAR(45) NULL COMMENT 'default content for the element',
  `idx` INT NOT NULL,
  `version` VARCHAR(45) NULL,
  `validation` INT NOT NULL COMMENT '0: false, 1: true' DEFAULT 1,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

INSERT INTO `language_ui` VALUES (1, 'QualityOverviewLayout', 'lblCommChart', 'text', NULL, '{\"zh_cn\": \"通用控件\",\"en_us\":\"Common Chart\"}', NULL, 1, NULL, 1);
INSERT INTO `language_ui` VALUES (2, 'QualityOverviewLayout', 'lblOtherChart', 'text', NULL, '{\"zh_cn\": \"其他报表控件\",\"en_us\":\"Other Chart\"}', NULL, 2, NULL, 1);
INSERT INTO `language_ui` VALUES (3, 'QualityOverviewLayout', 'lblListList', 'text', NULL, '{\"zh_cn\": \"列表设置：\",\"en_us\":\"List\"}', NULL, 3, NULL, 1);
INSERT INTO `language_ui` VALUES (4, 'QualityOverviewLayout', 'lblRowChart', 'text', NULL, '{\"zh_cn\": \"行控件设置：\",\"en_us\":\"Row Chart:\"}', NULL, 4, NULL, 1);
INSERT INTO `language_ui` VALUES (5, 'QualityOverviewLayout', 'lblPreview', 'text', NULL, '{\"zh_cn\": \"下拉框预览：\",\"en_us\":\"Preview\"}', NULL, 5, NULL, 1);

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
  `name` varchar(32) NOT NULL COMMENT 'Release name',
  `status` int unsigned NOT NULL DEFAULT '0' COMMENT 'Status：0-pending, 1-In Progress，2-Blocked, 3-completed with issue, 4-atrisk, 5-signoff',
  `start_time` datetime DEFAULT NULL COMMENT 'Start time. YYYY-MM-DD 00:00:00',
  `end_time` datetime DEFAULT NULL COMMENT 'End time. YYYY-MM-DD 23:59:59',
  `display` int unsigned NOT NULL DEFAULT '1' COMMENT 'Display or not. 0-hide, 1-show',
  `remark` varchar(255) DEFAULT NULL COMMENT 'remark',
  PRIMARY KEY (`id`),
  UNIQUE KEY `release_productid_name` (`product_id`,`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

Drop Table IF EXISTS `test_set`;
CREATE TABLE `test_set` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'key',
  `product_id` int unsigned NOT NULL COMMENT 'Project Id',
  `release_id` int unsigned NOT NULL COMMENT 'Release Id',
  `name` varchar(32) NOT NULL COMMENT 'Test set name',
  `type` int unsigned NOT NULL DEFAULT '0' COMMENT '1-Automation,2-Manual Test,4-API Test,8-Performance Test',
  `status` int unsigned NOT NULL DEFAULT '0' COMMENT 'Status：0-open,1-completed',
  `start_time` datetime DEFAULT NULL COMMENT 'Start time. YYYY-MM-DD 00:00:00',
  `end_time` datetime DEFAULT NULL COMMENT 'End time. YYYY-MM-DD 23:59:59',
  `qa_lead` int(10) unsigned NULL COMMENT 'QA leader',
  `jira_project` varchar(32) DEFAULT NULL COMMENT 'JIRA project for the bug',
  `bug_assignee` int(10) unsigned NULL COMMENT 'User ID of bug assignee',
  `description` varchar(255) DEFAULT NULL COMMENT 'Description',
  `locales` varchar(255) DEFAULT NULL COMMENT 'locales for the test set',
  `create_time` datetime DEFAULT NULL COMMENT 'create time',
  `create_user` int(10) unsigned NOT NULL COMMENT 'user id for creation',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'udate datetime',
  `update_user` int(10) unsigned DEFAULT NULL COMMENT 'user id updated',
  PRIMARY KEY (`id`),
  UNIQUE KEY `testset_product_release_name` (`product_id`,`release_id`, `name`)
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
  
  `customer_field_bool1` smallint(5) DEFAULT NULL COMMENT 'Customer field bool. 1-true,0-false',
  `customer_field_bool2` smallint(5) DEFAULT NULL COMMENT 'Customer field bool. 1-true,0-false',
  `customer_field_bool3` smallint(5) DEFAULT NULL COMMENT 'Customer field bool. 1-true,0-false',
  `customer_field_int1` int(10) DEFAULT NULL COMMENT 'Customer field int',
  `customer_field_int2` int(10) DEFAULT NULL COMMENT 'Customer field int',
  `customer_field_int3` int(10) DEFAULT NULL COMMENT 'Customer field int',
  `customer_field_str1` varchar(255) DEFAULT NULL COMMENT 'Customer field string',
  `customer_field_str2` varchar(255) DEFAULT NULL COMMENT 'Customer field string',
  `customer_field_str3` varchar(255) DEFAULT NULL COMMENT 'Customer field string',
  `customer_field_str4` varchar(255) DEFAULT NULL COMMENT 'Customer field string',
  `customer_field_str5` varchar(255) DEFAULT NULL COMMENT 'Customer field string',
  
  `create_time` datetime DEFAULT NULL,
  `create_user_id` int(10) unsigned DEFAULT NULL COMMENT 'user id',
  `create_user_name` varchar(32) DEFAULT NULL COMMENT 'user name',
  `update_time` datetime DEFAULT NULL,
  `update_user_id` int(10) unsigned DEFAULT NULL COMMENT 'user id',
  `update_user_name` varchar(32) DEFAULT NULL COMMENT 'user name',
  `record_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record update time',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_testcase_product_caseid_status` (`product_id`,`case_id`, `case_status`),
  KEY `idx_test_case_productId_caseId` (`product_id`,`case_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `test_run`;
CREATE TABLE `test_run` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Key id',
  `testset_id` int(10) unsigned NOT NULL COMMENT 'FK: [test_set].id',
  `testcase_id` int(10) unsigned NOT NULL COMMENT 'FK: [test_case].id',
  `locale` varchar(8) DEFAULT NULL COMMENT '语种。EN-US',

  `result_flag` int(10) unsigned DEFAULT '0' COMMENT 'record flag：0-normal, 4-delete',
  `display` int(10) unsigned DEFAULT '1' COMMENT 'Display or not.0-no,1-yes',
  `description` varchar(255) DEFAULT NULL COMMENT 'Description',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'udate datetime',

  `actrualTesterName` varchar(32) DEFAULT NULL COMMENT 'LQA user Name',
  
  `appPath` varchar(3000) DEFAULT NULL,
  
  PRIMARY KEY (`id`),
  KEY `idx_test_run_testset_id` (`testset_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `test_result`;
CREATE TABLE `test_result` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Key id',
  `test_run_id` int(10) unsigned NOT NULL COMMENT 'FK: [test_run].id',
  `testcase_id` int(10) unsigned NOT NULL COMMENT 'FK: [test_case].id',

  `auto_run_status` int(10) unsigned DEFAULT NULL COMMENT 'Auomation Status：0-Not RUN, 1-AUTOMATION SUCCESS,2-Automation FAILURE,3-Automation SKIP,4-Automation SUCCESS WITHIN PERCENTAGE,16-Automation STARTED, 101-TestNotRequired',
  `job_weather` int(10) unsigned DEFAULT '0' COMMENT 'job status：0-sunny,1-cloud,2-dull,3-fog,4-rainy',

  `startTime` datetime DEFAULT NULL COMMENT 'start Locale time',
  `endTime` datetime DEFAULT NULL COMMENT 'end Locale Time',
  `startUTCTime` datetime DEFAULT NULL COMMENT 'start Time',
  `timezone` float DEFAULT NULL COMMENT 'Timezone',
  `execDuration` int(10) unsigned DEFAULT NULL COMMENT 'execute duration seconds.',
  `note` varchar(255) DEFAULT NULL COMMENT 'Note or Bug',
  `exceptionDesc` varchar(8000) DEFAULT NULL COMMENT 'exception',
  `exception` text,
  `stacktrace` text,





  
  
  `qaStatus` varchar(32) DEFAULT '0' COMMENT 'Reference to dict_table.code. 0-(blank),1-pass,2-conditional pass,4-fail,5-Testing Not Required',
  `buildInfo` varchar(32) DEFAULT NULL COMMENT '1607.912',
  `environment` int(10) unsigned DEFAULT '0' COMMENT '类型：0-QA，1-E2E',
  `clusterName` varchar(16) DEFAULT NULL COMMENT 'C8',
  `browsers` int(10) unsigned DEFAULT '0' COMMENT '0-firefox,1-chrome',
  `browsersVesion` varchar(16) DEFAULT '' COMMENT 'version of browser',
  `skus` int(10) unsigned DEFAULT '0' COMMENT '0-blank,1-Simple,2-Essential,3-Plus',
  `locales` varchar(16) DEFAULT '' COMMENT 'local of the TR',
  `region` varchar(32) DEFAULT '',
  `method` int(10) unsigned DEFAULT '0' COMMENT '0-Automated,1-Manual',
  `testingNotes` varchar(255) DEFAULT '' COMMENT 'testingNotes',
  `notes` varchar(255) DEFAULT '' COMMENT 'notes',
  `bugReport` varchar(255) DEFAULT '' COMMENT 'JIRA link of bug',
  `requestId` varchar(255) DEFAULT '' COMMENT 'id of request',
  `qbApiStatus` int(10) unsigned DEFAULT '0' COMMENT '是否invoke success. 0-unkown，1-Yes, 4-Fail',
  `issueCategoryId` int(10) unsigned DEFAULT NULL COMMENT 'category id',
  `category` varchar(64) DEFAULT NULL COMMENT 'category space',
  `issueReasonId` int(10) unsigned DEFAULT NULL COMMENT 'reason id',
  `reason` varchar(64) DEFAULT NULL COMMENT 'reason space',
  `actualTesterUserId` int(10) unsigned DEFAULT NULL COMMENT 'Tester',
  `actualTesterUserName` varchar(32) DEFAULT NULL COMMENT 'Tester Name',
  `testTime` datetime DEFAULT NULL COMMENT 'Test date',
  `isReviewed` int(10) unsigned DEFAULT '0' COMMENT '是否reviewed.0-unReviewed，1-reviewed',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updateUserId` int(10) unsigned DEFAULT NULL COMMENT '更新人',
  `updateUserName` varchar(32) DEFAULT NULL COMMENT '更新人Name',
  `mobileDevice` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_test_result_testrun` (`testCaseId`,`testRunId`)
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


