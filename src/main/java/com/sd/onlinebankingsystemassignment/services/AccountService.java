package com.sd.onlinebankingsystemassignment.services;

import com.sd.onlinebankingsystemassignment.dto.AccountCreateDto;
import com.sd.onlinebankingsystemassignment.dto.AccountResponseDto;
import com.sd.onlinebankingsystemassignment.dto.AccountUpdateDto;
import org.springframework.data.domain.Page;

public interface AccountService {
    AccountResponseDto createAccount(AccountCreateDto req);
    AccountResponseDto updateAccount(Long id, AccountUpdateDto req);
    AccountResponseDto getAccountDetail(Long id);
    Page<AccountResponseDto> getAccountList(String query, int page, int size);
    void deleteAccount(Long id);
}
