# Docker

## daemon.json 
位置位于 `/etc/docker/daemon.json`

## 添加当前用户为 docker 用户

    # 执行完后需要重新登录
    sudo usermod -aG docker $USER

## Docker 容器的重启策略

* no
默认策略，在容器退出时不重启容器。启动容器时不添加参数 --restart 即可。
* on-failure
在容器非正常退出时（退出状态非0），才会重启容器。
* on-failure:n
在容器非正常退出时重启容器，并且指定重启次数。n 为正整数。如果不指定次数，则会一直重启。
* always
只要容器退出就重启容器。
* unless-stopped
在容器退出时总是重启容器，但是 Docker 守护进程启动之前就已经停止运行的容器不算在内。

创建容器时没有添加参数  --restart=always ，导致的后果是：当 Docker 重启时，容器未能自动启动。

> docker container update --restart=always 容器名字

> docker run -d --restart=always tomcat

## docker save和docker export的区别
* docker save 保存的是镜像（image），docker export 保存的是容器（container）；
* docker load 用来载入镜像包，docker import 用来载入容器包，但两者都会恢复为镜像；
* docker load 不能对载入的镜像重命名，而 docker import 可以为镜像指定新名称。
