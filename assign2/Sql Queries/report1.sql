select cust as customer, prod as product, avg(quant) as the_avg, 
		(select avg(quant) from sales where prod!=a.prod and cust=a.cust) as other_product_avg, 
		(select avg(quant) from sales where prod=a.prod and cust!=a.cust) as other_cust_avg 
	from sales a group by cust, prod order by cust, prod
