package ru.liga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.liga.model.RestaurantMenuItem;

@Repository
public interface MenuItemRepository extends JpaRepository<RestaurantMenuItem, Long> {
}