INSERT INTO orders (customer_id, restaurant_id, status, courier_id, timestamp)
VALUES
    (1, 1, 'active', null, '2023-10-15 12:00:00'),
    (2, 2, 'complete', 1, '2023-10-15 13:30:00'),
    (3, 3, 'complete', 3, '2023-10-15 14:45:00'),
    (1, 2, 'active', 2, '2023-10-15 15:15:00');