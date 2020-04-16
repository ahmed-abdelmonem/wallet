package com.leovegas.wallet.dataaccessobject;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leovegas.wallet.domainobject.PlayerDO;

/**
 * Database Access Object for player table.
 * 
 * @author ahmed.abdelmonem
 */
public interface PlayerRepository extends JpaRepository<PlayerDO, Long> {

}
