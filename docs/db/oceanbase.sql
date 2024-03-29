show databases;

SHOW PARAMETERS LIKE 'tde_method';


-- oracle 模式

-- 查看审计规则
SELECT * FROM SYS.ALL_DEF_AUDIT_OPTS;

select distinct class,stat_id,name,VALUE_TYPE from gv$sysstat where class=8;
