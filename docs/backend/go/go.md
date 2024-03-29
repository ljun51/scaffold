# Go

make和new

2020年12月21日

11:28

> Go有两个数据结构创建函数：new和make。两者的区别在学习Go语言的初期是一个常见的混淆点。基本的区别是new(T)返回一个*T，返回的这个指针可以被隐式地消除引用。而make(T, args)返回一个普通的T。通常情况下，T内部有一些隐式的指针。一句话，new返回一个指向已清零内存的指针，而make返回一个复杂的结构。
> 
1. new 可分配任意类型的数据；make 仅用来分配及初始化类型为 slice、map、chan 的数据。
2. new 分配返回的是指针，即类型 *Type；make 返回引用，即 Type。
3. new 分配的空间被清零；make 分配空间后，会进行初始化。

go 语法

2020年12月25日

10:19

- 引用类型：

指针：*

切片：slice

字典：map

函数：func

接口：interface

- 格式化输出：

%%        %字面量

%b        二进制整数值，基数为2，或者是一个科学记数法表示的指数为2的浮点数

%c        该值对应的unicode字符

%d        十进制数值，基数为10

%e        科学记数法e表示的浮点或者复数

%E        科学记数法E表示的浮点或者附属

%f        标准计数法表示的浮点或者附属

%o        8进制度

%p        十六进制表示的一个地址值

%s        输出字符串或字节数组

%T        输出值的类型，注意int32和int是两种不同的类型，编译器不会自动转换，需要类型转换。

%v        值的默认格式表示

%+v        类似%v，但输出结构体时会添加字段名

%#v        值的Go语法表示

%t        单词true或false

%q        该值对应的单引号括起来的go语法字符字面值，必要时会采用安全的转义表示

%x        表示为十六进制，使用a-f

%X        表示为十六进制，使用A-F

%U        表示为Unicode格式：U+1234，等价于"U+%04X"

- 代码测试覆盖率命令

*go test -cover*

常用package

2020年12月26日

10:51

日志管理：

https://github.com/sirupsen/logrus.git

Session：

https://github.com/gorilla/sessions.git

html&css操作：

https://github.com/PuerkitoBio/goquery.git

wasm:

https://www.vugu.org/doc/start

micro

2020年12月30日

16:53

micro new --gopath --namespace=local github.com/ljun51/helloworld

protoc --proto_path=.:$GOPATH/src --go_out=. --micro_out=. proto/helloworld/helloworld.proto

Go语言十大主流微服务框架

Saturday, April 15, 2023

11:51 AM

以下 star数截止2023年3月份

1.Istio（32.5K）

项目简介：Istio是由Google、IBM和Lyft开源的微服务管理、保护和监控框架。使用istio可以很简单的创建具有负载均衡、服务间认证、监控等功能的服务网络，而不需要对服务的代码进行任何修改。

仓库地址：

https://github.com/istio/istio

https://github.com/istio/istio

官方文档地址：

https://istio.io/latest/docs/

https://istio.io/latest/docs/

2.Go-kit（24.7K）

项目简介：Go-kit 是一个 Go 语言的分布式开发包，用于开发微服务。

仓库地址：

https://github.com/go-kit/kit/

https://github.com/go-kit/kit/

官方文档地址：

Go kit - A toolkit for microservices

https://gokit.io/

3.Go-zero（23K）

项目简介：go-zero 是一个集成了各种工程实践的 web 和 rpc 框架。

仓库地址：

https://github.com/tal-tech/go-zero

https://github.com/tal-tech/go-zero

官方文档地址：

项目介绍 | go-zero

https://go-zero.dev/cn/docs/introduction

4.Go-micro（20.1K）

项目简介：Micro是一个专注于简化分布式系统开发的微服务生态系统。可插拔的插件化设计，提供强大的可插拔的架构来保证基础组件可以被灵活替换。

仓库地址：

GitHub - asim/go-micro: A Go microservices framework

A Go microservices framework. Contribute to asim/go-micro development by creating an account on GitHub.

https://github.com/asim/go-micro

