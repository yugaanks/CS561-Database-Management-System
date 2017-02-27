WITH
maxq as
	(select B.prod as Product,B.quant as Max_Q,B.cust as CUSTOMER,B.day,B.month,B.year from (select prod , MAX(quant) as MAX_Q from sales group by prod) as A
	left join
	(select * from sales) as B
	on B.prod=A.prod and B.quant=A.MAX_Q),

minq as
	(select D.prod as Product,D.quant as MIN_Q,D.cust as CUSTOMER,D.day,D.month,D.year from (select prod , MIN(quant) as MIN_Q from sales group by prod) as C
	left join
	(select * from sales) as D
	on D.prod=C.prod and D.quant=C.MIN_Q),	

avgq as
	(select prod, AVG(quant) from sales group by prod)

select maxq.product,maxq.max_q,maxq.customer,concat(maxq.month,'/',maxq.day,'/',maxq.year) as MAX_DATE,minq.min_q,minq.customer,concat(minq.month,'/',minq.day,'/',minq.year) as MIN_DATE,avgq.avg 
from maxq join minq on maxq.product=minq.product join avgq on avgq.prod=maxq.product;		