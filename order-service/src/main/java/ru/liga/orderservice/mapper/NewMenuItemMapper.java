package ru.liga.orderservice.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.liga.orderservice.dto.NewMenuItemDto;
import ru.liga.orderservice.exception.DataNotFoundException;
import ru.liga.orderservice.model.Restaurant;
import ru.liga.orderservice.model.RestaurantMenuItem;
import ru.liga.orderservice.repository.RestaurantRepository;

@Component
@AllArgsConstructor
public class NewMenuItemMapper {
    private final RestaurantRepository restaurantRepository;

    public RestaurantMenuItem mapToEntity(NewMenuItemDto dto) {
        Restaurant restaurant = restaurantRepository.findById(dto.getRestaurantId())
                .orElseThrow(() -> new DataNotFoundException("Restaurant is not found"));

        return RestaurantMenuItem.builder()
                .restaurant(restaurant)
                .name(dto.getName())
                .price(dto.getPrice())
                .image(dto.getImage())
                .description(dto.getDescription())
                .build();
    }
}
