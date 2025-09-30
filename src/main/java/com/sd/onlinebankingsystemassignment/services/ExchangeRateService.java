package com.sd.onlinebankingsystemassignment.services;

import com.sd.onlinebankingsystemassignment.dto.bank_operation.ExchangeRateDto;
import com.sd.onlinebankingsystemassignment.dto.bank_operation.ExchangeRateResponseDto;
import com.sd.onlinebankingsystemassignment.models.ExchangeRate;
import org.springframework.data.domain.Page;

public interface ExchangeRateService {
    ExchangeRateResponseDto createExchangeRate(ExchangeRateDto exchangeRateDto);
    ExchangeRate getExchangeRate(String fromCurrency, String toCurrency);
    ExchangeRateResponseDto getExchangeRateDetail(Long id);
    void deleteExchangeRate(Long id);
    Page<ExchangeRateResponseDto> getExchangeRateList(String query, int page, int size);
}
