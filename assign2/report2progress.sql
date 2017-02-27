select customer, product, this_month, before_avg, after_avg from 
( 
	((select cust as customer, prod as product, month as this_month, avg(quant) as this_avg from sales group by cust, prod, month order by cust, month) as a
	left outer join
	(select cust, prod, month as after_month, avg(quant) as after_avg from sales group by cust, prod, month order by cust, month) as b on b.after_month=a.this_month+1 and a.customer=b.cust and a.product=b.prod
	left outer join
	(select cust, prod, month as before_month, avg(quant) as before_avg from sales group by cust, prod, month order by cust, month) as c on c.before_month=a.this_month-1 and a.customer=c.cust and a.product=c.prod)
)
as foo1 order by customer, product, this_month
