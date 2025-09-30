package com.sd.onlinebankingsystemassignment.services.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sd.onlinebankingsystemassignment.dto.bank_operation.*;
import com.sd.onlinebankingsystemassignment.exception.CustomException;
import com.sd.onlinebankingsystemassignment.models.Account;
import com.sd.onlinebankingsystemassignment.models.ExchangeRate;
import com.sd.onlinebankingsystemassignment.models.enums.TransactionDirection;
import com.sd.onlinebankingsystemassignment.models.enums.TransactionStatus;
import com.sd.onlinebankingsystemassignment.models.enums.TransactionType;
import com.sd.onlinebankingsystemassignment.services.AccountHistoryService;
import com.sd.onlinebankingsystemassignment.services.AccountService;
import com.sd.onlinebankingsystemassignment.services.BankOperationService;
import com.sd.onlinebankingsystemassignment.services.ExchangeRateService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.Date;

@Service
@AllArgsConstructor
public class BankOperationServiceImpl implements BankOperationService {
    private final Logger logger = LoggerFactory.getLogger(BankOperationServiceImpl.class);

    private final AccountService accountService;
    private final AccountHistoryService accountHistoryService;
    private final ExchangeRateService exchangeRateService;

    @Override
    public TransferResponseDto transferFunds(TransferDto transferDto) {
        logger.info("depositFunds: {}", transferDto.toString());

        // Sender account operations
        Account senderAccount = accountService.getAccountDetailByAccountNumber(transferDto.getFromAccountNumber());

        // Receiver account operations
        Account receiverAccount = accountService.getAccountDetailByAccountNumber(transferDto.getToAccountNumber());

        if (senderAccount.getBalance().compareTo(transferDto.getAmount()) < 0) {
            throw new CustomException(402, "Insufficient balance");
        }

        // Exchange rate
        ExchangeRate exchangeRate = exchangeRateService.getExchangeRate(senderAccount.getCurrency(), receiverAccount.getCurrency());
        BigDecimal amount = exchangeRate.getMethod().cal(transferDto.getAmount(), exchangeRate.getExchangeRate());


        // Deduct from sender
        BigDecimal newSenderBalance = senderAccount.getBalance().subtract(transferDto.getAmount());
        senderAccount.setBalance(newSenderBalance);
        accountService.updateAccountBalance(senderAccount);

        // Add to receiver
        BigDecimal newReceiverBalance = receiverAccount.getBalance().add(amount);
        receiverAccount.setBalance(newReceiverBalance);
        accountService.updateAccountBalance(receiverAccount);

        // Create account history for sender
        AccountHistoryDto senderHistoryDto = new AccountHistoryDto();
        senderHistoryDto.setFromAccountNumber(transferDto.getFromAccountNumber());
        senderHistoryDto.setToAccountNumber(transferDto.getToAccountNumber());
        senderHistoryDto.setAmount(transferDto.getAmount());
        senderHistoryDto.setTransactionType(TransactionType.TRANSFER);
        senderHistoryDto.setTransactionStatus(TransactionStatus.COMPLETED);
        senderHistoryDto.setTranDate(new Date());
        senderHistoryDto.setCurrency(senderAccount.getCurrency());
        senderHistoryDto.setDirection(TransactionDirection.DEBIT);
        AccountHistoryDto savedSenderHistory = accountHistoryService.createAccountHistory(senderHistoryDto);

        // Create account history for receiver
        AccountHistoryDto receiverHistoryDto = new AccountHistoryDto();
        receiverHistoryDto.setFromAccountNumber(transferDto.getFromAccountNumber());
        receiverHistoryDto.setToAccountNumber(transferDto.getToAccountNumber());
        receiverHistoryDto.setAmount(amount);
        receiverHistoryDto.setTransactionType(TransactionType.TRANSFER);
        receiverHistoryDto.setTransactionStatus(TransactionStatus.COMPLETED);
        receiverHistoryDto.setTranDate(new Date());
        receiverHistoryDto.setCurrency(receiverAccount.getCurrency());
        receiverHistoryDto.setDirection(TransactionDirection.CREDIT);
        accountHistoryService.createAccountHistory(receiverHistoryDto);

        // Create response DTO properly
        TransferResponseDto responseDto = new TransferResponseDto();
        BeanUtils.copyProperties(savedSenderHistory, responseDto);
        responseDto.setFromAccountNumber(savedSenderHistory.getFromAccountNumber());
        responseDto.setToAccountNumber(savedSenderHistory.getToAccountNumber());

        return responseDto;
    }

