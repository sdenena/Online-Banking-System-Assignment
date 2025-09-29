package com.sd.onlinebankingsystemassignment.dto.bank_operation;

import com.sd.onlinebankingsystemassignment.models.enums.Calculation;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateResponseDto {
    private Long id;
    private BigDecimal exchangeRate;
    private String fromCurrency;
    private String toCurrency;
    private Calculation method;
}
