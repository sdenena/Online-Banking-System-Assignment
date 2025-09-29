package com.sd.onlinebankingsystemassignment.services.implementations;

import com.sd.onlinebankingsystemassignment.dto.bank_operation.ExchangeRateDto;
import com.sd.onlinebankingsystemassignment.dto.bank_operation.ExchangeRateResponseDto;
import com.sd.onlinebankingsystemassignment.models.ExchangeRate;
import com.sd.onlinebankingsystemassignment.repositories.ExchangeRateRepository;
import com.sd.onlinebankingsystemassignment.services.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService {
    private final ExchangeRateRepository exchangeRateRepository;

    @Override
    public ExchangeRateResponseDto createExchangeRate(ExchangeRateDto exchangeRateDto) {
        ExchangeRate exchangeRate = new ExchangeRate();
        ExchangeRateResponseDto exchangeRateResponseDto = new ExchangeRateResponseDto();

        BeanUtils.copyProperties(exchangeRateDto, exchangeRate);
        exchangeRate = exchangeRateRepository.save(exchangeRate);

        BeanUtils.copyProperties(exchangeRate, exchangeRateResponseDto);

        return exchangeRateResponseDto;
    }

    @Override
    public ExchangeRate getExchangeRate(String fromCurrency, String toCurrency) {
        return exchangeRateRepository.findByFromCurrencyAndToCurrencyAndStatusTrue(fromCurrency, toCurrency)
                .orElseThrow(() -> new RuntimeException("Exchange rate not found"));
    }
}
