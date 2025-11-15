package ru.skillbox.socialnetwork.account.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.account.dto.AccountDTO;
import ru.skillbox.socialnetwork.account.mapper.AccountMapper;
import ru.skillbox.socialnetwork.account.model.Account;
import ru.skillbox.socialnetwork.account.repository.AccountRepository;

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

}
