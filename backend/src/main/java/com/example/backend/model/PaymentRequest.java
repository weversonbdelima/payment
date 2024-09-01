package com.example.backend.model;

public class PaymentRequest {
    private Customer Customer;
    private Payment Payment;
    private String MerchantOrderId;


    public PaymentRequest() {
    }

    public PaymentRequest(Customer customer, Payment payment, String merchantOrderId) {
        this.Customer = customer;
        this.Payment = payment;
        this.MerchantOrderId = merchantOrderId;
    }

    public Customer getCustomer() {
        return Customer;
    }

    public void setCustomer(Customer customer) {
        Customer = customer;
    }

    public Payment getPayment() {
        return Payment;
    }

    public void setPayment(Payment payment) {
        Payment = payment;
    }

    public String getMerchantOrderId() {
        return MerchantOrderId;
    }

    public void setMerchantOrderId(String merchantOrderId) {
        MerchantOrderId = merchantOrderId;
    }
}
