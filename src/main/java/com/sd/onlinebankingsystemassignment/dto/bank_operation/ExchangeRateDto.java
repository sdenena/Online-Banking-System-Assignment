package com.sd.onlinebankingsystemassignment.dto.bank_operation;

import com.sd.onlinebankingsystemassignment.models.enums.Calculation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExchangeRateDto {
    @NotNull(message = "Exchange rate is required")
    private BigDecimal exchangeRate;
    @NotBlank(message = "From currency is required")
    private String fromCurrency;
    @NotBlank(message = "To currency is required")
    private String toCurrency;
    @NotNull(message = "Method is required")
    private Calculation method;
}
