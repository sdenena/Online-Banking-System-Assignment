package com.sd.onlinebankingsystemassignment.dto;

import com.sd.onlinebankingsystemassignment.models.Account;
import com.sd.onlinebankingsystemassignment.models.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponseDto {
    private Long id;
    private AccountType accountType;
    private String accountNumber;
    private String accountHolderName;
    private String accountHolderEmail;
    private String accountHolderPhone;
    private Boolean isActive;
    private BigDecimal balance;

    public Account toAccount() {
        return new Account(id, accountHolderName, accountType, isActive);
    }
}
