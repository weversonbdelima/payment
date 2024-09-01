package com.example.backend.model;

import jakarta.persistence.*;

@Embeddable 
public class CardOnFile {

    private String usage;
    private String reason;


    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
