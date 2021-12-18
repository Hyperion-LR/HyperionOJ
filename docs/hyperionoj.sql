/*
 Navicat Premium Data Transfer

 Source Server         : Hyperion
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : localhost:3306
 Source Schema         : hyperionoj

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 18/12/2021 17:32:30
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
  `permission_level` int NOT NULL COMMENT '权限等级(1:根管理员 2:管理员 3:老师)',
  `salt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '加密盐(加密密码)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1234 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_admin
-- ----------------------------
INSERT INTO `oj_admin` VALUES (1, 'Hyperion', '083f3977344768987d0e1d315b358f90', 1, 'HyperionOJ');

-- ----------------------------
-- Table structure for oj_admin_action
-- ----------------------------
DROP TABLE IF EXISTS `oj_admin_action`;
CREATE TABLE `oj_admin_action`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `admin_id` bigint NOT NULL COMMENT '管理员id',
  `admin_action` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '干了啥',
  `action_time` bigint NOT NULL COMMENT '什么时候',
  `action_status` int NOT NULL COMMENT '状态(0:成功 1:撤销)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1470390324246024194 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_admin_action
-- ----------------------------
INSERT INTO `oj_admin_action` VALUES (1470386700480585729, 1, '/add/problem/category', 1639402482523, 0);
INSERT INTO `oj_admin_action` VALUES (1470387034431070209, 1, '/add/problem/category', 1639402562133, 0);
INSERT INTO `oj_admin_action` VALUES (1470387178056622081, 1, '/add/problem/category', 1639402596386, 0);
INSERT INTO `oj_admin_action` VALUES (1470387437419798529, 1, '/add/problem/category', 1639402658219, 0);
INSERT INTO `oj_admin_action` VALUES (1470387894615711745, 1, '/delete/problem/category', 1639402767231, 0);
INSERT INTO `oj_admin_action` VALUES (1470388090292604930, 1, '/delete/problem/category', 1639402813840, 0);
INSERT INTO `oj_admin_action` VALUES (1470388149033832449, 1, '/delete/problem/category', 1639402827888, 0);
INSERT INTO `oj_admin_action` VALUES (1470389250994933762, 1, '/add/problem', 1639403090607, 0);
INSERT INTO `oj_admin_action` VALUES (1470389547859382273, 1, '/update/problem', 1639403161391, 0);
INSERT INTO `oj_admin_action` VALUES (1470390324246024194, 1, '/delete/problem', 1639403346454, 0);

-- ----------------------------
-- Table structure for oj_article
-- ----------------------------
DROP TABLE IF EXISTS `oj_article`;
CREATE TABLE `oj_article`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '文章id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文章标题',
  `author_id` bigint NOT NULL COMMENT '作者id',
  `summary` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文章简介',
  `body_id` bigint NULL DEFAULT NULL COMMENT '内容id',
  `category_id` bigint NOT NULL COMMENT '分类id',
  `create_time` bigint NOT NULL COMMENT '创建时间',
  `comment_count` int NOT NULL COMMENT '评论数量',
  `view_count` int NOT NULL COMMENT '阅读量',
  `weight` int NOT NULL COMMENT '是否置顶',
  `is_solution` int NOT NULL COMMENT '是否为题解(0:不是 1:是)',
  `problem_id` bigint NOT NULL COMMENT '题目id',
  `is_delete` int NOT NULL COMMENT '是否删除(0:正常 1:删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1471453432829939714 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_article
-- ----------------------------
INSERT INTO `oj_article` VALUES (1, '测试文章', 15570357290, '这是...', 1, 1, 1, 3, 0, 0, 0, 0, 0);
INSERT INTO `oj_article` VALUES (1471319518832435201, 'test title', 15570357290, 'test', 1471319518899544067, 1, 1639624883755, 0, 0, 0, 0, 1, 0);
INSERT INTO `oj_article` VALUES (1471321831475212290, 'test title', 15570357290, 'test', 1471321831525543938, 1, 1639625435108, 0, 0, 0, 0, 1, 0);
INSERT INTO `oj_article` VALUES (1471452721505263617, 'test title', 15570357290, 'test', 1471452721538818050, 1, 1639656641725, 0, 0, 0, 0, 1, 0);
INSERT INTO `oj_article` VALUES (1471452997406580737, 'test title', 15570357290, 'test', 1471453082324459522, 1, 1639656691101, 0, 0, 0, 0, 1, 0);
INSERT INTO `oj_article` VALUES (1471453432829939714, 'test title', 15570357290, 'test', 1471453432829939716, 1, 1639656811317, 0, 0, 0, 0, 1, 0);

-- ----------------------------
-- Table structure for oj_article_article_tag
-- ----------------------------
DROP TABLE IF EXISTS `oj_article_article_tag`;
CREATE TABLE `oj_article_article_tag`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `article_id` bigint NOT NULL COMMENT '文章id',
  `tag_id` bigint NOT NULL COMMENT '标签id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1471453432829939715 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_article_article_tag
-- ----------------------------
INSERT INTO `oj_article_article_tag` VALUES (1471319518899544066, 1, 1);
INSERT INTO `oj_article_article_tag` VALUES (1471321831475212291, 1471321831475212290, 1);
INSERT INTO `oj_article_article_tag` VALUES (1471452721505263618, 1471452721505263617, 1);
INSERT INTO `oj_article_article_tag` VALUES (1471453024233349122, 1471452997406580737, 1);
INSERT INTO `oj_article_article_tag` VALUES (1471453432829939715, 1471453432829939714, 1);

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
) ENGINE = InnoDB AUTO_INCREMENT = 1471453432829939716 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_article_body
-- ----------------------------
INSERT INTO `oj_article_body` VALUES (1, '这是测试文字', '<p>这是测试文字</p>', 1);
INSERT INTO `oj_article_body` VALUES (1471319518899544067, '这是一段测试文字', '<p>这是一段测试文字</p>', 1471319518832435201);
INSERT INTO `oj_article_body` VALUES (1471321831525543938, '这是一段测试文字', '<p>这是一段测试文字</p>', 1471321831475212290);
INSERT INTO `oj_article_body` VALUES (1471452721538818050, '这是一段测试文字', '<p>这是一段测试文字</p>', 1471452721505263617);
INSERT INTO `oj_article_body` VALUES (1471453082324459522, '这是一段测试文字', '<p>这是一段测试文字</p>', 1471452997406580737);
INSERT INTO `oj_article_body` VALUES (1471453432829939716, '这是一段测试文字', '<p>这是一段测试文字</p>', 1471453432829939714);

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
  `is_delete` int NOT NULL COMMENT '是否删除(0:正常 1:删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1471459657550290947 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_article_comment
-- ----------------------------
INSERT INTO `oj_article_comment` VALUES (1471441486713868289, '测试内容', 1639653963146, 1, 15570357290, 0, 15570357290, '1', 0);
INSERT INTO `oj_article_comment` VALUES (1471457812790571009, '测试内容', 1639657855583, 1, 15570357290, 0, 15570357290, '1', 0);
INSERT INTO `oj_article_comment` VALUES (1471459657550290946, '测试内容', 1639658295407, 1, 15570357290, 0, 15570357290, '1', 0);

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_contest_user
-- ----------------------------

-- ----------------------------
-- Table structure for oj_page_category
-- ----------------------------
DROP TABLE IF EXISTS `oj_page_category`;
CREATE TABLE `oj_page_category`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '分类名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '分类描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_page_category
-- ----------------------------
INSERT INTO `oj_page_category` VALUES (1, 'test', '测试分类');

-- ----------------------------
-- Table structure for oj_page_tag
-- ----------------------------
DROP TABLE IF EXISTS `oj_page_tag`;
CREATE TABLE `oj_page_tag`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '标签id',
  `tag_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标签名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_page_tag
-- ----------------------------
INSERT INTO `oj_page_tag` VALUES (1, '测试标签');
INSERT INTO `oj_page_tag` VALUES (2, 'test2');

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
) ENGINE = InnoDB AUTO_INCREMENT = 1470389250931982337 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_problem
-- ----------------------------
INSERT INTO `oj_problem` VALUES (1, 'test', 1, 1, 1, 2, 2, 0, 1, 1000, 256, 1);
INSERT INTO `oj_problem` VALUES (2, 'test2', 2, 1, 1, 0, 0, 0, 0, 1000, 256, 1);

-- ----------------------------
-- Table structure for oj_problem_body
-- ----------------------------
DROP TABLE IF EXISTS `oj_problem_body`;
CREATE TABLE `oj_problem_body`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '题目描述id',
  `problem_body` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '题目描述(md)',
  `problem_body_html` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '题目描述(html)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1470389250806153218 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_problem_body
-- ----------------------------
INSERT INTO `oj_problem_body` VALUES (1, '这是测试', '<p>这是测试</p>');
INSERT INTO `oj_problem_body` VALUES (2, '这是测试', '<p>这是测试</p>');

-- ----------------------------
-- Table structure for oj_problem_comment
-- ----------------------------
DROP TABLE IF EXISTS `oj_problem_comment`;
CREATE TABLE `oj_problem_comment`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '题目评论id',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论内容',
  `create_time` bigint NOT NULL COMMENT '评论时间',
  `problem_id` bigint NOT NULL COMMENT '哪一个题目的评论',
  `author_id` bigint NOT NULL COMMENT '评论者id',
  `parent_id` bigint NOT NULL COMMENT '是那个评论下的',
  `to_uid` bigint NOT NULL COMMENT '回复谁(id)的',
  `level` int NOT NULL COMMENT '楼层(0:普通楼 1:楼中楼)',
  `support_number` int NOT NULL COMMENT '点赞数',
  `is_delete` int NOT NULL COMMENT '是否删除(0:正常 1:删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1471436141732007938 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_problem_comment
-- ----------------------------
INSERT INTO `oj_problem_comment` VALUES (1470694184046977025, '评论测试3', 1639475792316, 1, 15570357290, 0, 15570357290, 0, 4209, 0);
INSERT INTO `oj_problem_comment` VALUES (1471036857794617345, '评论测试3', 1639557492091, 1, 15570357290, 0, 15570357290, 0, 0, 0);
INSERT INTO `oj_problem_comment` VALUES (1471036863888941058, '评论测试3', 1639557493557, 1, 15570357290, 0, 15570357290, 0, 0, 0);
INSERT INTO `oj_problem_comment` VALUES (1471038806384402434, '评论测试3', 1639557956671, 1, 15570357290, 0, 15570357290, 0, 0, 0);
INSERT INTO `oj_problem_comment` VALUES (1471038960017563650, '评论测试3', 1639557993319, 1, 15570357290, 0, 15570357290, 0, 0, 0);
INSERT INTO `oj_problem_comment` VALUES (1471039140167114754, '评论测试3', 1639558036270, 1, 15570357290, 0, 15570357290, 0, 0, 0);
INSERT INTO `oj_problem_comment` VALUES (1471040114428436481, '评论测试3', 1639558268551, 1, 15570357290, 0, 15570357290, 0, 0, 0);
INSERT INTO `oj_problem_comment` VALUES (1471040527894482946, '评论测试3', 1639558367111, 1, 15570357290, 0, 15570357290, 0, 0, 0);
INSERT INTO `oj_problem_comment` VALUES (1471322339334123521, '评论测试3', 1639625556214, 1, 15570357290, 0, 15570357290, 0, 0, 0);
INSERT INTO `oj_problem_comment` VALUES (1471436141732007937, '评论测试3', 1639652688799, 1, 15570357290, 0, 0, 0, 0, 0);

-- ----------------------------
-- Table structure for oj_problem_problem_tag
-- ----------------------------
DROP TABLE IF EXISTS `oj_problem_problem_tag`;
CREATE TABLE `oj_problem_problem_tag`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `problem_id` bigint NOT NULL COMMENT '文章id',
  `tag_id` bigint NOT NULL COMMENT '标签id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_problem_problem_tag
-- ----------------------------
INSERT INTO `oj_problem_problem_tag` VALUES (1, 1, 1);
INSERT INTO `oj_problem_problem_tag` VALUES (2, 1, 2);
INSERT INTO `oj_problem_problem_tag` VALUES (3, 2, 2);

-- ----------------------------
-- Table structure for oj_problem_submit
-- ----------------------------
DROP TABLE IF EXISTS `oj_problem_submit`;
CREATE TABLE `oj_problem_submit`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '提交代码id',
  `problem_id` bigint NOT NULL COMMENT '哪题的代码',
  `author_id` bigint NOT NULL COMMENT '提交用户id',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名称',
  `code_lang` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '代码语言',
  `code_body` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '提交的代码',
  `run_time` int NOT NULL COMMENT '运行多少毫秒(ms)',
  `run_memory` int NOT NULL COMMENT '运行使用多少内存(mb)',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '代码运行结果',
  `create_time` bigint NOT NULL COMMENT '提交时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1469934295938920449 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_problem_submit
-- ----------------------------
INSERT INTO `oj_problem_submit` VALUES (1469932397286551554, 1, 15570357290, '冰箱的主人', 'java', 'import java.util.Scanner;\n/**\n * @author Hyperion\n * @date 2021/11/28\n */\npublic class Main {\npublic static void main(String[] args) {\nString name = \"Hyperion\";\nScanner in = new Scanner(System.in);\n int[] abc = new int[10000000];\nint t = 0, a = 0, b = 0;\nSystem.out.println(name);\nt = in.nextInt();\nwhile(t-- > 0){\na = in.nextInt();\nb = in.nextInt();\nSystem.out.println(a + b);\n}\nSystem.out.println(name);\n}\n}', 139, 0, 'AC', 1639294160967);
INSERT INTO `oj_problem_submit` VALUES (1469932502085431298, 1, 15570357290, '冰箱的主人', 'java', 'import java.util.Scanner;\n/**\n * @author Hyperion\n * @date 2021/11/28\n */\npublic class Main {\npublic static void main(String[] args) {\nString name = \"Hyperion\";\nScanner in = new Scanner(System.in);\n int[] abc = new int[10000000];\nint t = 0, a = 0, b = 0;\nSystem.out.println(name);\nt = in.nextInt();\nwhile(t++ > 0){\na = in.nextInt();\nb = in.nextInt();\nSystem.out.println(a + b);\n}\nSystem.out.println(name);\n}\n}', 139, 0, 'AC', 1639294193185);
INSERT INTO `oj_problem_submit` VALUES (1469932688937480194, 1, 15570357290, '冰箱的主人', 'java', 'import java.util.Scanner;\n/**\n * @author Hyperion\n * @date 2021/11/28\n */\npublic class Main {\npublic static void main(String[] args) {\nString name = \"Hyperion\";\nScanner in = new Scanner(System.in);\n int[] abc = new int[10000000];\nint t = 0, a = 0, b = 0;\nSystem.out.println(name);\nt = in.nextInt();\nwhile(t-- > 0){\na = in.nextInt();\nb = in.nextInt();\nSystem.out.println(a + b);\n}\nSystem.out.println(name)\n}\n}', 126, 0, 'AC', 1639294237741);
INSERT INTO `oj_problem_submit` VALUES (1469932890868051970, 1, 15570357290, '冰箱的主人', 'java', 'import java.util.Scanner;\n/**\n * @author Hyperion\n * @date 2021/11/28\n */\npublic class Main {\npublic static void main(String[] args) {\nString name = \"Hyperion\";\nScanner in = new Scanner(System.in);\n int[] abc = new int[10000000];\nint t = 0, a = 0, b = 0;\nSystem.out.println(name);\nt = in.nextInt();\nwhile(t-- > 0){\na = in.nextInt();\nb = in.nextInt();\nSystem.out.println(a + b);\n}\n//System.out.println(name);\n}\n}', 134, 0, 'WA', 1639294285873);
INSERT INTO `oj_problem_submit` VALUES (1469932994488332290, 1, 15570357290, '冰箱的主人', 'java', 'import java.util.Scanner;\n/**\n * @author Hyperion\n * @date 2021/11/28\n */\npublic class Main {\npublic static void main(String[] args) {\nString name = \"Hyperion\";\nScanner in = new Scanner(System.in);\n int[] abc = new int[10000000];\nint t = 0, a = 0, b = 0;\nSystem.out.println(name);\nt = in.nextInt();\nwhile(t-- > 0){\na = in.nextInt();\nb = in.nextInt();\nSystem.out.println(a + b);\n}\nSystem.out.println(name)\n}\n}', 116, 0, 'WA', 1639294310575);
INSERT INTO `oj_problem_submit` VALUES (1469933926999552002, 1, 15570357290, '冰箱的主人', 'java', 'import java.util.Scanner;\n/**\n * @author Hyperion\n * @date 2021/11/28\n */\npublic class Main {\npublic static void main(String[] args) {\nString name = \"Hyperion\";\nScanner in = new Scanner(System.in);\n int[] abc = new int[10000000];\nint t = 0, a = 0, b = 0;\nSystem.out.println(name);\nt = in.nextInt();\nwhile(t-- > 0){\na = in.nextInt();\nb = in.nextInt();\nSystem.out.println(a + b);\n}\nSystem.out.println(name);\n}\n}', 120, 0, 'AC', 1639294521051);
INSERT INTO `oj_problem_submit` VALUES (1469933996805353473, 1, 15570357290, '冰箱的主人', 'java', 'import java.util.Scanner;\n/**\n * @author Hyperion\n * @date 2021/11/28\n */\npublic class Main {\npublic static void main(String[] args) {\nString name = \"Hyperion\";\nScanner in = new Scanner(System.in);\n int[] abc = new int[10000000];\nint t = 0, a = 0, b = 0;\nSystem.out.println(name);\nt = in.nextInt();\nwhile(t-- > 0){\na = in.nextInt();\nb = in.nextInt();\nSystem.out.println(a + b);\n}\nSystem.out.println(name)\n}\n}', 126, 0, 'AC', 1639294545628);
INSERT INTO `oj_problem_submit` VALUES (1469934088903880705, 1, 15570357290, '冰箱的主人', 'java', 'import java.util.Scanner;\n/**\n * @author Hyperion\n * @date 2021/11/28\n */\npublic class Main {\npublic static void main(String[] args) {\nString name = \"Hyperion\";\nScanner in = new Scanner(System.in);\n int[] abc = new int[10000000];\nint t = 0, a = 0, b = 0;\nSystem.out.println(name);\nt = in.nextInt();\nwhile(t-- > 0){\na = in.nextInt();\nb = in.nextInt();\nSystem.out.println(a + b);\n}\nSystem.out.println(name)\n}\n}', 0, 0, 'CE', 1639294571520);
INSERT INTO `oj_problem_submit` VALUES (1469934165613506561, 1, 15570357290, '冰箱的主人', 'java', 'import java.util.Scanner;\n/**\n * @author Hyperion\n * @date 2021/11/28\n */\npublic class Main {\npublic static void main(String[] args) {\nString name = \"Hyperion\";\nScanner in = new Scanner(System.in);\n int[] abc = new int[10000000];\nint t = 0, a = 0, b = 0;\nSystem.out.println(name);\nt = in.nextInt();\nwhile(t-- > 0){\na = in.nextInt();\nb = in.nextInt();\nSystem.out.println(a + b);\n}\nSystem.out.println(name);\n}\n}', 0, 0, 'CE', 1639294589797);
INSERT INTO `oj_problem_submit` VALUES (1469934295938920449, 1, 15570357290, '冰箱的主人', 'java', 'import java.util.Scanner;\n/**\n * @author Hyperion\n * @date 2021/11/28\n */\npublic class Main {\npublic static void main(String[] args) {\nString name = \"Hyperion\";\nScanner in = new Scanner(System.in);\n int[] abc = new int[10000000];\nint t = 0, a = 0, b = 0;\nSystem.out.println(name);\nt = in.nextInt();\nwhile(t++ > 0){\na = in.nextInt();\nb = in.nextInt();\nSystem.out.println(a + b);\n}\nSystem.out.println(name);\n}\n}', 151, 0, 'AC', 1639294620868);
INSERT INTO `oj_problem_submit` VALUES (1472113584614805506, 1, 15570357290, '冰箱的主人', 'java', 'import java.util.Scanner;\n/**\n * @author Hyperion\n * @date 2021/11/28\n */\npublic class Main {\npublic static void main(String[] args) {\nString name = \"Hyperion\";\nScanner in = new Scanner(System.in);\n int[] abc = new int[10000000];\nint t = 0, a = 0, b = 0;\nSystem.out.println(name);\nt = in.nextInt();\nwhile(t-- > 0){\na = in.nextInt();\nb = in.nextInt();\nSystem.out.println(a + b);\n}\nSystem.out.println(name);\n}\n}', 129, 0, 'AC', 1639814203768);
INSERT INTO `oj_problem_submit` VALUES (1472126314742784001, 1, 15570357290, '冰箱的主人', 'java', 'import java.util.Scanner;\n/**\n * @author Hyperion\n * @date 2021/11/28\n */\npublic class Main {\npublic static void main(String[] args) {\nString name = \"Hyperion\";\nScanner in = new Scanner(System.in);\n int[] abc = new int[10000000];\nint t = 0, a = 0, b = 0;\nSystem.out.println(name);\nt = in.nextInt();\nwhile(t-- > 0){\na = in.nextInt();\nb = in.nextInt();\nSystem.out.println(a + b);\n}\nSystem.out.println(name);\n}\n}', 137, 0, 'AC', 1639817220000);

-- ----------------------------
-- Table structure for oj_sys_class
-- ----------------------------
DROP TABLE IF EXISTS `oj_sys_class`;
CREATE TABLE `oj_sys_class`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '班级id',
  `teacher_id` bigint NOT NULL COMMENT '教师id',
  `teacher_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '教师名字',
  `course_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '课程名称',
  `academy` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学院',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_sys_class
-- ----------------------------
INSERT INTO `oj_sys_class` VALUES (1472029371140030465, 1, 'Hyperion', '测试课程', '测试学院');
INSERT INTO `oj_sys_class` VALUES (1472059833363439617, 1, 'Hyperion', '测试课程2', '测试学院');

-- ----------------------------
-- Table structure for oj_sys_class_student
-- ----------------------------
DROP TABLE IF EXISTS `oj_sys_class_student`;
CREATE TABLE `oj_sys_class_student`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `class_id` bigint NOT NULL COMMENT '班级id',
  `student_number` bigint NOT NULL COMMENT '学生学号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_sys_class_student
-- ----------------------------
INSERT INTO `oj_sys_class_student` VALUES (1472053590628421633, 1472029371140030465, 2019213037);
INSERT INTO `oj_sys_class_student` VALUES (1472060239732776962, 1472059833363439617, 2019213037);

-- ----------------------------
-- Table structure for oj_sys_homework
-- ----------------------------
DROP TABLE IF EXISTS `oj_sys_homework`;
CREATE TABLE `oj_sys_homework`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `class_id` bigint NOT NULL COMMENT '班级id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '作业名称',
  `start_time` bigint NOT NULL COMMENT '开始时间',
  `end_time` bigint NOT NULL COMMENT '结束时间',
  `teacher_id` bigint NOT NULL COMMENT '布置作业的老师id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_sys_homework
-- ----------------------------
INSERT INTO `oj_sys_homework` VALUES (1472041160938569730, 1, '第一次测试作业', 1639800000000, 1639800000000, 1);
INSERT INTO `oj_sys_homework` VALUES (1472041215334498305, 1, '第二次测试作业', 1639800000000, 1639800000000, 1);
INSERT INTO `oj_sys_homework` VALUES (1472041260456820738, 1, '第二次测试作业', 1639800000000, 1639800000000, 1);
INSERT INTO `oj_sys_homework` VALUES (1472041681653022722, 1, '第二次测试作业', 1639800000000, 1639800000000, 1);
INSERT INTO `oj_sys_homework` VALUES (1472041764129816578, 1, '第二次测试作业', 1639800000000, 1639800000000, 1);

-- ----------------------------
-- Table structure for oj_sys_homework_problem
-- ----------------------------
DROP TABLE IF EXISTS `oj_sys_homework_problem`;
CREATE TABLE `oj_sys_homework_problem`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `homework_id` bigint NOT NULL COMMENT '作业id',
  `problem_id` bigint NOT NULL COMMENT '题目id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_sys_homework_problem
-- ----------------------------
INSERT INTO `oj_sys_homework_problem` VALUES (1472037698213482498, 1472037698054098945, 1);
INSERT INTO `oj_sys_homework_problem` VALUES (1472037778203054082, 1472037778135945217, 1);
INSERT INTO `oj_sys_homework_problem` VALUES (1472038070395047937, 1472037974832025602, 1);
INSERT INTO `oj_sys_homework_problem` VALUES (1472038371910905857, 1472038371881545730, 1);
INSERT INTO `oj_sys_homework_problem` VALUES (1472038371910905858, 1472038371881545730, 2);
INSERT INTO `oj_sys_homework_problem` VALUES (1472038447622287361, 1472038447555178498, 1);
INSERT INTO `oj_sys_homework_problem` VALUES (1472038447622287362, 1472038447555178498, 2);
INSERT INTO `oj_sys_homework_problem` VALUES (1472038603977551873, 1472038603918831617, 1);
INSERT INTO `oj_sys_homework_problem` VALUES (1472038603977551874, 1472038603918831617, 2);
INSERT INTO `oj_sys_homework_problem` VALUES (1472039548560031746, 1472039548547448834, 1);
INSERT INTO `oj_sys_homework_problem` VALUES (1472039548560031747, 1472039548547448834, 2);
INSERT INTO `oj_sys_homework_problem` VALUES (1472039636376174595, 1472039636376174594, 1);
INSERT INTO `oj_sys_homework_problem` VALUES (1472039636443283458, 1472039636376174594, 2);
INSERT INTO `oj_sys_homework_problem` VALUES (1472039655279902722, 1472039655212793857, 1);
INSERT INTO `oj_sys_homework_problem` VALUES (1472039655279902723, 1472039655212793857, 2);
INSERT INTO `oj_sys_homework_problem` VALUES (1472039666399002626, 1472039666399002625, 1);
INSERT INTO `oj_sys_homework_problem` VALUES (1472039666399002627, 1472039666399002625, 2);
INSERT INTO `oj_sys_homework_problem` VALUES (1472039694714748930, 1472039694714748929, 1);
INSERT INTO `oj_sys_homework_problem` VALUES (1472039694714748931, 1472039694714748929, 2);
INSERT INTO `oj_sys_homework_problem` VALUES (1472039701752791043, 1472039701752791042, 1);
INSERT INTO `oj_sys_homework_problem` VALUES (1472039701752791044, 1472039701752791042, 2);
INSERT INTO `oj_sys_homework_problem` VALUES (1472039704692998145, 1472039704625889282, 1);
INSERT INTO `oj_sys_homework_problem` VALUES (1472039704692998146, 1472039704625889282, 2);
INSERT INTO `oj_sys_homework_problem` VALUES (1472039707381547010, 1472039707314438146, 1);
INSERT INTO `oj_sys_homework_problem` VALUES (1472039707381547011, 1472039707314438146, 2);
INSERT INTO `oj_sys_homework_problem` VALUES (1472039709617111042, 1472039709550002178, 1);
INSERT INTO `oj_sys_homework_problem` VALUES (1472039709617111043, 1472039709550002178, 2);
INSERT INTO `oj_sys_homework_problem` VALUES (1472041161068593154, 1472041160938569730, 1);
INSERT INTO `oj_sys_homework_problem` VALUES (1472041161068593155, 1472041160938569730, 2);
INSERT INTO `oj_sys_homework_problem` VALUES (1472041215401607170, 1472041215334498305, 1);
INSERT INTO `oj_sys_homework_problem` VALUES (1472041215401607171, 1472041215334498305, 2);
INSERT INTO `oj_sys_homework_problem` VALUES (1472041260523929601, 1472041260456820738, 1);
INSERT INTO `oj_sys_homework_problem` VALUES (1472041260523929602, 1472041260456820738, 2);
INSERT INTO `oj_sys_homework_problem` VALUES (1472041681783046146, 1472041681653022722, 1);
INSERT INTO `oj_sys_homework_problem` VALUES (1472041681783046147, 1472041681653022722, 2);
INSERT INTO `oj_sys_homework_problem` VALUES (1472041764129816579, 1472041764129816578, 1);
INSERT INTO `oj_sys_homework_problem` VALUES (1472041764129816580, 1472041764129816578, 2);

-- ----------------------------
-- Table structure for oj_sys_homework_submit
-- ----------------------------
DROP TABLE IF EXISTS `oj_sys_homework_submit`;
CREATE TABLE `oj_sys_homework_submit`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `homework_id` bigint NOT NULL COMMENT '作业id',
  `problem_id` bigint NOT NULL COMMENT '题目id',
  `student_id` bigint NOT NULL COMMENT '学生id',
  `submit_id` bigint NOT NULL COMMENT '提交id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oj_sys_homework_submit
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
INSERT INTO `oj_sys_user` VALUES (15570357290, '冰箱的主人', '...', 'e56f47a5d842342487be1fee4ae0752d', '2019213037', 'Hyperion_LR@foxmail.com', 1638620737893, 1638620737893, 1, 13, 9, 'HyperionOJ', 0);

SET FOREIGN_KEY_CHECKS = 1;
