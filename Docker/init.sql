GRANT ALL PRIVILEGES ON DATABASE pap TO postgres;

-- Create the database schema
CREATE SCHEMA pap;

-- Create the tables
CREATE TABLE pap.test (id serial PRIMARY KEY, name varchar(255));
INSERT INTO pap.test (name) VALUES ('test');
INSERT INTO pap.test (name) VALUES ('test2');
