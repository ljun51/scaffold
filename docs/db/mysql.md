# MySQL

## mysql docker
```shell
docker run -d --name mysql5 \
  --restart=always \
  -p 3306:3306 \
  -e MYSQL_ROOT_PASSWORD=123456 \
  -e TZ=Asia/Shanghai \
  -v ./db/mysql5/conf:/etc/mysql/conf.d \
  -v ./db/mysql5/data:/var/lib/mysql \
  mysql:5.7.44 \
  --lower_case_table_names=1 \
  --max_connections=1000
```

```shell
docker run -d --name mysql8 \
  --restart=always \
  -p 3306:3306 \
  -e TZ=Asia/Shanghai \
  -e MYSQL_ROOT_PASSWORD=123456 \
  -v ./db/mysql8/conf:/etc/mysql/conf.d \
  -v ./db/mysql8/data:/var/lib/mysql \
  mysql:8.0.34 \
  --lower_case_table_names=1 \
  --max_connections=1000
```

## Buffer Pool、Redo Log Buffer 和undo log、redo log、bin log 概念以及关系？
* Buffer Pool 是 MySQL 的一个非常重要的组件，因为针对数据库的增删改操作都是在 Buffer Pool 中完成的
* Undo log 记录的是数据操作前的样子
* redo log 记录的是数据被操作后的样子（redo log 是 Innodb 存储引擎特有）
* bin log 记录的是整个操作记录（这个对于主从复制具有非常重要的意义）

## 从准备更新一条数据到事务的提交的流程描述？
* 首先执行器根据 MySQL 的执行计划来查询数据，先是从缓存池中查询数据，如果没有就会去数据库中查询，如果查询到了就将其放到缓存池中
* 在数据被缓存到缓存池的同时，会写入 undo log 日志文件
* 更新的动作是在 BufferPool 中完成的，同时会将更新后的数据添加到 redo log buffer 中
* 完成以后就可以提交事务，在提交的同时会做以下三件事 
  * 将redo log buffer中的数据刷入到 redo log 文件中
  * 将本次操作记录写入到 bin log文件中
  * 将 bin log 文件名字和更新内容在 bin log 中的位置记录到redo log中，同时在 redo log 最后添加 commit 标记#

## MyISAM 和 InnoDB
**MyISAM** 和 **InnoDB** 是 **MySQL** 中两种常用的存储引擎。让我们比较一下它们的区别：

1. **存储引擎类型**：
   - **MyISAM** 是 **MySQL** 的默认存储引擎，但在 2009 年后被 **InnoDB** 替代。
   - **InnoDB** 是一种支持事务和外键的存储引擎。

2. **锁定机制**：
   - **MyISAM** 使用表级锁定，这意味着在执行写操作时会锁定整个表。
   - **InnoDB** 支持行级锁定，允许更高的并发性。

3. **外键支持**：
   - **MyISAM** 不支持外键。
   - **InnoDB** 支持外键，可以维护数据完整性。

4. **ACID 特性**：
   - **MyISAM** 不支持事务和 ACID 特性。
   - **InnoDB** 支持事务、回滚和数据一致性。

5. **性能**：
   - **MyISAM** 在读操作上通常比 **InnoDB** 更快，但在写操作上较慢。
   - **InnoDB** 在处理大量写操作时更稳定。

6. **可靠性**：
   - **MyISAM** 不支持崩溃恢复。
   - **InnoDB** 支持崩溃恢复，数据更可靠。

7. **缓存和索引**：
   - **MyISAM** 使用键缓存来加速索引查找。
   - **InnoDB** 使用缓冲池来缓存数据和索引。

总之，如果您需要事务支持、外键完整性和更高的可靠性，应该选择 **InnoDB**。如果您对读操作的性能更关心，可以考虑使用 **MyISAM**。¹²³

## MySQL 锁的类型
MySQL 中有多种类型的锁，每种锁都有不同的特点和适用场景。以下是 MySQL 常见的锁类型：

1. **表级锁（Table-level lock）**：
   - 表级锁直接给整个表添加锁。
   - 适用于简单的读写操作，但会影响并发度。
   - 开销小，加锁快，但不适合高并发场景。

2. **行级锁（Record Locks）**：
   - 行级锁用于锁定表中的某一行数据。
   - 允许多个事务同时读取不同行的数据，但在写操作时会阻塞其他事务。
   - 提高并发度，但开销较大。

