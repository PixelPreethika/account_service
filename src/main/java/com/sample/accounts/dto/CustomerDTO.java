package com.sample.accounts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDTO {

	@NotEmpty(message = "Name must not be null or empty")
	@Size(min = 5, max = 30, message = "The length of the customer name should be between 5 to 30")
	private String name;

	@NotEmpty(message = "Email must not be null or empty")
	@Email(message = "Email address should be valid value")
	private String email;

	@NotEmpty(message = "Mobile number must not be null or empty")
	@Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
	private String mobileNumber;

	private AccountsDTO accountDetails;

}
