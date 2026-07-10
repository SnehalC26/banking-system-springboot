package com.fintech.banking_app.service.impl;

import java.util.List;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fintech.banking_app.dto.AccountDto;
import com.fintech.banking_app.dto.TransactionDto;
import com.fintech.banking_app.entity.Account;
import com.fintech.banking_app.entity.Transaction;
import com.fintech.banking_app.entity.TransactionDirection;
import com.fintech.banking_app.entity.TransactionType;
import com.fintech.banking_app.exception.AccountException;
import com.fintech.banking_app.exception.InsufficientBalanceException;
import com.fintech.banking_app.mapper.AccountMapper;
import com.fintech.banking_app.repository.AccountRepository;
import com.fintech.banking_app.repository.TransactionRepository;
import com.fintech.banking_app.service.AccountService;

import jakarta.transaction.Transactional;
@Service //automcatically create spring bean for this class
public class AccountServiceImpl implements AccountService{

	
	private AccountRepository accRepo;
	private TransactionRepository transRepo;
	// constructor based dependency injection to inject this accountrepository
	@Autowired //but spring 4.3 onwards not required becoz when spring find a single constructor in a spring bean spring will automatically inject a spring injection
	public AccountServiceImpl(AccountRepository accRepo, TransactionRepository transRepo) {
		super();
		this.accRepo = accRepo;
		this.transRepo= transRepo;
	}
	
	@Override
	public AccountDto createAccount(AccountDto aDto) { // convert account dto into account jpa entity and then save account jpa entity into database
		Account ac = AccountMapper.mapToAccount(aDto);
		Account savedAccount = accRepo.save(ac);
		
		return AccountMapper.mapToAccountDto(savedAccount);
	}

	@Override
	public AccountDto getAccountById(Long id) {
		Account account = accRepo
				.findById(id)
				.orElseThrow(() -> new AccountException(
						  "Account not found with id : " + id));
		
		
		return AccountMapper.mapToAccountDto(account);
	}

	@Override
	@Transactional
	public AccountDto deposit(Long id, double amount) {

	    Account account = accRepo.findById(id)
	            .orElseThrow(() ->
	                    new AccountException(
	                            "Account not found with id : " + id
	                    )
	            );


	    account.setBalance(account.getBalance() + amount);


	    Account savedAccount = accRepo.save(account);

	    Transaction transaction = new Transaction(
	            amount,
	            TransactionType.DEPOSIT,
	            TransactionDirection.CREDIT,
	            "Deposited to account " + savedAccount.getId(),
	            savedAccount
	    );

	    transRepo.save(transaction);


	    return AccountMapper.mapToAccountDto(savedAccount);
	}

	@Override
	@Transactional
	public AccountDto withdraw(Long id, double amount) {

	    Account account = accRepo
	            .findById(id)
	            .orElseThrow(() ->
	                    new AccountException(
	                            "Account does not exists."
	                    )
	            );


	    if(account.getBalance() < amount) {
	        throw new InsufficientBalanceException(
	                "Insufficient balance"
	        );
	    }


	    account.setBalance(
	            account.getBalance() - amount
	    );


	    Account savedAccount = accRepo.save(account);

	    Transaction transaction = new Transaction(
	            amount,
	            TransactionType.WITHDRAW,
	            TransactionDirection.DEBIT,
	            "Withdrawal from account " + savedAccount.getId(),
	            savedAccount
	    );

	    transRepo.save(transaction);


	    return AccountMapper.mapToAccountDto(savedAccount);
	}

	@Override
	public List<AccountDto> getAllAccounts() {
		List<Account> accounts = accRepo.findAll();
		return accounts.stream().map((account) -> AccountMapper.mapToAccountDto(account))
		.collect(Collectors.toList());
	}

	@Override
	public void deleteAccount(Long id) {
		Account account = accRepo.findById(id).orElseThrow(() -> new AccountException("Account does not exists."));
		accRepo.deleteById(id);
	}
	
	@Override
	public List<TransactionDto> getTransactionsByAccountId(Long id) {

	    Account account = accRepo.findById(id)
	            .orElseThrow(() ->
	                    new AccountException(
	                            "Account does not exists."
	                    )
	            );


	    List<Transaction> transactions =
	            transRepo.findByAccountId(id);


	    return transactions.stream()
	            .map(transaction ->
	                    new TransactionDto(
	                            transaction.getId(),
	                            transaction.getAmount(),
	                            transaction.getType(),
	                            transaction.getDirection(),
	                            transaction.getTimestamp()
	                    )
	            )
	            .collect(Collectors.toList());
	}
	    
	@Override
	@Transactional
	public void transfer(Long fromAccountId, Long toAccountId, double amount) {


	    // 1. Find sender account
	    Account sender = accRepo.findById(fromAccountId)
	            .orElseThrow(() ->
	                    new AccountException(
	                            "Sender account not found"
	                    )
	            );


	    // 2. Find receiver account
	    Account receiver = accRepo.findById(toAccountId)
	            .orElseThrow(() ->
	                    new AccountException(
	                            "Receiver account not found"
	                    )
	            );


	    // 3. Check same account transfer
	    if(sender.getId().equals(receiver.getId())) {

	        throw new AccountException(
	                "Cannot transfer money to the same account"
	        );

	    }


	    // 4. Check sufficient balance
	    if(sender.getBalance() < amount) {

	        throw new InsufficientBalanceException(
	                "Insufficient balance"
	        );

	    }


	    // 5. Update balances

	    sender.setBalance(
	            sender.getBalance() - amount
	    );


	    receiver.setBalance(
	            receiver.getBalance() + amount
	    );


	    // 6. Save both accounts

	    Account savedSender = accRepo.save(sender);

	    Account savedReceiver = accRepo.save(receiver);

// 7. Sender Side
	    
	    Transaction senderTransaction = new Transaction(
	            amount,
	            TransactionType.TRANSFER,
	            TransactionDirection.DEBIT,
	            "Transfer to account " + savedReceiver.getId(),
	            savedSender
	    );
	   // 8. Receiver side:

	    Transaction receiverTransaction = new Transaction(
	            amount,
	            TransactionType.TRANSFER,
	            TransactionDirection.CREDIT,
	            "Received from account " + savedSender.getId(),
	            savedReceiver
	    );
	    
	    

	   

	    // 9. Save transactions

	    transRepo.save(senderTransaction);

	    transRepo.save(receiverTransaction);

	}
	@Override
	public List<Transaction> getTransactionHistory(Long accountId) {

	    // check account exists
	    accRepo.findById(accountId)
	            .orElseThrow(() ->
	                    new AccountException(
	                            "Account not found"
	                    )
	            );


	    // fetch transactions
	    return transRepo.findByAccountId(accountId);
	}

}
