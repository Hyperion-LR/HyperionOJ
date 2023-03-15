#!/bin/bash

JOB=`ps -ef | grep java | grep hyperionoj-web | awk '{print $2}' | wc -l`
if [ $JOB -ge 1 ]
then
  echo "服务已经处于运行状态!"
  exit 1
fi

nohup java -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m -Xms256m -Xmx512m \
-jar ~/HyperionOJ-WEB/release/hyperionoj-web-1.0.0.jar \
--spring.profiles.active=test > ~/HyperionOJ-WEB/logs/console-web.log 2>&1 &

echo "服务已被启动~!"