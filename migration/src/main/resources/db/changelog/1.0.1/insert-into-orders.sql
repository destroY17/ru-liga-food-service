INSERT INTO orders (customer_id, restaurant_id, status, courier_id, timestamp)
VALUES
    (1, 1, 'PENDING', null, '2023-10-15 12:00:00'),
    (2, 2, 'PICKING', 1, '2023-10-15 13:30:00'),
    (3, 3, 'DELIVERING', 3, '2023-10-15 14:45:00'),
    (1, 2, 'COMPLETE', 2, '2023-10-15 15:15:00');