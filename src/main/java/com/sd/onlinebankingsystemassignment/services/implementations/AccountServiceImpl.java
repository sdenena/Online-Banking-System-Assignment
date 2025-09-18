package com.sd.onlinebankingsystemassignment.services.implementations;

import com.sd.onlinebankingsystemassignment.dto.AccountCreateDto;
import com.sd.onlinebankingsystemassignment.dto.AccountResponseDto;
import com.sd.onlinebankingsystemassignment.dto.AccountUpdateDto;
import com.sd.onlinebankingsystemassignment.exception.CustomException;
import com.sd.onlinebankingsystemassignment.models.Account;
import com.sd.onlinebankingsystemassignment.repositories.AccountRepository;
import com.sd.onlinebankingsystemassignment.services.AccountService;
import com.sd.onlinebankingsystemassignment.utils.Generator;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Objects;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Transactional
    @Override public AccountResponseDto createAccount(AccountCreateDto req) {
        logger.info("createAccount: {}", req);
        Account account = req.toAccount();
        account.setAccountNumber(Generator.generateAccountNumber(accountRepository.findTop() + 1));
        return accountRepository.save(account).toResponseDto();
    }

    @Transactional
    @Override public AccountResponseDto updateAccount(Long id, AccountUpdateDto req) {
        logger.info("updateAccount - id: {}, req: {}", id, req.toString());
        Account accountObj = getAccountDetail(id).toAccount();
        Account updatedAccount = req.updateAccount(accountObj);
        return accountRepository.save(updatedAccount).toResponseDto();
    }

    @Override
    public AccountResponseDto getAccountDetail(Long id) {
        logger.info("getAccountDetail - id: {}", id);
        return accountRepository.findByIdAndStatusTrue(id).orElseThrow(() -> new CustomException(404, "Account not found")).toResponseDto();
    }

    @Override
    public Page<AccountResponseDto> getAccountList(String query, int page, int size) {
        logger.info("getAccountList - query: {}, page: {}, size: {}", query, page, size);

        Page<Account> accountPage = accountRepository.findAll((root, cq, cb) -> {
            ArrayList<Predicate> predicates = new ArrayList<>();

            if (query != null && !query.trim().isEmpty()) {
                var searchAccountName = cb.like(cb.upper(root.get("accountHolderName")), "%" + query.toUpperCase() + "%");
                var searchAccountNum = cb.like(cb.upper(root.get("accountNumber")), "%" + query.toUpperCase() + "%");
                predicates.add(cb.or(searchAccountName, searchAccountNum));
            }

            // Fixed: changed "status" to "isActive" to match the entity field
            predicates.add(cb.isTrue(root.get("isActive")));
            Objects.requireNonNull(cq).orderBy(cb.desc(root.get("id")));
            return cb.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(page, size));

        // Convert Page<Account> to Page<AccountResponseDto>
        return accountPage.map(Account::toResponseDto);
    }

    @Override
    public void deleteAccount(Long id) {
        Account accountObj = getAccountDetail(id).toAccount();
        accountObj.setStatus(false);

        accountRepository.save(accountObj);
    }
}
