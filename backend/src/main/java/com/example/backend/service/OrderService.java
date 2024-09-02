package com.example.backend.service;

import com.example.backend.model.Order;
import com.example.backend.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order createOrderOrSave(Order order) {

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

    }

    public Order getOrder(Long id) {

        return orderRepository.getReferenceById(id);

    }

    public Order cancelOrder(Order order) {

        Order orderCanceled = orderRepository.getReferenceById(order.getId());

        orderCanceled.setStatus("canceled");
        return orderRepository.save(order);
    }
}
