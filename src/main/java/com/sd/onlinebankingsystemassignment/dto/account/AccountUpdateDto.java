package com.sd.onlinebankingsystemassignment.dto.account;

import com.sd.onlinebankingsystemassignment.models.Account;
import com.sd.onlinebankingsystemassignment.models.enums.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountUpdateDto {
    private Long id = null;

    @NotBlank(message = "Account holder name is required")
    private String accountHolderName = null;

    @NotNull(message = "Account type is required")
    private AccountType accountType;

    private String accountHolderEmail;

    @NotNull(message = "Phone number is required")
    private String accountHolderPhone;

    Boolean isActive = true;

    public Account updateAccount(Account account) {
        account.setAccountHolderName(accountHolderName);
        account.setAccountHolderEmail(accountHolderEmail);
        account.setAccountHolderPhone(accountHolderPhone);
        account.setAccountType(accountType);
        account.setIsActive(isActive);
        return account;
    }
}
