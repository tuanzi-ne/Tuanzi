-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自动编号',
  `username` varchar(50) NOT NULL COMMENT '账号',
  `realname` varchar(50) DEFAULT NULL COMMENT '用户真实姓名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `salt` varchar(50) DEFAULT NULL COMMENT '加盐',
  `phone` varchar(100) DEFAULT NULL COMMENT '手机',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `dept_id` int(11) DEFAULT NULL COMMENT '部门',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态,1表示正常; 0表示禁用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_name` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '某某某', 'e1153123d7d180ceeb820d577ff119876678732a68eef4e6ffc0b1f06a01f91b', 'YzcmCZNvbXocrsz9dm8e', '18812345678', 'pan@bigcat.com', '1', '1', '2018-03-09 00:00:00', '2018-04-12 10:29:33');
INSERT INTO `sys_user` VALUES ('2', 'pan', '某某某', 'e1153123d7d180ceeb820d577ff119876678732a68eef4e6ffc0b1f06a01f91b', 'YzcmCZNvbXocrsz9dm8e', '18812345678', 'pan@bigcat.com', '4', '1', '2018-05-01 10:25:13', '2018-05-01 10:25:13');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自动编号',
  `role_name` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `role_desc` varchar(200) DEFAULT NULL COMMENT '角色描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '管理员', '管理员角色', '2018-03-19 15:28:38', '2018-03-30 15:32:50');
INSERT INTO `sys_role` VALUES ('2', '普通用户', '普通用户角色', '2018-03-19 15:28:38', '2018-03-30 15:32:59');

-- ----------------------------
-- Table structure for sys_res
-- ----------------------------
DROP TABLE IF EXISTS `sys_res`;
CREATE TABLE `sys_res` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自动编号',
  `res_name` varchar(50) DEFAULT NULL COMMENT '资源名称',
  `res_desc` varchar(200) DEFAULT NULL COMMENT '资源描述',
  `res_url` varchar(200) DEFAULT NULL COMMENT '资源URL',
  `res_type` tinyint(1) DEFAULT NULL COMMENT '资源类型, 0表示目录，1表示菜单；2表示按钮',
  `res_perms` varchar(200) DEFAULT NULL COMMENT '资源授权标识，如：user:add:list',
  `res_icon` varchar(50) DEFAULT NULL COMMENT '资源图标',
  `parent_id` int(11) DEFAULT NULL COMMENT '父资源ID，一级资源为0',
  `order_no` int(11) DEFAULT NULL COMMENT '排序号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统资源';

-- ----------------------------
-- Records of sys_res
-- ----------------------------
INSERT INTO `sys_res` VALUES ('1', '系统管理', '管理系统', '', '0', '', 'fa fa-desktop', '0', '1', '2018-03-28 10:53:46', '2018-03-28 18:50:48');
INSERT INTO `sys_res` VALUES ('2', '用户管理', '用户管理模块', 'sys/user', '1', '', 'fa fa-user', '1', '1', '2018-03-28 10:55:18', '2018-04-13 11:12:42');
INSERT INTO `sys_res` VALUES ('3', '查询', '查询按钮', '', '2', 'sys:user:list', '', '2', '3', '2018-03-28 11:04:36', '2018-04-09 16:41:23');
INSERT INTO `sys_res` VALUES ('4', '资源管理', '资源管理模块', 'sys/res', '1', '', 'fa fa-list', '1', '3', '2018-03-29 10:23:09', '2018-04-13 11:13:08');
INSERT INTO `sys_res` VALUES ('5', '角色管理', '角色管理模块', 'sys/role', '1', '', 'fa fa-sitemap', '1', '2', '2018-03-29 15:22:31', '2018-04-13 11:12:32');
INSERT INTO `sys_res` VALUES ('6', '新增', '新增按钮', '', '2', 'sys:user:add', '', '2', '5', '2018-03-29 17:24:08', '2018-03-29 17:25:41');
INSERT INTO `sys_res` VALUES ('7', '部门管理', '部门管理模块', 'sys/dept', '1', '', 'fa fa-user-circle', '1', '4', '2018-03-29 17:36:18', '2018-04-13 11:12:59');
INSERT INTO `sys_res` VALUES ('8', '系统监控', '监控系统', '', '0', '', 'fa fa-video-camera', '0', '10', '2018-04-09 10:06:04', '2018-04-09 10:59:25');
INSERT INTO `sys_res` VALUES ('9', '图表管理', '统计图表', '', '0', '', 'fa fa-bar-chart-o', '0', '12', '2018-04-09 11:21:09', '2018-04-12 10:19:20');
INSERT INTO `sys_res` VALUES ('10', '系统日志', '系统日志', 'sys/log', '1', '', 'fa fa-file-text-o', '8', '11', '2018-04-09 15:54:45', '2018-04-10 20:13:38');
INSERT INTO `sys_res` VALUES ('11', '编辑', '编辑按钮', '', '2', 'sys:user:edit', '', '2', '6', '2018-04-09 16:41:09', '2018-04-09 16:41:38');
INSERT INTO `sys_res` VALUES ('12', '删除', '删除按钮', '', '2', 'sys:user:delete', '', '2', '7', '2018-04-09 16:42:07', '2018-04-09 16:42:14');
INSERT INTO `sys_res` VALUES ('13', '查询', '查询按钮', null, '2', 'sys:res:list', null, '4', '1', '2018-03-28 10:53:46', '2018-03-28 10:53:46');
INSERT INTO `sys_res` VALUES ('14', '新增', '新增按钮', null, '2', 'sys:res:add', null, '4', '2', '2018-03-28 10:53:46', '2018-03-28 10:53:46');
INSERT INTO `sys_res` VALUES ('15', '编辑', '编辑按钮', null, '2', 'sys:res:edit', null, '4', '3', '2018-03-28 10:53:46', '2018-03-28 10:53:46');
INSERT INTO `sys_res` VALUES ('17', '删除', '删除按钮', '', '2', 'sys:res:delete', '', '4', '4', '2018-04-09 17:12:16', '2018-04-09 17:12:16');
INSERT INTO `sys_res` VALUES ('18', '查询', '查询按钮', '', '2', 'sys:role:list', '', '5', '1', '2018-03-28 11:04:36', '2018-04-09 16:41:23');
INSERT INTO `sys_res` VALUES ('19', '新增', '新增按钮', '', '2', 'sys:role:add', '', '5', '2', '2018-03-29 17:24:08', '2018-03-29 17:25:41');
INSERT INTO `sys_res` VALUES ('20', '编辑', '编辑按钮', '', '2', 'sys:role:edit', '', '5', '3', '2018-04-09 16:41:09', '2018-04-09 16:41:38');
INSERT INTO `sys_res` VALUES ('21', '删除', '删除按钮', '', '2', 'sys:role:delete', '', '5', '4', '2018-04-09 16:42:07', '2018-04-09 16:42:14');
INSERT INTO `sys_res` VALUES ('22', '查询', '查询按钮', '', '2', 'sys:dept:list', '', '7', '1', '2018-03-28 11:04:36', '2018-04-09 17:16:39');
INSERT INTO `sys_res` VALUES ('23', '新增', '新增按钮', '', '2', 'sys:dept:add', '', '7', '2', '2018-03-29 17:24:08', '2018-03-29 17:25:41');
INSERT INTO `sys_res` VALUES ('24', '编辑', '编辑按钮', '', '2', 'sys:dept:edit', '', '7', '3', '2018-04-09 16:41:09', '2018-04-09 16:41:38');
INSERT INTO `sys_res` VALUES ('25', '删除', '删除按钮', '', '2', 'sys:dept:delete', '', '7', '4', '2018-04-09 16:42:07', '2018-04-09 16:42:14');
INSERT INTO `sys_res` VALUES ('26', '分配权限', '分配权限按钮', null, '2', 'sys:role:perm', null, '4', '5', '2018-03-29 17:24:08', '2018-03-29 17:24:08');
INSERT INTO `sys_res` VALUES ('27', '查询', '查询按钮', '', '2', 'sys:log:list', '', '10', '1', '2018-04-10 20:15:05', '2018-04-10 20:15:05');
INSERT INTO `sys_res` VALUES ('28', 'Druid监控', 'SQL监控', 'druid/index.html', '1', '', 'fa fa-film', '8', '2', '2018-04-12 14:24:15', '2018-04-12 16:31:52');
INSERT INTO `sys_res` VALUES ('29', 'ECharts', '百度图表', 'chart/echarts.html', '1', '', 'fa fa-line-chart', '9', '1', '2018-04-12 15:02:24', '2018-04-23 10:45:52');
INSERT INTO `sys_res` VALUES ('30', '系统异常', '系统异常', 'sys/exp', '1', '', 'fa fa-exclamation-circle', '8', '8', '2018-04-12 15:33:47', '2018-04-12 15:40:48');
INSERT INTO `sys_res` VALUES ('31', '查询', '查询按钮', '', '2', 'sys:exp:list', '', '30', '1', '2018-04-12 15:34:36', '2018-04-12 15:34:36');
INSERT INTO `sys_res` VALUES ('32', '系统工具', '常用工具', '', '0', '', 'fa fa-th', '0', '12', '2018-04-12 20:28:39', '2018-08-11 19:07:00');
INSERT INTO `sys_res` VALUES ('33', '代码生成器', '生成代码', 'sys/gen', '1', '', 'fa fa-th-large', '32', '1', '2018-04-12 20:29:45', '2018-04-12 20:29:45');
INSERT INTO `sys_res` VALUES ('34', '查询', '查询按钮', '', '2', 'sys:gen:list', '', '33', '1', '2018-04-12 20:35:41', '2018-04-12 20:35:41');
INSERT INTO `sys_res` VALUES ('35', '生成代码', '生成代码按钮', '', '2', 'sys:gen:code', '', '33', '2', '2018-04-12 20:36:16', '2018-04-12 20:36:16');
INSERT INTO `sys_res` VALUES ('36', '任务管理', '任务管理', '', '0', '', 'fa fa-th-list', '0', '10', '2018-04-18 17:49:28', '2018-04-18 17:49:53');
INSERT INTO `sys_res` VALUES ('37', '定时JOB', '', 'sys/job', '1', '', 'fa fa-list-ul', '36', '1', '2018-04-18 17:54:27', '2018-04-18 17:55:01');
INSERT INTO `sys_res` VALUES ('38', '查询', '查询按钮', '', '2', 'sys:job:list', '', '37', '1', '2018-04-18 17:57:18', '2018-04-18 17:57:18');
INSERT INTO `sys_res` VALUES ('39', '新增', '新增按钮', '', '2', 'sys:job:add', '', '37', '2', '2018-04-18 17:57:18', '2018-04-18 17:57:18');
INSERT INTO `sys_res` VALUES ('40', '修改', '修改按钮', '', '2', 'sys:job:edit', '', '37', '3', '2018-04-18 17:57:18', '2018-04-18 17:57:18');
INSERT INTO `sys_res` VALUES ('41', '删除', '删除按钮', '', '2', 'sys:job:delete', '', '37', '4', '2018-04-18 17:57:18', '2018-04-18 17:57:18');
INSERT INTO `sys_res` VALUES ('42', '暂停', '暂停按钮', '', '2', 'sys:job:pause', '', '37', '5', '2018-04-18 17:57:18', '2018-04-18 17:57:18');
INSERT INTO `sys_res` VALUES ('43', '恢复', '暂停按钮', '', '2', 'sys:job:resume', '', '37', '6', '2018-04-18 17:57:18', '2018-04-18 17:57:18');
INSERT INTO `sys_res` VALUES ('44', '立即执行', '立即执行按钮', '', '2', 'sys:job:run', '', '37', '7', '2018-04-18 17:57:18', '2018-04-18 17:57:18');
INSERT INTO `sys_res` VALUES ('45', '查询', '查询按钮', '', '2', 'sys:joblog:list', '', '46', '8', '2018-04-18 17:57:18', '2018-04-19 15:38:20');
INSERT INTO `sys_res` VALUES ('46', '定时JOB日志', '', 'sys/joblog', '1', '', 'fa fa-calendar', '36', '1', '2018-04-19 15:23:46', '2018-04-19 15:38:31');
INSERT INTO `sys_res` VALUES ('47', 'Swagger2', 'API 文档', 'swagger/index.html', '1', '', 'fa fa-file-text-o', '32', '2', '2018-08-11 17:50:49', '2018-08-11 19:02:41');
INSERT INTO `sys_res` VALUES ('48', '参数管理', '参数设置', 'sys/param', '1', '', 'fa fa-list-ol', '1', '5', '2018-08-26 11:10:08', '2018-08-26 11:10:08');
INSERT INTO `sys_res` VALUES ('49', '查询', '查询按钮', '', '2', 'sys:param:list', '', '48', '1', '2018-08-26 11:46:46', '2018-08-26 11:46:46');
INSERT INTO `sys_res` VALUES ('50', '新增', '新增按钮', '', '2', 'sys:param:add', '', '48', '2', '2018-08-26 11:47:33', '2018-08-26 11:47:33');
INSERT INTO `sys_res` VALUES ('51', '编辑', '编辑按钮', '', '2', 'sys:param:edit', '', '48', '3', '2018-08-26 11:47:58', '2018-08-26 11:47:58');
INSERT INTO `sys_res` VALUES ('52', '删除', '删除按钮', '', '2', 'sys:param:delete', '', '48', '4', '2018-08-26 11:48:23', '2018-08-26 11:48:23');
INSERT INTO `sys_res` VALUES ('53', '重置密码', '重置密码按钮', '', '2', 'sys:user:resetPwd', '', '2', '8', '2018-9-16 15:26:56', '2018-9-16 15:26:56');
-- ----------------------------
-- Table structure for sys_role_res
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_res`;
CREATE TABLE `sys_role_res` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自动编号',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `res_id` bigint(20) NOT NULL COMMENT '资源ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色与菜单对应';

