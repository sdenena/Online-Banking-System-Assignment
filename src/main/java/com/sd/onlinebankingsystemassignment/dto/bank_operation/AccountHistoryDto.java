package com.sd.onlinebankingsystemassignment.dto.bank_operation;

import com.sd.onlinebankingsystemassignment.models.enums.TransactionStatus;
import com.sd.onlinebankingsystemassignment.models.enums.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountHistoryDto {
    private String fromAccountNumber = null;
    private String toAccountNumber = null;

    @NotBlank(message = "Transaction Type is required")
    private TransactionType transactionType;

    @NotBlank(message = "Transaction Status is required")
    private TransactionStatus transactionStatus;

    @Positive
    @NotNull
    private BigDecimal amount = null;

    @NotNull
    private Date tranDate = null;
}
