package ru.liga.unit.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.dto.NewMenuItemDto;
import ru.liga.mapper.NewMenuItemMapper;
import ru.liga.model.RestaurantMenuItem;
import ru.liga.repository.MenuItemRepository;
import ru.liga.service.impl.MenuItemServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MenuItemServiceTest {

    @InjectMocks
    private MenuItemServiceImpl menuItemService;

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private NewMenuItemMapper newMenuItemMapper;

    @Test
    public void testAddMenuItem() {
        NewMenuItemDto newMenuItemDto = NewMenuItemDto.builder().build();
        RestaurantMenuItem restaurantMenuItem = new RestaurantMenuItem();

        when(newMenuItemMapper.toEntity(newMenuItemDto)).thenReturn(restaurantMenuItem);
        when(menuItemRepository.save(restaurantMenuItem)).thenReturn(restaurantMenuItem);

        RestaurantMenuItem result = menuItemService.addMenuItem(newMenuItemDto);

        verify(newMenuItemMapper).toEntity(newMenuItemDto);
        verify(menuItemRepository).save(restaurantMenuItem);

        assertNotNull(result);
        assertEquals(restaurantMenuItem, result);
    }

    @Test
    public void testFindMenuItemById() {
        Long menuItemId = 1L;
        RestaurantMenuItem restaurantMenuItem = new RestaurantMenuItem();

        when(menuItemRepository.findById(menuItemId)).thenReturn(Optional.of(restaurantMenuItem));

        RestaurantMenuItem result = menuItemService.findMenuItemById(menuItemId);

        verify(menuItemRepository).findById(menuItemId);

        assertNotNull(result);
        assertEquals(restaurantMenuItem, result);
    }

    @Test
    public void testChangePrice() {
        Long menuItemId = 1L;
        RestaurantMenuItem restaurantMenuItem = new RestaurantMenuItem();
        BigDecimal newPrice = BigDecimal.valueOf(10.0);

        when(menuItemRepository.findById(menuItemId)).thenReturn(Optional.of(restaurantMenuItem));
        when(menuItemRepository.save(restaurantMenuItem)).thenReturn(restaurantMenuItem);

        RestaurantMenuItem result = menuItemService.changePrice(menuItemId, newPrice);

        verify(menuItemRepository).findById(menuItemId);
        verify(menuItemRepository).save(restaurantMenuItem);

        assertNotNull(result);
        assertEquals(newPrice, result.getPrice());
    }

    @Test
    public void testFindAllCheaperPrice() {
        Long restaurantId = 1L;
        BigDecimal price = BigDecimal.valueOf(10.0);
        List<RestaurantMenuItem> menuItems = List.of(new RestaurantMenuItem(), new RestaurantMenuItem());

        when(menuItemRepository.findMenuItemsLessThanPrice(restaurantId, price)).thenReturn(menuItems);

        List<RestaurantMenuItem> result = menuItemService.findAllCheaperPrice(restaurantId, price);

        verify(menuItemRepository).findMenuItemsLessThanPrice(restaurantId, price);

        assertNotNull(result);
        assertEquals(menuItems, result);
    }
}