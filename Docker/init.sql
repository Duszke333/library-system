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
                       Is_available    bool not null default True, -- potencjalnie zniknie, zalezy od systemu wypozyczen
                       Description text not null default 'Description will be added soon.',
                       Date_added date not null default current_date
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
                       Password_hash   varchar(256) not null,
                       Password_salt   varchar(256) not null,
                       First_name  varchar(128) not null,
                       Last_name   varchar(128) not null,
                       Email       varchar(128) not null unique constraint email_ape check(Email like '%@%'),
                       Address_ID  integer not null,
                       Date_created    date not null default current_date,
                       Active      bool not null default True,
                       constraint address_id_fk foreign key (Address_ID) references pap.ADDRESSES (Address_ID)
);

create table pap.BRANCHES (
                              Branch_id       serial constraint branches_id_pk primary key,
                              Branch_name     varchar(128) not null,
                              Address_id      integer not null constraint address_id_fk_branch references pap.ADDRESSES (Address_id)
);

create table pap.EMPLOYEES (
                    Employee_id     serial constraint employees_id_pk primary key,
                    Password_hash   varchar(256) not null,
                    Password_salt   varchar(256) not null,
                    Username        varchar(128) not null unique,
                    User_ID         integer not null unique constraint user_id_fk_emp references pap.USERS (Account_ID),
                    Role            varchar(64) not null,
                    Branch_id       integer not null constraint branch_id_fk_emp references pap.BRANCHES (Branch_id),
                    Active          bool not null default True,
                    Date_created    date not null default current_date
);

-- Insert some data
insert into pap.ADDRESSES (Country, Postal_code, City, Street, House_number, Flat_number)
values ('Poland', '00-000', 'Warsaw', 'Marszalkowska', '1', '1');

-- Password: bozena
insert into pap.USERS (Password_hash, Password_salt, First_name, Last_name, Email, Address_ID)
values ('6bac816d909c11c50e83ef7cf13fe8eaeca1e9b832728ed43b6f7bc2dddac7a4130d55382299914301230a6e3016cb3c98b7b594c0f27ea93ba8e7ef6c08509d',
        '36ed02a4341da3f2e3c47af1b365431c', 'Bożena', 'Wiącek', 'bozenka33@poczta.onet.pl', 1);

insert into pap.BOOKS (ISBN, Title, Author, Genre, Publication_year, Language, Page_count, Publisher, Is_available, Description, Date_added)
values ('978-83-246-8865-8', 'Pan Tadeusz', 'Adam Mickiewicz', 'Poem', 1834, 'Polish', 400, 'Czytelnik', True, 'Pan Tadeusz to epopeja narodowa, napisana przez Adama Mickiewicza w latach 1832-1834 we Francji i w Szwajcarii. Utwór ten jest uważany za ostatni wielki poemat epicki w literaturze polskiej, a zarazem za jedno z największych osiągnięć literatury polskiej.', current_date);

insert into pap.BOOKS (ISBN, Title, Author, Genre, Publication_year, Language, Page_count, Publisher, Is_available, Description, Date_added)
values ('000-00-000-0000-0', 'The Bible', 'The Saints', 'Fantasy', 0, 'English', 1200, 'God himself', default, default, default);

insert into pap.BRANCHES (Branch_name, Address_id)
values ('Marszałkowski branch', 1);

-- Password: bozenka33
insert into pap.EMPLOYEES (Password_hash, Password_salt, Username, User_ID, Role, Branch_id)
values ('c2e847491a99c68cea3e2ed60cad9413523109efbefd094eb90bd234e0beb3106d5fa5fd9487d611d65eccdb6fe343e7daf04f47d8c07a664654002934c0ea1c',
        '96e3648d17c9399392a7d7256f7e1270', 'bozenawiacek', 1, 'Manager', 1);

-- more books
insert into pap.BOOKS (ISBN, Title, Author, Genre, Publication_year, Language, Page_count, Publisher, Is_available, Description, Date_added)
values ('978-83-654-5889-6', 'Dziady Cz. II', 'Adam Mickiewicz', 'Poem', 1823, 'Polish', 48, 'Czytelnik', True, 'Dziady część II to dramat romantyczny Adama Mickiewicza, napisany w latach 1822-1832. Utwór ten jest uważany za jedno z największych osiągnięć literatury polskiej.', current_date);

insert into pap.BOOKS (ISBN, Title, Author, Genre, Publication_year, Language, Page_count, Publisher, Is_available, Description, Date_added)
values ('978-83-788-7627-4', 'Dziady Cz. III', 'Adam Mickiewicz', 'Poem', 1832, 'Polish', 288, 'Czytelnik', True, 'Dziady część III to dramat romantyczny Adama Mickiewicza, napisany w latach 1822-1832. Utwór ten jest uważany za jedno z największych osiągnięć literatury polskiej.', current_date);

insert into pap.BOOKS (ISBN, Title, Author, Genre, Publication_year, Language, Page_count, Publisher, Is_available, Description, Date_added)
values ('978-83-953-8621-3', 'Dziady Cz. IV', 'Adam Mickiewicz', 'Poem', 1823, 'Polish', 80, 'Czytelnik', True, 'Dziady część IV to dramat romantyczny Adama Mickiewicza, napisany w latach 1822-1832. Utwór ten jest uważany za jedno z największych osiągnięć literatury polskiej.', current_date);

insert into pap.BOOKS (ISBN, Title, Author, Genre, Publication_year, Language, Page_count, Publisher, Is_available, Description, Date_added)
values ('978-83-280-9529-8', '1984', 'George Orwell', 'Dystopia', 1949, 'English', 384, 'Secker & Warburg', True, '1984 to antyutopia George’a Orwella, napisana w latach 1947-1948 i wydana w 1949 roku. Utwór ten jest uważany za jedno z największych osiągnięć literatury światowej.', current_date);