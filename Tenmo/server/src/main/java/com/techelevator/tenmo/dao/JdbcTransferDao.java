package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@Component
    public class JdbcTransferDao implements TransferDao {

        private JdbcTemplate jdbcTemplate;

        public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }


    public Integer create(Transfer transfer) throws Exception {
            Integer transferId;
            try {
                String sql = "INSERT INTO transfer (sender_id, recipient_id, amount) VALUES (?, ? , ?) RETURNING transfer_id;";
                transferId = jdbcTemplate.queryForObject(sql, Integer.class, transfer.getSender_id(), transfer.getRecipient_id(), transfer.getAmount());
            } catch ( Exception e ){
                throw new Exception ("Failed to create transfer");
           }
            return transferId;
    }

    @Override
    public List<Transfer> getTransfersBySenderId(int userId) throws Exception {
            List<Transfer> senderTransfers = new ArrayList<>();
            String sql = "SELECT * FROM transfer WHERE sender_id = ?;";
            try{
                SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
                while(rowSet.next()){
                    senderTransfers.add(mapRowToTransfer(rowSet));
                }
            } catch( Exception e){
                throw new Exception("Unable to get transfers");
            }
            return senderTransfers;
    }

    @Override
    public List<Transfer> getTransfersByRecipientId(int userId) throws Exception {
        List<Transfer> senderTransfers = new ArrayList<>();
        String sql = "SELECT * FROM transfer WHERE recipient_id = ?;";
        try{
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
            while(rowSet.next()){
                senderTransfers.add(mapRowToTransfer(rowSet));
            }
        } catch( Exception e){
            throw new Exception("Unable to get transfers");
        }
        return senderTransfers;
    }


    @Override
    public Transfer getTransferByTransferId(int transferId) throws Exception {
            Transfer transfer = null;
            String sql = "SELECT * FROM transfer WHERE transfer_id = ?;";
        try{
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, transferId);
            if(rowSet.next()){
                transfer = mapRowToTransfer(rowSet);
            }
        } catch( Exception e){
            throw new Exception("Unable to get transfers");
        }
        return transfer;

    }


    public Transfer mapRowToTransfer(SqlRowSet results){
            Transfer transfer = new Transfer(
                    results.getInt("transfer_id"),
                    results.getInt("sender_id"),
                    results.getInt("recipient_id"),
                    results.getDouble("amount")
            );
            return transfer;
    }

}
