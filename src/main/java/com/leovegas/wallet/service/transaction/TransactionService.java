package com.leovegas.wallet.service.transaction;

import java.util.List;

import com.leovegas.wallet.domainobject.TransactionDO;
import com.leovegas.wallet.exception.InsufficientBalanceException;
import com.leovegas.wallet.exception.PlayerNotFoundException;
import com.leovegas.wallet.exception.PlayerOptimisticLockException;
import com.leovegas.wallet.exception.TransactionAlreadyExistsException;

public interface TransactionService {
	
	TransactionDO addTransaction(TransactionDO transactionDO) throws PlayerNotFoundException, TransactionAlreadyExistsException, InsufficientBalanceException, PlayerOptimisticLockException;
	
	List<TransactionDO> getPlayerTransactions(Long playerId, Integer pageNo, Integer pageSize) throws PlayerNotFoundException;

}
