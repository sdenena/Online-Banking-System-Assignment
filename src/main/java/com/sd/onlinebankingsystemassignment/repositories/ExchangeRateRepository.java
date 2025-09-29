package com.sd.onlinebankingsystemassignment.repositories;

import com.sd.onlinebankingsystemassignment.base.repository.BaseRepository;
import com.sd.onlinebankingsystemassignment.models.ExchangeRate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends BaseRepository<ExchangeRate> {
    Optional<ExchangeRate> findByFromCurrencyAndToCurrencyAndStatusTrue(String fromCurrency, String toCurrency);
}
