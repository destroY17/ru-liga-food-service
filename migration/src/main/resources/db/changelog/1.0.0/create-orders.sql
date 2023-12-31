create sequence if not exists orders_seq;

create table if not exists orders
(
    id            uuid not null default gen_random_uuid(),
    customer_id   bigint not null,
    restaurant_id bigint not null,
    status        varchar(128),
    courier_id    bigint,
    timestamp     timestamptz,
    constraint orders_pk primary key (id),
    constraint orders_to_customer_fk foreign key (customer_id) references customers (id),
    constraint orders_to_restaurant_fk foreign key (restaurant_id) references restaurants (id),
    constraint orders_to_courier_fk foreign key (courier_id) references couriers (id)
);

comment on table orders is 'Заказы';
comment on column orders.id is 'Идентификатор заказа';
comment on column orders.customer_id is 'Идентификатор покупателя';
comment on column orders.restaurant_id is 'Идентификатор ресторана заказа';
comment on column orders.status is 'Статус заказа';
comment on column orders.courier_id is 'Идентификатор курьера заказа';
comment on column orders.timestamp is 'Время заказа';