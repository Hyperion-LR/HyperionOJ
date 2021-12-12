# HyperionOJ利用Docker搭建本地环境



### Nacos（注册中心和配置中心）

先在主机使用MySQL运行目录里的（[nacos.sql](./nacos.sql)）文件生成数据库，不推荐在Docker里面使用数据库

```dockerfile
// 拉取nacos镜像
docker pull nacos/nacos-server

// 创建并运行容器
docker run -d 
-e PREFER_HOST_MODE=
172.23.192.1 -e MODE=standalone -e JVM_XMS=256m -e JVM_XMX=256m -e JVM_XMN=128m -p 8848:8848 --name nacos --restart=always nacos/nacos-server
```

上面的`172.23.192.1`要改成自己的本地地址

然后进入Docker容器使用`ping host.docker.internal`查看宿主主机ip,这里好像都是`192.168.65.2`

之后修改配置文件

`vim conf/application.properties`

在配置文件最后加上

```dockerfile
spring.datasource.platform=mysql
db.num=1
db.url.0=jdbc:mysql://192.168.65.2:3306/nacos?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&serverTimezone=GMT%2B8
db.user=root
db.password=123212321
```

退出，这里sql账号密码记得改成自己的。然后退出重新运行容器。



### Kafka（用于提交代码及和各服务之间交流）

```dockerfile
// Kafka需要先安装zookeeper
docker pull zookeeper:3.6

// 创建并运行zookeeper容器
docker run -d --name zookeeper -p 2181:2181 -v /etc/localtime:/etc/localtime zookeeper:3.6

// 开始拉取kafka
docker pull wurstmeister/kafka:2.12-2.5.0

// 创建并运行kafka容器
docker run -d --restart=always --log-driver json-file --log-opt max-size=100m --log-opt max-file=2 --name kafka -p 9092:9092 -e KAFKA_BROKER_ID=0 -e KAFKA_ZOOKEEPER_CONNECT=172.23.192.1:2181/kafka -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://172.23.192.1:9092 -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 -v /etc/localtime:/etc/localtime wurstmeister/kafka:2.12-2.5.0
```

上面的`172.23.192.1`要改成自己的本地地址



### Redis（用于token鉴权及缓存）

```dockerfile
docker run --name redis -p 6379:6379 redis
```

嫌麻烦就不设置密码啥的了



### Nginx（如果组建Nacos集群用作负载均衡）

```dockerfile
docker run --name redis -p 6379:6379 redis
```

之后用到在写