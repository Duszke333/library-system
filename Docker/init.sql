GRANT ALL PRIVILEGES ON DATABASE pap TO postgres;

-- Create the database schema
CREATE SCHEMA pap;

-- Create the tables
create table pap.BOOKS (
                        Book_ID     serial constraint book_id_pk primary key,
                        ISBN        varchar(32) not null,
                        Title       varchar(128) not null,
                        Author      varchar(128) not null,
                        Genre       varchar(128) not null,
                        Publication_year    integer not null check (Publication_year <= extract(year from current_date)),
                        Language    varchar(64) not null default 'English',
                        Page_count  integer not null check (Page_count > 0),
                        Publisher   varchar(128) not null,
                        Status      varchar(32) not null default 'Available' check (Status in ('Available', 'Unavailable', 'Reserved')),
                        Description text not null default 'Description will be added soon.',
                        Date_added date not null default current_date,
                        Cover varchar(256) not null default 'resources/images/default_cover.png'
);

create table pap.ADDRESSES (
                        Address_ID  serial constraint address_id_pk primary key,
                        Country     varchar(64) not null,
                        Postal_code varchar(16) not null,
                        City        varchar(64) not null,
                        Street      varchar(64) not null,
                        House_number    varchar(16) not null,
                        Flat_number varchar(16)
);

create table pap.USERS (
                        Account_ID      serial constraint users_id_pk primary key,
                        Password_hash   varchar(128) not null,
                        Password_salt   varchar(32) not null,
                        First_name  varchar(128) not null,
                        Last_name   varchar(128) not null,
                        Email       varchar(128) not null unique constraint email_ape_dot check(Email like '%@%.%'),
                        Address_ID  integer not null,
                        Date_created    date not null default current_date,
                        Active      bool not null default True,
                        Has_unpaid_penalty bool not null default False,
                        constraint address_id_fk foreign key (Address_ID) references pap.ADDRESSES (Address_ID)
);

create table pap.BRANCHES (
                        Branch_id       serial constraint branches_id_pk primary key,
                        Branch_name     varchar(128) not null,
                        Address_id      integer not null constraint address_id_fk_branch references pap.ADDRESSES (Address_id)
);

create table pap.EMPLOYEES (
                        Employee_id     serial constraint employees_id_pk primary key,
                        Password_hash   varchar(128) not null,
                        Password_salt   varchar(32) not null,
                        Username        varchar(128) not null unique,
                        User_ID         integer not null unique constraint user_id_fk_emp references pap.USERS (Account_ID),
                        Role            varchar(64) not null,
                        Branch_id       integer not null constraint branch_id_fk_emp references pap.BRANCHES (Branch_id),
                        Active          bool not null default True,
                        Date_created    date not null default current_date
);

create table pap.BOOK_GRADES (
                        Grade_id        serial constraint grade_id_pk primary key,
                        Book_id         integer not null constraint book_id_fk_grade references pap.BOOKS (Book_ID),
                        User_id         integer not null constraint user_id_fk_grade references pap.USERS (Account_ID),
                        Grade           numeric(2,1) not null check (Grade >= 1 and Grade <= 5),
                        Date_added      date not null default current_date
);

create table pap.READ_LIST (
                        Read_list_id    serial constraint read_list_id_pk primary key,
                        User_id         integer not null constraint user_id_fk_read_list references pap.USERS (Account_ID),
                        Book_id         integer not null constraint book_id_fk_read_list references pap.BOOKS (Book_ID),
                        Date_added      date not null default current_date
);


create table pap.BOOK_RENTALS (
                        Rental_id       serial constraint rental_id_pk primary key,
                        Book_id         integer not null constraint book_id_fk_rental references pap.BOOKS (Book_ID),
                        User_id         integer not null constraint user_id_fk_rental references pap.USERS (Account_ID),
                        Date_rented     date not null default current_date,
                        Date_to_return  date not null default current_date + interval '1 month',
                        Date_returned   date
);

create table pap.RENTING_QUEUE (
                        Queue_id        serial constraint queue_id_pk primary key,
                        Book_id         integer not null constraint book_id_fk_queue references pap.BOOKS (Book_ID),
                        User_id         integer not null constraint user_id_fk_queue references pap.USERS (Account_ID),
                        Date_to_rent    date not null,
                        Date_to_return  date not null
);

create table pap.WISH_LIST (
                               Wish_id       serial constraint wish_id_pk primary key,
                               Book_id       integer not null constraint book_id_fk_wish references pap.BOOKS (Book_ID),
                               User_id       integer not null constraint user_id_fk_wish references pap.USERS (Account_ID),
                               Date_added    date not null default current_date
);

create table pap.PENALTIES (
                        Penalty_id      serial constraint penalty_id_pk primary key,
                        User_id         integer not null constraint user_id_fk_penalty references pap.USERS (Account_ID),
                        Rental_id       integer not null constraint rental_id_fk_penalty references pap.BOOK_RENTALS (Rental_id),
                        Date_added      date not null default current_date,
                        Date_paid       date default null,
                        Amount          integer not null check (Amount > 0),
                        Cause           varchar(256) not null default 'The deadline for returning the book has been exceeded.'
);

