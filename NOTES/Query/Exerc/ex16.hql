--execute ex01.hql and create a table called esakraw.USERRECORDS and change the table name to USER_RECORDS 
-- change the column name from FirstName  First_Name and change its data type to VARCHAR(64)
--add two column 
-- remove a colum 
ALTER TABLE  esakraw.USERRECORDS RENAME TO esakraw.USER_RECORDS
ALTER TABLE esakraw.USERRECORDS ADD COLUMN (COL1 STRING , SOL2 STRING)
ALTER TABLE esakraw.USERRECORDS DROP COL2
ALTER TABLE esakraw.USERRECORDS CHANGE FirstName First_Name VARCHAR(64)
