package ru.skillbox.socialnetwork.account.service.telegram;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.account.dto.telegram.response.AccountTelegramResponse;
import ru.skillbox.socialnetwork.account.dto.telegram.response.PageAccountDto;
import ru.skillbox.socialnetwork.account.exception.AccountException;
import ru.skillbox.socialnetwork.account.mapper.AccountMapper;
import ru.skillbox.socialnetwork.account.model.Account;
import ru.skillbox.socialnetwork.account.repository.AccountRepository;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class TelegramServiceImpl implements TelegramService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public AccountTelegramResponse getAccountById(UUID id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Аккаунт с таким id={} не найден!", id);
                    return new AccountException("Аккаунт не найден!");
                });

        return accountMapper.toTelegramDto(account);
    }

    @Override
    public PageAccountDto getAccounts(Pageable pageable) {
        Page<Account> accountPage = accountRepository.findAll(pageable);

        return new PageAccountDto(
                accountPage.getTotalElements(),
                accountPage.getTotalPages(),
                accountPage.getSize(),
                accountMapper.toTelegramDto(accountPage.getContent()),
                accountPage.getNumber(),
                accountPage.isFirst(),
                accountPage.isLast(),
                accountPage.isEmpty()
        );
    }

    @Override
    public PageAccountDto searchAccountsByEmail(String email, Pageable pageable) {
        Page<Account> accountPage = accountRepository.findByEmailContainingIgnoreCase(email, pageable);

        return new PageAccountDto(
                accountPage.getTotalElements(),
                accountPage.getTotalPages(),
                accountPage.getSize(),
                accountMapper.toTelegramDto(accountPage.getContent()),
                accountPage.getNumber(),
                accountPage.isFirst(),
                accountPage.isLast(),
                accountPage.isEmpty()
        );
    }
}

