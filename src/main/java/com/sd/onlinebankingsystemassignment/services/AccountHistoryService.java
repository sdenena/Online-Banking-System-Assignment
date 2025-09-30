package com.sd.onlinebankingsystemassignment.services;

import com.sd.onlinebankingsystemassignment.dto.bank_operation.AccountHistoryDto;
import com.sd.onlinebankingsystemassignment.dto.bank_operation.AccountHistoryResponseDto;
import com.sd.onlinebankingsystemassignment.models.AccountHistory;
import org.springframework.data.domain.Page;

public interface AccountHistoryService {
    AccountHistoryDto createAccountHistory(AccountHistoryDto req);
    Page<AccountHistoryResponseDto> getAccountHistoryByAccountNumber(String accountNumber, int page, int size);
}
