package ru.liga.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.liga.dto.*;
import ru.liga.exception.DataNotFoundException;
import ru.liga.mapper.OrderInfoMapper;
import ru.liga.model.*;
import ru.liga.repository.OrderRepository;
import ru.liga.service.impl.OrderServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceUnitTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderInfoMapper orderInfoMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    public void testFindAllOrders() {
        Pageable pageable = Pageable.unpaged();
        Page<Order> mockPage = new PageImpl<>(List.of(new Order(), new Order()));
        List<OrderInfo> orderInfos = Arrays.asList(
                OrderInfo.builder().build(),
                OrderInfo.builder().build()
        );

        when(orderRepository.findAll(pageable)).thenReturn(mockPage);
        when(orderInfoMapper.toDto(anyList())).thenReturn(orderInfos);

        Page<OrderInfo> result = orderService.findAllOrders(pageable);

        assertEquals(mockPage.getTotalElements(), result.getTotalElements());
        assertEquals(2, result.getContent().size());
    }

    @Test
    public void testFindOrdersByStatus() {
        Pageable pageable = Pageable.unpaged();
        OrderStatus status = OrderStatus.CUSTOMER_CREATED;
        Page<Order> mockPage = new PageImpl<>(List.of(new Order(), new Order()));
        List<OrderInfo> orderInfos = Arrays.asList(
                OrderInfo.builder().build(),
                OrderInfo.builder().build()
        );

        when(orderRepository.findOrdersByStatus(pageable, status)).thenReturn(mockPage);
        when(orderInfoMapper.toDto(anyList())).thenReturn(orderInfos);

        Page<OrderInfo> result = orderService.findOrdersByStatus(pageable, status);

        assertEquals(mockPage.getTotalElements(), result.getTotalElements());
        assertEquals(2, result.getContent().size());
    }

    @Test
    public void testFindOrderById() {
        Long orderId = 1L;
        Order order = Order.builder().build();
        OrderInfo orderInfo = OrderInfo.builder().build();

        when(orderRepository.findById(orderId)).thenReturn(java.util.Optional.of(order));
        when(orderInfoMapper.toDto(order)).thenReturn(orderInfo);

        OrderInfo result = orderService.findOrderById(orderId);

        assertEquals(orderInfo, result);
    }

    @Test
    public void testFindOrderById_WhenNotFound() {
        Long orderId = 1L;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> orderService.findOrderById(orderId));
    }
}