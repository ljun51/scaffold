# AWS 服务介绍

## Amazon EC2
Amazon Elastic Compute Cloud（Amazon EC2）提供最广泛、最深入的计算平台，拥有超过 500 个实例，可选择最新的处理器、存储、网络、操作系统和购买模型，以帮助您最好地满足工作负载的需求。我们是首家支持英特尔、AMD 和 Arm 处理器的主要云提供商，既是唯一具有按需 EC2 Mac 实例的云，也是唯一具有 400 Gbps 以太网网络的云。我们为机器学习培训提供最佳性价比，同时也为云中的每个推理实例提供了最低的成本。与任何其他云相比，有更多的 SAP、高性能计算 (HPC)、机器学习 (ML) 和 Windows 工作负载在 AWS 上运行。

## Amazon S3
Amazon Simple Storage Service (Amazon S3) 是一种对象存储服务，提供行业领先的可扩展性、数据可用性、安全性和性能。各种规模和行业的客户可以为几乎任何使用案例存储和保护任意数量的数据，例如数据湖、云原生应用程序和移动应用程序。借助高成本效益的存储类和易于使用的管理功能，您可以优化成本、组织数据并配置精细调整过的访问控制，从而满足特定的业务、组织和合规性要求。

## Amazon Aurora
Amazon Aurora 在全球范围内提供无与伦比的高性能和可用性，完全兼容 MySQL 和 PostgreSQL，而成本仅为商业数据库的十分之一。Aurora 的吞吐量是 MySQL 的 5 倍，是 PostgreSQL 的 3 倍。Aurora 拥有广泛的合规性标准和一流的安全功能。Aurora 通过使数据在 3 个可用区内持久耐用（客户只需支付 1 个副本的费用）来提供存储弹性。Aurora 的可用性高达 99.99%，跨 AWS 区域部署时，客户可以使用全球数据库访问本地读取性能。使用无服务器功能，Aurora 可在不到一秒钟的时间内扩展到能够处理数十万个事务的能力。Aurora 与 Amazon Redshift 的零 ETL 集成可近乎实时地对事务数据进行分析。

## Amazon DynamoDB
Amazon DynamoDB 是一项无服务器的 NoSQL、完全托管的数据库，在任何规模下均具有个位数毫秒级的性能，您可以通过它来开发任何规模的现代应用程序。作为无服务器数据库，您只需按使用量为其付费，DynamoDB 可以扩展到零，没有冷启动，没有版本升级，没有维护窗口，没有修补，也没有停机维护。DynamoDB 提供一系列广泛的安全控制措施和合规性标准。对于全球分布式应用程序，DynamoDB 全局表是一个多区域、多活动数据库，具有 99.999% 的可用性 SLA 和更高的弹性。托管备份、时间点恢复等功能有助于确保 DynamoDB 的可靠性。借助 DynamoDB 流，您可以构建无服务器的事件驱动型应用程序。

## Amazon RDS
Amazon Relational Database Service（Amazon RDS）是一个托管式服务的集合，可以简化在云中设置、运营和扩展数据库的过程。提供八种热门引擎以供选择：Amazon Aurora PostgreSQL 兼容版、Amazon Aurora MySQL 兼容版、RDS for PostgreSQL、RDS for MySQL、RDS for MariaDB、RDS for SQL Server、RDS for Oracle 和 RDS for Db2。 使用 Amazon RDS 在 AWS Outposts 上进行本地部署，或者使用 Amazon RDS Custom 提高对底层操作系统和数据库环境的访问权限。

## AWS Lambda
AWS Lambda 是一项无服务器事件驱动型计算服务，该服务使您可以运行几乎任何类型的应用程序或后端服务的代码，而无需预置或管理服务器。您可以从 200 多个 AWS 服务和软件即服务 (SaaS) 应用程序中触发 Lambda，且只需按您的使用量付费。

## Amazon VPC
Amazon Virtual Private Cloud (Amazon VPC) 让您能够全面地控制自己的虚拟网络环境，包括资源放置、连接性和安全性。首先在 AWS 服务控制台中设置 VPC。然后，向其中添加资源，例如 Amazon Elastic Compute Cloud (EC2) 和 Amazon Relational Database Service (RDS) 实例。最后，您可以定义 VPC 相互之间以及跨账户、可用区或 AWS 区域通信的方式。

## Amazon Lightsail
Amazon Lightsail 以经济实惠的月度价格提供易于使用的虚拟专用服务器 (VPS) 实例、容器、存储、数据库等。虚拟专用服务器，价格低廉且可预测。只需几次点击即可创建网站或应用程序。自动配置联网、访问和安全环境。随着您的发展轻松扩展，或将您的资源迁移到更广泛的 AWS 生态系统，例如 Amazon EC2。

使用案例
* 启动简单的 Web 应用程序。使用预配置的开发堆栈，如 LAMP、Nginx、MEAN 和 Node.js，以快速轻松地上网。
* 创建自定义网站。使用预配置的应用程序，如 WordPress、Magento、Prestashop 和 Joomla，只需几次点击，就可以构建和个性化您的博客、电子商务或个人网站。
* 构建小型业务应用程序。启动业务线软件，如文件存储和共享、备份、财务和会计软件等等。
* 启动测试环境。轻松创建和删除开发沙箱和测试环境，您可以在其中无风险地尝试新想法。

## Amazon SageMaker
Amazon SageMaker 通过完全托管的基础设施、工具和工作流程为任何用例构建、训练和部署机器学习（ML）模型。Amazon SageMaker 是一项完全托管的服务，它汇集了大量工具，可为任何使用案例提供高性能、低成本的机器学习（ML）。借助 SageMaker，您可以使用笔记本、调试器、分析器、管道、MLOps 等工具大规模构建、训练和部署机器学习模型——这一切都在一个集成式开发环境（IDE）中完成。SageMaker 通过简化的访问控制和机器学习项目的透明度来支持治理要求。此外，您可以使用专门构建的工具来微调、实验、再训练和部署基础模型，构建自己的基础模型（在海量数据集上训练过的大型模型）。 SageMaker 提供对数百个预训练模型的访问权限，包括公开的基础模型，您只需点击几下即可部署这些模型。