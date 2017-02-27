with 
a1 as (select distinct cust, prod, t1.month from sales,(select distinct month from sales) as t1)

select a1.cust as customer, a1.prod as product, a1.month, 
		(select avg(quant) as before_avg from sales where cust=a1.cust and prod=a1.prod and month=a1.month-1 group by cust, prod, month),
		(select avg(quant) as after_avg from sales where cust=a1.cust and prod=a1.prod and month=a1.month+1 group by cust, prod, month) 
	from a1 order by customer, product, month
