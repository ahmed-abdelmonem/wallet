package com.aabdelmonem.wallet.controller;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.aabdelmonem.wallet.controller.mapper.PlayerMapper;
import com.aabdelmonem.wallet.controller.mapper.TransactionMapper;
import com.aabdelmonem.wallet.datatransferobject.BalanceDTO;
import com.aabdelmonem.wallet.datatransferobject.TransactionDTO;
import com.aabdelmonem.wallet.domainvalue.TransactionType;
import com.aabdelmonem.wallet.exception.InsufficientBalanceException;
import com.aabdelmonem.wallet.exception.PageParametersException;
import com.aabdelmonem.wallet.exception.PlayerNotFoundException;
import com.aabdelmonem.wallet.exception.TransactionAlreadyExistsException;
import com.aabdelmonem.wallet.exception.TransactionAmountTooLongException;
import com.aabdelmonem.wallet.exception.TransactionLessOrEqualZeroAmountException;
import com.aabdelmonem.wallet.service.player.PlayerService;
import com.aabdelmonem.wallet.service.transaction.TransactionService;

/**
 * All operations with a wallet will be routed by this controller.
 * 
 */
@RestController
@RequestMapping("wallet/{playerId}")
public class WalletController {

	private static final Logger logger = LoggerFactory.getLogger(WalletController.class);

	@Autowired
	private PlayerService playerService;

	@Autowired
	private TransactionService transactionService;

	@Value("#{new Integer('${wallet.transaction.amount.maxPrecision}')}")
	private Integer maxPrecision;

	@Value("#{new Integer('${wallet.transaction.amount.maxScale}')}")
	private Integer maxScale;

	@GetMapping("/balance")
	public BalanceDTO getPlayerBalance(@PathVariable Long playerId) throws PlayerNotFoundException {

		logger.debug("Called WalletController.getPlayerBalance with palyerId :{}", playerId);

		return PlayerMapper.makeBalanceDTO(playerId, playerService.findPlayer(playerId).getBalance());
	}

	@PostMapping("/withdraw")
	@ResponseStatus(HttpStatus.CREATED)
	public TransactionDTO addDebitTransaction(@PathVariable Long playerId, @RequestParam String transactionNumber,
			@RequestParam BigDecimal amount) throws PlayerNotFoundException, TransactionAlreadyExistsException,
			InsufficientBalanceException, TransactionLessOrEqualZeroAmountException, TransactionAmountTooLongException {

		logger.debug("Called WalletController.addDebitTransaction with palyerId :{}", playerId);

		checkTransactionAmount(amount);

		return TransactionMapper
				.makeTransactionDTO(transactionService.addTransaction(TransactionMapper.makeTransactionDO(
						playerService.findPlayer(playerId), transactionNumber, TransactionType.DEBIT, amount)));

	}

	@PostMapping("/deposit")
	@ResponseStatus(HttpStatus.CREATED)
	public TransactionDTO addCreditTransaction(@PathVariable Long playerId, @RequestParam String transactionNumber,
			@RequestParam BigDecimal amount) throws PlayerNotFoundException, TransactionAlreadyExistsException,
			InsufficientBalanceException, TransactionLessOrEqualZeroAmountException, TransactionAmountTooLongException {

		logger.debug("Called WalletController.addCreditTransaction with palyerId :{}", playerId);

		checkTransactionAmount(amount);

		return TransactionMapper
				.makeTransactionDTO(transactionService.addTransaction(TransactionMapper.makeTransactionDO(
						playerService.findPlayer(playerId), transactionNumber, TransactionType.CREDIT, amount)));

	}

	@GetMapping("/transactions")
	public List<TransactionDTO> getPlayerTransactions(@PathVariable Long playerId,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "5") Integer pageSize)
			throws PlayerNotFoundException, PageParametersException {

		logger.debug("Called WalletController.getPlayerTransactions with palyerId :{}", playerId);

		if (pageNo < 0)
			throw new PageParametersException();

		if (pageSize < 1)
			throw new PageParametersException();

		return TransactionMapper
				.makeTransactionDTOList(transactionService.getPlayerTransactions(playerId, pageNo, pageSize));
	}

	private void checkTransactionAmount(BigDecimal amount)
			throws TransactionLessOrEqualZeroAmountException, TransactionAmountTooLongException {
		// check amount > 0
		if (amount.compareTo(new BigDecimal(0)) <= 0)
			throw new TransactionLessOrEqualZeroAmountException();

		// check precision and scale
		if (amount.precision() > maxPrecision || amount.scale() > maxScale)
			throw new TransactionAmountTooLongException();

	}

}
