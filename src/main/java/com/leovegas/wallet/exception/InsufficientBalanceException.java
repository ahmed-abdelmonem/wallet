package com.leovegas.wallet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @author ahmed.abdelmonem
 *
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Insufficient funds on the wallet")
public class InsufficientBalanceException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8943510158166953878L;

}
