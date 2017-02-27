with 
a1 as (	select foo1.prod, this, count(before_quant) as BEFORE_TOT from
		(select  prod, month as this, avg(quant) as q1, max(quant) as q2 from sales group by prod, month) as foo1
		left outer join
		(select prod, month as before_month, quant as before_quant from sales) as foo2 on foo1.prod=foo2.prod and foo1.this=foo2.before_month+1 and before_quant between q1 and q2
		group by foo1.prod, this,q1,q2 order by foo1.prod, this),
a2 as ( select foo1.prod, this, count(after_quant) as AFTER_TOT from
		(select  prod, month as this, avg(quant) as q1, max(quant) as q2 from sales group by prod, month) as foo1
		left outer join
 		(select prod, month as after_month, quant as after_quant from sales) as foo3 on foo1.prod=foo3.prod and foo1.this=foo3.after_month-1 and after_quant between q1 and q2
 		group by foo1.prod, this,q1,q2 order by foo1.prod, this)

 select prod as PRODUCT, this as MONTH, BEFORE_TOT, AFTER_TOT from a1 natural full outer join a2