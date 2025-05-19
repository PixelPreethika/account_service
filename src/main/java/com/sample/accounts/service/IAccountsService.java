package com.sample.accounts.service;

import com.sample.accounts.dto.CustomerDTO;

public interface IAccountsService {

	public void createAccount(CustomerDTO customerDTO);

	public CustomerDTO fetchAccountDetails(String mobileNumber);

	public boolean updateAccountDetails(CustomerDTO customerDTO);

	public boolean deleteAccount(String mobileNumber);

}
