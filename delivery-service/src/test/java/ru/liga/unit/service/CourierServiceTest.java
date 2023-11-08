package ru.liga.unit.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.batismapper.CourierMapper;
import ru.liga.dto.OrderActionDto;
import ru.liga.model.Courier;
import ru.liga.model.CourierStatus;
import ru.liga.model.Order;
import ru.liga.model.OrderStatus;
import ru.liga.queue.DeliveryQueue;
import ru.liga.repository.OrderRepository;
import ru.liga.service.RabbitService;
import ru.liga.service.impl.CourierServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourierServiceTest {

    @InjectMocks
    private CourierServiceImpl courierService;

    @Mock
    private RabbitService rabbitService;

    @Mock
    private CourierMapper courierMapper;

    @Mock
    private OrderRepository orderRepository;

    @Test
    public void testFindAllCouriers() {
        List<Courier> couriers = List.of(new Courier(), new Courier());
        when(courierMapper.findAll()).thenReturn(couriers);

        List<Courier> result = courierService.findAllCouriers();

        verify(courierMapper).findAll();

        assertNotNull(result);
        assertEquals(couriers, result);
    }

    @Test
    public void testFindCourierById() {
        Long courierId = 1L;
        Courier courier = new Courier();
        when(courierMapper.findCourierById(courierId)).thenReturn(Optional.of(courier));

        Courier result = courierService.findCourierById(courierId);

        verify(courierMapper).findCourierById(courierId);

        assertNotNull(result);
        assertEquals(courier, result);
    }

    @Test
    public void testCompleteDelivery() {
        UUID orderId = UUID.randomUUID();
        Order order = Order.builder().status(OrderStatus.DELIVERY_DELIVERING).build();

        Courier courier = new Courier();
        courier.setStatus(CourierStatus.ACTIVE);
        order.setCourier(courier);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        courierService.completeDelivery(orderId);

        verify(orderRepository).findById(orderId);
        verify(orderRepository).updateOrderByStatus(orderId, OrderStatus.DELIVERY_COMPLETE);
        verify(rabbitService).sendDeliveryStatus(any(OrderActionDto.class), eq(DeliveryQueue.DELIVERY_COMPLETE));

        assertEquals(courier.getStatus(), CourierStatus.INACTIVE);
    }
}