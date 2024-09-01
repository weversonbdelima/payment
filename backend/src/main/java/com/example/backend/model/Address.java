package com.example.backend.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    private String street; 
    private String number;
    private String complement;
    private String zipCode;
    private String city;
    private String state;
    private String country;
    private String neighborhood; 


    public Address() {
    }


    public Address(String street, String number, String complement, String zipCode,
            String city, String state, String country, String neighborhood) {
        this.street = street;
        this.number = number;
        this.complement = complement;
        this.zipCode = zipCode;
        this.city = city;
        this.state = state;
        this.country = country;
        this.neighborhood = neighborhood; 
    }


    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }
}
