#!/bin/bash

JOB=`ps -ef | grep java | grep hyperionoj-web | awk '{print $2}' | wc -l`
if [ $JOB -ge 1 ]
then
  echo "服务已经处于运行状态!"
  exit 1
fi

nohup java -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m -Xms512m -Xmx10240m \
-jar ~/HyperionOJ-WEB/server/hyperionoj-web-1.0.0.jar \
--spring.profiles.active=test > ~/HyperionOJ-WEB/logs/console.log &

echo "服务已被启动~!"