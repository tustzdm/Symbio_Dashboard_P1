-- 2019/7/15
INSERT INTO `dictionary`(`id`,`type`,`code`,`value`) VALUES ('150', 'Page_Element_Setting', 'QualityOverviewLayout', 'Quality Overview Layout');
INSERT INTO `dictionary`(`type`,`code`,`value`) VALUES ('Page_Element_Setting', 'Product', 'Product');
INSERT INTO `dictionary`(`type`,`code`,`value`) VALUES ('Page_Element_Setting', 'Release', 'Release');
INSERT INTO `dictionary`(`type`,`code`,`value`) VALUES ('Page_Element_Setting', 'TestSet', 'Test Set');
INSERT INTO `dictionary`(`type`,`code`,`value`) VALUES ('Page_Element_Setting', 'TestCase', 'Test Case');

-- 2019/7/17
ALTER Table `ui_info`
ADD UNIQUE KEY `uniqe_ui_info_page_dbfield` (`page`, `db_field`),
MODIFY COLUMN `disp_status` SMALLINT(5) DEFAULT 0 COMMENT 'Display status for the element. 0-edit only, 1-user defined';

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
	VALUES (260, 'ColumnTypeEnd', 'coltypeend', 'Column Type End', 'place holder for last of col type');

-- 2019/7/16
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
