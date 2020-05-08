package com.aabdelmonem.wallet.datatransferobject;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.aabdelmonem.wallet.domainvalue.TransactionType;

/**
 * Transaction data transfer object used to return/pass transaction data from/to
 * end points.
 * 
 * @author ahmed.abdelmonem
 *
 */
public class TransactionDTO {

	@JsonIgnore
	private Long id;

	@NotNull(message = "Transaction number can not be null!")
	// real transaction identifier
	private String number;

	@NotNull(message = "Player ID can not be null!")
	private Long playerId;

	@NotNull(message = "Transaction type can not be null!")
	private TransactionType type;

	@NotNull(message = "Amount can not be null!")
	private BigDecimal amount;

	private BigDecimal previousBalance;

	private BigDecimal newBalance;

	@NotNull(message = "Creation date can not be null!")
	private ZonedDateTime dateCreated;

	/**
	 * default constructor used by JUnit test classes.
	 */
	public TransactionDTO() {

	}

	public TransactionDTO(String number, Long playerId, BigDecimal amount, TransactionType transactionType,
			BigDecimal newBalance, ZonedDateTime dateCreated) {
		this.number = number;
		this.amount = amount;
		this.type = transactionType;
		this.playerId = playerId;
		this.newBalance = newBalance;
		// calculate previous balance based on transaction type
		this.previousBalance = transactionType.equals(TransactionType.DEBIT) ? newBalance.add(amount)
				: newBalance.subtract(amount);
		this.dateCreated = dateCreated;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getPreviousBalance() {
		return previousBalance;
	}

	public void setPreviousBalance(BigDecimal previousBalance) {
		this.previousBalance = previousBalance;
	}

	public BigDecimal getNewBalance() {
		return newBalance;
	}

	public void setNewBalance(BigDecimal newBalance) {
		this.newBalance = newBalance;
	}

	public ZonedDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(ZonedDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		TransactionDTO otherTransaction = (TransactionDTO) obj;
		return this.number.equals(otherTransaction.getNumber()) && this.type.equals(otherTransaction.getType())
				&& this.playerId.equals(otherTransaction.getPlayerId())
				&& this.amount.compareTo(otherTransaction.getAmount()) == 0
				&& this.newBalance.compareTo(otherTransaction.getNewBalance()) == 0
				&& this.previousBalance.compareTo(otherTransaction.getPreviousBalance()) == 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		return result;
	}
}
