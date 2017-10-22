Use DBlibrary;

insert into BOOK_AUTHORS(Isbn,Author_id)
select Isbn, Author_id
from BOOK, AUTHORS
WHERE BOOK.Name=AUTHORS.Name;
