-- 2019-03-27, Ver0.1, for QualityOverviewLayout
-- 2019-04-24 	Ver0.2 	Add Table: base_code

Drop Table IF EXISTS `base_code`;
CREATE TABLE `base_code` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `type` varchar(50) NOT NULL COMMENT 'group name',
  `name` varchar(100) NOT NULL COMMENT 'name for the item',
  `code` varchar(20) NOT NULL COMMENT 'unique code',
  `description` varchar(200) NOT NULL COMMENT 'description',
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
