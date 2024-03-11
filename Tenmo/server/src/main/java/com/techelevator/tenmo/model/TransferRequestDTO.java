package com.techelevator.tenmo.model;

public class TransferRequestDTO {

    private double amount;

    private String username;

    public TransferRequestDTO(double amount, String username) {
        this.amount = amount;
        this.username = username;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
