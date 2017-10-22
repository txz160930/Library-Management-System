use DBlibrary;

drop table if exists BOOK;
create table BOOK(
Isbn   bigint(20),
Title   varchar(500),
Name varchar(400),
primary key(Isbn)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


drop table if exists AUTHORS;
create table AUTHORS(
AUTHOR_ID int(11) unsigned not null auto_increment,
Name varchar(400) ,
primary key(AUTHOR_ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists BOOK_AUTHORS;
create table BOOK_AUTHORS(
AUTHOR_ID int(11),
Isbn  bigint(20),
primary key(Isbn))ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists BORROWER;
create table BORROWER(
Card_id varchar(8) NOT NULL,
SSN varchar(11) not null,
First_Name varchar(30) not null,
Last_Name varchar(30) not null,
Address LONGBLOB not null,
City varchar(20) not null,
State varchar(11) not null,
Phone varchar(15) not null,
primary key(Card_id))ENGINE=InnoDB DEFAULT CHARSET=utf8;


drop table if exists BOOK_LOANS;
create table BOOK_LOANS(
Loan_id int(11) unsigned not null auto_increment,
Isbn   bigint(20),
Card_id varchar(8) NOT NULL,
Date_out datetime DEFAULT current_timestamp,
Due_date datetime Not NULL,
Date_in Date default null,
primary key (Loan_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists FINES;
create table FINES(
Loan_id int(11),
Fine_amt float not null,
Paid tinyint(1) default 0,
primary key(Loan_id))
ENGINE=InnoDB DEFAULT CHARSET=utf8;

