package com.aabdelmonem.wallet.service.player;

import com.aabdelmonem.wallet.domainobject.PlayerDO;
import com.aabdelmonem.wallet.exception.PlayerNotFoundException;

public interface PlayerService {

	PlayerDO findPlayer(Long playerId) throws PlayerNotFoundException;

}
