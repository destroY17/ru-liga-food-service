package ru.liga.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.liga.model.Order;
import ru.liga.model.OrderStatus;

import java.util.UUID;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, UUID> {
    Page<Order> findOrdersByStatus(Pageable pageable, OrderStatus status);

    @Modifying
    @Query(value = "update Order o set o.status = :status where o.id = :orderId")
    void updateOrderByStatus(UUID orderId, OrderStatus status);
}
