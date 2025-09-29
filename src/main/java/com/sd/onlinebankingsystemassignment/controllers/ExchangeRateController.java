package com.sd.onlinebankingsystemassignment.controllers;

import com.sd.onlinebankingsystemassignment.base.response.ResponseObj;
import com.sd.onlinebankingsystemassignment.dto.bank_operation.ExchangeRateDto;
import com.sd.onlinebankingsystemassignment.dto.bank_operation.ExchangeRateResponseDto;
import com.sd.onlinebankingsystemassignment.services.ExchangeRateService;
import com.sd.onlinebankingsystemassignment.utils.Constant;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constant.MAIN_PATH + "/exchange-rate")
@RequiredArgsConstructor
public class ExchangeRateController {
    private final ExchangeRateService exchangeRateService;

    @PostMapping
    public ResponseObj<ExchangeRateResponseDto> createExchangeRate(@Valid @RequestBody ExchangeRateDto exchangeRateDto) {
        return new ResponseObj<>(exchangeRateService.createExchangeRate(exchangeRateDto));
    }
}