3. **页级锁（Page-level lock）**：
   - 页级锁介于表级锁和行级锁之间。
   - 锁定相邻的一组记录，适用于某些特定场景。
   - 会出现死锁，需要谨慎使用。

4. **意向锁（Intention Locks）**：
   - 意向锁用于协调表级锁和行级锁之间的关系。
   - 分为意向共享锁和意向排他锁。
     1. **意向共享锁（IS锁）**：
        - **IS锁**用于表明一个事务计划在某个表上设置共享锁（读锁）。
        - 当一个事务在某一行上设置共享锁时，它会首先请求**IS锁**。
        - **IS锁**不会阻止其他事务设置共享锁，但会阻止其他事务设置排他锁。
     2. **意向排他锁（IX锁）**：
        - **IX锁**用于表明一个事务计划在某个表上设置排他锁（写锁）。
        - 当一个事务在某一行上设置排他锁时，它会首先请求**IX锁**。
        - **IX锁**不会阻止其他事务设置共享锁，但会阻止其他事务设置排他锁。
     3. **适用场景**：
        - **意向锁**通常在表级锁和行级锁之间起到协调作用。
        - 它们不会直接影响数据的读写，而是用于通知其他事务某个表上已经存在了共享锁或排他锁。

总之，根据不同的需求和场景，我们可以选择不同类型的锁来控制并发访问和保证数据一致性。

两个维度结合来看：

* 共享锁(行锁):Shared Locks 
  * 读锁(s锁),多个事务对于同一数据可以共享访问,不能操作修改
  * 使用方法: 
    * 加锁:SELECT * FROM table WHERE id=1 LOCK IN SHARE MODE
    * 释锁:COMMIT/ROLLBACK
* 排他锁（行锁）：Exclusive Locks 
  * 写锁(X锁)，互斥锁/独占锁,事务获取了一个数据的X锁，其他事务就不能再获取该行的读锁和写锁（S锁、X锁），只有获取了该排他锁的事务是可以对数据行进行读取和修改
  * 使用方法: 
    * DELETE/ UPDATE/ INSERT -- 加锁
    * SELECT * FROM table WHERE ... FOR UPDATE -- 加锁
    * COMMIT/ROLLBACK -- 释锁
* 意向锁(IS) 
  * InnoDB自动处理，不需要用户干预。

## mysql常用操作

### 修改密码

ALTER USER 'root'@'%' IDENTIFIED BY 'ljun51';

CREATE USER 'ljun51'@'%' IDENTIFIED BY 'ljun51';

GRANT ALL PRIVILEGES ON *.* TO 'ljun51'@'%' WITH GRANT OPTION;

### 授权

GRANT ALL PRIVILEGES ON *.* TO 'myuser'@'%' IDENTIFIED BY 'mypassword';

GRANT SELECT ON *.* TO ‘abc123456_ro’@‘%’ IDENTIFIED BY ‘abc123456';

FLUSH PRIVILEGES;

create user 'ljun51'@'%' identified by 'ljun51';

grant all privileges on *.* to 'ljun51'@'%';

revoke all on *.* to 'abc123456_ro'@'118.118.116.%';

FLUSH PRIVILEGES;

### 跳过授权表

mysqld_safe --skip-grant-tables &

update user set password=PASSWORD("abc123456") where User='root';

flush privileges;

### 复制表结构（不包含约束关系）

create table gw_api_log_20170503 select * from gw_api_log;

### 复制表结构（包含约束关系）

create table gw_api_log_20170502 like gw_api_log;

skip-name-resolve

在linux下配置文件是/etc/my.cnf，在windows下配置文件是mysql安装目录下的my.ini文件。注意该配置是加在 [mysqld]下面，在更改配置并保存后，然后重启mysql并远程连接测试，一切恢复如初。

lower_case_table_names = 1 表名不区分大小写，0区分大小写

### 取消外键约束

SET foreign_key_checks = 0;

DELETE FROM qrtz_job_details WHERE 1=1;

### 启用外键约束

SET foreign_key_checks = 1;

federated

> show engines;

> create table t_1 (id int not null auto_increment primary key, c_str char(20) not null) engine federated connection = 'mysql://root:123456@192.168.0.233:3307/t_boy/t_tableB';

### HOW TO FORCE UNLOCK for locked tables in MySQL

1) Let's see the list of locked tables

mysql> show open tables where in_use>0;

2) Let's see the list of the current processes, one of them is locking your table(s)

mysql> show processlist;

3) Kill one of these processes

mysql> kill <put_process_id_here>;

### 导入txt文件

