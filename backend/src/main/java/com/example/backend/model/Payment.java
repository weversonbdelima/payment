package com.example.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único

    @Column(name = "type")
    private String type;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "airline_data_id")
    private AirlineData airlineData;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credit_card_id")
    private CreditCard creditCard;

    @Column(name = "currency")
    private String currency;

    @Column(name = "country")
    private String country;

    @Column(name = "service_tax_amount")
    private double serviceTaxAmount;

    @Column(name = "installments")
    private int installments;

    @Column(name = "interest")
    private String interest;

    @Column(name = "capture")
    private boolean capture;

    @Column(name = "authenticate")
    private boolean authenticate;

    @Column(name = "recurrent")
    private boolean recurrent;

    @Column(name = "soft_descriptor")
    private String softDescriptor;

    @Column(name = "tip")
    private boolean tip;

    @Column(name = "is_crypto_currency_negotiation")
    private boolean isCryptoCurrencyNegotiation;

    @Column(name = "amount")
    private int amount;

    public Payment() {
    }

    public Payment(String type, AirlineData airlineData, CreditCard creditCard, String currency, String country,
            double serviceTaxAmount, int installments, String interest, boolean capture, boolean authenticate,
            boolean recurrent, String softDescriptor, boolean tip, boolean isCryptoCurrencyNegotiation, int amount) {
        this.type = type;
        this.airlineData = airlineData;
        this.creditCard = creditCard;
        this.currency = currency;
        this.country = country;
        this.serviceTaxAmount = serviceTaxAmount;
        this.installments = installments;
        this.interest = interest;
        this.capture = capture;
        this.authenticate = authenticate;
        this.recurrent = recurrent;
        this.softDescriptor = softDescriptor;
        this.tip = tip;
        this.isCryptoCurrencyNegotiation = isCryptoCurrencyNegotiation;
        this.amount = amount;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AirlineData getAirlineData() {
        return airlineData;
    }

    public void setAirlineData(AirlineData airlineData) {
        this.airlineData = airlineData;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getServiceTaxAmount() {
        return serviceTaxAmount;
    }

    public void setServiceTaxAmount(double serviceTaxAmount) {
        this.serviceTaxAmount = serviceTaxAmount;
    }

    public int getInstallments() {
        return installments;
    }

    public void setInstallments(int installments) {
        this.installments = installments;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public boolean isCapture() {
        return capture;
    }

    public void setCapture(boolean capture) {
        this.capture = capture;
    }

    public boolean isAuthenticate() {
        return authenticate;
    }

    public void setAuthenticate(boolean authenticate) {
        this.authenticate = authenticate;
    }

    public boolean isRecurrent() {
        return recurrent;
    }

    public void setRecurrent(boolean recurrent) {
        this.recurrent = recurrent;
    }

    public String getSoftDescriptor() {
        return softDescriptor;
    }

    public void setSoftDescriptor(String softDescriptor) {
        this.softDescriptor = softDescriptor;
    }

    public boolean isTip() {
        return tip;
    }

    public void setTip(boolean tip) {
        this.tip = tip;
    }

    public boolean isIsCryptoCurrencyNegotiation() {
        return isCryptoCurrencyNegotiation;
    }

    public void setIsCryptoCurrencyNegotiation(boolean isCryptoCurrencyNegotiation) {
        this.isCryptoCurrencyNegotiation = isCryptoCurrencyNegotiation;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
