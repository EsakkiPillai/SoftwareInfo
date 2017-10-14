sqoop import --connect jdbc:mysql://nn01.itversity.com/retail_db --username retail_dba --password itversity --table products --hive-import --create-hive-table  --hive-overwrite --hive-database esakraw --hive-table products_raw --target-dir /user/esakkipillai/hadoop/hive/products -m 1

CREATE TABLE products_all_f(
product_id INT,
product_category_id INT,
product_name STRING,
Product_NameLength INT,
product_description STRING,
product_price DOUBLE,
Price_Range VARCHAR(12),
GST INT,
NEW_PRICE DOUBLE,
product_image STRING,
Product_url_s1 STRING,
Product_url_s2 STRING,
Product_url STRING
);

INSERT OVERWRITE TABLE esakraw.products_all_f
SELECT
product_id,
product_category_id,
product_name,
length(product_name),
CASE WHEN length(product_description) > 0 then product_description ELSE "NILL" END ,
product_price,
CASE WHEN product_price < 10 THEN 'CHEAPER' 
WHEN product_price BETWEEN   10   AND  100 THEN 'OKAY' 
WHEN product_price BETWEEN   100   AND  1000  THEN 'HIGHER' 
WHEN product_price > 1000   THEN 'LUXURY' 
END,
CASE WHEN product_price < 10 THEN 5 
 WHEN product_price BETWEEN   10   AND  100  THEN 10
 WHEN product_price BETWEEN   100   AND  1000  THEN 20
 WHEN product_price > 1000   THEN 40
END,
CASE WHEN product_price < 10 THEN ((0.05* product_price)+product_price)
 WHEN product_price BETWEEN   10   AND  100 THEN ((0.1* product_price)+product_price)
 WHEN product_price BETWEEN   100   AND  1000  THEN ((0.2* product_price)+product_price)
 WHEN product_price > 1000   THEN ((0.4* product_price)+product_price)
END,
product_image ,
split(product_image,'\\:')[0],
regexp_replace(split(product_image,'\\:')[1],"//",""),
concat(split(product_image,'\\:')[0],split(product_image,'\\:')[1] )
from products_raw;


select 
product_image ,
split(product_image,'\\:')[0],
regexp_replace(split(product_image,'\\:')[1],"//","")
from esakraw.products_raw limit 10;