官方文档地址：

go-micro.dev

https://go-micro.dev/

中文文档：

介绍 · go-micro 微服务开发中文手册 · 看云

Micro是一个微服务生态系统。目标是简化分布式系统开发。技术正在迅速发展。现在云计算能够给我们几乎是无限的scale能力，但是采用现有工具来使用scale能力仍然是很困难的。Micro试图去解决这个问题，开发人员首先关注。Micro的核心是简单易用，任何人都可以轻松开始编写微服务。随着您扩展到数百种服务，Micro将提供管理微服务环境所需的基本工具

https://www.kancloud.cn/linimbus/go-micro/529015

5.Kratos（20.1K）

项目简介：哔哩哔哩(B站)开源的一套Go微服务框架，包含大量微服务相关框架及工具。

仓库地址：

https://github.com/go-kratos/kratos

https://github.com/go-kratos/kratos

官方文档地址：

简介 | Kratos

https://go-kratos.dev/docs/

6.CloudWeGo-Kitex（5.7K）

项目简介：字节跳动，KiteX 自 2020.04 正式发布以来，公司内部服务数量 8k+，QPS 过亿。KiteX 是字节跳动框架组研发的下一代高性能、强可扩展性的 Go RPC 框架。除具备丰富的服务治理特性外，相比其他框架还有以下特点：集成了自研的网络库 Netpoll；支持多消息协议（Thrift、Protobuf）和多交互方式（Ping-Pong、Oneway、 Streaming）；提供了更加灵活可扩展的代码生成器。

仓库地址：

https://github.com/cloudwego/kitex

https://github.com/cloudwego/kitex

官方文档地址：

概览 | CloudWeGo

https://www.cloudwego.io/zh/docs/overview/

7.Goa（5K）

项目简介：Goa 是一款用 Go 用于构建微服务的框架，采用独特的设计优先的方法

仓库地址：

https://github.com/goadesign/goa

https://github.com/goadesign/goa

官方文档地址：

https://goa.design/

https://goa.design/

8.Dubbo-go（4.4K）

项目简介：阿里，由Apache 软件基金会官方发布Go 语言加入 Dubbo 生态，架构是基于dubbo的extension模块和分层的代码设计，主要解决 Go 项目与 Java & Dubbo 项目的互通问题，同时也为 Go 项目提供了一种 RPC 与微服务

仓库地址：

https://github.com/apache/dubbo-go

https://github.com/apache/dubbo-go

官方文档地址：

https://dubbogo.github.io/dubbo-go-website/zh-cn/

https://dubbogo.github.io/dubbo-go-website/zh-cn/

9.Jupiter（4K）

项目简介：斗鱼开源的一套微服务治理框架，提供丰富的后台功能，管理应用的资源、配置，应用的性能、配置等可视化。

仓库地址：

https://github.com/douyu/jupiter

https://github.com/douyu/jupiter

官方文档地址：

http://jupiter.douyu.com/

http://jupiter.douyu.com/

10.Tars-go（3.2K）

项目简介：腾讯，Tarsgo是基于Golang编程语言使用Tars协议的高性能RPC框架

仓库地址：

https://github.com/TarsCloud/TarsGo

https://github.com/TarsCloud/TarsGo

官方文档地址：

Table of contents | TarsDocs

https://tarscloud.github.io/TarsDocs/SUMMARY.html#TarsGo

附：北极星-polaris-go

北极星是腾讯开源的服务发现和治理中心，致力于解决分布式或者微服务架构中的服务可见、故障容错、流量控制和安全问题。

仓库地址：

https://github.com/polarismesh/polaris

https://github.com/polarismesh/polaris

官方文档地址：

北极星

https://polarismesh.cn/#/

————————————————

版权声明：本文为CSDN博主「深漂小码哥」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。

原文链接：https://blog.csdn.net/qq2942713658/article/details/112721577

**Go技巧**

golang chan<- 和 <-chan，作为函数参数时

<-chan int 像这样的只能接收值

chan<- int 像这样的只能发送值