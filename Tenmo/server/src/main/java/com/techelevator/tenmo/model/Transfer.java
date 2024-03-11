package com.techelevator.tenmo.model;

public class Transfer {

    private int transfer_id;

    private int sender_id;

    private int recipient_id;
    private double amount;

    public Transfer(int transfer_id, int sender_id, int recipient_id, double amount) {
        this.transfer_id = transfer_id;
        this.sender_id = sender_id;
        this.recipient_id = recipient_id;
        this.amount = amount;
    }

    public Transfer(  int sender_id, int recipient_id, double amount) {
        this.sender_id = sender_id;
        this.recipient_id = recipient_id;
        this.amount = amount;
    }

    public int getTransfer_id() {
        return transfer_id;
    }

    public void setTransfer_id(int transfer_id) {
        this.transfer_id = transfer_id;
    }

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public int getRecipient_id() {
        return recipient_id;
    }

    public void setRecipient_id(int recipient_id) {
        this.recipient_id = recipient_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
