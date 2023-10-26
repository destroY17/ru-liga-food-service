package ru.liga.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.liga.dto.NewMenuItemDto;
import ru.liga.exception.DataNotFoundException;
import ru.liga.model.Restaurant;
import ru.liga.model.RestaurantMenuItem;
import ru.liga.repository.RestaurantRepository;

@Component
@AllArgsConstructor
public class NewMenuItemMapper {
    private final RestaurantRepository restaurantRepository;

    public RestaurantMenuItem toEntity(NewMenuItemDto dto) {
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
