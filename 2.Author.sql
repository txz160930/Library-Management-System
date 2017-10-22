use DBlibrary;

insert into AUTHORS(Name)
select distinct Name
from BOOK;

