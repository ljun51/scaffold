# zookeeper

## docker
```shell
docker run -p 2181:2181 -p 2888:2888 -p 3888:3888 -v ./cmw/zookeeper/data:/data/ --name zookeeper --restart always -d zookeeper

docker run -it --rm --link zookeeper:zookeeper zookeeper zkCli.sh -server zookeeper

docker run -it --rm -v /home/john/Projects/dubbo-admin/dubbo-admin-server/src/main/resources/properties:/config -p 38080:38080 apache/dubbo-admin
```echo $XDG_SESSION_TYPE
