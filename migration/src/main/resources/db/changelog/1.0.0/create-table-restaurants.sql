create sequence restaurants_seq;

create table restaurants
(
    id      bigint not null default nextval('restaurants_seq'),
    address varchar(512),
    status  varchar(64),
    constraint restaurants_pk primary key (id)
);

comment on table restaurants is 'Рестораны';
comment on column restaurants.id is 'Идентификатор ресторана';
comment on column restaurants.address is 'Адрес ресторана';
comment on column restaurants.status is 'Статус ресторана';