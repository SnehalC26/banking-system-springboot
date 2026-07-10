package com.fintech.banking_app.service;


import java.util.List;

import com.fintech.banking_app.dto.AccountDto;
import com.fintech.banking_app.dto.TransactionDto;
import com.fintech.banking_app.entity.Transaction;

public interface AccountService {
AccountDto createAccount(AccountDto acccount);
AccountDto getAccountById(Long id);
AccountDto deposit(Long id, double amount);
AccountDto withdraw(Long id, double amount);
List<AccountDto> getAllAccounts();
void deleteAccount(Long id);
List<TransactionDto> getTransactionsByAccountId(Long id);
void transfer(Long fromAccountId, Long toAccountId, double amount);
List<Transaction> getTransactionHistory(Long accountId);

}