LOAD DATA LOCAL INFILE 'C://stu.txt' INTO TABLE stu;

导入csv文件：

load data local infile '/Users/lijun/Downloads/uc_swjg.csv'

into table abc123456_uc.uc_swjg

fields terminated by ',' optionally enclosed by '"' escaped by '"'

lines terminated by '\r\n';

### 导出csv文件

**select * from** tablename

**into outfile**'/tmp/a.csv'fields**terminatedby**","**escapedby**''**optionallyenclosedby**''**linesterminatedby**'\n';

### 看用户建立的表

select table_name from user_tables; //当前用户的表

select table_name from all_tables; //所有用户的表

select table_name from dba_tables; //包括系统表

select * from user_indexes //可以查询出所有的用户表索引

desc table_name; //查看表结构

查所有用户的表在all_tables

主键名称、外键在all_constraints

索引在all_indexes

但主键也会成为索引，所以主键也会在all_indexes里面。

具体需要的字段可以DESC下这几个view，dba登陆的话可以把all换成dba

* Table name and comment
```mysql
SELECT table_name, comment FROM information_schema.tables WHERE table_schema = 'healthy_home';
```

1、查找表的所有索引（包括索引名，类型，构成列）：

select t.*,i.index_type from user_ind_columns t,user_indexes i where t.index_name = i.index_name and t.table_name = i.table_name and t.table_name = 要查询的表

show index from db_name.table_name;

2、查找表的主键（包括名称，构成列）：

select cu.* from user_cons_columns cu, user_constraints au where cu.constraint_name = au.constraint_name and au.constraint_type = 'P' and au.table_name = 要查询的表

3、查找表的唯一性约束（包括名称，构成列）：

select column_name from user_cons_columns cu, user_constraints au where cu.constraint_name = au.constraint_name and au.constraint_type = 'U' and au.table_name = 要查询的表

4、查找表的外键（包括名称，引用表的表名和对应的键名，下面是分成多步查询）：

select * from user_constraints c where c.constraint_type = 'R' and c.table_name = 要查询的表

查询外键约束的列名：

select * from user_cons_columns cl where cl.constraint_name = 外键名称

查询引用表的键的列名：

select * from user_cons_columns cl where cl.constraint_name = 外键引用表的键名

5、查询表的所有列及其属性

select t.*,c.COMMENTS from user_tab_columns t,user_col_comments c where t.table_name = c.table_name and t.column_name = c.column_name and t.table_name = 要查询的表

6、查看表结构：

看字段名与数据类型

select * from cols WHERE TABLE_name=upper( 'table_name ')

查看主键：

select * from user_constraints where constraint_type= 'P'

and TABLE_name=upper( 'table_name ')

另外：

在SQLPLUS中,用 desc tablename

在PL/SQL工具中,可以通过视图user_tab_columns 查看

select * from user_tab_columns where table_name = upper(tablename)

查看备注：

- -查看表的comment

select * from all_tab_comments where table_name= upper(tablename) ;

- -查看列的comment

select * from all_col_comments where table_name=upper(tablename);

主从复制落后时间

使用`show slave status`命令查看参数Seconds_Behind_Master，如果为0表示没有延后。

查看数据表使用的存储引擎：

select table_catalog, table_schema, table_name, engine from tables where table_schema like 'abc123456%' and engine = 'MyISAM';

## mysql backup

1.1 导出所有数据库

mysqldump --column-statistics=0 -h主机IP -u用户名 -p密码 --all-databases --skip-lock-tables > dump20200328.sql

1.2 导出指定数据库

mysqldump --column-statistics=0 -h主机IP -u用户名 -p密码 --databases 数据库1 数据库2... > dump20200328.sql

1.3 导出数据库数据，不含表结构

mysqldump -h118.118.116.208 -uabc123456 -p --databases abc123456_client --replace --no-create-info > abc123456_client.sql

- -replace: 避免主键冲突
- -no-create-info: 仅导出数据

1.4 导出数据库表结构，不含数据

mysqldump -h118.118.116.208 -uabc123456 -p --databases abc123456_client --no-data > abc123456_client.sql

1.5 导出指定表

mysqldump -h118.118.116.208 -uabc123456 -pabc123456 --databases abc123456_client --tables client_log client_area_url > abc123456_client.sql

1.6 where条件导出
> mysqldump -h192.168.100.163 -uroot -pYkhl@2023 --databases gw_fj --tables t_cxjm_mzzy -t --where="F_CJSJ > '2022-12-01'" > t_cxjm_mzzy.sql

