package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.model.Transfer;


import java.util.List;


public interface TransferDao {

    Integer create (Transfer transfer) throws Exception;


    List<Transfer> getTransfersBySenderId(int userId) throws Exception;

    List<Transfer> getTransfersByRecipientId(int userId) throws Exception;

    Transfer getTransferByTransferId (int transferId) throws Exception;





}