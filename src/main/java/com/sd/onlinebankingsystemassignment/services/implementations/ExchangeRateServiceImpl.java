package com.sd.onlinebankingsystemassignment.services.implementations;

import com.sd.onlinebankingsystemassignment.dto.bank_operation.ExchangeRateDto;
import com.sd.onlinebankingsystemassignment.dto.bank_operation.ExchangeRateResponseDto;
import com.sd.onlinebankingsystemassignment.exception.CustomException;
import com.sd.onlinebankingsystemassignment.models.Account;
import com.sd.onlinebankingsystemassignment.models.ExchangeRate;
import com.sd.onlinebankingsystemassignment.repositories.ExchangeRateRepository;
import com.sd.onlinebankingsystemassignment.services.ExchangeRateService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService {
    private final ExchangeRateRepository exchangeRateRepository;
    private final Logger logger = LoggerFactory.getLogger(ExchangeRateServiceImpl.class);

    @Transactional
    @Override public ExchangeRateResponseDto createExchangeRate(ExchangeRateDto exchangeRateDto) {
        ExchangeRate exchangeRate = new ExchangeRate();
        ExchangeRateResponseDto exchangeRateResponseDto = new ExchangeRateResponseDto();

        Optional<ExchangeRate> existingExchangeRate = exchangeRateRepository
                .findByFromCurrencyAndToCurrencyAndStatusTrue(exchangeRateDto.getFromCurrency(), exchangeRateDto.getToCurrency());

        // If an active exchange rate already exists for the given currency pair, delete it
        existingExchangeRate.ifPresent(rate -> deleteExchangeRate(rate.getId()));

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

    @Override
    public ExchangeRateResponseDto getExchangeRateDetail(Long id) {
        logger.info("getAccountDetail - id: {}", id);
        return exchangeRateRepository.findByIdAndStatusTrue(id).orElseThrow(() -> new CustomException(404, "Exchange Rate not found")).toResponseDto();
    }

    @Override
    public void deleteExchangeRate(Long id) {
        Optional<ExchangeRate> exchangeRate = exchangeRateRepository.findByIdAndStatusTrue(id);

        exchangeRate.ifPresent(rate -> {
            if (!rate.getDefaultExchangeRate()) {
                rate.setStatus(false);
                exchangeRateRepository.save(rate);
            } else {
                throw new CustomException(400, "Cannot delete default exchange rate");
            }
        });
    }

    @Override
    public Page<ExchangeRateResponseDto> getExchangeRateList(String query, int page, int size) {
        logger.info("getAccountList - query: {}, page: {}, size: {}", query, page, size);

        Page<ExchangeRate> exchangeRatePage = exchangeRateRepository.findAll((root, cq, cb) -> {
            ArrayList<Predicate> predicates = new ArrayList<>();

            if (query != null && !query.trim().isEmpty()) {
                var searchFromCurrency = cb.like(cb.upper(root.get("fromCurrency")), "%" + query.toUpperCase() + "%");
                var searchToCurrency = cb.like(cb.upper(root.get("toCurrency")), "%" + query.toUpperCase() + "%");
                predicates.add(cb.or(searchFromCurrency, searchToCurrency));
            }

            // Fixed: changed "status" to "isActive" to match the entity field
            predicates.add(cb.isTrue(root.get("status")));
            predicates.add(cb.isFalse(root.get("defaultExchangeRate")));
            Objects.requireNonNull(cq).orderBy(cb.desc(root.get("id")));
            return cb.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(page, size));

        // Convert Page<Account> to Page<AccountResponseDto>
        return exchangeRatePage.map(ExchangeRate::toResponseDto);
    }
}
