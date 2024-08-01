# Common Middleware

## CDN
## Drools
## Elastic Stack
## Flowable
## Kafka
## [MyBatis](./mybatis.md)
## Nacos
    ```shell
    docker-compose -f cmw/nacos/standalone-mysql-8.yaml up -d
    ```
## Nginx

docker run -p 8080:80 --name nginx -v ./cmw/nginx/html:/usr/share/nginx/html -d nginx:latest

## Pulsar
## [Redis](./redis/redis.md)
## ShardingSphere
## 分布式事物
### Seata(./seata.md)
### Atomikos
在 Spring Boot 2.x.x 中存在。
### Bitronix
在 Spring Boot 2.x.x 中存在。
## Tomcat
## Zookeeper
