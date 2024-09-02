package com.example.backend.service;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.backend.model.Order;
import com.example.backend.model.PaymentRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CieloService {
    private static final Logger logger = LoggerFactory.getLogger(CieloService.class);

    @Value("${cielo.sandbox.api.key}")
    private String merchantId;

    @Value("${cielo.sandbox.api.secret}")
    private String merchantKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public CieloService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public Boolean cancel(Order order) {
        String url = "https://apisandbox.cieloecommerce.cielo.com.br/1/sales/OrderId/" + order.getId() + "/void?amount="
                + order.getAmount();

        logger.info("Attempting to cancel order with ID: {}", order.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.set("MerchantId", merchantId);
        headers.set("MerchantKey", merchantKey);
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            logger.info("Transaction approved for order ID: {}", order.getId());

            JSONObject jsonResponse = new JSONObject(response.getBody());
            String reasonMessage = jsonResponse.optString("ReasonMessage", "");

            if ("Successful".equals(reasonMessage)) {
                logger.info("Cancellation of order ID: {} was successful.", order.getId());
                return true;
            } else {
                logger.warn("Cancellation of order ID: {} was not successful. Reason: {}", order.getId(),
                        reasonMessage);
            }
        } else {
            logger.warn("Transaction not approved for order ID: {}. Status: {}", order.getId(),
                    response.getStatusCode());
        }

        return false;
    }

    public Boolean processPayment(PaymentRequest paymentRequest) {
        String url = "https://apisandbox.cieloecommerce.cielo.com.br/1/sales/";

        logger.info("Attempting to process payment for: {}", paymentRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.set("MerchantId", merchantId);
        headers.set("MerchantKey", merchantKey);
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");

        String jsonBody;
        try {
            jsonBody = objectMapper.writeValueAsString(paymentRequest);
            logger.info("Payment request converted to JSON: {}", jsonBody);
        } catch (JsonProcessingException e) {
            logger.error("Error converting PaymentRequest object to JSON", e);
            throw new RuntimeException("Error converting PaymentRequest object to JSON", e);
        }

        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode() == HttpStatus.CREATED) { // 201
            logger.info("Payment processed successfully for: {}", paymentRequest);
            return true;
        } else {
            logger.warn("Failed to process payment. Status: {}", response.getStatusCode());
            return false;
        }
    }
}
