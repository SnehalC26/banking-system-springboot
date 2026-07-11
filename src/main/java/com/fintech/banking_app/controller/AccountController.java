package com.fintech.banking_app.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fintech.banking_app.dto.AccountDto;
import com.fintech.banking_app.dto.TransactionDto;
import com.fintech.banking_app.dto.TransactionRequest;
import com.fintech.banking_app.dto.TransferRequestDto;
import com.fintech.banking_app.service.AccountService;

import jakarta.validation.Valid;

@RestController // make spring mvc  rest controller class 
@RequestMapping("/api/accounts")
public class AccountController {
	private AccountService accountService;
//to inject this accountService we create da constructor based injection
	// since this class has only one constructor so spring will automatically inject the dependency
	public AccountController(AccountService accountService) {
		super();
		this.accountService = accountService;
	}
	

	//add accont REST API
	@PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
	@PostMapping
	public ResponseEntity<AccountDto> addAccount(
	        @Valid @RequestBody AccountDto aDto,
	        Authentication authentication
	){

	    AccountDto account =
	            accountService.createAccount(
	                    aDto,
	                    authentication.getName()
	            );


	    return new ResponseEntity<>(
	            account,
	            HttpStatus.CREATED
	    );
	}
	
	
	//GET Account REST API
	@PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id){
		AccountDto accountDto = accountService.getAccountById(id);
		return ResponseEntity.ok(accountDto);
	}
	
	
	// Deposit REST API
	@PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
	@PutMapping("/{id}/deposit")
	public ResponseEntity<AccountDto> deposit(
	        @PathVariable Long id,
	        @Valid @RequestBody TransactionRequest request) {


	    AccountDto accountDto = accountService.deposit(
	            id,
	            request.amount()
	    );


	    return ResponseEntity.ok(accountDto);
	}
	
	//Withdraw REST API
	@PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
	@PutMapping("/{id}/withdraw")
	public ResponseEntity<AccountDto> withdraw(
	        @PathVariable Long id,
	        @Valid @RequestBody TransactionRequest request) {


	    AccountDto accountDto = accountService.withdraw(
	            id,
	            request.amount()
	    );


	    return ResponseEntity.ok(accountDto);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<AccountDto>> getAllAccounts(){
		List<AccountDto> accounts = accountService.getAllAccounts();
		return ResponseEntity.ok(accounts);
	}
	
	//Delete Account REST API
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteAccount(@PathVariable Long id){
		accountService.deleteAccount(id);
		return ResponseEntity.ok("Account is deleted successfully!!");
	}

	@PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
	@GetMapping("/{id}/transactions")
	public ResponseEntity<List<TransactionDto>> getTransactions(
	        @PathVariable Long id) {


	    return ResponseEntity.ok(
	            accountService.getTransactionsByAccountId(id)
	    );
	}

	@PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
	@PostMapping("/transfer")
	public ResponseEntity<String> transfer(
	        @Valid @RequestBody TransferRequestDto request) {


	    accountService.transfer(
	            request.fromAccountId(),
	            request.toAccountId(),
	            request.amount()
	    );


	    return ResponseEntity.ok(
	            "Transfer successful"
	    );
	}
}
