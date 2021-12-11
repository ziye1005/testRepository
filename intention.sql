/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : intention

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 18/09/2021 11:09:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_gl_trackline
-- ----------------------------
DROP TABLE IF EXISTS `t_gl_trackline`;
CREATE TABLE `t_gl_trackline`  (
  `id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标识',
  `target_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目标标识',
  `target_sort` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目标类别',
  `name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '航线名称',
  `takeoff_airport_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '起飞机场',
  `landing_airport_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '降落机场',
  `task_action_area_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'RW活动区域编号',
  `save_path` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动规律存储路径',
  `import_time` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新时间'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_gl_trackline
-- ----------------------------
INSERT INTO `t_gl_trackline` VALUES ('1', '2', '41', 'A57', '62', '44', '55', '/model/save', '2021-09-17 16:56:24');

-- ----------------------------
-- Table structure for t_yp_target_recog
-- ----------------------------
DROP TABLE IF EXISTS `t_yp_target_recog`;
CREATE TABLE `t_yp_target_recog`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '研判结果id',
  `target_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目标批号',
  `target_type` tinyint(0) NULL DEFAULT NULL COMMENT '目标类别\r\n（0：ZC机\r\n1：Z斗机\r\n2：HZ机\r\n3：预警机\r\n4：民航\r\n255:其他）\r\n',
  `point_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当前航点号',
  `aircraft_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目标编号',
  `target_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目标名称',
  `judge_means` tinyint(0) NULL DEFAULT NULL COMMENT '研判手段（0：属性研判\r\n1：活动规律研判\r\n2：Z术Z法研判\r\n3：DC特征研判\r\n4：综合研判\r\n5：人工判证\r\n255：其他）\r\n',
  `probability` tinyint(0) NULL DEFAULT NULL COMMENT '研判概率',
  `import_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_yp_target_recog
-- ----------------------------
INSERT INTO `t_yp_target_recog` VALUES (1, '2', 33, '42', '44', 'A-57', 1, 1, '2021-09-17 20:14:42');
INSERT INTO `t_yp_target_recog` VALUES (11, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_yp_target_recog` VALUES (12, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `userid` int(0) NOT NULL AUTO_INCREMENT,
  `userpw` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`userid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('alice', 1, '123');
INSERT INTO `user` VALUES ('bob', 2, '456');
INSERT INTO `user` VALUES ('catlin', 3, '789');
INSERT INTO `user` VALUES ('david', 4, '012');

SET FOREIGN_KEY_CHECKS = 1;
