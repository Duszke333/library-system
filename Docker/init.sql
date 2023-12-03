GRANT ALL PRIVILEGES ON DATABASE pap TO postgres;

-- Create the database schema
CREATE SCHEMA pap;

-- Create the tables
create table pap.BOOKS (
                       Book_ID     integer constraint book_id_pk primary key,
                       ISBN        varchar(13) not null,
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
                           Address_ID  integer constraint address_id_pk primary key,
                           Country     varchar(64) not null,
                           Postal_code varchar(16) not null,
                           City        varchar(64) not null,
                           Street      varchar(64) not null,
                           House_number    varchar(16) not null,
                           Flat_number varchar(16)
);

create table pap.USERS (
                       Account_ID  integer constraint users_id_pk primary key,
                       Login       varchar(128) not null,
                       Password_hash   varchar(256) not null,
                       Password_salt   varchar(256) not null,
                       First_name  varchar(128) not null,
                       Last_name   varchar(128) not null,
                       Email       varchar(128) not null constraint email_ape check(Email like '%@%'),
                       Address_ID  integer not null,
                       Date_created    date not null default current_date,
                       Active      bool not null default True,
                       constraint address_id_fk foreign key (Address_ID) references ADDRESSES (Address_ID)
);

