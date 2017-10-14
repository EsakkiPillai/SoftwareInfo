CREATE TABLE PRODUCTS_AGG(
PRODUCT_CATEGORY_ID INT ,
TOTAL INT ,
COUNT INT ,
Average DOUBLE,
Minimumm DOUBLE, 
Maximum DOUBLE 
);


INSERT OVERWRITE TABLE PRODUCTS_AGG
SELECT 
product_category_id,
SUM(new_price),
COUNT(product_category_id),
AVG(new_price),
MIN(new_price),
MAX(new_price)
FROM esakraw.products_all_f
group by  product_category_id;

select * from PRODUCTS_AGG;