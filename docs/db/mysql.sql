show databases;
show tables;
select database();
-- 查看端口
show variables like 'port';
-- 查看数据库编码
show variables like 'character%';
-- 查看字符集
show full columns from health_crowd.generate_tag_record;
-- 修改字符集
alter table health_crowd.generate_tag_record convert to character set utf8mb4 collate utf8mb4_general_ci;
-- 是否开启慢SQL
show variables like 'slow_query_log';
show variables like 'slow_query_log_file';
show variables like 'long_query_time';
-- 查看binlog存放日志文件目录
show variables like '%datadir%';
-- 是否开启binlog
show variables like '%log_bin%';
-- 刷新binlog
mysql -uroot -pljun51 -e "flush logs"
show variables like '%timeout%';
show global variables like '%secure_file_priv%';
show variables like '%max_connect_errors%';
SET GLOBAL max_connect_errors=10000;
SET GLOBAL max_connections=2000;
show variables like 'max_connections';
SHOW VARIABLES LIKE 'group_concat_max_len';
SET GLOBAL group_concat_max_len=4294967295;
SET SESSION group_concat_max_len=4294967295;
show variables like '%max_life%';
-- set GLOBAL max_connections=1000;
-- 查看数据库大小
use information_schema;
select concat(round(sum(data_length) / (1024 * 1024), 2) + round(sum(index_length) / (1024 * 1024), 2),
              'MB') as 'DB Size'
from tables
where table_schema = 'healthy_home';
-- 查看表大小
SELECT CONCAT(table_schema,'.',table_name) AS 'Table Name',
table_rows AS 'Number of Rows',
CONCAT(ROUND(data_length/(1024*1024*1024),4),'G') AS 'Data Size',
CONCAT(ROUND(index_length/(1024*1024*1024),4),'G') AS 'Index Size',
CONCAT(ROUND((data_length+index_length)/(1024*1024*1024),4),'G') AS'Total'
FROM information_schema.TABLES WHERE table_schema LIKE 'his3newzs' order by Total DESC ;

-- 获取数据库表数量
SELECT COUNT(*) TABLES, table_schema FROM information_schema.TABLES
WHERE table_schema = 'healthy_home' GROUP BY table_schema;

select id, db, user, host, command, time, state, info
from information_schema.processlist
where command != 'Sleep'
order by time desc ;

-- 锁表处理
show OPEN TABLES where In_use > 0;
SELECT * FROM information_schema.INNODB_TRX;
show full processlist;
kill 4308;
select version();
show variables like '%sql_mode%';
show variables like 'performance_schema';
-- 支持的存储引擎
show engines;
select * from information_schema.engines;
-- 查看mysql内存，缓存的相关配置
show global variables like '%sort_buffer_size%';

-- performance_schema库相关
use performance_schema;
SHOW TABLES FROM performance_schema;
-- 所有线程
select name, type, thread_id, processlist_id from performance_schema.threads;
select * from performance_schema.threads;
-- 语句事件记录表
show tables like 'events_statement%';
-- 等待事件记录表
show tables like 'events_wait%';
SELECT * FROM performance_schema.events_waits_current;
SELECT * FROM performance_schema.events_waits_history;
SELECT * FROM performance_schema.events_waits_history_long;
-- 阶段事件记录表
show tables like 'events_stage%';
-- 事务事件记录表
show tables like 'events_transaction%';
-- 监视文件系统层调用的表
show tables LIKE '%file%';
-- 监视内存使用的表
show tables like '%memory%';
select * from performance_schema.memory_summary_by_account_by_event_name order by HIGH_NUMBER_OF_BYTES_USED desc;
select * from performance_schema.memory_summary_by_host_by_event_name;
select * from performance_schema.memory_summary_by_thread_by_event_name;
select * from performance_schema.memory_summary_by_user_by_event_name;
select * from performance_schema.memory_summary_global_by_event_name order by HIGH_NUMBER_OF_BYTES_USED desc;
-- 动态对performance_schema配置的表
show tables like '%setup%';
select THREAD_ID,
       EVENT_NAME,
       SOURCE,
       sys.format_time(TIMER_WAIT),
       sys.format_time(LOCK_TIME),
       SQL_TEXT,
       CURRent_schema,
       message_text,
       rows_affected,
       rows_sent,
       rows_examined
from performance_schema.events_statements_history
where current_schema != 'performance_schema'
order by timer_wait desc
limit 10;
select * from performance_schema.events_statements_history;
select * from performance_schema.events_statements_summary_by_digest;
-- topsql排序分析
-- 排序占用cpu高，可以从平均单条sql指纹的资源占用和并发条数来统计总的一个消耗，这里可以用count_star（可以每次采集后置0）和sum_sort_rows来计算总的排序行数
SELECT DIGEST_TEXT,
       COUNT_STAR,
       SUM_SORT_ROWS,
       TRUNCATE(AVG_TIMER_WAIT / 1000000000000, 6) as AVG_TIMER_WAIT,
       SUM_ROWS_EXAMINED,
       SUM_CREATED_TMP_TABLES,
       SUM_CREATED_TMP_DISK_TABLES,
       SUM_ROWS_SENT,
       FIRST_SEEN,
       LAST_SEEN
