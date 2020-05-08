package com.aabdelmonem.wallet.datatransferobject;

import java.math.BigDecimal;

/**
 * Balance data transfer object used to return balance data from end
 * points.
 * 
 * @author ahmed.abdelmonem
 *
 */
public class BalanceDTO {

	private Long playerId;

	private BigDecimal balance;

	public BalanceDTO() {
	}

	public BalanceDTO(Long playerId, BigDecimal balance) {
		this.playerId = playerId;
		this.balance = balance;
	}

	public Long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

}
