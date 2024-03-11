package com.techelevator.tenmo.model;

public class TransferResponseDTO {

    private int transferId;

    private double amount;

    private String from;

    private String to;

    public TransferResponseDTO(int transferId, double amount, String from, String to) {
        this.transferId = transferId;
        this.amount = amount;
        this.from = from;
        this.to = to;
    }

    public TransferResponseDTO() {
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
