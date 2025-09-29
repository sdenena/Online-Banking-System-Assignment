package com.sd.onlinebankingsystemassignment.services.implementations;

import com.sd.onlinebankingsystemassignment.dto.bank_operation.AccountHistoryDto;
import com.sd.onlinebankingsystemassignment.models.AccountHistory;
import com.sd.onlinebankingsystemassignment.models.enums.TransactionStatus;
import com.sd.onlinebankingsystemassignment.repositories.AccountHistoryRepository;
import com.sd.onlinebankingsystemassignment.services.AccountHistoryService;
import com.sd.onlinebankingsystemassignment.utils.Generator;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class AccountHistoryServiceImpl implements AccountHistoryService {
    private final AccountHistoryRepository accountHistoryRepository;

    @Override
    public AccountHistoryDto createAccountHistory(AccountHistoryDto req) {
        AccountHistory accountHistory = new AccountHistory();

        BeanUtils.copyProperties(req, accountHistory);

        accountHistory.setTransactionStatus(TransactionStatus.COMPLETED);
        accountHistory.setTranId(Generator.generateTranId(req.getTransactionType(), 4));

        accountHistoryRepository.save(accountHistory);

        return req;
    }
}
