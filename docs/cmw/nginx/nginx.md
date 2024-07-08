# nginx

## nginx容器加载配置
修改 nginx 配置，在不能重启的情况下，可以无需重新启动 Docker 容器即可重新加载新配置。Nginx 可以hot-reload配置而不重新启动。

查询nginx所在容器id：

    docker ps -a

测试nginx配置

    docker exec 容器id nginx -t 

重新加载nginx配置
    
    docker exec 容器id  nginx -s reload 
