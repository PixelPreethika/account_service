package com.sample.accounts.mapper;

import com.sample.accounts.dto.AccountsDTO;
import com.sample.accounts.entity.Accounts;

public class AccountsMapper {

	public static AccountsDTO maptoAccountsDTO(Accounts accounts, AccountsDTO accountsDTO) {
		accountsDTO.setAccountNumber(accounts.getAccountNumber());
		accountsDTO.setAccountType(accounts.getAccountType());
		accountsDTO.setBranchAddres(accounts.getBranchAddres());
		return accountsDTO;

	}

	public static Accounts maptoAccounts(AccountsDTO accountsDTO, Accounts accounts) {
		accounts.setAccountNumber(accountsDTO.getAccountNumber());
		accounts.setAccountType(accountsDTO.getAccountType());
		accounts.setBranchAddres(accountsDTO.getBranchAddres());
//		accounts.setCreatedAt(null);
//		accounts.setCreatedBy(null);
//		accounts.setCustomerId(null);
//		accounts.setUpdatedBy(null);
//		accounts.setUpdatedAt(null);
		return accounts;

	}
}
