create sequence if not exists order_items_seq;

create table if not exists order_items
(
    id                   bigint not null default nextval('order_items_seq'),
    order_id             uuid not null,
    restaurant_menu_item bigint not null,
    price                numeric(10,2),
    quantity             int,
    constraint order_items_fk primary key (id),
    constraint order_items_to_order_fk foreign key (order_id) references orders (id),
    constraint orders_items_to_restaurant_menu_item_fk foreign key (restaurant_menu_item) references restaurant_menu_items (id)
);

comment on table order_items is 'Товары, входящие в заказ';
comment on column order_items.order_id is 'Идентификатор заказа';
comment on column order_items.restaurant_menu_item is 'Идентификатор товара из меню ресторана';
comment on column order_items.price is 'Стоимость заказа';
comment on column order_items.quantity is 'Количество товара';