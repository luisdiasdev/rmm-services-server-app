create table available_service (
    id int8 not null,
    description varchar(255),
    name varchar(255) not null,
    primary key (id));

create table available_service_pricing_policy (
    id int8 not null,
    os varchar(255) not null,
    price int8 not null,
    available_service_id int8 not null,
    primary key (id));

create table customer (
    id int8 not null,
    password varchar(255) not null,
    username varchar(30) not null,
    primary key (id));

create table customer_service (
    id int8 not null,
    customer_id int8 not null,
    service_id int8 not null,
    primary key (id));

create table device (
    id int8 not null,
    os varchar(255) not null,
    system_name varchar(255) not null,
    customer_id int8 not null,
    primary key (id));

alter table if exists available_service drop constraint if exists uk_available_service_name;
alter table if exists available_service add constraint uk_available_service_name unique (name);

alter table if exists customer drop constraint if exists uk_customer_username;
alter table if exists customer add constraint uk_customer_username unique (username);

alter table if exists customer_service drop constraint if exists uk_customer_service;
alter table if exists customer_service add constraint uk_customer_service unique (customer_id, service_id);

create sequence avail_serv_id_seq start 1 increment 1;
create sequence avail_serv_pol_id_seq start 1 increment 1;
create sequence customer_id_seq start 1 increment 1;
create sequence device_id_seq start 1 increment 1;
create sequence customer_service_id_seq start 1 increment 1;

alter table if exists available_service_pricing_policy add constraint fk_avail_serv_pol foreign key (available_service_id) references available_service;
alter table if exists customer_service add constraint fk_service_customer foreign key (customer_id) references customer;
alter table if exists customer_service add constraint fk_customer_available_serv foreign key (service_id) references available_service;
alter table if exists device add constraint fk_device_customer foreign key (customer_id) references customer;
