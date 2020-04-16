package com.leovegas.wallet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @author ahmed.abdelmonem
 *
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Transaction Amount should be greater than Zero!")
public class TransactionLessOrEqualZeroAmountException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4749796238136420778L;
}
