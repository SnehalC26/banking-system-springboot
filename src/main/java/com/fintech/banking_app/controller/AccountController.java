package com.fintech.banking_app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	@PostMapping
	public ResponseEntity<AccountDto> addAccount(   @Valid  @RequestBody AccountDto aDto){
		return new ResponseEntity<>(accountService.createAccount(aDto), HttpStatus.CREATED);
	}
	
	
	//GET Account REST API
	@GetMapping("/{id}")
	public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id){
		AccountDto accountDto = accountService.getAccountById(id);
		return ResponseEntity.ok(accountDto);
	}
	
	
	// Deposit REST API
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
	@PutMapping("/{id}/withdraw")
	public ResponseEntity<AccountDto> withdraw(@PathVariable Long id, @RequestBody Map<String, Double> request)
	{
		double amount = request.get("amount");
		AccountDto accountDto = accountService.withdraw(id, amount); 
		return ResponseEntity.ok(accountDto);
	}
	
	@GetMapping
	public ResponseEntity<List<AccountDto>> getAllAccounts(){
		List<AccountDto> accounts = accountService.getAllAccounts();
		return ResponseEntity.ok(accounts);
	}
	
	//Delete Account REST API
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteAccount(@PathVariable Long id){
		accountService.deleteAccount(id);
		return ResponseEntity.ok("Account is deleted successfully!!");
	}

	@GetMapping("/{id}/transactions")
	public ResponseEntity<List<TransactionDto>> getTransactions(
	        @PathVariable Long id) {


	    return ResponseEntity.ok(
	            accountService.getTransactionsByAccountId(id)
	    );
	}

	
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
