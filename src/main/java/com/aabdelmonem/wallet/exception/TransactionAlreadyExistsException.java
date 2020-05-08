package com.aabdelmonem.wallet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @author ahmed.abdelmonem
 *
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Transaction number already exists.")
public class TransactionAlreadyExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7026153540957542289L;
}
