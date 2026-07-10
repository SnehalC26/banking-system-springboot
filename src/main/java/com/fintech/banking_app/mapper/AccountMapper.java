package com.fintech.banking_app.mapper;

import com.fintech.banking_app.dto.AccountDto;
import com.fintech.banking_app.entity.Account;

public class AccountMapper {

	public static Account mapToAccount(AccountDto aDto) {
		Account account = new Account(aDto.id(), aDto.accountHolderName(), aDto.balance());
	return account;
	}

	public static AccountDto mapToAccountDto(Account ac) {
		AccountDto aDto = new AccountDto(ac.getId(), ac.getAccountHolderName(), ac.getBalance());
		return aDto;
	}
}
