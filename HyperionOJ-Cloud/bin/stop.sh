#!/bin/bash
survive=$(ps -ef | grep java | grep hyperionoj-web | grep spring.profiles.active | awk '{print $2}' | wc -l)

if [ "$survive" -eq 0 ]
then
  echo "服务当前已经处于停止状态~!"
  exit 0
fi

echo "正在停止服务~!"

for i in {1..150}; do
  ps -ef | grep java | grep hyperionoj-web | grep spring.profiles.active | awk '{print $2}' | xargs kill
  sleep 1s
  echo "sleep 1s, i=${i}"
  survive=$(ps -ef | grep java | grep hyperionoj-web | grep spring.profiles.active | awk '{print $2}' | wc -l)
  if [ "$survive" != 1 ]; then
    break
  fi
done

survive=$(ps -ef | grep java | grep hyperionoj-web | grep spring.profiles.active | awk '{print $2}' | wc -l)
if [ "$survive" -eq 1 ]; then
    ps -ef | grep java | grep hyperionoj-web | grep spring.profiles.active | awk '{print $2}' | xargs kill -9
    echo "暂停时间超过150s kill-9 停止服务"
fi


echo "服务已被停止~!"