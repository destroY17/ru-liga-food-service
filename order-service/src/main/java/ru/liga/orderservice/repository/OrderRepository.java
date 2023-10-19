package ru.liga.orderservice.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.liga.orderservice.model.Order;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
}
