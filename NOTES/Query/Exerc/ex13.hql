--data 1, "some text, with comma in it", 111, "more text"
--2, "some text, with comma in it", 222, "more text"
--3, "some text, with comma in it", 333, "more text"
use esakraw;
CREATE TABLE esakraw.CSVEXAMPLE ( 
ID INT,
VALUE1 STRING,
VALUE2 STRING,
PIN INT,
VALUE3 STRING
) 
ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.OpenCSVSerde'
WITH SERDEPROPERTIES(
"separatorchar"=",",
"quotechar"=""",
"escapedchar"="\\"
)
STORED AS TEXTFILE;
TBLPROPERTIES('skip.header.line.count'='1');
--LOADING 
LOAD DATA INPATH '' INTO TABLE esakraw.CSVEXAMPLE;