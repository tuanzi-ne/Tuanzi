#部署文档

##本地部署
1. 创建MySQL数据库springboot,数据库编码为UTF-8
2. 执行sql/db_init.sql和sql/quartz_mysql_innodb.sql文件,分别初始化表和定时任务表数据
3. 根据不同的部署环境修改application.yml文件中spring.profiles.active属性值(默认dev),再找到对应的application-*.yml(默认dev)文件修改自己的MySQL账号和密码
4. 开发环境使用IDE工具MyEclipse或Idea导入项目,点击Debug或Run按钮即可运行
5. 启动成功后,推荐使用Chrome浏览器,地址栏输入http://localhost:8080/
6. 系统默认登录用户名/密码=admin/admin
7. druid默认登录用户名/密码=druid/druid

##Redis共享部署
1. 安装redis,并根据redis安装信息配置db.properties的redis信息
2. 配置【shiro.cache.type=1】,表示开启redis缓存并把shiro session存到redis,默认保存到Echache