package com.saitechie.order_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saitechie.order_service.dto.OrderResponseDTO;
import com.saitechie.order_service.dto.PaymentDTO;
import com.saitechie.order_service.dto.UserDTO;
import com.saitechie.order_service.entity.Order;
import com.saitechie.order_service.repository.OrderRepository;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
public class OrderProcessingService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${order.producer.kafka.topic}")
    private String topicName;

    private static final String PAYMENTSERVICE_URL = "http://PAYMENT-SERVICE/payments";

    private static final String USERSERVICE_URL = "http://USER-SERVICE/users";

    public Order processOrder(Order order){
       log.info("In Process Order Service Method Sending the Order Details to the Kafka and Saving into the DB");
        try {
            order.setPurchasedDate(new Date());
            order.setOrderId(UUID.randomUUID().toString().split("-")[0]);
            kafkaTemplate.send(topicName, new ObjectMapper().writeValueAsString(order));
            return orderRepository.save(order);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public OrderResponseDTO getOrderDetails(String orderId){
        Order orderDTO = orderRepository.findByOrderId(orderId);
        PaymentDTO paymentDTO = restTemplate.getForObject(PAYMENTSERVICE_URL + "/" + orderDTO.getOrderId(), PaymentDTO.class);
        UserDTO userDTO = restTemplate.getForObject(USERSERVICE_URL + "/" + orderDTO.getUserId(), UserDTO.class);
        return new OrderResponseDTO().builder()
                .order(orderDTO)
                .payment(paymentDTO)
                .user(userDTO)
                .build();
    }



}
