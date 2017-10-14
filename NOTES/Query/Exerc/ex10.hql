set hive.exec.partition=true;
set hive.exec.hive.partition.mode=nonstrict;
set hive.exec.hive.partition.pernode=1000;
use esakraw;
DROP TABLE IF EXISTS esakraw.USERPART;
CREATE TABLE esakraw.USERPART(
first_name  STRING,
last_name STRING,
address STRING,
city STRING,
post BIGINT,
phone1 STRING,
phone2 STRING,
email STRING,
web STRING ,
)
PARTITIONED BY (country,state)
ROW FORMAT DELIMITED 
FIELDS TERMINATED BY ','
STORED AS SEQUENCEFILE;
--loading data into Partition Table 
LOAD DATA INPATH '/USER/ESAKKIPILLAI/HADOOP/DATA' INTO TABLE esakraw.USERPART
--


