package com.sd.onlinebankingsystemassignment.dto.bank_operation;

import com.sd.onlinebankingsystemassignment.models.enums.TransactionDirection;
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
public class AccountHistoryResponseDto {
    private String fromAccountNumber = null;
    private String toAccountNumber = null;
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;
    private String currency;
    private BigDecimal amount = null;
    private Date tranDate = null;
    private TransactionDirection direction; // DEBIT or CREDIT
}
