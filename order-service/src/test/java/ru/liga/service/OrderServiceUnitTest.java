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
import ru.liga.mapper.DeliveryOrderMapper;
import ru.liga.mapper.NewOrderMapper;
import ru.liga.mapper.OrderInfoMapper;
import ru.liga.model.Order;
import ru.liga.model.OrderStatus;
import ru.liga.repository.OrderRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

    @Mock
    private NewOrderMapper newOrderMapper;

    @Mock
    private DeliveryOrderMapper deliveryOrderMapper;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    public void testFindAllOrders() {
        Pageable pageable = Pageable.unpaged();
        Page<Order> mockPage = new PageImpl<>(List.of(new Order(), new Order()));
        List<OrderInfoDto> orderInfoDtos = Arrays.asList(
                OrderInfoDto.builder().build(),
                OrderInfoDto.builder().build()
        );

        when(orderRepository.findAll(pageable)).thenReturn(mockPage);
        when(orderInfoMapper.toDto(anyList())).thenReturn(orderInfoDtos);

        Page<OrderInfoDto> result = orderService.findAllOrders(pageable);

        assertEquals(mockPage.getTotalElements(), result.getTotalElements());
        assertEquals(2, result.getContent().size());
    }

    @Test
    public void testFindOrdersByStatus() {
        Pageable pageable = Pageable.unpaged();
        OrderStatus status = OrderStatus.CUSTOMER_CREATED;
        Page<Order> mockPage = new PageImpl<>(List.of(new Order(), new Order()));
        List<OrderInfoDto> orderInfoDtos = Arrays.asList(
                OrderInfoDto.builder().build(),
                OrderInfoDto.builder().build()
        );

        when(orderRepository.findOrdersByStatus(pageable, status)).thenReturn(mockPage);
        when(orderInfoMapper.toDto(anyList())).thenReturn(orderInfoDtos);

        Page<OrderInfoDto> result = orderService.findOrdersByStatus(pageable, status);

        assertEquals(mockPage.getTotalElements(), result.getTotalElements());
        assertEquals(2, result.getContent().size());
    }

    @Test
    public void testFindOrderById() {
        Long orderId = 1L;
        Order order = Order.builder().build();
        OrderInfoDto orderInfoDto = OrderInfoDto.builder().build();

        when(orderRepository.findById(orderId)).thenReturn(java.util.Optional.of(order));
        when(orderInfoMapper.toDto(order)).thenReturn(orderInfoDto);

        OrderInfoDto result = orderService.findOrderById(orderId);

        assertEquals(orderInfoDto, result);
    }

    @Test
    public void testFindOrderById_WhenNotFound() {
        Long orderId = 1L;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> orderService.findOrderById(orderId));
    }

    @Test
    public void testAddOrder() {
        Long customerId = 1L;
        NewOrderDto newOrder = NewOrderDto.builder().build();
        Order order = Order.builder().build();
        DeliveryOrderDto deliveryOrderDto = DeliveryOrderDto.builder().build();

        when(newOrderMapper.toEntity(customerId, newOrder)).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order);
        when(deliveryOrderMapper.toDto(order)).thenReturn(deliveryOrderDto);

        DeliveryOrderDto result = orderService.addOrder(customerId, newOrder);

        assertEquals(deliveryOrderDto, result);
    }

    @Test
    public void testSendNewOrder() {
        Long orderId = 1L;
        String routingKey = "notification-key";

        Order order = Order.builder().status(OrderStatus.CUSTOMER_PAID).build();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        orderService.sendNewOrder(orderId, routingKey);

        verify(rabbitTemplate, times(1))
                .convertAndSend(eq("directExchange"), eq(routingKey), any(OrderActionDto.class));
    }
}