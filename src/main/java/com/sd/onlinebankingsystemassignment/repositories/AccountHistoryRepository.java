package com.sd.onlinebankingsystemassignment.repositories;

import com.sd.onlinebankingsystemassignment.base.repository.BaseRepository;
import com.sd.onlinebankingsystemassignment.models.AccountHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountHistoryRepository extends BaseRepository<AccountHistory> {
    // Get transactions for specific account with pagination
    @Query("""
                SELECT ah FROM AccountHistory ah
                                 JOIN (
                                     SELECT MIN(id) as min_id
                                     FROM AccountHistory
                                     WHERE (fromAccountNumber = :accountNumber AND direction = 'DEBIT')
                                        OR (toAccountNumber = :accountNumber AND direction = 'CREDIT')
                                     GROUP BY tranId
                                 ) grouped ON grouped.min_id = ah.id
                                 ORDER BY ah.tranDate DESC
        """)
    Page<AccountHistory> findByAccountNumber(
            @Param("accountNumber") String accountNumber,
            Pageable pageable
    );
}
