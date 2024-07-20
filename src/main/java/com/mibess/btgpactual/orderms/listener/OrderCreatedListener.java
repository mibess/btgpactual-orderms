package com.mibess.btgpactual.orderms.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.mibess.btgpactual.orderms.listener.dto.OrderCreatedEvent;
import com.mibess.btgpactual.orderms.service.OrderService;

import static com.mibess.btgpactual.orderms.config.RabbitMQConfig.ORDER_CREATED_QUEUE;

@Component
public class OrderCreatedListener {

    private final Logger log = LoggerFactory.getLogger(OrderCreatedListener.class);

    private final OrderService orderService;

    public OrderCreatedListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = ORDER_CREATED_QUEUE)
    public void listen(Message<OrderCreatedEvent> message) {
        log.info("RabbitMQ: {} received message: {}", ORDER_CREATED_QUEUE, message.getPayload());

        orderService.createOrder(message.getPayload());
    }

}
