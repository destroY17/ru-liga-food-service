create sequence if not exists couriers_seq;

create table couriers
(
    id          bigint not null default nextval('couriers_seq'),
    phone       varchar(64),
    status      varchar(128),
    coordinates varchar(128),
    constraint couriers_pk primary key (id),
    constraint couriers_phone_unique unique (phone)
);

comment on table couriers is 'Курьеры';
comment on column couriers.id is 'Идентификатор курьера';
comment on column couriers.phone is 'Номер телефона';
comment on column couriers.status is 'Статус курьера';
comment on column couriers.coordinates is 'Координаты местонахождения курьера';