## mysql restore

2.1 先登录数据库，在执行source恢复
> source dump20200328.sql

2.2 mysql cli
> mysql -h182.92.107.203 -P53406 -uroot -p'!@#qwe123' gw_fj < t_cxjm_mzzy.sql

## Insert两种形式
### INSERT INTO SELECT

语句形式为：`Insert into Table2(field1,field2,...) select value1,value2,... from Table1`

要求目标表Table2必须存在，由于目标表Table2已经存在，所以我们除了插入源表Table1的字段外，还可以插入常量。

### SELECT INTO FROM
语句形式为：`SELECT vale1, value2 into Table2 from Table1`

要求目标表Table2不存在，因为在插入时会自动创建表Table2，并将Table1中指定字段数据复制到Table2中。

类似的：`create table table1 as select * from table2`
```sql
  create table TABLE_NAME as select * from OTHER_TABLE;
  insert into TABLE_NAME select * from OTHER_NAME;
```

## INSERT ... ON DUPLICATE KEY UPDATE Statement
语法参见 *[官网][2]*。

## REPLACE Statement
```sql
REPLACE [LOW_PRIORITY | DELAYED]
    [INTO] tbl_name
    [PARTITION (partition_name [, partition_name] ...)]
    [(col_name [, col_name] ...)]
    { {VALUES | VALUE} (value_list) [, (value_list)] ...
      |
      VALUES row_constructor_list
    }

REPLACE [LOW_PRIORITY | DELAYED]
    [INTO] tbl_name
    [PARTITION (partition_name [, partition_name] ...)]
    SET assignment_list

REPLACE [LOW_PRIORITY | DELAYED]
    [INTO] tbl_name
    [PARTITION (partition_name [, partition_name] ...)]
    [(col_name [, col_name] ...)]
    {SELECT ... | TABLE table_name}

value:
    {expr | DEFAULT}

value_list:
    value [, value] ...

row_constructor_list:
    ROW(value_list)[, ROW(value_list)][, ...]

assignment:
    col_name = value

assignment_list:
    assignment [, assignment] ...
```

## UPDATE JOIN
MySQL UPDATE JOIN的语法如下：
```sql
UPDATE T1, T2,
[INNER JOIN | LEFT JOIN] T1 ON T1.C1 = T2. C1
SET T1.C2 = T2.C2, 
    T2.C3 = expr
WHERE condition
```
让我们更详细地看看MySQL UPDATE JOIN语法：
* 首先，在UPDATE子句之后，指定主表(T1)和希望主表连接表(T2)。 请注意，必须在UPDATE子句之后至少指定一个表。UPDATE子句后未指定的表中的数据未更新。
* 第二，指定一种要使用的连接，即INNER JOIN或LEFT JOIN和连接条件。JOIN子句必须出现在UPDATE子句之后。
* 第三，要为要更新的T1和/或T2表中的列分配新值。
* 第四，WHERE子句中的条件用于指定要更新的行。

## 数据误删及恢复
### 方式一：通过全备份 + 增量sql恢复
#### 工作场景
1. MySQL 数据库每晚 12:00 自动完全备份。
2. 某天早上上班，9点的时候，一同事犯晕 drop 了一个数据库！
3. 需要紧急恢复！可利用备份的数据文件以及增量的 binlog 文件进行数据恢复。

#### 数据恢复思路
1. 利用全备的 sql 文件中记录的CHANGE MASTER语句，binlog 文件及其位置点信息，找出binlog文件中增量的那部分。
2. 用 mysqlbinlog 命令将上述的binlog文件导出为sql文件，并剔除其中的drop语句。
3. 通过全备文件和增量 binlog 文件的导出 sql 文件，就可以恢复到完整的数据。

确保 MySQL 开启了 binlog 日志功能，在`/etc/my.cnf`文件里的`[mysqld]`区块添加下列配置，然后重启 MySQL。
```cnf
log-bin=mysql-bin
```

#### 模拟步骤

* 建表
```sql
create table ops.customers(
 id int not null auto_increment,
 name char(20) not null,
 age int not null,
 primary key(id)
 )engine=InnoDB;
```

* 新增数据
```sql
insert into ops.customers values(1,"wangbo","24"),(2,"guohui","22"),(3,"zhangheng","27");
```

