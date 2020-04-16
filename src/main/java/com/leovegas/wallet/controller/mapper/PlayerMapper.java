package com.leovegas.wallet.controller.mapper;

import java.math.BigDecimal;

import com.leovegas.wallet.datatransferobject.BalanceDTO;

/**
 * 
 * @author ahmed.abdelmonem
 *
 */
public class PlayerMapper {

	/**
	 * private constructor to hide the implicit public one
	 */
	private PlayerMapper() {
	}

	public static BalanceDTO makeBalanceDTO(Long playerId, BigDecimal balance) {

		return new BalanceDTO(playerId, balance);
	}

}
