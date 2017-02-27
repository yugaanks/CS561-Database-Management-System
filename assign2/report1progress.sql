-- with a as (select cust, prod, avg(quant) as this from sales where  prod='Yogurt' group by cust, prod),
-- b as (select cust, prod, avg(quant) as other from sales where prod!='Yogurt' group by cust, prod),
-- c as (select cust, prod, avg(quant) as other_cust from sales where prod='Yogurt' group by cust, prod)
-- 
-- select a.cust, a.prod, this, avg(other) as other_product_avg, avg(other_cust) as other_customer_avg from a, b, c group by a.cust, a.prod, this
-- 
select cust, prod, q1 as THE_AVG, avg(q2) as other_product_avg, avg(q3) as other_customer_avg from 
	(select foo1.cust, foo1.prod, q1, q2, q3 from 
		((select cust, prod, avg(quant) as q1 from sales group by cust, prod) as foo1 
			join (select cust, avg(quant) as q2 from sales group by cust) as foo2 on foo1.prod!=foo2.prod) 
			join (select prod, avg(quant) as q3 from sales group by prod) as foo3 on foo1.cust!=foo3.cust) 
			as lol group by cust, prod, q1 order by cust