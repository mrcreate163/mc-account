package ru.skillbox.socialnetwork.account.service;

import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.account.dto.AccountDTO;
import ru.skillbox.socialnetwork.account.repository.AccountRepository;

import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountDTO findAccountById(UUID id) {
        return accountRepository.findById(id).orElse(null);
    }
}
