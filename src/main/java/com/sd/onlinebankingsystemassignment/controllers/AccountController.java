package com.sd.onlinebankingsystemassignment.controllers;

import com.sd.onlinebankingsystemassignment.aop.AuditFilter;
import com.sd.onlinebankingsystemassignment.base.response.ResponseMessage;
import com.sd.onlinebankingsystemassignment.base.response.ResponseObj;
import com.sd.onlinebankingsystemassignment.base.response.ResponsePage;
import com.sd.onlinebankingsystemassignment.dto.account.AccountCreateDto;
import com.sd.onlinebankingsystemassignment.dto.account.AccountResponseDto;
import com.sd.onlinebankingsystemassignment.dto.account.AccountUpdateDto;
import com.sd.onlinebankingsystemassignment.dto.bank_operation.*;
import com.sd.onlinebankingsystemassignment.repositories.AccountRepository;
import com.sd.onlinebankingsystemassignment.services.AccountHistoryService;
import com.sd.onlinebankingsystemassignment.services.AccountService;
import com.sd.onlinebankingsystemassignment.services.BankOperationService;
import com.sd.onlinebankingsystemassignment.utils.Constant;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.MAIN_PATH + "/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final BankOperationService bankOperationService;
    private final AccountHistoryService accountHistoryService;

    @AuditFilter()
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CREATE_ACCOUNT')")
    public ResponseObj<AccountResponseDto> createAccount(@Valid @RequestBody AccountCreateDto accountCreateDto) {
        return new ResponseObj<>(accountService.createAccount(accountCreateDto));
    }

    @AuditFilter()
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'VIEW_ALL_ACCOUNTS')")
    public ResponseObj<AccountResponseDto> getAccountById(@PathVariable Long id) {
        return new ResponseObj<>(accountService.getAccountDetail(id));
    }

    @AuditFilter()
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'UPDATE_USER')")
    public ResponseObj<AccountResponseDto> updateAccount(@PathVariable Long id, @Valid @RequestBody AccountUpdateDto accountUpdateDto) {
        return new ResponseObj<>(accountService.updateAccount(id, accountUpdateDto));
    }

    @AuditFilter()
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'UPDATE_USER')")
    public ResponseMessage deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return new ResponseMessage();
    }


    @AuditFilter()
    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'VIEW_ALL_ACCOUNTS')")
    public ResponsePage<AccountResponseDto> getAccountListPage(
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        var listPage = accountService.getAccountList(query, page, size);

        return new ResponsePage<>(listPage.getContent(), listPage.getTotalElements());
    }

    @AuditFilter()
    @PostMapping("/deposit")
    public ResponseObj<DepositWithdrawResponseDto> deposit(@Valid @RequestBody DepositWithdrawDto req) {
        return new ResponseObj<>(bankOperationService.depositFunds(req));
    }

    @AuditFilter()
    @PostMapping("/withdraw")
    public ResponseObj<DepositWithdrawResponseDto> withdraw(@Valid @RequestBody DepositWithdrawDto req) {
        return new ResponseObj<>(bankOperationService.withdrawFunds(req));
    }

    @AuditFilter()
    @PostMapping("/transfer")
    public ResponseObj<TransferResponseDto> transfer(@Valid @RequestBody TransferDto req) {
        return new ResponseObj<>(bankOperationService.transferFunds(req));
    }

    @AuditFilter
    @GetMapping("/history")
    public ResponsePage<AccountHistoryResponseDto> getAccountHistoryByAccountNumber(
            @RequestParam String accountNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        var listPage = accountHistoryService.getAccountHistoryByAccountNumber(accountNumber, page, size);
        return new ResponsePage<>(listPage.getContent(), listPage.getTotalElements());
    }
}