FROM performance_schema.events_statements_summary_by_digest
ORDER BY SUM_SORT_ROWS DESC
limit 10;
-- topsql耗时分析
-- 定位top耗时长的前5条sql
SELECT EVENT_ID, TRUNCATE(TIMER_WAIT / 1000000000000, 6) as Duration, SQL_TEXT
FROM performance_schema.events_statements_history_long
order by Duration desc
limit 5;
-- 根据对应的top5耗时长的sql的event_id查看该sql具体耗时在哪个地方
SELECT event_name AS Stage, TRUNCATE(TIMER_WAIT/1000000000000,6) AS Duration FROM performance_schema.events_stages_history_long; -- WHERE NESTING_EVENT_ID=1436;
-- 统计top表的逻辑io次数（根据增删改查的请求频率来了解当前数据库的一个压力情况）
SELECT object_schema                                              AS tb_schema,
       object_name                                                AS table_name,
       count_star                                                 AS rows_io_total,
       count_read                                                 AS rows_read,
       count_write                                                AS rows_write,
       count_fetch                                                AS rows_fetchs,
       count_insert                                               AS rows_inserts,
       count_update                                               AS rows_updates,
       count_delete                                               AS rows_deletes,
       CONCAT(ROUND(sum_timer_fetch / 3600000000000000, 2), 'h')  AS fetch_latency,
       CONCAT(ROUND(sum_timer_insert / 3600000000000000, 2), 'h') AS insert_latency,
       CONCAT(ROUND(sum_timer_update / 3600000000000000, 2), 'h') AS update_latency,
       CONCAT(ROUND(sum_timer_delete / 3600000000000000, 2), 'h') AS delete_latency
FROM performance_schema.table_io_waits_summary_by_table
ORDER BY sum_timer_wait DESC
limit 10;
-- 统计mysql物理文件的物理io写入字节数(可以定位出一个表的读写io占比和实际的平均写入量来判断占用io资源)
select * from sys.io_global_by_file_by_bytes limit 10;

-- sys库相关
use sys;
show tables from sys;
select * from sys.version;
-- 大部分视图成对出现，带'x$'前缀表示视图显示的是原始数据，不带'x$'表示经过单位换算后的数据
select * from host_summary_by_file_io;
select * from x$host_summary_by_file_io;
select * from session where command = 'query';
select * from processlist;
-- 是否有锁等待
select * from innodb_lock_waits;
-- 缓冲池热点数据
select * from innodb_buffer_stats_by_schema;
-- 查看冗余索引
select * from schema_redundant_indexes;
-- 查看未使用的索引
select * from schema_unused_indexes;
-- 查询表的增、删、改、查数据量和I/O耗时统计信息
select * from schema_table_statistics_with_buffer;
-- 查看MySQL磁盘文件产生的磁盘流量与读写比例
select * from io_global_by_file_by_bytes;
-- 查看哪些语句使用了全表扫描
select * from statements_with_full_table_scans;
-- 查看哪些语句使用了文件排序
select * from statements_with_sorting;
-- 查看哪些语句使用了临时表
select * from statements_with_temp_tables;
select * from sys.metrics;
-- Mysql Server Memory Usage= Sum of Global Buffers + (number of Connection Per thread memory variables)
-- 单个mysql连接线程的内存消耗统计，这里只是统计分配值（具体驻留内存占用值统计不到）
select b.thd_id, b.user, current_count_used,current_allocated, current_avg_alloc, current_max_alloc,total_allocated,current_statement
from sys.memory_by_thread_by_current_bytes a, sys.session b where a.thread_id = b.thd_id limit 5;
-- 统计top 10的buffer pool占用内存的表
select * from sys.innodb_buffer_stats_by_table order by pages desc limit 20;
-- 查看监控
select * from sys.memory_by_host_by_current_bytes   ;
select * from sys.memory_by_thread_by_current_bytes ;
select * from sys.memory_by_user_by_current_bytes   ;
select * from sys.memory_global_by_current_bytes    ;
select * from sys.memory_global_total               ;

-- information_schema 库相关
show tables from information_schema;
-- 查看数据库中是否使用了外键
select * from information_schema.KEY_COLUMN_USAGE where CONSTRAINT_SCHEMA = 'abc123456_tax' and REFERENCED_TABLE_SCHEMA is not null;
-- 查看数据库中是否使用了存储程序
select * from information_schema.ROUTINES where ROUTINE_SCHEMA = 'abc123456_uc';
-- 查看数据库中的计划任务
select * from information_schema.events where event_schema = 'abc123456_uc';
-- 查看客户端会话的状态信息
select * from information_schema.PROCESSLIST;

-- mysql库相关
show tables from mysql;
select * from mysql.user;
select * from mysql.db;
select * from mysql.tables_priv;
select * from mysql.columns_priv;