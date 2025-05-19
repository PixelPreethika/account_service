package com.sample.accounts.mapper;

import java.time.LocalDateTime;

import com.sample.accounts.dto.CustomerDTO;
import com.sample.accounts.entity.Customer;

public class CustomerMapper {

	public static CustomerDTO maptoCustomerDTO(Customer customer, CustomerDTO customerDTO) {
		customerDTO.setEmail(customer.getEmail());
		customerDTO.setMobileNumber(customer.getMobileNumber());
		customerDTO.setName(customer.getName());
		return customerDTO;

	}

	public static Customer maptoCustomer(CustomerDTO customerDTO, Customer customer) {
		customer.setEmail(customerDTO.getEmail());
		customer.setMobileNumber(customerDTO.getMobileNumber());
		customer.setName(customerDTO.getName());
//		customer.setCreatedAt(LocalDateTime.now());
//		customer.setCreatedBy("Admin");
//		customer.setUpdatedAt(LocalDateTime.now());
//		customer.setUpdatedBy("Admin");
		return customer;

	}

}