* 全备份
```shell
mysqldump -uroot -p -B -F -R -x --master-data=2 ops | gzip > ops_$(date +%F).sql.gz
```

  参数说明：
  -B：指定数据库
  -F：刷新日志
  -R：备份存储过程等
  -x：锁表
  --master-data：在备份语句里添加 CHANGE MASTER 语句以及 binlog 文件及位置点信息

* 再次新增数据
```sql
insert into ops.customers values(4,"liupeng","21"),(5,"xiaoda","31"),(6,"fuaiai","26");
```

* 模拟误操作
```sql
drop database ops;
```

此时，全备之后到误操作时刻之间，用户写入的数据在binlog中，需要恢复出来!

* 查看全备之后新增的 binlog 文件
```shell
$ ls
ops_2023-12-25.sql.gz

$ gzip -d ops_2023-12-25.sql.gz

$ ls
ops_2023-12-25.sql

$ grep CHANGE ops_2023-12-25.sql
-- CHANGE MASTER TO MASTER_LOG_FILE='mysql-bin.000005', MASTER_LOG_POS=157;
```

* 查看 binlog 文件位置
```sql
mysql> show variables like '%log_bin%';
+---------------------------------+--------------------------------+
| Variable_name                   | Value                          |
+---------------------------------+--------------------------------+
| log_bin                         | ON                             |
| log_bin_basename                | /var/log/mysql/mysql-bin       |
| log_bin_index                   | /var/log/mysql/mysql-bin.index |
| log_bin_trust_function_creators | OFF                            |
| log_bin_use_v1_row_events       | OFF                            |
| sql_log_bin                     | ON                             |
+---------------------------------+--------------------------------+
6 rows in set (0.00 sec)
```

* 导出为sql文件，并用 vim 剔除其中的`drop`语句
```shell
mysqlbinlog --skip-gtids /var/log/mysql/mysql-bin.000005 > 005bin.sql
```
**用 vim 删除 drop 语句**

* 恢复全备份数据
```shell
mysql -uroot -p < ops_2023-12-25.sql
```

* 恢复增量数据
```shell
mysql -uroot -p < 005bin.sql
```

* 验证数据是否已恢复
```sql
mysql> select * from ops.customers;
+----+-----------+-----+
| id | name      | age |
+----+-----------+-----+
|  1 | wangbo    |  24 |
|  2 | guohui    |  22 |
|  3 | zhangheng |  27 |
|  4 | liupeng   |  21 |
|  5 | xiaoda    |  31 |
|  6 | fuaiai    |  26 |
+----+-----------+-----+
6 rows in set (0.00 sec)
```

### 方式二：通过 position 恢复
* 需要开启 binlog

* 模拟误操作
```sql
delete * from ops.customers;
```

* 转为可读的日志格式，binlog 使用了 Base64 编码
```shell
  mysqlbinlog \
  --start-datetime="2023-12-25 11:06:00" \
  --stop-datetime="2023-12-25 11:07:00" \
  --base64-output=DECODE-ROWS \
  -vv \
  --skip-gtids  \
  /var/log/mysql/mysql-bin.000005 \
  --database=ops \
```
> t.txt

* 查看误操作前的`end_log_pos`。
```shell
vim t.txt

BEGIN
/*!*/;
# at 3955
#231225 11:06:32 server id 1  end_log_pos 4018 CRC32 0xb3ae48cf         Table_map: `ops`.`customers` mapped to number 139
# at 4018
#231225 11:06:32 server id 1  end_log_pos 4153 CRC32 0x90224f9d         Delete_rows: table id 139 flags: STMT_END_F
### DELETE FROM `ops`.`customers`
### WHERE
###   @1=1 /* INT meta=0 nullable=0 is_null=0 */
###   @2='wangbo' /* STRING(80) meta=65104 nullable=0 is_null=0 */
###   @3=24 /* INT meta=0 nullable=0 is_null=0 */
### DELETE FROM `ops`.`customers`
### WHERE
###   @1=2 /* INT meta=0 nullable=0 is_null=0 */
###   @2='guohui' /* STRING(80) meta=65104 nullable=0 is_null=0 */
###   @3=22 /* INT meta=0 nullable=0 is_null=0 */
### DELETE FROM `ops`.`customers`
### WHERE
###   @1=3 /* INT meta=0 nullable=0 is_null=0 */
###   @2='zhangheng' /* STRING(80) meta=65104 nullable=0 is_null=0 */
###   @3=27 /* INT meta=0 nullable=0 is_null=0 */
### DELETE FROM `ops`.`customers`
### WHERE
###   @1=4 /* INT meta=0 nullable=0 is_null=0 */
###   @2='liupeng' /* STRING(80) meta=65104 nullable=0 is_null=0 */
###   @3=21 /* INT meta=0 nullable=0 is_null=0 */
### DELETE FROM `ops`.`customers`
### WHERE
###   @1=5 /* INT meta=0 nullable=0 is_null=0 */
###   @2='xiaoda' /* STRING(80) meta=65104 nullable=0 is_null=0 */
###   @3=31 /* INT meta=0 nullable=0 is_null=0 */
### DELETE FROM `ops`.`customers`
### WHERE
###   @1=6 /* INT meta=0 nullable=0 is_null=0 */
###   @2='fuaiai' /* STRING(80) meta=65104 nullable=0 is_null=0 */
###   @3=26 /* INT meta=0 nullable=0 is_null=0 */
# at 4153
#231225 11:06:32 server id 1  end_log_pos 4184 CRC32 0x2897d472         Xid = 228
COMMIT/*!*/;
DELIMITER ;
# End of log file
/*!50003 SET COMPLETION_TYPE=@OLD_COMPLETION_TYPE*/;
/*!50530 SET @@SESSION.PSEUDO_SLAVE_MODE=0*/;
```
可以看到 delete 之前的 end_log_pos 为 `4513`。

