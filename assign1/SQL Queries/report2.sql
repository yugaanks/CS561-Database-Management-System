WITH
CT1 as (select cust,prod,max(quant) as ctmax from sales A group by cust,prod,STATE),
CT2 as (select cust as CUSTOMER, prod as product, quant as quantity, day as ctday,month as ctmonth,year as ctyear from sales where state='CT' and year between 2000 and 2005),

NY1 as (select cust, prod, min(quant) as nymin from sales group by cust, prod,STATE),
NY2 as (select cust as CUSTOMER, prod as PRODUCT, quant as QUANTITY, day as nyday, month as nymonth, year as nyyear from sales where state='NY'),

NJ1 as (select cust, prod, min(quant) as njmin from sales group by cust, prod,STATE),
NJ2 as (select cust as CUSTOMER, prod as PRODUCT, quant as QUANTITY, day as njday, month as njmonth, year as njyear from sales where state='NJ'),

CT as (select foo1.CUSTOMER, foo1.product, foo1.quantity as CT_MAX, foo1.ctday, foo1.ctmonth, foo1.ctyear from (CT1 join CT2 on CT1.cust=CT2.CUSTOMER and CT1.prod=CT2.product and CT1.ctmax=CT2.quantity) as foo1),
NY as (select foo2.CUSTOMER, foo2.product, foo2.quantity as NY_MIN, foo2.nyday, foo2.nymonth, foo2.nyyear from (NY1 join NY2 on NY1.cust=NY2.CUSTOMER and NY1.prod=NY2.product and NY1.nymin=NY2.quantity) as foo2),
NJ as (select foo3.CUSTOMER, foo3.product, foo3.quantity as NJ_MIN, foo3.njday, foo3.njmonth, foo3.njyear from (NJ1 join NJ2 on NJ1.cust=NJ2.CUSTOMER and NJ1.prod=NJ2.product and NJ1.nJmin=NJ2.quantity) as foo3)

select * from ((CT natural full outer join NY) natural full outer join NJ) as result ORDER BY CUSTOMER
