package com.fintech.banking_app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.fintech.banking_app.entity.Transaction;
import com.fintech.banking_app.service.AccountService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {


    private final AccountService accountService;


    public TransactionController(AccountService accountService) {
        this.accountService = accountService;
    }


    @GetMapping("/account/{accountId}")
    public List<Transaction> getTransactionHistory(
            @PathVariable Long accountId
    ) {

        return accountService.getTransactionHistory(accountId);

    }
}