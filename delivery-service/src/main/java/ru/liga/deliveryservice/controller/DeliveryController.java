package ru.liga.deliveryservice.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.liga.deliveryservice.configuration.CaseInsensitiveEnumEditor;
import ru.liga.deliveryservice.dto.DeliveryDto;
import ru.liga.deliveryservice.dto.OrderActionDto;
import ru.liga.deliveryservice.model.DeliveryStatus;
import ru.liga.deliveryservice.service.DeliveryService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
public class DeliveryController {
    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping("/deliveries")
    public List<DeliveryDto> findAllDeliveries(@PositiveOrZero @RequestParam(defaultValue = "0") Integer pageIndex,
                                               @Positive @RequestParam(defaultValue = "10") Integer pageCount,
                                               @RequestParam DeliveryStatus status) {
        Pageable page = PageRequest.of(pageIndex, pageCount);
        return deliveryService.findAllDeliveries(page, status);
    }

    @PostMapping("/delivery/{id}")
    public void addDelivery(@PathVariable Long id, @Valid @RequestBody OrderActionDto orderAction) {
        deliveryService.addDelivery(id, orderAction);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(DeliveryStatus.class, new CaseInsensitiveEnumEditor(DeliveryStatus.class));
    }
}