--------------------- ADDRESSES ---------------------
-- Polish library branch address
insert into pap.ADDRESSES (Country, Postal_code, City, Street, House_number, Flat_number)
values ('Poland', '00-000', 'Warsaw', 'Marszalkowska', '1', '1');

-- Canadian library branch address
insert into pap.ADDRESSES (Country, Postal_code, City, Street, House_number, Flat_number)
values ('Canada', '133-12', 'Toronto', 'Bloor', '1', '1');

-- Users addresses
insert into pap.ADDRESSES (Country, Postal_code, City, Street, House_number, Flat_number)
values ('Poland', '12-345', 'Warsaw', 'Krucza', '21', '37');

--------------------- USERS ---------------------
-- Credentials: user@user.user, user
insert into pap.USERS (Password_hash, Password_salt, First_name, Last_name, Email, Address_ID)
values ('7eb3a4cc3e9527219291dbb4fdd9c26041e0498135b6087276f7708d29e397c3fbf155c4c5a670803f035f7c82dcad325182859ba0e06fc6b76be48e972c6ffa',
        '96702cb6850a3dfdc8b08363d805a88d', 'user', 'user', 'user@user.user', 3);

-- Credentials: bozenka33@poczta.onet.pl, bozena
insert into pap.USERS (Password_hash, Password_salt, First_name, Last_name, Email, Address_ID)
values ('6bac816d909c11c50e83ef7cf13fe8eaeca1e9b832728ed43b6f7bc2dddac7a4130d55382299914301230a6e3016cb3c98b7b594c0f27ea93ba8e7ef6c08509d',
        '36ed02a4341da3f2e3c47af1b365431c', 'Bożena', 'Wiącek', 'bozenka33@poczta.onet.pl', 3);

-- This account is deactivated, so you cannot log in with it
-- Credentials: deactivated@deactivated.deactivated, user
insert into pap.USERS (Password_hash, Password_salt, First_name, Last_name, Email, Address_ID, Active)
values ('7eb3a4cc3e9527219291dbb4fdd9c26041e0498135b6087276f7708d29e397c3fbf155c4c5a670803f035f7c82dcad325182859ba0e06fc6b76be48e972c6ffa',
        '96702cb6850a3dfdc8b08363d805a88d', 'deactivated', 'deactivated', 'deactivated@deactivated.deactivated', 3, False);
--------------------- BOOKS ---------------------
-- Bible
insert into pap.BOOKS (ISBN, Title, Author, Genre, Publication_year, Language, Page_count, Publisher, Status, Description, Date_added)
values ('000-00-000-0000-0', 'The Bible', 'The Saints', 'Fantasy', 0, 'English', 1200, 'God himself', default, default, default);

-- Pan Tadeusz
insert into pap.BOOKS (ISBN, Title, Author, Genre, Publication_year, Language, Page_count, Publisher, Status, Description, Date_added)
values ('978-83-246-8865-8', 'Pan Tadeusz', 'Adam Mickiewicz', 'Poem', 1834, 'Polish', 400, 'Czytelnik', default, 'Pan Tadeusz to epopeja narodowa, napisana przez Adama Mickiewicza w latach 1832-1834 we Francji i w Szwajcarii. Utwór ten jest uważany za ostatni wielki poemat epicki w literaturze polskiej, a zarazem za jedno z największych osiągnięć literatury polskiej.', current_date);

-- Dziady Cz. II
insert into pap.BOOKS (ISBN, Title, Author, Genre, Publication_year, Language, Page_count, Publisher, Status, Description, Date_added)
values ('978-83-654-5889-6', 'Dziady Cz. II', 'Adam Mickiewicz', 'Poem', 1823, 'Polish', 48, 'Czytelnik', default, 'Dziady część II to dramat romantyczny Adama Mickiewicza, napisany w latach 1822-1832. Utwór ten jest uważany za jedno z największych osiągnięć literatury polskiej.', current_date);

-- Dziady Cz. III
insert into pap.BOOKS (ISBN, Title, Author, Genre, Publication_year, Language, Page_count, Publisher, Status, Description, Date_added)
values ('978-83-788-7627-4', 'Dziady Cz. III', 'Adam Mickiewicz', 'Poem', 1832, 'Polish', 288, 'Czytelnik', default, 'Dziady część III to dramat romantyczny Adama Mickiewicza, napisany w latach 1822-1832. Utwór ten jest uważany za jedno z największych osiągnięć literatury polskiej.', current_date);

