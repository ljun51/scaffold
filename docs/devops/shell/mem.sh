#!/bin/sh
# 
#获取内存使用率的脚本
 
time=$(date "+%Y-%m-%d %H:%M:%S")
memoryUsed=`free -m | sed -n '2p' | awk '{printf "%f\n",($3)/$2*100}'`
 
echo "Mem Usage:${memoryUsed}% ${time}"
memory=`echo "$memoryUsed" | cut -d "." -f 1`
if [ $memory -gt 75 ]
then
    echo "警告，您当前内存使用率${memoryUsed}%，已严重超标"$time | mail -s "内存告警" ljun51@outlook.com
else
    exit
fi