package ru.skillbox.socialnetwork.account.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.account.dto.AccountDto;
import ru.skillbox.socialnetwork.account.dto.AccountByFilterDTO;
import ru.skillbox.socialnetwork.account.mapper.AccountMapper;
import ru.skillbox.socialnetwork.account.model.Account;
import ru.skillbox.socialnetwork.account.repository.AccountRepository;
import ru.skillbox.socialnetwork.account.repository.AccountSpecification;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final EntityManager entityManager;

    @Override
    public boolean blockedAccountById(UUID id) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null)
            return false;

        account.setIsBlocked(true);
        accountRepository.save(account);
        return true;
    }

    @Override
    public boolean unblockAccount(UUID id) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null)
            return false;

        account.setIsBlocked(false);
        accountRepository.save(account);
        return true;
    }

    @Override
    public Page<AccountDto> getAllAccounts(Pageable pageable) {
        return accountRepository.findAll(pageable)
                .map(accountMapper::toDto);
    }

    @Override
    @Transactional
    public AccountDto createAccount(AccountDto dto) {
        Account account = accountMapper.toEntity(dto);

        if (accountRepository.existsById(account.getId()))
            return null;

        entityManager.persist(account);
        return accountMapper.toDto(account);
    }

    @Override
    public List<AccountDto> getAccountByIds(List<UUID> ids) {
        List<Account> accounts = accountRepository.findAllById(ids);
        return accountMapper.toDto(accounts);
    }

    @Override
    public AccountDto getAccountById(UUID id) {
        Account findingAccount = accountRepository.findById(id).orElse(null);
        return accountMapper.toDto(findingAccount);
    }

    @Override
    public Page<AccountDto> getAllAccountByIds(List<UUID> ids, Pageable pageable) {
        return accountRepository.findAllByIdIn(ids, pageable)
                .map(accountMapper::toDto);
    }

    @Override
    public Page<AccountDto> searchAccounts(AccountByFilterDTO request, Pageable pageable) {
        Specification<Account> specification = AccountSpecification.byRequest(request);
        return accountRepository.findAll(specification, pageable)
                .map(accountMapper::toDto);
    }
}
