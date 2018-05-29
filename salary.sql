/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50536
Source Host           : localhost:3306
Source Database       : salary

Target Server Type    : MYSQL
Target Server Version : 50536
File Encoding         : 65001

Date: 2018-05-29 17:30:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for s_department
-- ----------------------------
DROP TABLE IF EXISTS `s_department`;
CREATE TABLE `s_department` (
  `depart_id` int(20) NOT NULL AUTO_INCREMENT,
  `depart_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`depart_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_department
-- ----------------------------

-- ----------------------------
-- Table structure for s_employee
-- ----------------------------
DROP TABLE IF EXISTS `s_employee`;
CREATE TABLE `s_employee` (
  `empid` varchar(32) NOT NULL COMMENT '员工编号',
  `emp_name` varchar(10) DEFAULT NULL,
  `emp_phone` varchar(15) DEFAULT NULL,
  `emp_card_num` varchar(19) DEFAULT NULL COMMENT '员工身份证号',
  `emp_type` char(1) DEFAULT NULL COMMENT '员工类型',
  `waltz_date` date DEFAULT NULL COMMENT '入职日期',
  `depart_id` bigint(20) DEFAULT NULL COMMENT '科室id',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `base_salary` bigint(20) DEFAULT NULL COMMENT '基本工资',
  PRIMARY KEY (`empid`),
  UNIQUE KEY `emp_depart_index` (`empid`,`depart_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_employee
-- ----------------------------

-- ----------------------------
-- Table structure for s_emp_type
-- ----------------------------
DROP TABLE IF EXISTS `s_emp_type`;
CREATE TABLE `s_emp_type` (
  `emp_type` char(1) NOT NULL,
  `emp_type_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`emp_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_emp_type
-- ----------------------------
INSERT INTO `s_emp_type` VALUES ('0', '正式员工');
INSERT INTO `s_emp_type` VALUES ('1', '合同员工');

-- ----------------------------
-- Table structure for s_salary
-- ----------------------------
DROP TABLE IF EXISTS `s_salary`;
CREATE TABLE `s_salary` (
  `salary_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `emp_id` bigint(20) NOT NULL,
  `money` bigint(20) DEFAULT NULL,
  `salary_type` int(3) DEFAULT NULL,
  `salary_date` date NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`salary_id`),
  UNIQUE KEY `emp_salary_date_index` (`salary_id`,`emp_id`,`salary_date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_salary
-- ----------------------------

-- ----------------------------
-- Table structure for s_salary_archive
-- ----------------------------
DROP TABLE IF EXISTS `s_salary_archive`;
CREATE TABLE `s_salary_archive` (
  `salary_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `emp_id` bigint(20) NOT NULL,
  `money` bigint(20) DEFAULT NULL,
  `salary_type` int(3) DEFAULT NULL,
  `salary_date` date NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`salary_id`),
  UNIQUE KEY `emp_salary_date_index` (`salary_id`,`emp_id`,`salary_date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_salary_archive
-- ----------------------------

-- ----------------------------
-- Table structure for s_salary_type
-- ----------------------------
DROP TABLE IF EXISTS `s_salary_type`;
CREATE TABLE `s_salary_type` (
  `salary_type` int(3) NOT NULL AUTO_INCREMENT,
  `salary_name` varchar(20) DEFAULT NULL,
  `type` char(1) DEFAULT NULL COMMENT '是加 还是扣',
  `emp_type` varchar(10) DEFAULT NULL COMMENT '员工类型是否有这项工资项',
  PRIMARY KEY (`salary_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_salary_type
-- ----------------------------

-- ----------------------------
-- Table structure for s_user
-- ----------------------------
DROP TABLE IF EXISTS `s_user`;
CREATE TABLE `s_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `empid` varchar(32) NOT NULL,
  `password` varchar(15) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `user_type` char(1) NOT NULL DEFAULT '0' COMMENT '用户类型',
  PRIMARY KEY (`id`),
  UNIQUE KEY `empId_index` (`empid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_user
-- ----------------------------
INSERT INTO `s_user` VALUES ('1', 'admin', 'admin', '2018-05-29 16:51:48', '2018-05-29 16:51:55', '0');

-- ----------------------------
-- Table structure for s_user_type
-- ----------------------------
DROP TABLE IF EXISTS `s_user_type`;
CREATE TABLE `s_user_type` (
  `user_type` char(1) NOT NULL COMMENT '用户类型id',
  `type_name` varchar(20) DEFAULT NULL COMMENT '用户类型名字',
  PRIMARY KEY (`user_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_user_type
-- ----------------------------
INSERT INTO `s_user_type` VALUES ('0', '管理员');
INSERT INTO `s_user_type` VALUES ('1', '普通用户');
