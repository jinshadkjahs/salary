/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50640
 Source Host           : localhost:3306
 Source Schema         : salary

 Target Server Type    : MySQL
 Target Server Version : 50640
 File Encoding         : 65001

 Date: 03/06/2018 21:18:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for s_department
-- ----------------------------
DROP TABLE IF EXISTS `s_department`;
CREATE TABLE `s_department`  (
  `depart_id` int(20) NOT NULL AUTO_INCREMENT,
  `depart_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`depart_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 539 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of s_department
-- ----------------------------
INSERT INTO `s_department` VALUES (361, '综合办1');
INSERT INTO `s_department` VALUES (362, '医务部');
INSERT INTO `s_department` VALUES (363, '综合办2');
INSERT INTO `s_department` VALUES (364, '人力资源室');
INSERT INTO `s_department` VALUES (365, '护理部');
INSERT INTO `s_department` VALUES (366, '科技开发室');
INSERT INTO `s_department` VALUES (367, '医疗设备科');
INSERT INTO `s_department` VALUES (368, '待分配');
INSERT INTO `s_department` VALUES (369, '党群工作室');
INSERT INTO `s_department` VALUES (370, '医保科');
INSERT INTO `s_department` VALUES (371, '保卫科');
INSERT INTO `s_department` VALUES (372, '质控科');
INSERT INTO `s_department` VALUES (373, '防保科');
INSERT INTO `s_department` VALUES (374, '院感科');
INSERT INTO `s_department` VALUES (375, '规划部');
INSERT INTO `s_department` VALUES (376, '规培');
INSERT INTO `s_department` VALUES (377, '信息科');
INSERT INTO `s_department` VALUES (378, '服务分公司');
INSERT INTO `s_department` VALUES (379, '急救中心');
INSERT INTO `s_department` VALUES (380, '心血管内科');
INSERT INTO `s_department` VALUES (381, '神内五病区');
INSERT INTO `s_department` VALUES (382, '呼吸内科');
INSERT INTO `s_department` VALUES (383, '消化内科');
INSERT INTO `s_department` VALUES (384, '内分泌科');
INSERT INTO `s_department` VALUES (385, '中医科');
INSERT INTO `s_department` VALUES (386, '皮肤科');
INSERT INTO `s_department` VALUES (387, '骨一科');
INSERT INTO `s_department` VALUES (388, '神经外科');
INSERT INTO `s_department` VALUES (389, '泌尿外科');
INSERT INTO `s_department` VALUES (390, '普外科');
INSERT INTO `s_department` VALUES (391, '外科门诊');
INSERT INTO `s_department` VALUES (392, '胸心外科');
INSERT INTO `s_department` VALUES (393, '麻醉科');
INSERT INTO `s_department` VALUES (394, '儿科');
INSERT INTO `s_department` VALUES (395, '眼  科');
INSERT INTO `s_department` VALUES (396, '检验科');
INSERT INTO `s_department` VALUES (397, '放射科');
INSERT INTO `s_department` VALUES (398, '机能科');
INSERT INTO `s_department` VALUES (399, '药材科');
INSERT INTO `s_department` VALUES (400, '供应室');
INSERT INTO `s_department` VALUES (401, '病理科');
INSERT INTO `s_department` VALUES (402, '耳鼻喉');
INSERT INTO `s_department` VALUES (403, '财务结算部');
INSERT INTO `s_department` VALUES (404, 'ＣＴ室');
INSERT INTO `s_department` VALUES (405, '内科门诊');
INSERT INTO `s_department` VALUES (406, '注射室');
INSERT INTO `s_department` VALUES (407, '血液风湿科');
INSERT INTO `s_department` VALUES (408, '疼痛科');
INSERT INTO `s_department` VALUES (409, '超声科');
INSERT INTO `s_department` VALUES (410, '神内六病区');
INSERT INTO `s_department` VALUES (411, '门诊客服');
INSERT INTO `s_department` VALUES (412, '内窥镜室');
INSERT INTO `s_department` VALUES (413, '核磁共振室');
INSERT INTO `s_department` VALUES (414, '营养室');
INSERT INTO `s_department` VALUES (415, '乳腺外科');
INSERT INTO `s_department` VALUES (416, 'CCU');
INSERT INTO `s_department` VALUES (417, '业务部');
INSERT INTO `s_department` VALUES (418, '产科');
INSERT INTO `s_department` VALUES (419, '重症ICU');
INSERT INTO `s_department` VALUES (420, '针灸科');
INSERT INTO `s_department` VALUES (421, '科级');
INSERT INTO `s_department` VALUES (422, '骨二科');
INSERT INTO `s_department` VALUES (423, '策划中心');
INSERT INTO `s_department` VALUES (424, '妇科');
INSERT INTO `s_department` VALUES (425, '肿瘤介入');
INSERT INTO `s_department` VALUES (426, '血管外科');
INSERT INTO `s_department` VALUES (427, '感染科');
INSERT INTO `s_department` VALUES (428, '全科医学科');
INSERT INTO `s_department` VALUES (429, '袁家卫生所');
INSERT INTO `s_department` VALUES (430, '检验学系');
INSERT INTO `s_department` VALUES (431, '病案室');
INSERT INTO `s_department` VALUES (432, '月子会所');
INSERT INTO `s_department` VALUES (433, '老年内一科');
INSERT INTO `s_department` VALUES (434, '急诊科');
INSERT INTO `s_department` VALUES (435, '供应挂号');
INSERT INTO `s_department` VALUES (436, '五官科');
INSERT INTO `s_department` VALUES (437, '烧伤一科');
INSERT INTO `s_department` VALUES (438, '烧伤整形科');
INSERT INTO `s_department` VALUES (439, '烧伤二科');
INSERT INTO `s_department` VALUES (440, '康复科');
INSERT INTO `s_department` VALUES (441, '烧伤ICU');
INSERT INTO `s_department` VALUES (442, '老年内二科');
INSERT INTO `s_department` VALUES (443, '结算部');
INSERT INTO `s_department` VALUES (444, '伤口科');
INSERT INTO `s_department` VALUES (445, '综合病区');
INSERT INTO `s_department` VALUES (446, '妇产科');
INSERT INTO `s_department` VALUES (447, '心内二病区');
INSERT INTO `s_department` VALUES (448, '口腔科');
INSERT INTO `s_department` VALUES (449, '骨外科');
INSERT INTO `s_department` VALUES (450, '呼吸科');
INSERT INTO `s_department` VALUES (451, '神内二病区');
INSERT INTO `s_department` VALUES (452, '神内三病区');
INSERT INTO `s_department` VALUES (453, '泌尿科');
INSERT INTO `s_department` VALUES (454, '肾内科');
INSERT INTO `s_department` VALUES (455, '导管室');
INSERT INTO `s_department` VALUES (456, '妇产科门诊');
INSERT INTO `s_department` VALUES (457, '高压氧');
INSERT INTO `s_department` VALUES (458, '核医学科');
INSERT INTO `s_department` VALUES (459, '中心实验室');
INSERT INTO `s_department` VALUES (460, '妇幼所');
INSERT INTO `s_department` VALUES (461, 'CT室');
INSERT INTO `s_department` VALUES (462, 'MRI室');
INSERT INTO `s_department` VALUES (463, '神内一病区');
INSERT INTO `s_department` VALUES (464, '心内一病区');
INSERT INTO `s_department` VALUES (465, '急救120');
INSERT INTO `s_department` VALUES (466, '急诊汽车班');
INSERT INTO `s_department` VALUES (467, '心内三病区');
INSERT INTO `s_department` VALUES (468, '职业卫生科');
INSERT INTO `s_department` VALUES (469, '健康管理科');
INSERT INTO `s_department` VALUES (470, '流行病科');
INSERT INTO `s_department` VALUES (471, '老年康复院');
INSERT INTO `s_department` VALUES (472, '职业卫生部');
INSERT INTO `s_department` VALUES (473, '放射卫生科');
INSERT INTO `s_department` VALUES (474, '体检中心');
INSERT INTO `s_department` VALUES (475, '卫生防治科');
INSERT INTO `s_department` VALUES (476, '滨西');
INSERT INTO `s_department` VALUES (477, '心内科五');
INSERT INTO `s_department` VALUES (478, '肿瘤科');
INSERT INTO `s_department` VALUES (479, '胸外消化科');
INSERT INTO `s_department` VALUES (480, '内镜室');
INSERT INTO `s_department` VALUES (481, '神经内科六');
INSERT INTO `s_department` VALUES (482, '神经内科五');
INSERT INTO `s_department` VALUES (483, '内分泌眼科');
INSERT INTO `s_department` VALUES (484, '血管普外科');
INSERT INTO `s_department` VALUES (485, '骨科一病区');
INSERT INTO `s_department` VALUES (486, '骨科二病区');
INSERT INTO `s_department` VALUES (487, '神经外科二科');
INSERT INTO `s_department` VALUES (488, '普外');
INSERT INTO `s_department` VALUES (489, '儿内一科');
INSERT INTO `s_department` VALUES (490, '心内五');
INSERT INTO `s_department` VALUES (491, 'ICU');
INSERT INTO `s_department` VALUES (492, '手术室一');
INSERT INTO `s_department` VALUES (493, '放射介入科');
INSERT INTO `s_department` VALUES (494, '超声');
INSERT INTO `s_department` VALUES (495, 'MRI');
INSERT INTO `s_department` VALUES (496, '血液科');
INSERT INTO `s_department` VALUES (497, '心内一');
INSERT INTO `s_department` VALUES (498, '心内二');
INSERT INTO `s_department` VALUES (499, '心内三');
INSERT INTO `s_department` VALUES (500, '心导管');
INSERT INTO `s_department` VALUES (501, '消化科');
INSERT INTO `s_department` VALUES (502, '内分泌');
INSERT INTO `s_department` VALUES (503, '骨科');
INSERT INTO `s_department` VALUES (504, '配液中心');
INSERT INTO `s_department` VALUES (505, '输液室');
INSERT INTO `s_department` VALUES (506, '透析科');
INSERT INTO `s_department` VALUES (507, '产房二');
INSERT INTO `s_department` VALUES (508, '手术室二');
INSERT INTO `s_department` VALUES (509, '康复医学科');
INSERT INTO `s_department` VALUES (510, '核医学');
INSERT INTO `s_department` VALUES (511, '老年医学科');
INSERT INTO `s_department` VALUES (512, '整形科');
INSERT INTO `s_department` VALUES (513, '伤口');
INSERT INTO `s_department` VALUES (514, '供应');
INSERT INTO `s_department` VALUES (515, '老年康复');
INSERT INTO `s_department` VALUES (516, 'VIP体检');
INSERT INTO `s_department` VALUES (517, '护办室');
INSERT INTO `s_department` VALUES (518, '护理站');
INSERT INTO `s_department` VALUES (519, '药房');
INSERT INTO `s_department` VALUES (520, '内科');
INSERT INTO `s_department` VALUES (521, '护理部4');
INSERT INTO `s_department` VALUES (522, '普外科4');
INSERT INTO `s_department` VALUES (523, '老年二科');
INSERT INTO `s_department` VALUES (524, '老年康复科');
INSERT INTO `s_department` VALUES (525, '社区东矿卫生所');
INSERT INTO `s_department` VALUES (526, '外科');
INSERT INTO `s_department` VALUES (527, '基建部');
INSERT INTO `s_department` VALUES (528, '机房');
INSERT INTO `s_department` VALUES (529, '结算中心');
INSERT INTO `s_department` VALUES (530, '办公室');
INSERT INTO `s_department` VALUES (531, '设备科');
INSERT INTO `s_department` VALUES (532, '综合办公室');
INSERT INTO `s_department` VALUES (533, '药剂科');
INSERT INTO `s_department` VALUES (534, '功能检查科');
INSERT INTO `s_department` VALUES (535, '计算机室');
INSERT INTO `s_department` VALUES (536, '康复科（尖）');
INSERT INTO `s_department` VALUES (537, '康复医学');
INSERT INTO `s_department` VALUES (538, '职业卫生');

-- ----------------------------
-- Table structure for s_emp_type
-- ----------------------------
DROP TABLE IF EXISTS `s_emp_type`;
CREATE TABLE `s_emp_type`  (
  `emp_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `emp_type_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`emp_type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of s_emp_type
-- ----------------------------
INSERT INTO `s_emp_type` VALUES ('0', '正式员工');
INSERT INTO `s_emp_type` VALUES ('1', '合同员工');

-- ----------------------------
-- Table structure for s_employee
-- ----------------------------
DROP TABLE IF EXISTS `s_employee`;
CREATE TABLE `s_employee`  (
  `empid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '员工编号',
  `emp_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `emp_phone` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `emp_card_num` varchar(19) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '员工身份证号',
  `emp_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '员工类型',
  `waltz_date` date DEFAULT NULL COMMENT '入职日期',
  `depart_id` bigint(20) DEFAULT NULL COMMENT '科室id',
  `create_time` datetime(0) DEFAULT NULL,
  `update_time` datetime(0) DEFAULT NULL,
  `base_salary` bigint(20) DEFAULT NULL COMMENT '基本工资',
  PRIMARY KEY (`empid`) USING BTREE,
  UNIQUE INDEX `emp_depart_index`(`empid`, `depart_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for s_salary_archive
-- ----------------------------
DROP TABLE IF EXISTS `s_salary_archive`;
CREATE TABLE `s_salary_archive`  (
  `salary_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `emp_id` bigint(20) NOT NULL,
  `money` bigint(20) DEFAULT NULL,
  `salary_type` int(3) DEFAULT NULL,
  `salary_date` date NOT NULL,
  `create_time` datetime(0) DEFAULT NULL,
  `update_time` datetime(0) DEFAULT NULL,
  `update_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`salary_id`) USING BTREE,
  UNIQUE INDEX `emp_salary_date_index`(`salary_id`, `emp_id`, `salary_date`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for s_salary_main
-- ----------------------------
DROP TABLE IF EXISTS `s_salary_main`;
CREATE TABLE `s_salary_main`  (
  `salary_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `salary_date` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发哪一个月工资',
  `emp_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `gross_pay` bigint(20) DEFAULT NULL COMMENT '应发工资',
  `net_payroll` bigint(20) DEFAULT NULL COMMENT '实发工资',
  `create_time` datetime(0) DEFAULT NULL,
  `updae_time` datetime(0) DEFAULT NULL,
  `update_user_id` bigint(20) DEFAULT NULL COMMENT '修改人员',
  PRIMARY KEY (`salary_id`) USING BTREE,
  UNIQUE INDEX `salary_index`(`salary_date`, `emp_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for s_salary_type
-- ----------------------------
DROP TABLE IF EXISTS `s_salary_type`;
CREATE TABLE `s_salary_type`  (
  `salary_type` int(3) NOT NULL AUTO_INCREMENT,
  `salary_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是加 还是扣',
  `emp_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '员工类型是否有这项工资项',
  PRIMARY KEY (`salary_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of s_salary_type
-- ----------------------------
INSERT INTO `s_salary_type` VALUES (1, '其他奖', '0', '0,1');
INSERT INTO `s_salary_type` VALUES (2, '基本工资', '0', '1');
INSERT INTO `s_salary_type` VALUES (3, '绩效', '0', '1');
INSERT INTO `s_salary_type` VALUES (4, '节加', '0', '0,1');
INSERT INTO `s_salary_type` VALUES (5, '全勤奖', '0', '1');
INSERT INTO `s_salary_type` VALUES (6, '补发', '0', '1');
INSERT INTO `s_salary_type` VALUES (7, '应领工资', '3', '0,1');
INSERT INTO `s_salary_type` VALUES (8, '实领工资', '3', '0,1');
INSERT INTO `s_salary_type` VALUES (9, '月工资', '0', '0');
INSERT INTO `s_salary_type` VALUES (10, '工资额', '0', '0');
INSERT INTO `s_salary_type` VALUES (11, '职业', '0', '0');
INSERT INTO `s_salary_type` VALUES (12, '房补', '0', '0');
INSERT INTO `s_salary_type` VALUES (13, '补发', '0', '0');
INSERT INTO `s_salary_type` VALUES (14, '回民', '0', '0');
INSERT INTO `s_salary_type` VALUES (15, '岗薪工资', '0', '0');
INSERT INTO `s_salary_type` VALUES (16, '书报费', '0', '0');
INSERT INTO `s_salary_type` VALUES (17, '奖金', '0', '0');
INSERT INTO `s_salary_type` VALUES (18, '专项奖', '0', '0');
INSERT INTO `s_salary_type` VALUES (19, '煤气补', '0', '0');
INSERT INTO `s_salary_type` VALUES (20, '水补', '0', '0');
INSERT INTO `s_salary_type` VALUES (21, '扣款', '1', '0');
INSERT INTO `s_salary_type` VALUES (22, '应领A卡', '3', '0');
INSERT INTO `s_salary_type` VALUES (23, '实领A卡', '3', '0');
INSERT INTO `s_salary_type` VALUES (24, '卫生费', '0', '0');
INSERT INTO `s_salary_type` VALUES (25, '交通补', '0', '0');
INSERT INTO `s_salary_type` VALUES (26, '应领B卡', '3', '0');
INSERT INTO `s_salary_type` VALUES (27, '煤气费', '1', '0');
INSERT INTO `s_salary_type` VALUES (28, '养老', '1', '0');
INSERT INTO `s_salary_type` VALUES (29, '医疗', '1', '0');
INSERT INTO `s_salary_type` VALUES (30, '失业', '1', '0');
INSERT INTO `s_salary_type` VALUES (31, '公积金', '1', '0');

-- ----------------------------
-- Table structure for s_salary_type_emp
-- ----------------------------
DROP TABLE IF EXISTS `s_salary_type_emp`;
CREATE TABLE `s_salary_type_emp`  (
  `salary_type_emp_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `salary_id` bigint(20) NOT NULL,
  `money` bigint(20) DEFAULT NULL,
  `salary_type` int(3) DEFAULT NULL,
  PRIMARY KEY (`salary_type_emp_id`) USING BTREE,
  UNIQUE INDEX `salary_type_emp_index`(`salary_type_emp_id`, `salary_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for s_user
-- ----------------------------
DROP TABLE IF EXISTS `s_user`;
CREATE TABLE `s_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `empid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `create_time` datetime(0) DEFAULT NULL,
  `update_time` datetime(0) DEFAULT NULL,
  `user_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '用户类型',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `empId_index`(`empid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of s_user
-- ----------------------------
INSERT INTO `s_user` VALUES (1, 'admin', 'admin', '2018-05-29 16:51:48', '2018-05-29 16:51:55', '0');

-- ----------------------------
-- Table structure for s_user_type
-- ----------------------------
DROP TABLE IF EXISTS `s_user_type`;
CREATE TABLE `s_user_type`  (
  `user_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户类型id',
  `type_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户类型名字',
  PRIMARY KEY (`user_type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of s_user_type
-- ----------------------------
INSERT INTO `s_user_type` VALUES ('0', '管理员');
INSERT INTO `s_user_type` VALUES ('1', '普通用户');

SET FOREIGN_KEY_CHECKS = 1;
