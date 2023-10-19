package ru.liga.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.liga.orderservice.model.RestaurantMenuItem;

@Repository
public interface MenuItemRepository extends JpaRepository<RestaurantMenuItem, Long> {
}
