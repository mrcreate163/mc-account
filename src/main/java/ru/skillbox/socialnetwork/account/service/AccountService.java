package ru.skillbox.socialnetwork.account.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.account.dto.AccountDTO;
import ru.skillbox.socialnetwork.account.dto.AccountSearchRequest;
import ru.skillbox.socialnetwork.account.mapper.AccountMapper;
import ru.skillbox.socialnetwork.account.model.Account;
import ru.skillbox.socialnetwork.account.repository.AccountRepository;
import ru.skillbox.socialnetwork.account.repository.AccountSpecification;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountService(
            AccountRepository accountRepository,
            AccountMapper accountMapper
    ) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    public AccountDTO findAccountById(UUID id) {
        Account findingAccount = accountRepository.findById(id).orElse(null);
        return accountMapper.toDto(findingAccount);
    }

    public Page<AccountDTO> getAccountsByIds(List<UUID> ids, Pageable pageable) {
        return accountRepository.findAllByIdIn(ids, pageable)
                .map(accountMapper::toDto);
    }

    public Page<AccountDTO> getAccountsByParams(AccountSearchRequest request, Pageable pageable) {
        Specification<Account> specification = AccountSpecification.byRequest(request);
        return accountRepository.findAll(specification, pageable)
                .map(accountMapper::toDto);
    }
}
