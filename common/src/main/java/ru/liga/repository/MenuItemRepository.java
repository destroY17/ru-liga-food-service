package ru.liga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.liga.model.RestaurantMenuItem;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<RestaurantMenuItem, Long> {
    @Modifying
    @Query(value = "select r from RestaurantMenuItem r where r.restaurant.id = :restaurantId and r.price < :price")
    List<RestaurantMenuItem> findMenuItemsLessThanPrice(Long restaurantId, BigDecimal price);
}