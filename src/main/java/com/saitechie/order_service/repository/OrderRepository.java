package com.saitechie.order_service.repository;


import com.saitechie.order_service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findByOrderId(String orderId);
}
