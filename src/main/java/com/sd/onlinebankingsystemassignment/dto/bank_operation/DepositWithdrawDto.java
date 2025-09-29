package com.sd.onlinebankingsystemassignment.dto.bank_operation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DepositWithdrawDto {
    @NotBlank(message = "Account number is required")
    private String accountNumber = null;

    @Positive
    @NotNull
    private BigDecimal amount = null;
}
