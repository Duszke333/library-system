FROM postgres:12.1-alpine

COPY init.sql /docker-entrypoint-initdb.d/