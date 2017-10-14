CREATE TABLE esakraw.orders_all_f (
order_id INT,
order_date DATE,
order_status STRING,
order_status_code VARCHAR(8),
orderdate_month INT,
orderdate_day INT,
orderdate_year INT,
juliandate INT,
currentday INT,
MONTHEND INT,
SinceOrderedDate BIGINT,
userdate DATE,
time TIMESTAMP,
evenorderid INT,
oddorderid INT,
orderid_code VARCHAR(4)
);


INSERT OVERWRITE  TABLE esakraw.orders_all_f
SELECT 
order_id,
TO_DATE(from_unixtime(UNIX_TIMESTAMP(order_date))),
order_status,
CASE 
WHEN order_status='CANCELED' THEN 'CA'
WHEN order_status='CLOSED'   THEN 'CL'
WHEN order_status='COMPLETE' THEN 'CP'
WHEN order_status='ON_HOLD'  THEN 'OH'
WHEN order_status='PAYMENT_REVIEW' THEN 'PR'
WHEN order_status='PENDING' THEN 'P'
WHEN order_status='PENDING_PAYMENT' THEN 'PP'
WHEN order_status='PROCESSING'  THEN 'PRO'
WHEN order_status='SUSPECTED_FRAUD' THEN 'SF'
END, 
cast(month(order_date) as INT),
cast(day(order_date) as INT),
cast(year(order_date) as INT),
from_unixtime(unix_timestamp(order_date), 'DDD'),
cast(day(CURRENT_DATE) as INT),
cast(day(last_day(CURRENT_DATE)) as INT)-cast(day(CURRENT_DATE) as INT),
datediff(current_date,last_day(order_date)),
CURRENT_DATE,
CURRENT_TIMESTAMP,
CASE WHEN order_customer_id % 2 == 0 THEN order_customer_id ELSE 0 END ,
CASE WHEN order_customer_id % 2 != 0 THEN order_customer_id ELSE 0 END ,
CASE WHEN order_customer_id % 2 == 0 THEN 'E' ELSE 'O' END 
FROM esakraw.orders_raw
limit 10;


