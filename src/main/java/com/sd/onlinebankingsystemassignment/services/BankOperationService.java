package com.sd.onlinebankingsystemassignment.services;

import com.sd.onlinebankingsystemassignment.dto.bank_operation.DepositWithdrawDto;
import com.sd.onlinebankingsystemassignment.dto.bank_operation.DepositWithdrawResponseDto;
import com.sd.onlinebankingsystemassignment.dto.bank_operation.TransferDto;
import com.sd.onlinebankingsystemassignment.dto.bank_operation.TransferResponseDto;

public interface BankOperationService {
    TransferResponseDto transferFunds(TransferDto transferDto);

    DepositWithdrawResponseDto depositFunds(DepositWithdrawDto depositWithdrawDto);

    DepositWithdrawResponseDto withdrawFunds(DepositWithdrawDto depositWithdrawDto);
}
