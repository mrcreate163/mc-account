package ru.skillbox.socialnetwork.account.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.account.client.auth.UserDataDetails;
import ru.skillbox.socialnetwork.account.dto.kafka.UserRegisteredEvent;
import ru.skillbox.socialnetwork.account.dto.request.AccountByFilterDto;
import ru.skillbox.socialnetwork.account.dto.request.AccountSearchDto;
import ru.skillbox.socialnetwork.account.dto.request.CreatedAccountRequest;
import ru.skillbox.socialnetwork.account.dto.response.AccountDto;
import ru.skillbox.socialnetwork.account.exception.AccountException;
import ru.skillbox.socialnetwork.account.exception.GeneralException;
import ru.skillbox.socialnetwork.account.mapper.AccountMapper;
import ru.skillbox.socialnetwork.account.model.Account;
import ru.skillbox.socialnetwork.account.repository.AccountRepository;
import ru.skillbox.socialnetwork.account.repository.AccountSpecification;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public AccountDto updateAccount(AccountDto dto) {
        Account account = accountMapper.toEntity(dto);
        Account savedAccount = accountRepository.save(account);
        return accountMapper.toDto(savedAccount);
    }

    @Override
    public String deleteAccount(UUID userId) {
        Account account = accountRepository.findById(userId)
                .orElse(null);

        if (account == null)
            throw new AccountException("Аккаунт с таким id не найден!");

        account.setIsDeleted(true);
        accountRepository.save(account);

        return "Аккаунт успешно удален";
    }

    @Override
    public void blockedAccountById(UUID id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Аккаунт с таким id={} не найден!", id);
                    return new AccountException("Аккаунт не найден!");
                });

        if (account.getIsBlocked()) {
            log.info("Аккаунт с id={} уже заблокирован", id);
            throw new AccountException("Аккаунт уже заблокирован!");
        }

        account.setIsBlocked(true);
        accountRepository.save(account);
    }

    @Override
    public void unblockedAccountById(UUID id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Аккаунт с таким id={} не найден!", id);
                    return new AccountException("Аккаунт не найден!");
                });

        if (!account.getIsBlocked()) {
            log.info("Аккаунт с id={} не заблокирован", id);
            throw new AccountException("Аккаунт не заблокирован!");
        }

        account.setIsBlocked(false);
        accountRepository.save(account);
    }

    @Override
    public Page<AccountDto> getAllAccounts(Pageable pageable) {
        return accountRepository.findAll(pageable)
                .map(accountMapper::toDto);
    }

    @Override
    @Transactional
    public AccountDto createAccount(CreatedAccountRequest dto, UserDataDetails user) {
        Account account = accountMapper.toEntity(dto);
        account.setId(user.getUserId());
        account.setEmail(user.getEmail());

        if (accountRepository.existsById(user.getUserId()))
            throw new AccountException("Аккаунт не найден!");

        accountRepository.save(account);
        return accountMapper.toDto(account);
    }

    @Override
    public Page<AccountDto> searchAccountsByFilter(AccountByFilterDto request) {
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize());
        Specification<Account> specification = AccountSpecification.byRequest(request.getAccountSearchDto());
        return accountRepository.findAll(specification, pageable)
                .map(accountMapper::toDto);
    }

    @Override
    public List<AccountDto> getAccountByIds(List<UUID> ids) {
        List<Account> accounts = accountRepository.findAllById(ids);
        return accountMapper.toDto(accounts);
    }

    @Override
    public AccountDto getAccountById(UUID id) {
        Account account = accountRepository.findById(id)
                .orElse(null);

        if (account == null)
            throw new AccountException("Аккаунт с таким id не найден!");

        return accountMapper.toDto(account);
    }

    @Override
    public Page<AccountDto> searchAccountsByFilter(AccountSearchDto request, Pageable pageable) {
        Specification<Account> specification = AccountSpecification.byRequest(request);
        return accountRepository.findAll(specification, pageable)
                .map(accountMapper::toDto);
    }

    @Override
    public Page<AccountDto> getAllAccountByIds(List<UUID> ids, Pageable pageable) {
        return accountRepository.findAllByIdIn(ids, pageable)
                .map(accountMapper::toDto);
    }

    @Override
    @Transactional
    public void createAccountAnEvent(UserRegisteredEvent event) {
        if (event == null)
            throw new GeneralException("При регистрации пользователя произошла ошибка!");

        if (accountRepository.existsById(event.getUserId()))
            throw new AccountException("Аккаунт уже существует!");

        accountRepository.save(accountMapper.toEntity(event));
    }
}
