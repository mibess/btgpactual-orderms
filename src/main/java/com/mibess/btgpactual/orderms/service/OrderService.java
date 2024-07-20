package com.mibess.btgpactual.orderms.service;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.mibess.btgpactual.orderms.controller.dto.OrderResponse;
import com.mibess.btgpactual.orderms.entity.OrderEntity;
import com.mibess.btgpactual.orderms.entity.OrderItem;
import com.mibess.btgpactual.orderms.listener.dto.OrderCreatedEvent;
import com.mibess.btgpactual.orderms.repository.OrderRepository;

@Service
public class OrderService {

    private final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void createOrder(OrderCreatedEvent event) {

        var entity = new OrderEntity();

        entity.setId(event.codigoPedido());
        entity.setCustumerId(event.codigoCliente());
        entity.setItems(getOrderItems(event));
        entity.setTotal(getTotal(event));

        orderRepository.save(entity);

        log.info("Order created: {}", entity.getId());

    }

    public Page<OrderResponse> getOrdersByCustumerId(Long customerId, PageRequest pageRequest) {
        var orders = orderRepository.findAllByCustumerId(customerId, pageRequest);

        log.info("Orders found: {}", orders.getTotalElements());

        return orders.map(OrderResponse::fromEntity);

    }

    private BigDecimal getTotal(OrderCreatedEvent event) {
        return event.itens().stream().map(item -> item.preco().multiply(BigDecimal.valueOf(item.quantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<OrderItem> getOrderItems(OrderCreatedEvent event) {
        return event.itens().stream().map(item -> {
            var orderItem = new OrderItem();
            orderItem.setProduct(item.produto());
            orderItem.setQuantity(item.quantidade());
            orderItem.setPrice(item.preco());
            return orderItem;
        }).toList();
    }
}