* 查看所有 end_log_pos 。
```sql
mysql> show binlog events in 'mysql-bin.000005';
+------------------+------+----------------+-----------+-------------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| Log_name         | Pos  | Event_type     | Server_id | End_log_pos | Info                                                                                                                                                                                                                                               |
+------------------+------+----------------+-----------+-------------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| mysql-bin.000005 |    4 | Format_desc    |         1 |         126 | Server ver: 8.0.35-0ubuntu0.22.04.1, Binlog ver: 4                                                                                                                                                                                                 |
| mysql-bin.000005 |  126 | Previous_gtids |         1 |         157 |                                                                                                                                                                                                                                                    |
| mysql-bin.000005 |  157 | Anonymous_Gtid |         1 |         236 | SET @@SESSION.GTID_NEXT= 'ANONYMOUS'                                                                                                                                                                                                               |
| mysql-bin.000005 |  236 | Query          |         1 |         310 | BEGIN                                                                                                                                                                                                                                              |
......                                                                                                                                                                                                                             |
| mysql-bin.000005 | 3805 | Anonymous_Gtid |         1 |        3884 | SET @@SESSION.GTID_NEXT= 'ANONYMOUS'                                                                                                                                                                                                               |
| mysql-bin.000005 | 3884 | Query          |         1 |        3955 | BEGIN                                                                                                                                                                                                                                              |
| mysql-bin.000005 | 3955 | Table_map      |         1 |        4018 | table_id: 139 (ops.customers)                                                                                                                                                                                                                      |
| mysql-bin.000005 | 4018 | Delete_rows    |         1 |        4153 | table_id: 139 flags: STMT_END_F                                                                                                                                                                                                                    |
| mysql-bin.000005 | 4153 | Xid            |         1 |        4184 | COMMIT /* xid=228 */                                                                                                                                                                                                                               |
+------------------+------+----------------+-----------+-------------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
54 rows in set (0.01 sec)
```

* 生成恢复脚本
```shell
mysqlbinlog --no-defaults --start-position=126 --stop-position=4018 /var/log/mysql/mysql-bin.000005 > t.sql
```

* 开始恢复
```shell
mysql -uroot -p
mysql > source t.sql
```

* 验证数据是否已恢复
```sql
mysql> select * from ops.customers;
+----+-----------+-----+
| id | name      | age |
+----+-----------+-----+
|  1 | wangbo    |  24 |
|  2 | guohui    |  22 |
|  3 | zhangheng |  27 |
|  4 | liupeng   |  21 |
|  5 | xiaoda    |  31 |
|  6 | fuaiai    |  26 |
+----+-----------+-----+
6 rows in set (0.00 sec)
```

### 方式三：通过 datetime 恢复
通过 datetime 和 position 恢复相差不大，主要的区分在 mysqlbinlog 的参数。
```
--database=ops 仅导出指定需要恢复的数据库
--start-datetime --stop-datetime 开始时间，结束时间
--start-position --stop-position 开始 position，结束 position
```

完整的mysqlbinlog命令参见 *[官网][1]*。

[1]: https://dev.mysql.com/doc/refman/8.0/en/mysqlbinlog.html
[2]: https://dev.mysql.com/doc/refman/8.0/en/insert-on-duplicate.html