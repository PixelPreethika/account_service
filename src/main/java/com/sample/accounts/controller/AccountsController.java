package com.sample.accounts.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sample.accounts.constants.AccountsConstants;
import com.sample.accounts.dto.CustomerDTO;
import com.sample.accounts.dto.ResponseDTO;
import com.sample.accounts.service.IAccountsService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/api", produces = { MediaType.APPLICATION_JSON_VALUE })
@AllArgsConstructor
@Validated
public class AccountsController {

	private IAccountsService iAccountsService;

	@PostMapping("/create")
	public ResponseEntity<ResponseDTO> createAccount(@Valid @RequestBody CustomerDTO customerDTO) {
		iAccountsService.createAccount(customerDTO);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDTO(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
	}

	@GetMapping("/fetch")
	public ResponseEntity<CustomerDTO> fetchAccountDetails(
			@RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
		CustomerDTO customerDTO = iAccountsService.fetchAccountDetails(mobileNumber);
		return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
	}

	@PutMapping("/update")
	public ResponseEntity<ResponseDTO> updateAccountDetails(@Valid @RequestBody CustomerDTO customerDTO) {
		boolean isUpdated = iAccountsService.updateAccountDetails(customerDTO);
		if (isUpdated) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseDTO(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
		} else {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseDTO(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
		}
	}

	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDTO> deleteUserAndAccountDetails(
			@RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
		boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
		if (isDeleted) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseDTO(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
		} else {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseDTO(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
		}
	}

}
