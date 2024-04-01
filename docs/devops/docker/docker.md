# Docker

## Docker Desktop
## Docker Extensions
Docker 扩展允许您使用 Docker Desktop 中的第三方工具来扩展其功能。

您可以将自己喜欢的开发工具无缝连接到应用程序开发和部署工作流。使用调试、测试、安全和网络功能增强 Docker Desktop，并使用扩展 SDK 创建自定义加载项。

您可以在 Docker Hub 或 Docker Desktop 内的扩展市场中浏览可用扩展的列表。
## Docker Engine
### Docker Storage
Docker 有两个选项让容器在主机上存储文件，以便即使在容器停止后文件也能保留：`卷`和`绑定挂载`。

Docker 还支持将文件存储在主机内存中的容器。此类文件不会持久化。如果在 Linux 上运行 Docker，则使用 `tmpfs` 挂载将文件存储在主机的系统内存中。如果在 Windows 上运行 Docker，则使用`命名管道`将文件存储在主机的系统内存中。

区分卷、绑定挂载、tmpfs可之间差异的一个简单方法是数据位于 Docker 主机上的位置。
![挂载类型](./images/types-of-mounts.webp)
* `卷`存储在由 Docker 管理的主机文件系统的一部分中（Linux 上的 /var/lib/docker/volumes/）。非 Docker 进程不应修这一部分。卷是在 Docker 中保存数据的最佳方式。
* `绑定挂载`可以存储在主机系统上的任何位置。它们甚至可能是重要的系统文件或目录。 Docker 主机或 Docker 容器上的非 Docker 进程可以随时修改它们。
* `tmpfs` 仅存储在主机系统的内存中，并且永远不会写入主机系统的文件系统。

`绑定挂载`和卷都可以通过 `-v` 或 `--volume` 标志被挂载到容器里，只是语法上有一些差异。而 `tmpfs` 可以使用 `--tmpfs` 标志。建议对容器和服务，`绑定挂载`、`卷`或 `tmpfs` 挂载使用 `--mount` 标志，因为语法更清晰。

### Docker Networking
### Docker Containers
### Logs and metrics
### Security
### Swarm mode
## Docker Compose
## Docker Build
## 常用配置
### daemon.json 
位置位于 `/etc/docker/daemon.json`

### 添加当前用户为 docker 用户

    # 执行完后需要重新登录
    sudo usermod -aG docker $USER

### Docker 容器的重启策略

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

### docker save和docker export的区别
* docker save 保存的是镜像（image），docker export 保存的是容器（container）；
* docker load 用来载入镜像包，docker import 用来载入容器包，但两者都会恢复为镜像；
* docker load 不能对载入的镜像重命名，而 docker import 可以为镜像指定新名称。
