package ru.liga.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.dto.NewOrderItemDto;
import ru.liga.mapper.NewOrderItemMapper;
import ru.liga.model.OrderItem;
import ru.liga.repository.OrderItemRepository;
import ru.liga.service.OrderItemService;

@Service
@AllArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
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
