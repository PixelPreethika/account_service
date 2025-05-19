package com.sample.accounts.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.sample.accounts.constants.AccountsConstants;
import com.sample.accounts.dto.AccountsDTO;
import com.sample.accounts.dto.CustomerDTO;
import com.sample.accounts.entity.Accounts;
import com.sample.accounts.entity.Customer;
import com.sample.accounts.exception.CustomerAlreadyExsistsException;
import com.sample.accounts.exception.ResourceNotFoundException;
import com.sample.accounts.mapper.AccountsMapper;
import com.sample.accounts.mapper.CustomerMapper;
import com.sample.accounts.repository.AccountsRepository;
import com.sample.accounts.repository.CustomerRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor // instead of using @Autowire to inject repository we can use this annotation to
					// the class
public class AccountsServiceImpl implements IAccountsService {

	private AccountsRepository accountsRepository;
	private CustomerRepository customerRepository;

	@Override
	public void createAccount(CustomerDTO customerDTO) {
		Customer customer = CustomerMapper.maptoCustomer(customerDTO, new Customer());
		Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDTO.getMobileNumber());
		if (optionalCustomer.isPresent()) {
			throw new CustomerAlreadyExsistsException(
					"Customer already registered with given number : " + customerDTO.getMobileNumber());
		}
		Customer savedCustomer = customerRepository.save(customer);
		accountsRepository.save(createNewAccount(savedCustomer));
	}

	@Transactional
	private Accounts createNewAccount(Customer customer) {
		Accounts newAccount = new Accounts();
		newAccount.setCustomerId(customer.getCustomerId());
		long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

		newAccount.setAccountNumber(randomAccNumber);
		newAccount.setAccountType(AccountsConstants.SAVINGS);
		newAccount.setBranchAddres(AccountsConstants.ADDRESS);
//		newAccount.setVersion(0);
//		newAccount.setCreatedAt(LocalDateTime.now());
//		newAccount.setCreatedBy("Admin");
//		newAccount.setUpdatedAt(LocalDateTime.now());
//		newAccount.setUpdatedBy("Admin");
		return newAccount;
	}

	@Override
	public CustomerDTO fetchAccountDetails(String mobileNumber) {
		Customer customer = customerRepository.findByMobileNumber(mobileNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

		Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
				() -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));
		CustomerDTO customerDTO = CustomerMapper.maptoCustomerDTO(customer, new CustomerDTO());
		customerDTO.setAccountDetails(AccountsMapper.maptoAccountsDTO(accounts, new AccountsDTO()));
		return customerDTO;
	}

	@Override
	public boolean updateAccountDetails(CustomerDTO customerDTO) {
		boolean isUpdated = false;
		AccountsDTO accountsDTO = customerDTO.getAccountDetails();
		if (accountsDTO != null) {
			Accounts accounts = accountsRepository.findById(accountsDTO.getAccountNumber())
					.orElseThrow(() -> new ResourceNotFoundException("Account", "AccountNumber",
							accountsDTO.getAccountNumber().toString()));
			AccountsMapper.maptoAccounts(accountsDTO, accounts);
			accounts = accountsRepository.save(accounts);

			Long customerId = accounts.getCustomerId();
			Customer customer = customerRepository.findById(customerId)
					.orElseThrow(() -> new ResourceNotFoundException("Customer", "CustomerId", customerId.toString()));

			CustomerMapper.maptoCustomer(customerDTO, customer);
			customerRepository.save(customer);
			isUpdated = true;

		}
		return isUpdated;
	}

	@Override
	public boolean deleteAccount(String mobileNumber) {
		Customer customer = customerRepository.findByMobileNumber(mobileNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
		accountsRepository.deleteByCustomerId(customer.getCustomerId());
		customerRepository.deleteById(customer.getCustomerId());
		return true;
	}

}
