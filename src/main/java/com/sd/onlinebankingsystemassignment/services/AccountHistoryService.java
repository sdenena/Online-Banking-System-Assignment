package com.sd.onlinebankingsystemassignment.services;

import com.sd.onlinebankingsystemassignment.dto.bank_operation.AccountHistoryDto;
import com.sd.onlinebankingsystemassignment.models.AccountHistory;

public interface AccountHistoryService {
    AccountHistoryDto createAccountHistory(AccountHistoryDto req);
}
