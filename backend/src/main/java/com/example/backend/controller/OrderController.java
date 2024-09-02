package com.example.backend.controller;

import com.example.backend.model.CreditCard;
import com.example.backend.model.Order;
import com.example.backend.model.Payment;
import com.example.backend.model.PaymentRequest;
import com.example.backend.service.CieloService;
import com.example.backend.service.OrderService;
import com.example.backend.utils.CryptoUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
                                                                                         
    @Autowired
    private OrderService orderService;
    @Autowired
    private CieloService cieloService;

    @Value("${encryption.secretKey}")
    private String secretKey;

    @PostMapping
    public ResponseEntity<Object> createOrder(@RequestBody Order order) {
        logger.info("Starting order creation process...");

        try {
            logger.info("Decrypting credit card information...");
            order.setCreditCard(CryptoUtils.decryptCard(order.getCreditCard(), secretKey));
            order.setStatus("payment-pendent");

            logger.info("Saving order...");
            order = orderService.createOrderOrSave(order);

            CreditCard creditCard = new CreditCard();
            creditCard.setCardNumber(order.getCreditCard().getCardNumber());
            creditCard.setHolder(order.getCreditCard().getHolder());
            creditCard.setExpirationDate(order.getCreditCard().getExpirationDate());
            creditCard.setSecurityCode(order.getCreditCard().getSecurityCode());
            creditCard.setSaveCard(order.getCreditCard().isSaveCard());
            creditCard.setBrand(order.getCreditCard().getBrand());

            Payment payment = new Payment();
            payment.setType("CreditCard");
            payment.setCreditCard(creditCard);
            payment.setCurrency("BRL");
            payment.setCountry("BRA");
            payment.setInstallments(1);
            payment.setInterest("ByMerchant");
            payment.setCapture(true);
            payment.setAmount(order.getAmount());

            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setPayment(payment);
            paymentRequest.setMerchantOrderId(order.getId().toString());

            logger.info("Processing payment...");
            boolean isApprovedPayment = cieloService.processPayment(paymentRequest);
            order.setStatus(isApprovedPayment ? "payment-approved" : "payment-not-approved");

            logger.info("Updating order status to: {}", order.getStatus());
            order = orderService.createOrderOrSave(order);

            logger.info("Order created successfully with ID: {}", order.getId());
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            logger.error("Failed to create order: ", e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Erro interno, tente novamente.");
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<Object> cancelOrder(@PathVariable Long id) {
        logger.info("Cancel order with ID: {}", id);

        try {
            Order order = orderService.getOrder(id);
            logger.info("Retrieved order with ID: {}", id);

            if ("canceled".equals(order.getStatus())) {
                logger.warn("Order with ID {} is already canceled.", id);
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("message", "Erro ao tentar cancelar o pedido. O pedido já está cancelado.");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            logger.info("Attempting to cancel order with ID: {}", id);
            boolean isCanceled = cieloService.cancel(order);

            if (isCanceled) {
                logger.info("Order with ID: {} canceled successfully.", id);
                Order orderCanceled = orderService.cancelOrder(order);
                return ResponseEntity.ok(orderCanceled);
            } else {
                logger.error("Failed to cancel order with ID: {}", id);
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("message", "Erro ao tentar cancelar pedido.");
                return ResponseEntity.badRequest().body(errorResponse);
            }

        } catch (Exception e) {
            logger.error("Failed to cancel Order with ID: {}: ", id, e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Erro interno, tente novamente.");
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping
    public ResponseEntity<Object> getAllOrders() {
        logger.info("Retrieving all orders");
        try {
            return ResponseEntity.ok(orderService.getAllOrders());
        } catch (Exception e) {
            logger.error("Failed to get all orders: ", e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Erro interno, tente novamente.");
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}