    @Override
    @Transactional
    public DepositWithdrawResponseDto depositFunds(DepositWithdrawDto depositWithdrawDto) {
        logger.info("depositFunds: {}", depositWithdrawDto.toString());
        // Fetch account details
        Account account = accountService.getAccountDetailByAccountNumber(depositWithdrawDto.getAccountNumber());

        // Update balance
        BigDecimal newBalance = account.getBalance().add(depositWithdrawDto.getAmount());
        account.setBalance(newBalance);

        // Save changes to database - THIS IS CRITICAL
        accountService.updateAccountBalance(account);

        // Create account history
        AccountHistoryDto accountHistoryDto = new AccountHistoryDto();
        accountHistoryDto.setToAccountNumber(depositWithdrawDto.getAccountNumber());
        accountHistoryDto.setAmount(depositWithdrawDto.getAmount());
        accountHistoryDto.setTransactionType(TransactionType.DEPOSIT);
        accountHistoryDto.setTransactionStatus(TransactionStatus.COMPLETED);
        accountHistoryDto.setCurrency(account.getCurrency());
        accountHistoryDto.setDirection(TransactionDirection.CREDIT);
        accountHistoryDto.setTranDate(new Date());

        AccountHistoryDto savedHistory = accountHistoryService.createAccountHistory(accountHistoryDto);

        // Create response DTO properly
        DepositWithdrawResponseDto responseDto = new DepositWithdrawResponseDto();
        BeanUtils.copyProperties(savedHistory, responseDto);
        responseDto.setAccountNumber(savedHistory.getToAccountNumber());

        return responseDto;
    }

    @Override
    public DepositWithdrawResponseDto withdrawFunds(DepositWithdrawDto depositWithdrawDto) {
        // Fetch account details
        Account account = accountService.getAccountDetailByAccountNumber(depositWithdrawDto.getAccountNumber());

        if (account.getBalance().compareTo(depositWithdrawDto.getAmount()) < 0) {
            throw new CustomException(402, "Insufficient balance");
        }

        // Update balance
        BigDecimal newBalance = account.getBalance().subtract(depositWithdrawDto.getAmount());
        account.setBalance(newBalance);

        // Save changes to database - THIS IS CRITICAL
        accountService.updateAccountBalance(account);

        // Create account history
        AccountHistoryDto accountHistoryDto = new AccountHistoryDto();
        accountHistoryDto.setFromAccountNumber(depositWithdrawDto.getAccountNumber());
        accountHistoryDto.setAmount(depositWithdrawDto.getAmount());
        accountHistoryDto.setTransactionType(TransactionType.WITHDRAWAL);
        accountHistoryDto.setTransactionStatus(TransactionStatus.COMPLETED);
        accountHistoryDto.setDirection(TransactionDirection.DEBIT);
        accountHistoryDto.setCurrency(account.getCurrency());

        AccountHistoryDto savedHistory = accountHistoryService.createAccountHistory(accountHistoryDto);

        // Create response DTO properly
        DepositWithdrawResponseDto responseDto = new DepositWithdrawResponseDto();
        BeanUtils.copyProperties(savedHistory, responseDto);
        responseDto.setAccountNumber(savedHistory.getFromAccountNumber());

        return responseDto;
    }
}
