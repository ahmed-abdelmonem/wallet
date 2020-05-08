package com.aabdelmonem.wallet.service.transaction;

import java.util.List;

import com.aabdelmonem.wallet.domainobject.TransactionDO;
import com.aabdelmonem.wallet.exception.InsufficientBalanceException;
import com.aabdelmonem.wallet.exception.PlayerNotFoundException;
import com.aabdelmonem.wallet.exception.TransactionAlreadyExistsException;

public interface TransactionService {
	
	TransactionDO addTransaction(TransactionDO transactionDO) throws PlayerNotFoundException, TransactionAlreadyExistsException, InsufficientBalanceException;
	
	List<TransactionDO> getPlayerTransactions(Long playerId, Integer pageNo, Integer pageSize) throws PlayerNotFoundException;

}
