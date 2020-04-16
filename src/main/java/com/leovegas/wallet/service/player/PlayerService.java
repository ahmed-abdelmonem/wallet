package com.leovegas.wallet.service.player;

import com.leovegas.wallet.domainobject.PlayerDO;
import com.leovegas.wallet.exception.PlayerNotFoundException;

public interface PlayerService {

	PlayerDO findPlayer(Long playerId) throws PlayerNotFoundException;

}
