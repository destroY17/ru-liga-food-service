package ru.liga.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.liga.model.Order;
import ru.liga.model.OrderStatus;

import java.util.List;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
    List<Order> findOrdersByStatus(OrderStatus status);
}
