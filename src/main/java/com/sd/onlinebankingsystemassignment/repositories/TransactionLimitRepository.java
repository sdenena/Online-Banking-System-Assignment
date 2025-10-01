package com.sd.onlinebankingsystemassignment.repositories;

import com.sd.onlinebankingsystemassignment.base.repository.BaseRepository;
import com.sd.onlinebankingsystemassignment.models.TransactionLimit;
import com.sd.onlinebankingsystemassignment.models.enums.TransactionType;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionLimitRepository extends BaseRepository<TransactionLimit> {
    TransactionLimit findByTransactionTypeAndStatusTrue(TransactionType transactionType);
}
