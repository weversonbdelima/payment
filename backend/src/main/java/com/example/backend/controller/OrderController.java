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

    private static final Logger logger = LoggerFactory.getLogger(CieloService.class);
    @Autowired
    private OrderService orderService;
    @Autowired
    private CieloService cieloService;

    @Value("${encryption.secretKey}")
    private String secretKey;

    @PostMapping
    public ResponseEntity<Object> createOrder(@RequestBody Order order) {

        try {

            // TODO: Descriptografar dados do cartão.
            order.setCreditCard(CryptoUtils.decryptCard(order.getCreditCard(), secretKey));

            logger.info("Decrypt {} ", order.getCreditCard().getSecurityCode());

            order.setStatus("payment-pendent");

            Order newOrder = new Order();

            newOrder.setAmount(order.getAmount());
            newOrder.setDescription(order.getDescription());
            newOrder.setStatus(order.getStatus());
            newOrder.setCreditCard(order.getCreditCard());

            order = orderService.createOrder(newOrder);

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

            boolean isAprovvedPayment = cieloService.processPayment(paymentRequest);

            if (isAprovvedPayment) {
                order.setStatus("payment-approved");
            } else {
                order.setStatus("payment-not-approved");
            }

            logger.error("ORDER ID {}", order.getId());

            order = orderService.save(order);

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
        try {
            logger.info("Cancel order with ID: {}", id);

            Order order = orderService.getOrder(id);

            if ("canceled".equals(order.getStatus())) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("message", "Erro ao tentar cancelar o pedido. O pedido já está cancelado.");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            boolean isCanceled = cieloService.cancel(order);

            if (isCanceled) {
                Order orderCanceled = orderService.cancelOrder(order);
                return ResponseEntity.ok(orderCanceled);

            } else {

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
        try {
            logger.info("Get all orders");
            return ResponseEntity.ok(orderService.getAllOrders());
        } catch (Exception e) {
            logger.error("Failed to get all orders: ", e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Erro interno, tente novamente.");
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}
