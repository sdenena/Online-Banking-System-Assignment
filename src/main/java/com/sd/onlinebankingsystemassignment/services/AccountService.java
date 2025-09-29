package com.sd.onlinebankingsystemassignment.services;

import com.sd.onlinebankingsystemassignment.dto.account.AccountCreateDto;
import com.sd.onlinebankingsystemassignment.dto.account.AccountResponseDto;
import com.sd.onlinebankingsystemassignment.dto.account.AccountUpdateDto;
import com.sd.onlinebankingsystemassignment.models.Account;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public interface AccountService {
    AccountResponseDto createAccount(AccountCreateDto req);
    AccountResponseDto updateAccount(Long id, AccountUpdateDto req);
    AccountResponseDto getAccountDetail(Long id);
    Page<AccountResponseDto> getAccountList(String query, int page, int size);
    void deleteAccount(Long id);
    Account getAccountDetailByAccountNumber(String accountNumber);
    void updateAccountBalance(Account account, BigDecimal newBalance);
}
