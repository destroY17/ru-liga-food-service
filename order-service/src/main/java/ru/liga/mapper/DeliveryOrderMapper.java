package ru.liga.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.liga.dto.DeliveryOrderDto;
import ru.liga.model.Order;

@Mapper(componentModel = "spring")
public interface DeliveryOrderMapper {
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "secretPaymentUrl", expression = "java(entity.getId() + \"paymentUrl\")")
    @Mapping(target = "estimatedTimeOfArrival", source = "entity.timestamp")
    DeliveryOrderDto toDto(Order entity);

}