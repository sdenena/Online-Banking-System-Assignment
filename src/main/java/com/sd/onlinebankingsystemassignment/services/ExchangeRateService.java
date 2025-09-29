package com.sd.onlinebankingsystemassignment.services;

import com.sd.onlinebankingsystemassignment.dto.bank_operation.ExchangeRateDto;
import com.sd.onlinebankingsystemassignment.dto.bank_operation.ExchangeRateResponseDto;
import com.sd.onlinebankingsystemassignment.models.ExchangeRate;

public interface ExchangeRateService {
    ExchangeRateResponseDto createExchangeRate(ExchangeRateDto exchangeRateDto);
    ExchangeRate getExchangeRate(String fromCurrency, String toCurrency);
}
