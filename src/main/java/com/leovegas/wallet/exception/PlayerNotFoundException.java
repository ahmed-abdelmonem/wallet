package com.leovegas.wallet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @author ahmed.abdelmonem
 *
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Could not find player with the provided ID.")
public class PlayerNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 119732113034553353L;

}
