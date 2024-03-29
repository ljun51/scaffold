#!/bin/sh
#
# 获取磁盘使用率脚本

get_disk_rate() {
    # 获取磁盘总量
    totalDiskSize=$(df |grep -w "/" | awk '{if(NR == 1)print $2}')
    # 获取磁盘使用量
    usedDiskSize=$(df |grep -w "/" | awk '{if(NR == 1)print $3}')
    # 磁盘使用率
    diskPercent=`awk 'BEGIN{printf "%.2f",'${usedDiskSize}'/'${totalDiskSize}' * 100}'`
    echo ${diskPercent}
}
 
time=$(date "+%Y-%m-%d %H:%M:%S")
diskUsage=$(get_disk_rate)
echo "Disk Usage:${diskUsage}%" $time
disk=`echo "$diskUsage" | cut -d "%" -f 1`
disk=`echo "$diskUsage" | cut -d "." -f 1`
if [ $disk -gt 75 ]
then
    echo "警告，您当前磁盘使用率${disk}%，已严重超标"$time | mail -s "磁盘告警" ljun51@outlook.com 
else
    exit
fi