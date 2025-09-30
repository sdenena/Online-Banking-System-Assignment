package com.sd.onlinebankingsystemassignment.controllers;

import com.sd.onlinebankingsystemassignment.aop.AuditFilter;
import com.sd.onlinebankingsystemassignment.base.response.ResponseObj;
import com.sd.onlinebankingsystemassignment.base.response.ResponsePage;
import com.sd.onlinebankingsystemassignment.dto.bank_operation.ExchangeRateDto;
import com.sd.onlinebankingsystemassignment.dto.bank_operation.ExchangeRateResponseDto;
import com.sd.onlinebankingsystemassignment.services.ExchangeRateService;
import com.sd.onlinebankingsystemassignment.utils.Constant;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.MAIN_PATH + "/exchange-rate")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class ExchangeRateController {
    private final ExchangeRateService exchangeRateService;

    @AuditFilter
    @PostMapping
    public ResponseObj<ExchangeRateResponseDto> createExchangeRate(@Valid @RequestBody ExchangeRateDto exchangeRateDto) {
        return new ResponseObj<>(exchangeRateService.createExchangeRate(exchangeRateDto));
    }

    @AuditFilter
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseObj<ExchangeRateResponseDto> getExchangeRateById(@PathVariable Long id) {
        return new ResponseObj<>(exchangeRateService.getExchangeRateDetail(id));
    }

    @AuditFilter
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponsePage<ExchangeRateResponseDto> getExchangeRateList(
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        var listPage = exchangeRateService.getExchangeRateList(query, page, size);

        return new ResponsePage<>(listPage.getContent(), listPage.getTotalElements());
    }
}
