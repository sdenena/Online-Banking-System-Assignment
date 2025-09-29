package com.sd.onlinebankingsystemassignment.models;

import com.sd.onlinebankingsystemassignment.base.entity.BaseEntity;
import com.sd.onlinebankingsystemassignment.dto.account.AccountResponseDto;
import com.sd.onlinebankingsystemassignment.models.enums.AccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "cus_account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_holder_name", length = 50)
    private String accountHolderName;

    @Column(name = "account_holder_email", length = 100)
    private String accountHolderEmail;

    @Column(name = "account_holder_phone", length = 20)
    private String accountHolderPhone;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "active")
    Boolean isActive = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", length = 20)
    private AccountType accountType = AccountType.CHECKING;

    @Column(name = "balance", precision = 15, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    // For Create
    public Account(Long id, String accountHolderName, AccountType accountType) {
        this.id = id;
        this.accountHolderName = accountHolderName;
        this.accountHolderEmail = accountHolderName;
        this.accountHolderPhone = accountHolderName;
        this.accountType = accountType;
    }

    // For Response
    public Account(Long id, String accountHolderName, AccountType accountType, Boolean isActive) {
        this.id = id;
        this.accountHolderName = accountHolderName;
        this.accountHolderEmail = accountHolderName;
        this.accountHolderPhone = accountHolderName;
        this.accountType = accountType;
        this.isActive = isActive;
    }

    // Convert Entity to Response DTO
    public AccountResponseDto toResponseDto() {
        return new AccountResponseDto(id, accountType, accountNumber, accountHolderName, accountHolderEmail, accountHolderPhone, isActive, balance);
    }
}
