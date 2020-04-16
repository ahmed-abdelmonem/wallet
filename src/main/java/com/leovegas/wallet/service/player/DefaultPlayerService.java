package com.leovegas.wallet.service.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.leovegas.wallet.dataaccessobject.PlayerRepository;
import com.leovegas.wallet.domainobject.PlayerDO;
import com.leovegas.wallet.exception.PlayerNotFoundException;

/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for player.
 * <p>
 * 
 * @author ahmed.abdelmonem
 */
@Service
public class DefaultPlayerService implements PlayerService {

	private static final Logger logger = LoggerFactory.getLogger(DefaultPlayerService.class);
	
	@Autowired
	private PlayerRepository playerRepository;

	
	@Override
	public PlayerDO findPlayer(Long playerId) throws PlayerNotFoundException {
		
		logger.debug("Called DefaultPlayerService.findPlayer with palyerId :{}", playerId);
		
		return playerRepository
				.findById(playerId)
				.orElseThrow(() -> new PlayerNotFoundException());
	}

}
