package com.sd.onlinebankingsystemassignment.dto.bank_operation;

import com.sd.onlinebankingsystemassignment.models.enums.TransactionStatus;
import com.sd.onlinebankingsystemassignment.models.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepositWithdrawResponseDto {
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;
    private String accountNumber = null;
    private BigDecimal amount = null;
}
