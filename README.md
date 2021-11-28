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
├── hyperion-ui              							// 前端框架 [80]
|
├── hyperion-gateway         							// 网关模块 [8080]
|
├── hyperion-oss            							// 单点登录 [9999]
|
├── hyperion-config         							// 配置模块
|
├── hyperion-service         							// 业务模块 [9010-9090]
│       └── hyperion-service-judge						// 判题服务 [9010]
│       └── hyperion-service-page 						// 题解博客 [9020]
│       └── hyperion-service-xxx 						// 其他
|
├── hyperion-admin          							// 管理模块和监控中心 [9000]
|
├── hyperion-order          							// 支付模块 
│       └── hyperion-order-alipay                       // 阿里支付 [8888]
│       └── hyperion-order-wxpay               		 	// 微信支付 [8889]
|
├── hyperion-commom             						// 通用模块
│       └── hyperion-commom-datasource               	// 数据来源
│       └── hyperion-commom-redis               		// 缓存服务
│       └── hyperion-commom-log               			// 日志服务
│       └── hyperion-commom-utils                       // 系统工具类
│       └── hyperion-commom-feign                       // 调用接口
│       └── hyperion-commom-constant                    // 常量包
│       └── hyperion-commom-mail                        // 邮箱接口
|
├──pom.xml                								// 公共依赖
~~~
