package com.aabdelmonem.wallet.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.aabdelmonem.wallet.WalletApplicationTests;
import com.aabdelmonem.wallet.datatransferobject.BalanceDTO;
import com.aabdelmonem.wallet.datatransferobject.TransactionDTO;
import com.aabdelmonem.wallet.domainvalue.TransactionType;

/**
*
* @author ahmed.abdelmonem
*/
public class WalletControllerTest extends WalletApplicationTests {

	@Override
	@Before
	public void setup() {
		super.setup();

	}

	@Test
	public void testGetBallanceForManyPlayers() throws Exception {
		// check balance for players 1, 2, 3, 4, 5, 12
		checkPlayerBalance(1L, new BigDecimal("342"), HttpStatus.OK);

		checkPlayerBalance(2L, new BigDecimal("149.67"), HttpStatus.OK);

		checkPlayerBalance(3L, new BigDecimal("1146"), HttpStatus.OK);

		checkPlayerBalance(4L, new BigDecimal("0"), HttpStatus.OK);

		checkPlayerBalance(5L, new BigDecimal("625"), HttpStatus.OK);

		// playerId 12 not found
		checkPlayerBalance(12L, new BigDecimal("342"), HttpStatus.NOT_FOUND);
	}

	@Test
	public void testGetNumberTransactionsForManyPlayers() throws Exception {

		// check number transactions for players 1, 2, 3, 4, 5, 12, 13
		assertEquals(2, getNoTransactionsForPlayer(1L, 0, 2, HttpStatus.OK));
		
		assertEquals(1, getNoTransactionsForPlayer(1L, 1, 2, HttpStatus.OK));

		assertEquals(1, getNoTransactionsForPlayer(2L, 0, 1, HttpStatus.OK));

		assertEquals(3, getNoTransactionsForPlayer(3L, 0, 3, HttpStatus.OK));

		assertEquals(0, getNoTransactionsForPlayer(4L, 0, 1, HttpStatus.OK));

		assertEquals(3, getNoTransactionsForPlayer(5L, 0, 3, HttpStatus.OK));

		assertEquals(-1, getNoTransactionsForPlayer(12L, 0, 1, HttpStatus.NOT_FOUND));

		assertEquals(-1, getNoTransactionsForPlayer(13L, 0, 1, HttpStatus.NOT_FOUND));

	}
	
	@Test
	public void testGetNumberTransactionsWithInvalidPageNo_GetBadRequest() throws Exception {

		assertEquals(-1, getNoTransactionsForPlayer(1L, -1, 2, HttpStatus.BAD_REQUEST));
		
		assertEquals(-1, getNoTransactionsForPlayer(1L, -15, 2, HttpStatus.BAD_REQUEST));

	}
	
	@Test
	public void testGetNumberTransactionsWithInvalidPageSize_GetBadRequest() throws Exception {

		assertEquals(-1, getNoTransactionsForPlayer(1L, 0, 0, HttpStatus.BAD_REQUEST));
		
		assertEquals(-1, getNoTransactionsForPlayer(1L, 1, -5, HttpStatus.BAD_REQUEST));

	}

	@Test
	public void testAddCreditTransaction_CheckBallanceBeforeAndAfter() throws Exception {

		checkPlayerBalance(6L, new BigDecimal("0"), HttpStatus.OK);

		// add credit transaction for player id 6
		TransactionDTO creditTransaction = addTransaction("ASD3435RREDD", 6L, TransactionType.CREDIT,
				new BigDecimal("123.54"), HttpStatus.CREATED);

		assertTrue(creditTransaction.equals(new TransactionDTO("ASD3435RREDD", 6L, new BigDecimal("123.54"),
				TransactionType.CREDIT, new BigDecimal("123.54"), ZonedDateTime.now())));

		// check new balance in player table
		checkPlayerBalance(6L, new BigDecimal("123.54"), HttpStatus.OK);
	}

