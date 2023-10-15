create sequence restaurant_menu_items_seq;

create table restaurant_menu_items
(
    id            bigint not null default nextval('restaurant_menu_items_seq'),
    restaurant_id bigint not null,
    name          varchar(512),
    price         numeric(10,2),
    image         varchar(256),
    description   varchar(512),
    constraint restaurant_menu_items_pk primary key (id),
    constraint restaurant_menu_items_fk foreign key (restaurant_id) references restaurants (id)
);

comment on table restaurant_menu_items is 'Товары из меню ресторана';
comment on column restaurant_menu_items.id is 'Идентификатор меню ресторана';
comment on column restaurant_menu_items.restaurant_id is 'Идентификатор ресторана';
comment on column restaurant_menu_items.name is 'Название товара';
comment on column restaurant_menu_items.price is 'Цена товара';
comment on column restaurant_menu_items.image is 'Изображение товара';
comment on column restaurant_menu_items.description is 'Описание товара';