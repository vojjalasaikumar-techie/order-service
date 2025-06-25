package com.saitechie.order_service.dto;

import com.saitechie.order_service.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDTO {
    private String message;
    private Order order;
    private PaymentDTO payment;
    private UserDTO user;
}
