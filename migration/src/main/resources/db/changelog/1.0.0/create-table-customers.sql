create sequence customers_seq;

create table customers
(
    id      bigint not null default nextval('customers_seq'),
    phone   varchar(64),
    email   varchar(320),
    address varchar(512),
    constraint customers_id_pk primary key (id),
    constraint customers_phone_unique unique (phone),
    constraint customers_email_unique unique (email)
);

comment on table customers is 'Покупатели';
comment on column customers.id is 'Идентификатор покупателя';
comment on column customers.phone is 'Номер телефона';
comment on column customers.email is 'Электронная почта';
comment on column customers.address is 'Адрес';