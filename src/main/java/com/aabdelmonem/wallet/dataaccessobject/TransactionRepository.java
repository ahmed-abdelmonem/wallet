package com.aabdelmonem.wallet.dataaccessobject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.google.common.base.Optional;
import com.aabdelmonem.wallet.domainobject.PlayerDO;
import com.aabdelmonem.wallet.domainobject.TransactionDO;

/**
 * Database Access Object for transaction table.
 *
 * @author ahmed.abdelmonem
 */
public interface TransactionRepository extends JpaRepository<TransactionDO, Long> {

    Page<TransactionDO> findByPlayer(PlayerDO playerDO, Pageable pageable);
    
    Optional<TransactionDO> findByNumber(String transactionNumber);

}
