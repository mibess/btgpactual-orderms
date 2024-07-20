package com.mibess.btgpactual.orderms.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mibess.btgpactual.orderms.controller.dto.OrderResponse;
import com.mibess.btgpactual.orderms.service.OrderService;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/customers/{customerId}/orders")
    @ResponseStatus(value = HttpStatus.OK)
    public Page<OrderResponse> getOrders(
            @PathVariable Long customerId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {

        var pageRequest = PageRequest.of(page, pageSize);

        return orderService.getOrdersByCustumerId(customerId, pageRequest);
    }
}
