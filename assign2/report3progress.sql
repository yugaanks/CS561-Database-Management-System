-- select prod, month, count(*) from sales a where quant between (select avg(quant) from sales where month=a.month+1 and prod=a.prod group by prod, month) and (select max(quant) from sales where month=a.month+1 and a.prod=prod group by prod, month) and prod='Eggs' group by prod, month
-- select prod, month, quant from sales where prod='Eggs' and month=9
with 
a1 as(select prod, month, quant from sales),
a2 as(select prod, month, max(quant) as maximum from sales group by prod, month),
a3 as(select prod, month, avg(quant) as average from sales group by prod, month),
a4 as (select * from a1 natural full outer join a2),
a5 as (select * from a4 natural full outer join a3)
-- select * from a5  order by prod, month, quant, maximum
select prod, month, count(*) as before_tot from (select * from a5  order by prod, month) as foo where foo.quant between average and maximum and month>foo.month group by prod, month, maximum, average order by prod, month;
-- select * from a4 order by prod, month;
-- select prod, month, count(*) from (select * from a4  order by prod, month) as foo where foo.quant<maximum group by prod, month, maximum;


