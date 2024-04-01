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

挂载卷时，该卷可以是命名的，也可以是匿名的。匿名卷被赋予一个随机名称，该名称保证在给定的 Docker 主机中是唯一的。与命名卷一样，即使删除使用匿名卷的容器，匿名卷也会保留，除非在创建容器时使用 `--rm` 标志。

与卷相比，绑定挂载的功能有限。 使用绑定挂载时，主机上的文件或目录将挂载到容器中。 文件或目录由其在主机上的完整路径引用。 该文件或目录不需要已存在于 Docker 主机上。 如果尚不存在，则会根据需要创建它。 绑定安装速度很快，但它们依赖于具有可用的特定目录结构的主机文件系统。 如果您正在开发新的 Docker 应用程序，请考虑使用命名卷。 您无法使用 Docker CLI 命令直接管理绑定安装。

tmpfs 挂载不会持久保存在磁盘上，无论是在 Docker 主机上还是在容器内。 容器可以在容器的生命周期内使用它来存储非持久状态或敏感信息。 例如，在内部，Swarm 服务使用 tmpfs 挂载将机密挂载到服务的容器中。

```shell
# create a volume
docker volume create my-vol
# list volumes
docker volume ls
# inspect a volume
docker volume inspect my-vol
# remove a volume
docker volume rm my-vol
# volume 不会自动删除，可以通过命令删除未使用的 volume
docker volume prune


# Start a container with a volume
docker run -d \
  --name devtest \
  --mount source=myvol2,target=/app \
  nginx:latest
  
# Populate a volume using a container
docker run -d \
  --name=nginxtest \
  --mount source=nginx-vol,destination=/usr/share/nginx/html \
  nginx:latest
  
# Use a read-only volume
docker run -d \
  --name=nginxtest \
  --mount source=nginx-vol,destination=/usr/share/nginx/html,readonly \
  nginx:latest

  
# Share data between machines  
## Use a volume driver
docker plugin install --grant-all-permissions vieux/sshfs
## Create a volume using a volume driver
docker volume create --driver vieux/sshfs \
  -o sshcmd=test@node2:/home/test \
  -o password=testpassword \
  sshvolume
## Start a container which creates a volume using a volume driver
docker run -d \
  --name sshfs-container \
  --volume-driver vieux/sshfs \
  --mount src=sshvolume,target=/app,volume-opt=sshcmd=test@node2:/home/test,volume-opt=password=testpassword \
  nginx:latest


# Back up, restore, or migrate data volumes
## Back up a volume
docker run -v /dbdata --name dbstore ubuntu /bin/bash
docker run --rm --volumes-from dbstore -v $(pwd):/backup ubuntu tar cvf /backup/backup.tar /dbdata
## Restore volume from a backup
docker run -v /dbdata --name dbstore2 ubuntu /bin/bash
docker run --rm --volumes-from dbstore2 -v $(pwd):/backup ubuntu bash -c "cd /dbdata && tar xvf /backup/backup.tar --strip 1"


# Remove anonymous volumes
docker run --rm -v /foo -v awesome:/bar busybox top


# Start a container with a bind mount, `/target` 目录必须先存在
docker run -d \
  -it \
  --name devtest \
  --mount type=bind,source="$(pwd)"/target,target=/app \
  nginx:latest
# Mount into a non-empty directory on the container
docker run -d \
  -it \
  --name broken-container \
  --mount type=bind,source=/tmp,target=/usr \
  nginx:latest

docker: Error response from daemon: oci runtime error: container_linux.go:262:
starting container process caused "exec: \"nginx\": executable file not found in $PATH".  
# Use a read-only bind mount
docker run -d \
  -it \
  --name devtest \
  --mount type=bind,source="$(pwd)"/target,target=/app,readonly \
  nginx:latest
# Configure bind propagation
docker run -d \
  -it \
  --name devtest \
  --mount type=bind,source="$(pwd)"/target,target=/app \
  --mount type=bind,source="$(pwd)"/target,target=/app2,readonly,bind-propagation=rslave \
  nginx:latest
# Configure the selinux label
docker run -d \
  -it \
  --name devtest \
  -v "$(pwd)"/target:/app:z \
  nginx:latest    
  
  
# Use a tmpfs mount in a container
docker run -d \
  -it \
  --name tmptest \
  --mount type=tmpfs,destination=/app \
  nginx:latest  
# or 
docker run -d \
  -it \
  --name tmptest \
  --tmpfs /app \
  nginx:latest  
#
docker run -d \
  -it \
  --name tmptest \
  --mount type=tmpfs,destination=/app,tmpfs-size=1024,tmpfs-mode=1770 \
  nginx:latest  
```

### Docker Networking
### Docker Containers
### Logs and metrics
### Security
### Swarm mode
## Docker Compose
## Docker Build

```shell
# 查找未使用的镜像
docker images -f dangling=true
# 删除所有未使用的镜像（包括已停止的容器使用的镜像和未使用的镜像），如果要绕过提示，可以使用 -f 或 --force 标志。
docker image prune -a
# 删除停止的容器使用的镜像
docker image prune
# 删除24小时前创建的镜像
docker image prune -a --filter "until=24h"

```
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
