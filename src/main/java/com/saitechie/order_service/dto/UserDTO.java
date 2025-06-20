package com.saitechie.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer userId;
    private String userName;
    private String email;
    private double availableAmount;
    private String accNumber;
    private String paymentMethod;
}
