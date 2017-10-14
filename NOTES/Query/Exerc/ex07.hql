USE esakraw;
DROP TABLE IF EXISTS esakraw.ex07;
--CREATE THE TABLE
--12345|John,Smith|123 Main St,New York, NY,00000|45,40,17,13|weekly_update:true,special_clearance:true,birthday_greeting:false
CREATE TABLE esakraw.ex07(
Id INT,
Name Array<STRING,STRING>,
Address STRUCT<street:STRING,State:STRING,statecode:STRING,zip:INT>
weeklyUpdate MAP<key:STRING,Value:BOOLEAN>,
Special_clearance MAP<key:STRING,Value:BOOLEAN>
BirthDay_Greeting MAP<key:STRING,Value:BOOLEAN>
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '|'
COLLECTION ITEMS TERMINATED BY ','
MAP ITEMS TERMINATED BY ':'
LINES TERMINATD BY '\n'
STORED AS PARQUET;
--LOADING DATA 
LOAD DATA LOCAL INPATH 'FILENAME' INTo TABLE esakraw.ex07;
