package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/transfers/")
public class TransferController {
    private final AccountDao accountDao;
    private final TransferDao transferDao;
    private final UserDao userDao;

    public TransferController(AccountDao accountDao, TransferDao transferDao, UserDao userDao) {
        this.accountDao = accountDao;
        this.transferDao = transferDao;
        this.userDao = userDao;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "newtransfer", method = RequestMethod.POST)
    public TransferResponseDTO create(@Valid  @RequestBody TransferRequestDTO transferrequestDTO, Principal principal) throws Exception {
        if ( transferrequestDTO.getAmount() <= 0){
            throw new Exception("Transfer amount can't be less then or equal to zero");
        }

        User recipient = userDao.findByUsername(transferrequestDTO.getUsername());
        User sender = userDao.findByUsername(principal.getName());

        if (recipient.equals(sender)){
            throw new Exception("Can't send money to yourself");
        }
        Account recipientAccount = accountDao.getAccountByUserId(recipient.getId());
        Account senderAccount = accountDao.getAccountByUserId(sender.getId());

        if (senderAccount.getBalance() < transferrequestDTO.getAmount()){
            throw new Exception("Insufficient Funds");
        }

        recipientAccount.setBalance(recipientAccount.getBalance() + transferrequestDTO.getAmount());
        senderAccount.setBalance(senderAccount.getBalance() - transferrequestDTO.getAmount());

        accountDao.update(recipientAccount);
        accountDao.update(senderAccount);

        Transfer transfer = new Transfer(senderAccount.getAccountId(), recipientAccount.getAccountId(), transferrequestDTO.getAmount());

        int transferId = transferDao.create(transfer);

        TransferResponseDTO transferresponse = new TransferResponseDTO(transferId, transfer.getAmount(), sender.getUsername(), recipient.getUsername());
        return transferresponse;
    }


    @RequestMapping(path="gettransfers", method = RequestMethod.GET)
    public List<TransferResponseDTO> getTransfers(Principal principal) throws Exception {

        User user = userDao.findByUsername(principal.getName());
        Account userAccount = accountDao.getAccountByUserId(user.getId());

        List<Transfer> recipientTransfers = transferDao.getTransfersByRecipientId(userAccount.getAccountId());
        List<Transfer> senderTransfers = transferDao.getTransfersBySenderId(userAccount.getAccountId());

        List<TransferResponseDTO> responses = new ArrayList<>();
        for(Transfer transfer: recipientTransfers){
            Account account = accountDao.getAccountAccountId(transfer.getSender_id());
            User sender = userDao.findByUserId(account.getUserId());
            TransferResponseDTO response = new TransferResponseDTO();
            response.setAmount(transfer.getAmount());
            response.setFrom(sender.getUsername());
            response.setTo(principal.getName());
            response.setTransferId(transfer.getTransfer_id());
            responses.add(response);
        }

        for(Transfer transfer: senderTransfers){
            Account account = accountDao.getAccountAccountId(transfer.getRecipient_id());
            User recipient = userDao.findByUserId(account.getUserId());
            TransferResponseDTO response = new TransferResponseDTO();
            response.setAmount(transfer.getAmount());
            response.setFrom(principal.getName());
            response.setTo(recipient.getUsername());
            response.setTransferId(transfer.getTransfer_id());
            responses.add(response);
        }

        return responses;

    }

    @RequestMapping(path="gettransfer/{transferid}", method = RequestMethod.GET)
    public TransferResponseDTO getTransfer(@PathVariable int transferid, Principal principal) throws Exception {
            Transfer transfer = transferDao.getTransferByTransferId(transferid);

            Account senderAccount = accountDao.getAccountAccountId(transfer.getSender_id());
            Account recipientAccount = accountDao.getAccountAccountId(transfer.getRecipient_id());

            User sender = userDao.findByUserId(senderAccount.getUserId());
            User recipient = userDao.findByUserId(recipientAccount.getUserId());

            TransferResponseDTO response = new TransferResponseDTO(transferid, transfer.getAmount(), sender.getUsername(), recipient.getUsername());
            return response;

    }
}

