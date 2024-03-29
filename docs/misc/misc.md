
kafka

2019年4月4日

14:42

调大max.poll.interval.ms，默认300000（300s）

调小max.poll.records，默认500

git

Thursday, May 16, 2019

3:26 PM

Token: 6ad4b8f467afa2abc5f3811b0a285269dabe079b

GistID: 28ea7a96dbcdd6887947612a3c608a3d



nginx

2019年7月9日

9:23

location /cms {

proxy_http_version 1.1;

proxy_set_header Connection "";

proxy_pass [http://118.118.116.202:9401](http://118.118.116.202:9401/);

#Proxy Settings

proxy_redirect off;

proxy_set_header Host $host;

proxy_set_header X-Real-IP $remote_addr;

proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;

proxy_next_upstream error timeout invalid_header http_500 http_502 http_503 http_504;

proxy_max_temp_file_size 0;

proxy_connect_timeout 90;

proxy_send_timeout 90;

proxy_read_timeout 90;

proxy_buffer_size 4k;

proxy_buffers 4 32k;

proxy_busy_buffers_size 64k;

proxy_temp_file_write_size 64k;

}

npm

2020年11月24日

9:09

npm install --registry=https://registry.npm.taobao.org

systemd

Friday, March 5, 2021

5:39 PM

# 查看状态

$ systemctl status

# 重新载入配置

$ systemctl daemon-reload

# 立即启动一个服务

$ systemctl start apache.service

# 立即停止一个服务

$ systemctl stop apache.service

# 重启一个服务

$ systemctl restart apache.service

# 杀死一个服务的所有子进程

$ systemctl kill apache.service

# 重新加载一个服务的配置文件

$ systemctl reload apache.service

# 重载所有修改过的配置文件

$ systemctl daemon-reload

# 显示某个 Unit 的所有底层参数

$ systemctl show httpd.service

# 显示某个 Unit 的指定属性的值

$ systemctl show -p CPUShares httpd.service

# 设置某个 Unit 的指定属性

$ systemctl set-property httpd.service CPUShares=500

# systemctl list-dependencies命令列出一个 Unit 的所有依赖

$ systemctl list-dependencies nginx.service

journalctl

Friday, March 5, 2021

6:25 PM

# 查看所有日志（默认情况下 ，只保存本次启动的日志）

$ sudo journalctl

# 查看内核日志（不显示应用日志）

$ sudo journalctl -k

# 查看系统本次启动的日志

$ sudo journalctl -b

$ sudo journalctl -b -0

# 查看上一次启动的日志（需更改设置）

$ sudo journalctl -b -1

# 查看指定时间的日志

$ sudo journalctl --since="2012-10-30 18:17:16"

$ sudo journalctl --since "20 min ago"

$ sudo journalctl --since yesterday

$ sudo journalctl --since "2015-01-10" --until "2015-01-11 03:00"

$ sudo journalctl --since 09:00 --until "1 hour ago"

# 显示尾部的最新10行日志

$ sudo journalctl -n

# 显示尾部指定行数的日志

$ sudo journalctl -n 20

# 实时滚动显示最新日志

$ sudo journalctl -f

# 查看指定服务的日志

$ sudo journalctl /usr/lib/systemd/systemd

# 查看指定进程的日志

$ sudo journalctl _PID=1

# 查看某个路径的脚本的日志

$ sudo journalctl /usr/bin/bash

# 查看指定用户的日志

$ sudo journalctl _UID=33 --since today

# 查看某个 Unit 的日志

$ sudo journalctl -u nginx.service

$ sudo journalctl -u nginx.service --since today

# 实时滚动显示某个 Unit 的最新日志

$ sudo journalctl -u nginx.service -f

# 合并显示多个 Unit 的日志

$ journalctl -u nginx.service -u php-fpm.service --since today

# 查看指定优先级（及其以上级别）的日志，共有8级

# 0: emerg

# 1: alert

# 2: crit

# 3: err

# 4: warning

# 5: notice

# 6: info

# 7: debug

$ sudo journalctl -p err -b

# 日志默认分页输出，--no-pager 改为正常的标准输出

$ sudo journalctl --no-pager

# 以 JSON 格式（单行）输出

$ sudo journalctl -b -u nginx.service -o json

# 以 JSON 格式（多行）输出，可读性更好

$ sudo journalctl -b -u nginx.serviceqq

- o json-pretty

# 显示日志占据的硬盘空间

$ sudo journalctl --disk-usage

# 指定日志文件占据的最大空间

$ sudo journalctl --vacuum-size=1G

# 指定日志文件保存多久

$ sudo journalctl --vacuum-time=1years