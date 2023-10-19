package ru.liga.orderservice.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.orderservice.dto.NewOrderItemDto;
import ru.liga.orderservice.mapper.NewOrderItemMapper;
import ru.liga.orderservice.model.OrderItem;
import ru.liga.orderservice.repository.OrderItemRepository;
import ru.liga.orderservice.service.OrderItemsService;

@Service
@AllArgsConstructor
public class OrderItemServiceImpl implements OrderItemsService {
    private final OrderItemRepository orderItemRepository;
    private final NewOrderItemMapper newOrderItemMapper;

    @Override
    public OrderItem addOrderItem(NewOrderItemDto newOrderItemDto) {
        return orderItemRepository.save(newOrderItemMapper.mapToEntity(newOrderItemDto));
    }

    @Override
    public void deleteOrderItemById(Long id) {
        orderItemRepository.deleteById(id);
    }
}