-- ----------------------------
-- Records of sys_role_res
-- ----------------------------
INSERT INTO `sys_role_res` VALUES ('1', '1', '1');
INSERT INTO `sys_role_res` VALUES ('2', '1', '2');
INSERT INTO `sys_role_res` VALUES ('3', '1', '3');
INSERT INTO `sys_role_res` VALUES ('4', '2', '1');
INSERT INTO `sys_role_res` VALUES ('5', '2', '2');
INSERT INTO `sys_role_res` VALUES ('6', '2', '3');
INSERT INTO `sys_role_res` VALUES ('7', '2', '6');
INSERT INTO `sys_role_res` VALUES ('8', '2', '11');
INSERT INTO `sys_role_res` VALUES ('9', '2', '12');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自动编号',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户与角色对应';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('37', '2', '2');


-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dept_name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `dept_desc` varchar(200) DEFAULT NULL COMMENT '部门描述',
  `parent_id` int(11) DEFAULT NULL COMMENT '上级部门ID，一级部门为0',
  `order_no` int(11) DEFAULT NULL COMMENT '排序值',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统部门';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('1', '集团总部', '负责整个集团业务', '0', '1', '2018-03-23 09:30:51', '2018-03-23 09:30:53');
INSERT INTO `sys_dept` VALUES ('2', '销售部', '负责销售', '1', '3', '2018-03-22 14:56:24', '2018-03-22 14:56:30');
INSERT INTO `sys_dept` VALUES ('3', '技术部', '负责技术', '1', '2', '2018-03-22 14:56:33', '2018-03-22 14:56:35');
INSERT INTO `sys_dept` VALUES ('4', '技术一部', '负责系统一开发', '3', '4', '2018-03-22 14:56:24', '2018-03-22 14:56:24');
INSERT INTO `sys_dept` VALUES ('5', '销售一部', '负责一区销售', '2', '6', '2018-03-22 14:56:24', '2018-03-22 14:56:24');
INSERT INTO `sys_dept` VALUES ('6', '技术二部', '负责系统二开发', '3', '5', '2018-03-22 16:45:58', '2018-03-22 16:46:00');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自动编号',
  `username` varchar(50) NOT NULL COMMENT '账号',
  `desc` varchar(500) DEFAULT NULL COMMENT '描述',
  `ip` varchar(50) DEFAULT NULL COMMENT '请求地址',
  `url` varchar(500) DEFAULT NULL COMMENT '请求url',
  `method` varchar(10) DEFAULT NULL COMMENT '请求方式',
  `param` varchar(500) DEFAULT NULL COMMENT '请求参数',
  `runtime` bigint(20) DEFAULT NULL COMMENT '执行时长,单位毫秒',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志';