	@Test
	public void testAddDebitTransaction_CheckBallanceBeforeAndAfter() throws Exception {
		// balance before transaction
		checkPlayerBalance(7L, new BigDecimal("120"), HttpStatus.OK);

		// add debit transaction for player id 7
		TransactionDTO debitTransaction = addTransaction("HTTRREEEE5554", 7L, TransactionType.DEBIT,
				new BigDecimal("70"), HttpStatus.CREATED);

		assertTrue(debitTransaction.equals(new TransactionDTO("HTTRREEEE5554", 7L, new BigDecimal("70"),
				TransactionType.DEBIT, new BigDecimal("50"), ZonedDateTime.now())));

		// check new balance in player table
		checkPlayerBalance(7L, new BigDecimal("50"), HttpStatus.OK);

		// check number of transactions for the player
		assertEquals(2, getNoTransactionsForPlayer(7L, 0, 10, HttpStatus.OK));

	}

	@Test
	public void testAddDebitTransactionWithInsufficientBalance_GetBadRequest() throws Exception {

		checkPlayerBalance(8L, new BigDecimal("237"), HttpStatus.OK);

		// add debit transaction for player id 8
		TransactionDTO debitTransaction = addTransaction("GTRREEEFFF", 8L, TransactionType.DEBIT, new BigDecimal("400"),
				HttpStatus.BAD_REQUEST);

		assertNull(debitTransaction);

		// check balance in player table (should be unchanged)
		checkPlayerBalance(8L, new BigDecimal("237"), HttpStatus.OK);

		// check number of transactions for the player (should be unchanged)
		assertEquals(1, getNoTransactionsForPlayer(8L, 0, 1, HttpStatus.OK));

	}

	@Test
	public void testAddTransactionsWithAmountLessOrEqualZero_GetBadRequest() throws Exception {

		checkPlayerBalance(9L, new BigDecimal("0"), HttpStatus.OK);

		// add credit transaction for player id 9 with zero amount
		TransactionDTO creditTransaction = addTransaction("HGGFFFDD", 9L, TransactionType.CREDIT, new BigDecimal("0"),
				HttpStatus.BAD_REQUEST);

		// add debit transaction for player id 9 with amount less than zero
		TransactionDTO debitTransaction = addTransaction("KFFF4444", 9L, TransactionType.DEBIT, new BigDecimal("-33"),
				HttpStatus.BAD_REQUEST);

		assertNull(creditTransaction);

		assertNull(debitTransaction);

		// check balance in player table (should be unchanged)
		checkPlayerBalance(9L, new BigDecimal("0"), HttpStatus.OK);

		// check number of transactions for the player (should be unchanged)
		assertEquals(0, getNoTransactionsForPlayer(9L, 0, 10, HttpStatus.OK));
	}
	
	@Test
	public void testAddTransactionsWithTooLongAmount_GetBadRequest() throws Exception {

		TransactionDTO creditTransaction = addTransaction("HGGFSSFFDD", 9L, TransactionType.CREDIT, new BigDecimal("9999999999.9999"),
				HttpStatus.BAD_REQUEST);

		TransactionDTO debitTransaction = addTransaction("KFFFDD4444", 9L, TransactionType.DEBIT, new BigDecimal("999999.444444"),
				HttpStatus.BAD_REQUEST);

		assertNull(creditTransaction);

		assertNull(debitTransaction);

	}


	@Test
	public void testAddCreditAndDebitTransactionsWithSameNumber_GetConflict() throws Exception {

		checkPlayerBalance(11L, new BigDecimal("0"), HttpStatus.OK);

		// add credit transaction for player id 11
		TransactionDTO creditTransaction = addTransaction("DSSSDDFF444", 11L, TransactionType.CREDIT,
				new BigDecimal("655"), HttpStatus.CREATED);

		// add debit transaction for player id 11 with same number
		TransactionDTO debitTransaction = addTransaction("DSSSDDFF444", 11L, TransactionType.DEBIT,
				new BigDecimal("443"), HttpStatus.CONFLICT);

		assertTrue(creditTransaction.equals(new TransactionDTO("DSSSDDFF444", 11L, new BigDecimal("655"),
				TransactionType.CREDIT, new BigDecimal("655"), ZonedDateTime.now())));

		assertNull(debitTransaction);

		// check balance in player table
		checkPlayerBalance(11L, new BigDecimal("655"), HttpStatus.OK);

		// check number of transactions for the player
		assertEquals(1, getNoTransactionsForPlayer(11L, 0, 1, HttpStatus.OK));

	}

