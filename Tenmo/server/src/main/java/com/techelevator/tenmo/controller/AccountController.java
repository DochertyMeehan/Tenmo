package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.BalanceDTO;
import com.techelevator.tenmo.model.Recipients;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
    @PreAuthorize("isAuthenticated()")
    public class AccountController {

        private AccountDao accountDao;
        private UserDao userDao;

        public AccountController(AccountDao accountDao, UserDao userDao) {
            this.accountDao = accountDao;
            this.userDao = userDao;
        }

        @RequestMapping(path="/balance", method = RequestMethod.GET)
        public BalanceDTO getBalance(Principal principal) {
            return accountDao.getBalance( principal.getName() );
        }

       @RequestMapping(path="/recipients", method = RequestMethod.GET)
       public List<Recipients> getRecipients() {
            List <Recipients> recipients = userDao.getRecipients();
        return recipients;
       }








    }
