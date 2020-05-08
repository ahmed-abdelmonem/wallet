package com.aabdelmonem.wallet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @author ahmed.abdelmonem
 *
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Transaction Amount is too long!")
public class TransactionAmountTooLongException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2057434226173732248L;

}
