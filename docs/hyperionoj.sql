-- HyperionOJ.admin definition

CREATE TABLE `admin` (
  `id` bigint NOT NULL,
  `name` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `permission_level` int NOT NULL DEFAULT '2',
  `salt` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- HyperionOJ.admin_action definition

CREATE TABLE `admin_action` (
  `id` bigint NOT NULL,
  `admin_id` bigint NOT NULL,
  `admin_action` varchar(256) NOT NULL,
  `action_time` bigint NOT NULL,
  `action_status` int NOT NULL,
  KEY `admin_action_admin_id_IDX` (`admin_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- HyperionOJ.category definition

CREATE TABLE `category` (
  `id` bigint NOT NULL,
  `category_name` varchar(100) NOT NULL,
  `description` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- HyperionOJ.job_base definition

CREATE TABLE `job_base` (
  `id` bigint NOT NULL COMMENT '作业id',
  `name` varchar(100) NOT NULL COMMENT '作业名',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `owner_id` bigint NOT NULL COMMENT '所有者id',
  `status` varchar(100) NOT NULL DEFAULT 'new' COMMENT '作业状态（新建:new、启动中:start、运行中:run 、停止中:stoping、运行失败:fail、已停止stop、运行结束:end）',
  `start_time` bigint DEFAULT NULL COMMENT '作业开始运行时间',
  `create_time` bigint NOT NULL COMMENT '作业创建时间',
  `cpu_usage` int NOT NULL COMMENT 'cpu使用情况 单位：毫核',
  `mem_usage` int NOT NULL COMMENT '内存使用情况 单位：MiB',
  `flink_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'Flink WebUI地址',
  `monitor_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '作业对应的grafana监控url路径',
  `outer_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'Ingress外部暴露作业访问地址',
  KEY `job_base_owner_id_IDX` (`owner_id`) USING BTREE,
  KEY `job_base_status_IDX` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- HyperionOJ.job_working definition

CREATE TABLE `job_working` (
  `id` bigint NOT NULL,
  `job_id` bigint NOT NULL COMMENT 'job_base表id，用于标记jar文件目录',
  `type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'job类型，jar和sql两种',
  `flink_id` bigint DEFAULT NULL,
  `jar_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'jar包名称',
  `main_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '启动类名',
  `main_args` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '启动参数',
  `user_sql` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'sql语句',
  KEY `job_working_job_id_IDX` (`job_id`) USING BTREE,
  KEY `job_working_type_IDX` (`type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- HyperionOJ.problem definition

CREATE TABLE `problem` (
  `id` bigint NOT NULL,
  `title` varchar(100) NOT NULL,
  `problem_body` varchar(1024) NOT NULL,
  `problem_body_html` varchar(1024) NOT NULL,
  `problem_level` int NOT NULL DEFAULT '0',
  `case_number` int NOT NULL DEFAULT '0',
  `category_id` bigint DEFAULT NULL,
  `ac_number` int NOT NULL DEFAULT '0',
  `submit_number` bigint NOT NULL DEFAULT '0',
  `comment_number` bigint NOT NULL DEFAULT '0',
  `run_time` int NOT NULL DEFAULT '1000',
  `run_memory` int NOT NULL DEFAULT '128',
  KEY `problem_category_id_IDX` (`category_id`) USING BTREE,
  KEY `problem_problem_level_IDX` (`problem_level`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- HyperionOJ.problem_comment definition

CREATE TABLE `problem_comment` (
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
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `点赞数量` (`id`,`support_number`,`is_delete`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1473209202153680898 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;


-- HyperionOJ.problem_submit definition

CREATE TABLE `problem_submit` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '提交代码id',
  `problem_id` bigint NOT NULL COMMENT '哪题的代码',
  `author_id` bigint NOT NULL COMMENT '提交用户id',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名称',
  `code_lang` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '代码语言',
  `code_body` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '提交的代码',
  `case_number` int DEFAULT NULL,
  `run_time` int NOT NULL COMMENT '运行多少毫秒(ms)',
  `run_memory` int NOT NULL COMMENT '运行使用多少内存(mb)',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '代码运行结果',
  `create_time` bigint NOT NULL COMMENT '提交时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `getEveryday` (`author_id`,`status`,`create_time`) USING BTREE,
  KEY `problem_submit_problem_id_IDX` (`problem_id`) USING BTREE,
  KEY `problem_submit_code_lang_IDX` (`code_lang`) USING BTREE,
  KEY `problem_submit_status_IDX` (`status`) USING BTREE,
  KEY `problem_submit_create_time_IDX` (`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1634015317175119874 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;


-- HyperionOJ.problem_tag definition

CREATE TABLE `problem_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `problem_id` bigint NOT NULL COMMENT '文章id',
  `tag_id` bigint NOT NULL COMMENT '标签id',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `problem_tag_problem_id_IDX` (`problem_id`) USING BTREE,
  KEY `problem_tag_tag_id_IDX` (`tag_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1633446146331942915 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;


-- HyperionOJ.tag definition

CREATE TABLE `tag` (
  `id` bigint NOT NULL,
  `tag_name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- HyperionOJ.`user` definition

CREATE TABLE `user` (
  `id` bigint NOT NULL,
  `username` varchar(100) NOT NULL,
  `avatar` varchar(256) NOT NULL,
  `password` varchar(256) NOT NULL,
  `mail` varchar(64) NOT NULL,
  `create_time` bigint NOT NULL,
  `last_login` bigint NOT NULL,
  `problem_ac_number` int NOT NULL DEFAULT '0',
  `problem_submit_ac_number` int NOT NULL DEFAULT '0',
  `problem_submit_number` int NOT NULL,
  `salt` varchar(100) NOT NULL,
  `status` tinyint NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;