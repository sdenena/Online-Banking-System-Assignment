package com.sd.onlinebankingsystemassignment.services.implementations;

import com.sd.onlinebankingsystemassignment.dto.bank_operation.AccountHistoryDto;
import com.sd.onlinebankingsystemassignment.dto.bank_operation.AccountHistoryResponseDto;
import com.sd.onlinebankingsystemassignment.models.AccountHistory;
import com.sd.onlinebankingsystemassignment.models.enums.TransactionStatus;
import com.sd.onlinebankingsystemassignment.repositories.AccountHistoryRepository;
import com.sd.onlinebankingsystemassignment.services.AccountHistoryService;
import com.sd.onlinebankingsystemassignment.utils.Generator;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
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

    @Override
    public Page<AccountHistoryResponseDto> getAccountHistoryByAccountNumber(String accountNumber, int page, int size) {
        Page<AccountHistory> historyPage = accountHistoryRepository.findByAccountNumber(
                accountNumber,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "tranDate"))
        );

        return historyPage.map(AccountHistory::toResponseDto);
    }
}
