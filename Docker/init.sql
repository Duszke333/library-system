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

create table pap.BRANCHES (
                       Branch_id serial constraint branches_id_pk primary key,
                       Branch_name varchar(128) not null,
                       Address_id integer not null,
                       constraint address_id_fk_branch foreign key (Address_id) references pap.ADDRESSES (Address_id)
);
-- Insert some data
insert into pap.ADDRESSES (Country, Postal_code, City, Street, House_number, Flat_number)
values ('Poland', '00-000', 'Warsaw', 'Marszalkowska', '1', '1');

insert into pap.USERS (Password_hash, Password_salt, First_name, Last_name, Email, Address_ID)
values ('admin', 'admin', 'admin', 'admin', 'admin@admin', 1);

insert into pap.BOOKS (ISBN, Title, Author, Genre, Publication_year, Language, Page_count, Publisher, Is_available, Description, Date_added)
values ('978-83-246-8865-8', 'Pan Tadeusz', 'Adam Mickiewicz', 'Poem', 1834, 'Polish', 400, 'Czytelnik', True, 'Pan Tadeusz to epopeja narodowa, napisana przez Adama Mickiewicza w latach 1832-1834 we Francji i w Szwajcarii. Utwór ten jest uważany za ostatni wielki poemat epicki w literaturze polskiej, a zarazem za jedno z największych osiągnięć literatury polskiej.', current_date);

insert into pap.BRANCHES (Branch_name, Address_id)
values ('Marszałkowski branch', 1);