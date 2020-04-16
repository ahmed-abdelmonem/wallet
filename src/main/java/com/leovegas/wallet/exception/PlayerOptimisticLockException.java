package com.leovegas.wallet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * Error raised when OptimisticLockException happen when trying to update player
 * balance.
 * 
 * @author ahmed.abdelmonem
 *
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Please try again because you tried to update outdated record!")
public class PlayerOptimisticLockException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7001208233121924745L;

}
