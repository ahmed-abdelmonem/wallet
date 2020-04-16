package com.leovegas.wallet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @author ahmed.abdelmonem
 *
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Illegal paging parameters.")
public class PageParametersException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7066259269914836331L;

}
