set hive.exec.partition=true;
set hive.exec.hive.partition.mode=nonstrict;
set hive.exec.hive.partition.pernode=1000;
use esakraw;
DROP TABLE IF EXISTS esakraw.USER_part;
CREATE TABLE esakraw.USER_src(
first_name  STRING,
last_name STRING,
address STRING,
country STRING,
city STRING,
state STRING,
post BIGINT,
phone1 STRING,
phone2 STRING,
email STRING,
web STRING ,
)
ROW FORMAT DELIMITED 
FIELDS TERMINATED BY ','
STORED AS TEXTFILE;
LOAD DATA INPATH '' INTO TABLE esakraw.USER_src;
CREATE TABLE esakraw.USER_part(
first_name  STRING,
last_name STRING,
address STRING,
city STRING,
state STRING,
post BIGINT,
phone1 STRING,
phone2 STRING,
email STRING,
web STRING ,
)
PARTITIONED BY (country)
ROW FORMAT DELIMITED 
FIELDS TERMINATED BY ','
STORED AS ORC
TBLPROPERTIES('orc.compress'='snappy');
--LOADING THE DATA 
INSERT INTO TABLE esakraw.USER_part 
PARTITION (country)
SELECT 
first_name,
last_name ,
address,
city ,
state,
post ,
phone1,
phone2,
email ,
web ,
country
FROM esakraw.USER_src ;
-- add the New Partition 
ALTER TABLE esakraw.USER_part ADD PARTITIOTN(country='AU',state='NS')
--CHANGE THE PARTITION 
ALTER TABLE esakraw.USER_part PARTITION (country='AU',state='NS') RENAME TO (country='AU',state='XX')
--drop a partition 
ALTER TABLE esakraw.USER_part DROP PARTITION(country='AU',state='XX')

