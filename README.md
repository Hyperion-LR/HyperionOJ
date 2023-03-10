# 技术栈

* 采用前后端分离的模式，微服务版本后端

* 后端采用Spring Boot、Spring Cloud

* 注册中心、配置中心选型Nacos，权限认证使用Redis + Jwt

* 持久层采用MySQL、MyBatis-Plus

* 消息队列采用kafka

* oss采用七牛云

# 后端系统模块

~~~
com.hyperion
|
├── hyperion-ui              							// 前端框架 [8080]
|
├── hyperion-gateway         							// 网关模块 [8181]
|
├── hyperion-web          								// 对外接口 [9010]
│       	  └── presentation                  		// 表示层
│       	  │			└── controller
│       	  │			└── listener
│       	  │			└── vo
│       	  │			└── dto
|			  │
│       	  └── application                  			// 应用层
│       	  │			└── api
|			  │
│       	  └── domain                  				// 领域层
│       	  │		└── service
|			  |
│       	  └── infrastructure                  		// 基础层
│      					└── dal
│      					└── utils
|
├── hyperion-judge         							    // 判题服务 [9000]
|
└── pom.xml                								// 公共依赖
~~~
