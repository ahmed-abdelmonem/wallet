package com.leovegas.wallet.domainobject;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 *
 * @author ahmed.abdelmonem
 */
@Entity
@Table(name = "player")
public class PlayerDO {

	@Id
	private Long id;

	@Column
	private BigDecimal balance;

	// enable optimistic locking
	@Version
	private Integer version;

	@OneToMany(mappedBy = "player")
	private List<TransactionDO> transactions;

	public PlayerDO() {
		// Player data object default constructor
	}

	public Long getId() {
		return id;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Integer getVersion() {
		return version;
	}
}
