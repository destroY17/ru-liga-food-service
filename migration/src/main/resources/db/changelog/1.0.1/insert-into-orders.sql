INSERT INTO orders (id, customer_id, restaurant_id, status, courier_id, timestamp)
VALUES
    ('b5a1c7b9-0d8e-4f3c-ae9a-1d6f5b2a8b1c', 1, 1, 'KITCHEN_ACCEPTED', null, '2023-10-15 12:00:00'),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d479', 2, 2, 'DELIVERY_PICKING', 1, '2023-10-15 13:30:00'),
    ('d8c7b3e5-2b0d-4c3e-ba6f-7b9f132f7d7d', 3, 3, 'DELIVERY_DELIVERING', 3, '2023-10-15 14:45:00'),
    ('9a5e9a15-7b8a-4d0a-9f8f-6e4a8c6c9c1e', 1, 2, 'DELIVERY_COMPLETE', 2, '2023-10-15 15:15:00');