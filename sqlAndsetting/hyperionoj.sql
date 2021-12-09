/*
 Navicat Premium Data Transfer

 Source Server         : HyperionBlog
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : localhost:3306
 Source Schema         : hyperionoj

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 09/12/2021 23:08:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oj_admin
-- ----------------------------
DROP TABLE IF EXISTS `oj_admin`;
CREATE TABLE `oj_admin`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `permission_level` int NOT NULL COMMENT '权限等级',
  `salt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '加密盐(加密密码)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_admin
-- ----------------------------

-- ----------------------------
-- Table structure for oj_admin_action
-- ----------------------------
DROP TABLE IF EXISTS `oj_admin_action`;
CREATE TABLE `oj_admin_action`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `admin_id` bigint NOT NULL COMMENT '管理员id',
  `action` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '干了啥',
  `time` bigint NOT NULL COMMENT '什么时候',
  `status` int NOT NULL COMMENT '状态(0:成功 1:撤销)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_admin_action
-- ----------------------------

-- ----------------------------
-- Table structure for oj_admin_permisssion
-- ----------------------------
DROP TABLE IF EXISTS `oj_admin_permisssion`;
CREATE TABLE `oj_admin_permisssion`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `permission_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限名称',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限url',
  `level` int NOT NULL COMMENT '需要的权限等级',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_admin_permisssion
-- ----------------------------

-- ----------------------------
-- Table structure for oj_article
-- ----------------------------
DROP TABLE IF EXISTS `oj_article`;
CREATE TABLE `oj_article`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '文章id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文章标题',
  `author_id` bigint NOT NULL COMMENT '作者id',
  `summary` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文章简介',
  `body_id` bigint NOT NULL COMMENT '内容id',
  `category_id` bigint NOT NULL COMMENT '分类id',
  `create_time` bigint NOT NULL COMMENT '创建时间',
  `comment_count` int NOT NULL COMMENT '评论数量',
  `view_counts` int NOT NULL COMMENT '阅读量',
  `weight` int NOT NULL COMMENT '是否置顶',
  `is_solution` int NOT NULL COMMENT '是否为题解(0:不是 1:是)',
  `problem_id` bigint NULL DEFAULT NULL COMMENT '题目id',
  `delete` int NOT NULL COMMENT '是否删除(0:正常 1:删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_article
-- ----------------------------

-- ----------------------------
-- Table structure for oj_article_article_tag
-- ----------------------------
DROP TABLE IF EXISTS `oj_article_article_tag`;
CREATE TABLE `oj_article_article_tag`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `article_id` bigint NOT NULL COMMENT '文章id',
  `tag_id` bigint NOT NULL COMMENT '标签id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_article_article_tag
-- ----------------------------

-- ----------------------------
-- Table structure for oj_article_body
-- ----------------------------
DROP TABLE IF EXISTS `oj_article_body`;
CREATE TABLE `oj_article_body`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '文章内容(md)',
  `content_html` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '文章内容(html)',
  `article_id` bigint NOT NULL COMMENT '文章id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_article_body
-- ----------------------------

-- ----------------------------
-- Table structure for oj_article_category
-- ----------------------------
DROP TABLE IF EXISTS `oj_article_category`;
CREATE TABLE `oj_article_category`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '分类名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '分类描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_article_category
-- ----------------------------

-- ----------------------------
-- Table structure for oj_article_comment
-- ----------------------------
DROP TABLE IF EXISTS `oj_article_comment`;
CREATE TABLE `oj_article_comment`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '文章评论id',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论内容',
  `create_time` bigint NOT NULL COMMENT '评论时间',
  `article_id` bigint NOT NULL COMMENT '哪一篇文章的评论',
  `author_id` bigint NOT NULL COMMENT '评论者id',
  `parent_id` bigint NOT NULL COMMENT '是那个评论下的',
  `to_uid` bigint NOT NULL COMMENT '回复谁(id)的',
  `level` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '楼层(0:普通楼 1:楼中楼)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_article_comment
-- ----------------------------

-- ----------------------------
-- Table structure for oj_article_tag
-- ----------------------------
DROP TABLE IF EXISTS `oj_article_tag`;
CREATE TABLE `oj_article_tag`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '标签id',
  `tag_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标签名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_article_tag
-- ----------------------------

-- ----------------------------
-- Table structure for oj_contest
-- ----------------------------
DROP TABLE IF EXISTS `oj_contest`;
CREATE TABLE `oj_contest`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '比赛id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '比赛名称',
  `create_time` bigint NOT NULL COMMENT '创建时间',
  `start_time` bigint NOT NULL COMMENT '比赛开始时间',
  `end_time` bigint NOT NULL COMMENT '比赛结束时间',
  `run_time` bigint NOT NULL COMMENT '比赛计划持续时间',
  `application_number` int NOT NULL COMMENT '报名人数',
  `real_number` int NOT NULL COMMENT '有效人数',
  `submit_number` int NOT NULL COMMENT '提交总数',
  `ac_number` int NOT NULL COMMENT '通过的提交数',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '参加比赛密码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_contest
-- ----------------------------

-- ----------------------------
-- Table structure for oj_contest_problem
-- ----------------------------
DROP TABLE IF EXISTS `oj_contest_problem`;
CREATE TABLE `oj_contest_problem`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `contests_id` bigint NOT NULL COMMENT '比赛id',
  `problem_id` bigint NOT NULL COMMENT '题目id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_contest_problem
-- ----------------------------

-- ----------------------------
-- Table structure for oj_contest_submit
-- ----------------------------
DROP TABLE IF EXISTS `oj_contest_submit`;
CREATE TABLE `oj_contest_submit`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '提交代码id',
  `contests_id` bigint NOT NULL COMMENT '比赛id',
  `problem_id` bigint NOT NULL COMMENT '哪题的代码',
  `author_id` bigint NOT NULL COMMENT '提交用户id',
  `code_lang` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '代码语言',
  `code_body` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '提交的代码',
  `status` int NOT NULL COMMENT '代码状态(0:运行中 1:通过 2:错误 3:运行超时 4:内存超限 5:运行时错误 6:段错误 7:格式错误 )',
  `run_time` int NOT NULL COMMENT '运行多少毫秒(ms)',
  `run_memory` int NOT NULL COMMENT '运行使用多少内存(mb)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_contest_submit
-- ----------------------------

-- ----------------------------
-- Table structure for oj_contest_user
-- ----------------------------
DROP TABLE IF EXISTS `oj_contest_user`;
CREATE TABLE `oj_contest_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `contests_is` bigint NOT NULL COMMENT '比赛id',
  `user_id` bigint NOT NULL COMMENT '参与者id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_contest_user
-- ----------------------------

-- ----------------------------
-- Table structure for oj_problem
-- ----------------------------
DROP TABLE IF EXISTS `oj_problem`;
CREATE TABLE `oj_problem`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '题目id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '题目名称',
  `body_id` bigint NOT NULL COMMENT '题目描述id',
  `problem_level` int NOT NULL COMMENT '题目难度',
  `category_id` bigint NOT NULL COMMENT '题目分类id',
  `ac_number` int NOT NULL COMMENT '题目通过数量',
  `submit_number` int NOT NULL COMMENT '提交数量',
  `solution_number` int NOT NULL COMMENT '题解数量',
  `comment_number` int NOT NULL COMMENT '评论数量',
  `run_time` bigint NOT NULL COMMENT '限制运行时间(s)',
  `run_memory` int NOT NULL COMMENT '限制运行内存(mb)',
  `case_number` int NOT NULL COMMENT '测试点数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_problem
-- ----------------------------
INSERT INTO `oj_problem` VALUES (1, 'test', 1, 1, 1, 0, 0, 0, 0, 1000, 256, 1);
INSERT INTO `oj_problem` VALUES (2, 'test2', 2, 1, 1, 0, 0, 0, 0, 1000, 256, 1);

-- ----------------------------
-- Table structure for oj_problem_body
-- ----------------------------
DROP TABLE IF EXISTS `oj_problem_body`;
CREATE TABLE `oj_problem_body`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '题目描述id',
  `problem_body` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '题目描述(md)',
  `problem_body_html` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '题目描述(html)',
  `problem_id` bigint NOT NULL COMMENT '题目id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_problem_body
-- ----------------------------

-- ----------------------------
-- Table structure for oj_problem_category
-- ----------------------------
DROP TABLE IF EXISTS `oj_problem_category`;
CREATE TABLE `oj_problem_category`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '题目分类id',
  `category_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '题目分类名',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '题目分类描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_problem_category
-- ----------------------------

-- ----------------------------
-- Table structure for oj_problem_comment
-- ----------------------------
DROP TABLE IF EXISTS `oj_problem_comment`;
CREATE TABLE `oj_problem_comment`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '题目评论id',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论内容',
  `create_time` bigint NOT NULL COMMENT '评论时间',
  `problem_id` bigint NOT NULL COMMENT '哪一个题目的评论',
  `author_id` bigint NOT NULL COMMENT '评论者id',
  `parent_id` bigint NOT NULL COMMENT '是那个评论下的',
  `to_uid` bigint NOT NULL COMMENT '回复谁(id)的',
  `level` int NOT NULL COMMENT '楼层(0:普通楼 1:楼中楼)',
  `delete` int NOT NULL COMMENT '是否删除(0:正常 1:删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_problem_comment
-- ----------------------------

-- ----------------------------
-- Table structure for oj_problem_problem_tag
-- ----------------------------
DROP TABLE IF EXISTS `oj_problem_problem_tag`;
CREATE TABLE `oj_problem_problem_tag`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `article_id` bigint NOT NULL COMMENT '文章id',
  `tag_id` bigint NOT NULL COMMENT '标签id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_problem_problem_tag
-- ----------------------------

-- ----------------------------
-- Table structure for oj_problem_submit
-- ----------------------------
DROP TABLE IF EXISTS `oj_problem_submit`;
CREATE TABLE `oj_problem_submit`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '提交代码id',
  `problem_id` bigint NOT NULL COMMENT '哪题的代码',
  `author_id` bigint NOT NULL COMMENT '提交用户id',
  `code_lang` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '代码语言',
  `code_body` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '提交的代码',
  `status` int NOT NULL COMMENT '代码状态(0:运行中 1:通过 2:错误 3:运行超时 4:内存超限 5:运行时错误 6:段错误 7:格式错误 )',
  `run_time` int NOT NULL COMMENT '运行多少毫秒(ms)',
  `run_memory` int NOT NULL COMMENT '运行使用多少内存(mb)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_problem_submit
-- ----------------------------

-- ----------------------------
-- Table structure for oj_problem_tag
-- ----------------------------
DROP TABLE IF EXISTS `oj_problem_tag`;
CREATE TABLE `oj_problem_tag`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '标签id',
  `tag_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标签名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_problem_tag
-- ----------------------------

-- ----------------------------
-- Table structure for oj_sys_class
-- ----------------------------
DROP TABLE IF EXISTS `oj_sys_class`;
CREATE TABLE `oj_sys_class`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '班级id',
  `teacher_id` bigint NOT NULL COMMENT '教师id',
  `course_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '课程名称',
  `academy` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学院',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_sys_class
-- ----------------------------

-- ----------------------------
-- Table structure for oj_sys_class_student
-- ----------------------------
DROP TABLE IF EXISTS `oj_sys_class_student`;
CREATE TABLE `oj_sys_class_student`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `class_id` bigint NOT NULL COMMENT '班级id',
  `student_number` bigint NOT NULL COMMENT '学生学号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_sys_class_student
-- ----------------------------

-- ----------------------------
-- Table structure for oj_sys_homework
-- ----------------------------
DROP TABLE IF EXISTS `oj_sys_homework`;
CREATE TABLE `oj_sys_homework`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `class_id` bigint NOT NULL COMMENT '班级id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '作业名称',
  `create_time` bigint NOT NULL COMMENT '创建时间',
  `start_time` bigint NOT NULL COMMENT '开始时间',
  `end_time` bigint NOT NULL COMMENT '结束时间',
  `teacher_id` bigint NOT NULL COMMENT '布置作业的老师id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_sys_homework
-- ----------------------------

-- ----------------------------
-- Table structure for oj_sys_homework_problem
-- ----------------------------
DROP TABLE IF EXISTS `oj_sys_homework_problem`;
CREATE TABLE `oj_sys_homework_problem`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `homework_id` bigint NOT NULL COMMENT '作业id',
  `problem_id` bigint NOT NULL COMMENT '题目id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_sys_homework_problem
-- ----------------------------

-- ----------------------------
-- Table structure for oj_sys_homework_submit
-- ----------------------------
DROP TABLE IF EXISTS `oj_sys_homework_submit`;
CREATE TABLE `oj_sys_homework_submit`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `homework_id` bigint NOT NULL COMMENT '作业id',
  `problem_id` bigint NOT NULL COMMENT '题目id',
  `student_id` bigint NOT NULL COMMENT '学生id',
  `submit_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '提交代码',
  `submit_time` bigint NOT NULL COMMENT '提交时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_sys_homework_submit
-- ----------------------------

-- ----------------------------
-- Table structure for oj_sys_teacher
-- ----------------------------
DROP TABLE IF EXISTS `oj_sys_teacher`;
CREATE TABLE `oj_sys_teacher`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id标识符(为手机号，可用于登录)',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号头像url',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户密码',
  `teacher_number` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学工号',
  `mail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '目前邮箱是唯一找回账号的方式(可用于登录)',
  `department` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属部门',
  `create_date` bigint NOT NULL COMMENT '账号创建时间',
  `last_login` bigint NOT NULL COMMENT '上一次登录时间',
  `salt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '加密盐(加密密码用)',
  `state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号状态(0:表示正常 1:冻结 2:删除 3:测试)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_sys_teacher
-- ----------------------------

-- ----------------------------
-- Table structure for oj_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `oj_sys_user`;
CREATE TABLE `oj_sys_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id标识符(为手机号，可用于登录)',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '账号头像url',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户密码',
  `student_number` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学号(非本校可以为空)',
  `mail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '目前邮箱是唯一找回账号的方式(可用于登录)',
  `create_time` bigint NOT NULL COMMENT '账号创建时间',
  `last_login` bigint NOT NULL COMMENT '上一次登录时间',
  `problem_ac_number` int NOT NULL COMMENT '通过题目数量',
  `problem_submit_number` int NOT NULL COMMENT '题目提交总数',
  `problem_submit_ac_number` int NOT NULL COMMENT '题目提交中通过总数',
  `salt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '加密盐(加密密码用)',
  `status` int NOT NULL COMMENT '账号状态(0:正常 1:停用)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1465874540008509443 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_sys_user
-- ----------------------------
INSERT INTO `oj_sys_user` VALUES (15570357290, '冰箱的主人', '...', 'e56f47a5d842342487be1fee4ae0752d', NULL, 'Hyperion_LR@foxmail.com', 1638620737893, 1638620737893, 0, 0, 0, 'HyperionOJ', 0);

SET FOREIGN_KEY_CHECKS = 1;
