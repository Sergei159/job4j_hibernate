create table if not exists brands (
id serial primary key,
name text
);

create table if not exists models (
id serial primary key,
name text,
brands_id int references brands(id)
);
