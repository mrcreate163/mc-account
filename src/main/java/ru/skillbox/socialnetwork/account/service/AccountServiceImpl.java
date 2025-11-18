package ru.skillbox.socialnetwork.account.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.account.dto.AccountDTO;
import ru.skillbox.socialnetwork.account.dto.AccountByFilterDTO;
import ru.skillbox.socialnetwork.account.mapper.AccountMapper;
import ru.skillbox.socialnetwork.account.model.Account;
import ru.skillbox.socialnetwork.account.repository.AccountRepository;
import ru.skillbox.socialnetwork.account.repository.AccountSpecification;

import java.util.List;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final EntityManager entityManager;

    public AccountServiceImpl(
            AccountRepository accountRepository,
            AccountMapper accountMapper,
            EntityManager entityManager
    ) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.entityManager = entityManager;
    }

    @Override
    public Page<AccountDTO> getAllAccounts(Pageable pageable) {
        return accountRepository.findAll(pageable)
                .map(accountMapper::toDto);
    }

    @Override
    @Transactional
    public AccountDTO createAccount(AccountDTO dto) {
        Account account = accountMapper.toEntity(dto);

        if (accountRepository.existsById(account.getId()))
            return null;

        entityManager.persist(account);
        return accountMapper.toDto(account);
    }

    @Override
    public List<AccountDTO> getAccountByIds(List<UUID> ids) {
        List<Account> accounts = accountRepository.findAllById(ids);
        return accountMapper.toDto(accounts);
    }

    @Override
    public AccountDTO getAccountById(UUID id) {
        Account findingAccount = accountRepository.findById(id).orElse(null);
        return accountMapper.toDto(findingAccount);
    }

    @Override
    public Page<AccountDTO> getAllAccountByIds(List<UUID> ids, Pageable pageable) {
        return accountRepository.findAllByIdIn(ids, pageable)
                .map(accountMapper::toDto);
    }

    @Override
    public Page<AccountDTO> searchAccounts(AccountByFilterDTO request, Pageable pageable) {
        Specification<Account> specification = AccountSpecification.byRequest(request);
        return accountRepository.findAll(specification, pageable)
                .map(accountMapper::toDto);
    }
}
