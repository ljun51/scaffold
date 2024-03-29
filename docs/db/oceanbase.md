# OceanBase

## 概念
OBAgent：是 OceanBase 数据库监控采集框架，支持推、拉两种数据采集模式，可以满足不同的应用场景。
OBD：OceanBase 安装部署工具（OceanBase Deployer）
OCP Express：基于 Web 的 OceanBase 数据库 4.x 管理工具，融合在 OceanBase 数据库集群中，支持对数据库集群关键性能及基本数据库管理功能。
ODC：OceanBase 开发者中心（OceanBase Developer Center）
ODP：OceanBase 数据库代理（OceanBase Database Proxy）

## 安装
**步骤一：拉取 OceanBase 数据库镜像**

* 搜索 OceanBase 数据库相关镜像
    
    docker search oceanbase

* 拉取 OceanBase 数据库最新镜像

    docker pull oceanbase/oceanbase-ce

**步骤二：启动 OceanBase 数据库实例**

* 根据当前容器部署最大规格实例
    
    docker run -p 2881:2881 --name obstandalone -e MINI_MODE=0 -d oceanbase/oceanbase-ce

* 部署 mini 的独立实例

    docker run -p 2881:2881 --name obstandalone -e MINI_MODE=1 -d oceanbase/oceanbase-ce

启动预计需要 2~5 分钟。执行以下命令，如果返回 `boot success!`，则表示启动成功。
    
    docker logs obstandalone | tail -1
    boot success!

**步骤三：连接 OceanBase 数据库实例**
oceanbase-ce 镜像安装了 OceanBase 数据库客户端 OBClient，并提供了默认连接脚本 ob-mysql。

    # 使用 root 用户登录集群的 sys 租户
    docker exec -it obstandalone ob-mysql sys

    # 使用 root 用户登录集群的 test 租户
    docker exec -it obstandalone ob-mysql root

    # 使用 test 用户登录集群的 test 租户
    docker exec -it obstandalone ob-mysql test

您也可以运行以下命令，使用您本机的 OBClient 或者 MySQL 客户端连接实例。

    obclient -uroot@sys -h127.1 -P2881

连接成功后，终端将显示如下内容：

    docker exec -it obstandalone ob-mysql sys

    login as root@sys
    Command is: obclient -h127.1 -uroot@sys -A -Doceanbase -P2881
    Welcome to the OceanBase.  Commands end with ; or \g.
    Your OceanBase connection id is 3221487727
    Server version: OceanBase_CE 4.1.0.0 (r100000192023032010-0265dfc6d00ff4f0ff4ad2710504a18962abaef6) (Built Mar 20 2023 10:12:57)

    Copyright (c) 2000, 2018, OceanBase and/or its affiliates. All rights reserved.

    Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

通过MYSQL客户端连接

格式：

    mysql -h 主机 IP -P 端口号 -u 账号 -p '数据库密码' -D 默认数据库 -Ac --prompt "OceanBase(\u@\d)> " 

比如：

    mysql -h 127.0.0.1 -P 2881 -u root -p -D test -Ac --prompt "OceanBase(\u@\d)> "
    obclient -h127.0.0.1 -P2881 -uroot@sys -Doceanbase -A
    ALTER USER 'root' IDENTIFIED BY 'ljun51';


## 使用 OceanBase 数据库在开发中要特别注意什么？
以下列出开发过程中的注意事项，仅供参考：

* 大数据量导入需要特别关注内存的使用情况。
* 索引生效时间较长，建议在建表时将索引语句一并纳入。
* 表建好后，主键不能更改。如果需要修改，只能删表重建。
* mysql-connector-java 的版本建议使用 5.1.30 及以上。
* 列类型修改有较大限制。Varchar 长度只能由短变长，不能由长变短。
* 如果一个连接超过 15 分钟空闲，服务端会主动断开，在使用连接池的时候需要设置一个连接最大的空闲时间。例如，Druid 的 minEvictableIdleTimeMillis 小于 15 分钟。