-- ----------------------------
-- Table structure for sys_exp
-- ----------------------------
DROP TABLE IF EXISTS `sys_exp`;
CREATE TABLE `sys_exp` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自动编号',
  `username` varchar(50) NOT NULL COMMENT '账号',
  `expection` text COMMENT '描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统异常';

-- ----------------------------
-- Table structure for schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job`;
CREATE TABLE `schedule_job` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `method_name` varchar(200) DEFAULT NULL COMMENT '方法名',
  `params` varchar(1000) DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT 'cron表达式',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态,1表示正常; 0表示暂停',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务';

-- ----------------------------
-- Records of schedule_job
-- ----------------------------
INSERT INTO `schedule_job` VALUES ('3', 'testTask', 'hello', 'hello world', '0 0/5 * * * ?', '0', '有参方法', '2018-04-19 09:14:51', '2018-04-19 14:29:46');
INSERT INTO `schedule_job` VALUES ('4', 'testTask', 'hello2', '', '0 0/5 * * * ?', '0', '无参方法', '2018-04-19 10:09:32', '2018-04-19 10:09:32');

-- ----------------------------
-- Table structure for schedule_job_log
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job_log`;
CREATE TABLE `schedule_job_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '任务日志id',
  `job_id` int(11) NOT NULL COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `method_name` varchar(200) DEFAULT NULL COMMENT '方法名',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT 'cron表达式',
  `status` tinyint(4) NOT NULL COMMENT '任务状态，0:成功,1:失败',
  `msg` text COMMENT '运行信息',
  `runtime` bigint(20) NOT NULL COMMENT '耗时(单位：毫秒)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `job_id` (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务日志';

-- ----------------------------
-- Table structure for sys_head
-- ----------------------------
DROP TABLE IF EXISTS `sys_head`;
CREATE TABLE `sys_head` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自动编号',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `src_img_name` varchar(500) DEFAULT NULL COMMENT '头像原图片地址',
  `head_img_name` varchar(500) DEFAULT NULL COMMENT '头像图片地址',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='系统头像表';

-- ----------------------------
-- Table structure for sys_param
-- ----------------------------
DROP TABLE IF EXISTS `sys_param`;
CREATE TABLE `sys_param` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自动编号',
  `param_key` varchar(200) DEFAULT NULL COMMENT '参数键名',
  `param_value` varchar(2000) DEFAULT NULL COMMENT '参数键值',
  `param_desc` varchar(200) DEFAULT NULL COMMENT '参数描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='系统参数';

-- ----------------------------
-- Records of sys_param
-- ----------------------------
INSERT INTO `sys_param` VALUES ('1', 'user.reset.pwd', 'abcd1234', '系统用户重置密码', '2018-08-26 14:38:58', '2018-08-26 16:52:06');
INSERT INTO `sys_param` VALUES ('2', 'sys.default.skin', 'defaultSkin', '系统默认皮肤', '2018-08-26 17:04:09', '2018-08-26 17:59:42');

commit;







