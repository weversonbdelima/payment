package com.example.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "airline_data")
public class AirlineData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    @Column(name = "ticket_number")
    private String ticketNumber;


    public AirlineData() {
    }


    public AirlineData(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }
}
