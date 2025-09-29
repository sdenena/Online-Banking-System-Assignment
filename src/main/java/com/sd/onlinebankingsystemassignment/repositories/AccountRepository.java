package com.sd.onlinebankingsystemassignment.repositories;

import com.sd.onlinebankingsystemassignment.base.repository.BaseRepository;
import com.sd.onlinebankingsystemassignment.models.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends BaseRepository<Account> {
    // Using @Query with COALESCE (works with most databases)
    @Query("SELECT COALESCE(MAX(a.id), 0) FROM Account a")
    Long findTop();

    Optional<Account> findByAccountNumberAndStatusTrue(String accountNumber);
}
