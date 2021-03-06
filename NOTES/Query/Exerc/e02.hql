--exo2
--Create the External table 
USE esakraw;
CREATE EXTERNAL TABLE IF NOT EXISTS USERRECORDEXT(
FirstName STRING ,
LastName STRING,
CompanyName STRING,
Address STRING,
COUNTRY STRING,
City STRING , 
State STRING ,
Post BIGINT,
Phone ARRAY<STRING>,
Email MAP<STRING,STRING> ,
WebUrl STRING
)
COMMENT 'FIRST EXTERNAL TABLE'
ROW FORMAT DELIMITED
MAP COLLECTION TERMINATED BY ':'
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
STORED AS TEXTFILE
LOCATION '/user/esakkipillai/hadoop/Data/';
--LOADING THE DATA 
LOAD DATA INPATH /user/esakkipillai/hadoop/Data/ OVERWRITE INTO TABLE USERRECORDEXT;