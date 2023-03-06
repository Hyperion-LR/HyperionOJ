# 技术栈

* 采用前后端分离的模式，微服务版本后端

* 后端采用Spring Boot、Spring Cloud

* 注册中心、配置中心选型Nacos，权限认证使用Redis + Jwt

* 持久层采用MySQL、MyBatis-Plus

* 消息队列采用kafka

* oss采用七牛云

# 系统模块

~~~
com.hyperion
|
├── hyperion-ui              							// 前端框架 [8080]
|
├── hyperion-gateway         							// 网关模块 [8181]
|
├── hyperion-web          								// 对外接口 [9010]
│       	  └── hyperion-web-article                  // 文章服务
│       	  └── hyperion-web-problem               	// 题库服务
│       	  └── hyperion-web-contest               	// 竞赛服务
│       	  └── hyperion-web-admin               		// 管理员服务
|
├── hyperion-api          								// 业务
│       	  └── hyperion-api-api         				// 具体业务实现
|
├── hyperion-biz          								// 业务实现
│       	  └── hyperion-biz-service         			// 具体业务实现
│       	  └── hyperion-biz-interceptor         		// 拦截器
│       	  └── hyperion-biz-schedule         		// 定时任务
|
├── hyperion-judge         							    // 判题服务 [9000]
|
├── hyperion-dal          								// 持久化模块
│       	  └── hyperion-dal-do                  		// 数据对象
│       	  └── hyperion-dal-po                  		// 实体对象
│       	  └── hyperion-dal-mapper              		// mapper
|
├── hyperion-commom             						// 通用模块
│       		└── hyperion-commom-config              // 配置相关
│       		└── hyperion-commom-constant            // 常量包
│       		└── hyperion-commom-utils               // 系统工具类
│       		└── hyperion-commom-vo                  // 前端交互对象
|
├──pom.xml                								// 公共依赖
~~~