	@Test
	public void testAddManyCreditAndDebitTransactionsForPlayer_CheckBalanceAndNoTransactions() throws Exception {

		checkPlayerBalance(10L, new BigDecimal("0"), HttpStatus.OK);

		// add 48 credit transactions to player 10 with amount 100
		for (int i = 0; i < 48; i++)
			addTransaction("ADDEEEECRED" + i, 10L, TransactionType.CREDIT, new BigDecimal("100"), HttpStatus.CREATED);
		

		checkPlayerBalance(10L, new BigDecimal("4800"), HttpStatus.OK);
		
		// total number of transactions now is 48 (4 * 10) + 8
		for (int i = 0; i < 4; i++)
			assertEquals(10, getNoTransactionsForPlayer(10L, i, 10, HttpStatus.OK));
			
		assertEquals(8, getNoTransactionsForPlayer(10L, 4, 10, HttpStatus.OK));

		// add 24 debit transactions to player 10 with amount 200
		for (int i = 0; i < 24; i++)
			addTransaction("ADDEEEEDEB" + i, 10L, TransactionType.DEBIT, new BigDecimal("200"), HttpStatus.CREATED);

		checkPlayerBalance(10L, new BigDecimal("0"), HttpStatus.OK);
		
		// total number of transactions now is 72 (14 * 5) + 2
		for (int i = 0; i < 14; i++)
			assertEquals(5, getNoTransactionsForPlayer(10L, i, 5, HttpStatus.OK));
		
		assertEquals(2, getNoTransactionsForPlayer(10L, 14, 5, HttpStatus.OK));

	}

	private void checkPlayerBalance(Long playerId, BigDecimal expectedBallance, HttpStatus expectedStatus)
			throws Exception {
		String uri = "/wallet/" + playerId + "/balance";
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		MockHttpServletResponse response = mvcResult.getResponse();

		// check HTTP status
		assertEquals(expectedStatus.value(), response.getStatus());

		if (expectedStatus.equals(HttpStatus.OK)) {

			// check content
			String content = response.getContentAsString();

			BalanceDTO balanceDTO = stringToJson(content, BalanceDTO.class);

			// check playerId
			assertEquals(playerId, balanceDTO.getPlayerId());
			// check balance
			assertThat(balanceDTO.getBalance(), Matchers.comparesEqualTo(expectedBallance));
		}

	}

	private TransactionDTO addTransaction(String transactionNumber, Long playerId, TransactionType type,
			BigDecimal amount, HttpStatus expectedStatus) throws Exception {
		StringBuilder uri = new StringBuilder("/wallet/");

		uri.append(playerId);

		if (type.equals(TransactionType.DEBIT))
			uri.append("/withdraw?");
		else
			uri.append("/deposit?");

		uri.append("transactionNumber=");
		uri.append(transactionNumber);

		uri.append("&&amount=");
		uri.append(amount);

		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post(uri.toString()).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		MockHttpServletResponse response = mvcResult.getResponse();

		// check HTTP status
		assertEquals(expectedStatus.value(), response.getStatus());

		if (expectedStatus.equals(HttpStatus.CREATED)) {
			assertNotNull(response.getContentAsString());
			return stringToJson(response.getContentAsString(), TransactionDTO.class);
		}

		return null;

	}

	private int getNoTransactionsForPlayer(Long playerId, Integer pageNo, Integer pageSize, HttpStatus expectedStatus)
			throws Exception {

		StringBuilder uri = new StringBuilder("/wallet/");
		uri.append(playerId);
		uri.append("/transactions?");
		uri.append("pageNo=");
		uri.append(pageNo);
		uri.append("&&pageSize=");
		uri.append(pageSize);

		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.get(uri.toString()).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		MockHttpServletResponse response = mvcResult.getResponse();

		// check HTTP status
		assertEquals(expectedStatus.value(), response.getStatus());

		if (expectedStatus.equals(HttpStatus.OK)) {
			assertNotNull(response.getContentAsString());
			return stringToJson(response.getContentAsString(), TransactionDTO[].class).length;
		}

		return -1;
	}

}
