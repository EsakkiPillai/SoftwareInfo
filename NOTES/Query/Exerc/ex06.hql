USE esaakraw;
CREATE TEMPORARY TABLE esakraw.temp1(
fname STRING ,
mark ARRAY<INT>
)
COMMENT 'SIMPLE TEMP TABLE'
ROW FORMAT DELIMITED 
FIELDS TERMINATED BY ','
COLLECTIOn ITEM TERMINATED BY '#'
LINES TERMINATEDD BY ','
STORED AS SEQUENCEFILE
--LOADING DATA 
INSERT INTO TABLE esakraw.temp1 (fname,mark)VALUES('esak',ARRAY(45,56,77,37,44));
INSERT INTO TABLE esakraw.temp1 (fname,mark)VALUES('arun',ARRAY(45,56,100,33,44));
INSERT INTO TABLE esakraw.temp1 (fname,mark)VALUES('sreeni',ARRAY(95,96,22,100,44));
INSERT INTO TABLE esakraw.temp1 (fname,mark)VALUES('rahul',ARRAY(99,90,22,37,100));
INSERT INTO TABLE esakraw.temp1 (fname,mark)VALUES('virat',ARRAY(85,96,92,93,94));
--Describe the Table 
DESCRIBE FORMATTED esakraw.temp1;
--Display the Records
SELECT * from esakraw.temp1;