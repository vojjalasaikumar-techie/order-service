package com.saitechie.order_service.controller;

import com.saitechie.order_service.dto.OrderResponseDTO;
import com.saitechie.order_service.entity.Order;
import com.saitechie.order_service.service.OrderProcessingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderProcessingController {

    @Autowired
    private OrderProcessingService orderProcessingService;

    @PostMapping
    public String processOrder(@RequestBody Order order){
        Order order1 = orderProcessingService.processOrder(order);
        if (order != null) {
            log.info("Order details stored in the kafka and the data saved in the databse for the user ");
            return "Order processed successfully with an Order ID "+order1.getOrderId()+"";
        }
        return null;
    }

    @GetMapping("/{orderId}")
    public OrderResponseDTO getOrderDetails(@PathVariable String orderId){
        log.info("OrderDetails Initiated");
        return orderProcessingService.getOrderDetails(orderId);
    }

}
