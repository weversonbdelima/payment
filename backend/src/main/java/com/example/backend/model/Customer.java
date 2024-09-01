package com.example.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    @Embedded
    private Address address; 

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "delivery_street")),
            @AttributeOverride(name = "number", column = @Column(name = "delivery_number")),
            @AttributeOverride(name = "complement", column = @Column(name = "delivery_complement")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "delivery_zip_code")),
            @AttributeOverride(name = "city", column = @Column(name = "delivery_city")),
            @AttributeOverride(name = "state", column = @Column(name = "delivery_state")),
            @AttributeOverride(name = "country", column = @Column(name = "delivery_country")),
            @AttributeOverride(name = "neighborhood", column = @Column(name = "delivery_neighborhood"))
    })
    private Address deliveryAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "billing_street")),
            @AttributeOverride(name = "number", column = @Column(name = "billing_number")),
            @AttributeOverride(name = "complement", column = @Column(name = "billing_complement")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "billing_zip_code")),
            @AttributeOverride(name = "city", column = @Column(name = "billing_city")),
            @AttributeOverride(name = "state", column = @Column(name = "billing_state")),
            @AttributeOverride(name = "country", column = @Column(name = "billing_country")),
            @AttributeOverride(name = "neighborhood", column = @Column(name = "billing_neighborhood"))
    })
    private Address billingAddress; 

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "birthdate")
    private String birthdate;


    public Customer() {
    }


    public Customer(Address address, Address deliveryAddress, Address billingAddress, String name, String email,
            String birthdate) {
        this.address = address;
        this.deliveryAddress = deliveryAddress;
        this.billingAddress = billingAddress;
        this.name = name;
        this.email = email;
        this.birthdate = birthdate;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }
}
