package com.aabdelmonem.wallet.domainobject;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.aabdelmonem.wallet.domainvalue.TransactionType;

/**
*
* @author ahmed.abdelmonem
*/
@Entity
@Table(name = "transaction", uniqueConstraints = @UniqueConstraint(name = "uc_transaction_number", columnNames = {
		"number" }))
public class TransactionDO {

	@Id
	@GeneratedValue
	// surrogate key
	private Long id;

	@Column(nullable = false)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private ZonedDateTime dateCreated = ZonedDateTime.now();

	@Column(nullable = false)
	@NotNull(message = "Transaction number can not be null!")
	// real transaction identifier
	private String number;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	@NotNull(message = "Transaction type can not be null!")
	private TransactionType type;

	@Column(nullable = false)
	@NotNull(message = "Amount can not be null!")
	private BigDecimal amount;

	@Column(nullable = false)
	@NotNull(message = "NewBalance can not be null!")
	private BigDecimal newBalance;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "player_id", nullable = false)
	private PlayerDO player;

	public TransactionDO() {
	}

	public TransactionDO(String number, BigDecimal amount, TransactionType transactionType,
			PlayerDO player) {
		this.number = number;
		this.amount = amount;
		this.type = transactionType;
		this.player = player;
		// Calculate new balance based on transaction type
		this.newBalance = transactionType.equals(TransactionType.DEBIT) ? player.getBalance().subtract(amount)
				: player.getBalance().add(amount);
	}

	public Long getId() {
		return id;
	}

	public ZonedDateTime getDateCreated() {
		return dateCreated;
	}

	public String getNumber() {
		return number;
	}

	public TransactionType getType() {
		return type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public PlayerDO getPlayer() {
		return player;
	}

	public BigDecimal getNewBalance() {
		return newBalance;
	}
}
