-- 2019/7/15
INSERT INTO `dictionary`(`id`,`type`,`code`,`value`) VALUES ('150', 'Page_Element_Setting', 'QualityOverviewLayout', 'Quality Overview Layout');
INSERT INTO `dictionary`(`type`,`code`,`value`) VALUES ('Page_Element_Setting', 'Product', 'Product');
INSERT INTO `dictionary`(`type`,`code`,`value`) VALUES ('Page_Element_Setting', 'Release', 'Release');
INSERT INTO `dictionary`(`type`,`code`,`value`) VALUES ('Page_Element_Setting', 'TestSet', 'Test Set');
INSERT INTO `dictionary`(`type`,`code`,`value`) VALUES ('Page_Element_Setting', 'TestCase', 'Test Case');

-- 2019/7/16
Drop Table IF EXISTS `sys_list_setting`;
CREATE TABLE `sys_list_setting` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(32) NOT NULL COMMENT 'List Name. Product, Release, TestSet, TestCase, QO_Product',

  `col` varchar(32) NOT NULL COMMENT 'group name',


  `code` varchar(32) NOT NULL COMMENT 'unique code',
  `name` varchar(64) NOT NULL COMMENT 'name for the item',
  `validation` smallint(5) unsigned NOT NULL DEFAULT '1' COMMENT '\n 1=valid,\n 0=invalid',
  `order_idx` smallint(5) unsigned NOT NULL COMMENT 'index for the group',
  `parent_id` int(10) unsigned DEFAULT NULL COMMENT 'parent id if has hierachy',
  `spare_smallint` smallint(10) unsigned DEFAULT NULL COMMENT 'spare field for small int type',
  `spare_int` int(10) unsigned DEFAULT NULL COMMENT 'spare field for int type',
  `spare_str100` varchar(100) DEFAULT NULL COMMENT 'spare field for string',
  `spare_str200` varchar(200) DEFAULT NULL COMMENT 'spare field for string',
  `spare_str500` varchar(500) DEFAULT NULL COMMENT 'spare field for string',


  `description` varchar(255) NOT NULL COMMENT 'description',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
