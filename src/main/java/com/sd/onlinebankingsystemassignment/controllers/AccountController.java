package com.sd.onlinebankingsystemassignment.controllers;

import com.sd.onlinebankingsystemassignment.aop.AuditFilter;
import com.sd.onlinebankingsystemassignment.base.response.ResponseMessage;
import com.sd.onlinebankingsystemassignment.base.response.ResponseObj;
import com.sd.onlinebankingsystemassignment.base.response.ResponsePage;
import com.sd.onlinebankingsystemassignment.dto.account.AccountCreateDto;
import com.sd.onlinebankingsystemassignment.dto.account.AccountResponseDto;
import com.sd.onlinebankingsystemassignment.dto.account.AccountUpdateDto;
import com.sd.onlinebankingsystemassignment.dto.bank_operation.DepositWithdrawDto;
import com.sd.onlinebankingsystemassignment.dto.bank_operation.DepositWithdrawResponseDto;
import com.sd.onlinebankingsystemassignment.repositories.AccountRepository;
import com.sd.onlinebankingsystemassignment.services.AccountService;
import com.sd.onlinebankingsystemassignment.services.BankOperationService;
import com.sd.onlinebankingsystemassignment.utils.Constant;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.MAIN_PATH + "/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final BankOperationService bankOperationService;

    @AuditFilter()
    @PostMapping
    public ResponseObj<AccountResponseDto> createAccount(@Valid @RequestBody AccountCreateDto accountCreateDto) {
        return new ResponseObj<>(accountService.createAccount(accountCreateDto));
    }

    @AuditFilter()
    @GetMapping("/{id}")
    public ResponseObj<AccountResponseDto> getAccountById(@PathVariable Long id) {
        return new ResponseObj<>(accountService.getAccountDetail(id));
    }

    @AuditFilter()
    @PutMapping("/{id}")
    public ResponseObj<AccountResponseDto> updateAccount(@PathVariable Long id, @Valid @RequestBody AccountUpdateDto accountUpdateDto) {
        return new ResponseObj<>(accountService.updateAccount(id, accountUpdateDto));
    }

    @AuditFilter()
    @DeleteMapping("/{id}")
    public ResponseMessage deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return new ResponseMessage();
    }


    @AuditFilter()
    @GetMapping("/list")
    public ResponsePage<AccountResponseDto> getAccountListPage(
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        var listPage = accountService.getAccountList(query, page, size);
        // Since getAccountList already returns Page<AccountResponseDto>, no conversion needed
        return new ResponsePage<>(listPage.getContent(), listPage.getTotalElements());
    }

    @AuditFilter()
    @PostMapping("/deposit")
    public ResponseObj<DepositWithdrawResponseDto> deposit(@Valid @RequestBody DepositWithdrawDto req) {
        return new ResponseObj<>(bankOperationService.depositFunds(req));
    }
}
