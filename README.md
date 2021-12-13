# 技术栈

* 采用前后端分离的模式，微服务版本后端

* 后端采用Spring Boot、Spring Cloud

* 注册中心、配置中心选型Nacos，权限认证使用Redis + Jwt。

* 持久层采用MySQL、MyBatis-Plus

* 消息队列采用kafka

* oss采用七牛云

# 系统模块

~~~
com.hyperion
├── hyperion-ui              							// 前端框架 [8080]
|
├── hyperion-gateway         							// 网关模块 [9000]
|
├── hyperion-oss            							// 单点登录 [9020]
|
├── hyperion-judge         							    // 判题模块 [9900] 
|
├── hyperion-admin          							// 管理模块和监控中心 [9999]
|
├── hyperion-page          								// 基本页面 [9010]
│       └── hyperion-page-article                       // 文章服务 
│       └── hyperion-page-problem               		// 题库服务
│       └── hyperion-page-contest               		// 比赛服务
|
├── hyperion-order          							// 支付模块 [8888](暂无)
│       └── hyperion-order-alipay                       // 阿里支付 
│       └── hyperion-order-wxpay               		 	// 微信支付 
|
├── hyperion-commom             						// 通用模块 [10000]
│       └── hyperion-commom-config               		// 配置相关
│       └── hyperion-commom-constant                    // 常量包
│       └── hyperion-commom-feign               		// 声明式调用
│       └── hyperion-commom-interceptor               	// 拦截器
│       └── hyperion-commom-service               		// 公共服务
│       └── hyperion-commom-utils                       // 系统工具类
│       └── hyperion-commom-vo                       	// 前端交互对象
|
├──pom.xml                								// 公共依赖
~~~