-- Dziady Cz. IV
insert into pap.BOOKS (ISBN, Title, Author, Genre, Publication_year, Language, Page_count, Publisher, Status, Description, Date_added)
values ('978-83-953-8621-3', 'Dziady Cz. IV', 'Adam Mickiewicz', 'Poem', 1823, 'Polish', 80, 'Czytelnik', default, 'Dziady część IV to dramat romantyczny Adama Mickiewicza, napisany w latach 1822-1832. Utwór ten jest uważany za jedno z największych osiągnięć literatury polskiej.', current_date);

-- 1984
insert into pap.BOOKS (ISBN, Title, Author, Genre, Publication_year, Language, Page_count, Publisher, Status, Description, Date_added)
values ('978-83-280-9529-8', '1984', 'George Orwell', 'Dystopia', 1949, 'English', 384, 'Secker & Warburg', default, '1984 to antyutopia George’a Orwella, napisana w latach 1947-1948 i wydana w 1949 roku. Utwór ten jest uważany za jedno z największych osiągnięć literatury światowej.', current_date);

--------------------- Branches ---------------------
-- Polish branch
insert into pap.BRANCHES (Branch_name, Address_id)
values ('Marszałkowski branch', 1);

-- Canadian branch
insert into pap.BRANCHES (Branch_name, Address_id)
values ('Bloor branch', 2);

--------------------- Employees ---------------------
-- Credentials: admin, admin
insert into pap.EMPLOYEES (Password_hash, Password_salt, Username, User_ID, Role, Branch_id)
values ('f14b30de13f52db28ba5312cef37a02f007d006ed732ff8e445d97ec4726cec91e9e28136c18724c4b989e831e4817f77707a65d741ec88ff7ffad3e6a91a89c',
        'f8f4b608a4f6a5c03d603ef03079830d', 'admin', 1, 'Admin', 1);

-- Credentials: bozenawiacek, bozenka33
insert into pap.EMPLOYEES (Password_hash, Password_salt, Username, User_ID, Role, Branch_id)
values ('c2e847491a99c68cea3e2ed60cad9413523109efbefd094eb90bd234e0beb3106d5fa5fd9487d611d65eccdb6fe343e7daf04f47d8c07a664654002934c0ea1c',
        '96e3648d17c9399392a7d7256f7e1270', 'bozenawiacek', 2, 'Manager', 2);

-- This account is deactivated, so you cannot log in with it
-- Credentials: deactivated, admin
insert into pap.EMPLOYEES (Password_hash, Password_salt, Username, User_ID, Role, Branch_id, Active)
values ('f14b30de13f52db28ba5312cef37a02f007d006ed732ff8e445d97ec4726cec91e9e28136c18724c4b989e831e4817f77707a65d741ec88ff7ffad3e6a91a89c',
        'f8f4b608a4f6a5c03d603ef03079830d', 'deactivated', 3, 'Deactivated', 1, False);

--------------------- Book grades ---------------------
-- Bible
insert into pap.BOOK_GRADES (Book_id, User_id, Grade)
values (1, 1, 5);
insert into pap.BOOK_GRADES (Book_id, User_id, Grade)
values (1, 2, 2.5);

-- Pan Tadeusz
insert into pap.BOOK_GRADES (Book_id, User_id, Grade)
values (2, 1, 3);

--------------------- Read list ---------------------
-- User
insert into pap.READ_LIST (User_id, Book_id)
values (1, 1);
insert into pap.READ_LIST (User_id, Book_id)
values (1, 2);
insert into pap.READ_LIST (User_id, Book_id)
values (1, 5);

-- Bozena
insert into pap.READ_LIST (User_id, Book_id)
values (2, 4);
insert into pap.READ_LIST (User_id, Book_id)
values (2, 3);

--------------------- Book rentals ---------------------
-- User returned the book
insert into pap.BOOK_RENTALS (Book_id, User_id, Date_rented, Date_to_return, Date_returned)
values (1, 1, current_date - interval '1 month', current_date, current_date);
-- User just rented the book
insert into pap.BOOK_RENTALS (Book_id, User_id, Date_rented, Date_to_return)
values (2, 1, current_date, current_date + interval '1 month');

--------------------------------- RENTING QUEUE ---------------------------------
-- User
insert into pap.RENTING_QUEUE (Book_id, User_id, Date_to_rent, Date_to_return)
values (3, 1, current_date + interval '1 month', current_date + interval '2 months');

-- Bozena
insert into pap.RENTING_QUEUE (Book_id, User_id, Date_to_rent, Date_to_return)
values (3, 2, current_date + interval '2 months', current_date + interval '3 months');

--------------------------------- PENALTIES ---------------------------------
-- User
insert into pap.PENALTIES (User_id, Rental_id, Date_added, Date_paid, Amount)
values (1, 1, current_date, current_date, 15);

-- Bozena
insert into pap.PENALTIES (User_id, Rental_id, Date_added, Date_paid, Amount)
values (2, 1, current_date, null, 15);

----------------------------------WISH LIST -----------------------------------
--User
INSERT INTO pap.WISH_LIST (User_id, Book_id, Date_added)
VALUES (1, 4, CURRENT_DATE);

